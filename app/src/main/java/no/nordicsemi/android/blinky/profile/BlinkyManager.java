/*
 * Copyright (c) 2018, Nordic Semiconductor
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products derived from this
 * software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 * ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE
 * USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package no.nordicsemi.android.blinky.profile;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattService;
import android.content.Context;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.UUID;

import no.nordicsemi.android.ble.BleManager;
import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.blinky.profile.callback.RxDataCallback;
import no.nordicsemi.android.blinky.profile.callback.TXDataCallback;
import no.nordicsemi.android.blinky.profile.data.BlinkDoorlock;
import no.nordicsemi.android.blinky.viewmodels.ByteUtil;
import no.nordicsemi.android.blinky.viewmodels.DoorlockUtil;
import no.nordicsemi.android.log.LogContract;
import no.nordicsemi.android.log.LogSession;
import no.nordicsemi.android.log.Logger;

public class BlinkyManager extends BleManager<BlinkyManagerCallbacks> {

	public final static UUID UUID_MAIN_SERVICE = UUID.fromString("48400001-B5A3-F393-E0A9-E50E24DCCA9E");
	private final static UUID UUID_RX = UUID.fromString("48400002-B5A3-F393-E0A9-E50E24DCCA9E");
	private final static UUID UUID_TX = UUID.fromString("48400003-B5A3-F393-E0A9-E50E24DCCA9E");

	private BluetoothGattCharacteristic mTxCharacteristic, mRxCharacteristic;
	private LogSession mLogSession;
	private boolean mSupported;

	public BlinkyManager(@NonNull final Context context) {
		super(context);
	}



	@NonNull
	@Override
	protected BleManagerGattCallback getGattCallback() {
		return mGattCallback;
	}

	/**
	 * Sets the log session to be used for low level logging.
	 * @param session the session, or null, if nRF Logger is not installed.
	 */
	public void setLogger(@Nullable final LogSession session) {
		this.mLogSession = session;
	}

	@Override
	public void log(final int priority, @NonNull final String message) {
		// The priority is a Log.X constant, while the Logger accepts it's log levels.
		Logger.log(mLogSession, LogContract.Log.Level.fromPriority(priority), message);
		Log.d("plusapps", message);
	}

	@Override
	protected boolean shouldClearCacheWhenDisconnected() {
		return !mSupported;
	}




	private final TXDataCallback mTxDataCallback = new TXDataCallback() {
		@Override
		public void onDoorlockStateChanged(@NonNull final BluetoothDevice device,
										   final boolean on) {
		    mCallbacks.onDoorlockStateChanged(device, on);
		}

		@Override
		public void onInvalidDataReceived(@NonNull final BluetoothDevice device,
										  @NonNull final Data data) {
			// Data can only invalid if we read them. We assume the app always sends correct data.
			Log.d("plusapps" , "TXDataCallback onInvalidDataReceived: " +  ByteUtil.mf_str_toHexString(data.getValue()));

		}
	};



	private final RxDataCallback mRxDataCallback = new RxDataCallback() {
		@Override
		public void onDataSent(@NonNull BluetoothDevice device, @NonNull Data data) {
			Log.d("plusapps" , "RxDataCallback onDataSent: " +  ByteUtil.mf_str_toHexString(data.getValue()));

		}

		@Override
		public void onButtonStateChanged(@NonNull BluetoothDevice device, boolean pressed) {

		}
	};






	/**
	 * BluetoothGatt callbacks object.
	 */
	private final BleManagerGattCallback mGattCallback = new BleManagerGattCallback() {
		@Override
		protected void initialize() {
			//descriptor를 가지고 있는 mTxCharacteristic에 setNotificationCallback, enableNotifications 호출
			//readCharacteristic은 mTxCharacteristic/mRxCharacteristic 둘다 호출
			setNotificationCallback(mTxCharacteristic).with(mTxDataCallback);
			readCharacteristic(mRxCharacteristic).with(mRxDataCallback).enqueue();
			readCharacteristic(mTxCharacteristic).with(mTxDataCallback).enqueue();
			enableNotifications(mTxCharacteristic).enqueue();
		}

		@Override
		public boolean isRequiredServiceSupported(@NonNull final BluetoothGatt gatt) {
			final BluetoothGattService service = gatt.getService(UUID_MAIN_SERVICE);
			if (service != null) {
				mTxCharacteristic = service.getCharacteristic(UUID_TX);
				mRxCharacteristic = service.getCharacteristic(UUID_RX);
			}

			boolean writeRequest = false;
			//write속성(WR)을 가지고 있는 mRxCharacteristic에 대해 체크
			if (mRxCharacteristic != null) {
				final int rxProperties = mRxCharacteristic.getProperties();
				writeRequest = (rxProperties & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0;
			}

			mSupported = mTxCharacteristic != null && mTxCharacteristic != null && writeRequest;
			return mSupported;
		}

		@Override
		protected void onDeviceDisconnected() {
			mTxCharacteristic = null;
			mRxCharacteristic = null;
		}
	};



	/**
	 * Sends a request to the device to turn the LED on or off.
	 *
	 * @param isLock true to turn the LED on, false to turn it off.
	 */
	public void send(final boolean isLock) {

		writeCharacteristic(mRxCharacteristic, isLock ? BlinkDoorlock.lock() : BlinkDoorlock.unlock())
				.with(mRxDataCallback).enqueue();
	}

	public void authDoorlock() {

        //write속성(WR)을 가지고 있는 mRxCharacteristic에 write함
		writeCharacteristic(mRxCharacteristic,  BlinkDoorlock.getExchangeKeyRequest())
				.with(mRxDataCallback).enqueue();
	}
}
