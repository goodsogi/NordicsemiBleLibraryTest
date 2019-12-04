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

package no.nordicsemi.android.blinky.viewmodels;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.os.Build;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import java.util.UUID;

import no.nordicsemi.android.blinky.R;
import no.nordicsemi.android.blinky.adapter.DiscoveredBluetoothDevice;
import no.nordicsemi.android.blinky.profile.BlinkyManager;
import no.nordicsemi.android.blinky.profile.BlinkyManagerCallbacks;
import no.nordicsemi.android.log.LogSession;
import no.nordicsemi.android.log.Logger;

public class BlinkyViewModel extends AndroidViewModel implements BlinkyManagerCallbacks {
	private final BlinkyManager mBlinkyManager;

    private BluetoothDevice mDevice;

	// Connection states Connecting, Connected, Disconnecting, Disconnected etc.
	private final MutableLiveData<String> mConnectionState = new MutableLiveData<>();

	// Flag to determine if the device is connected
	private final MutableLiveData<Boolean> mIsConnected = new MutableLiveData<>();

	// Flag to determine if the device has required services
	private final MutableLiveData<Boolean> mIsSupported = new MutableLiveData<>();

	// Flag to determine if the device is ready
	private final MutableLiveData<Void> mOnDeviceReady = new MutableLiveData<>();

	// Flag that holds the on off state of the LED. On is true, Off is False
	private final MutableLiveData<Boolean> mDoorlockState = new MutableLiveData<>();


    // Flag that holds the pressed released state of the button on the devkit.
	// Pressed is true, Released is false

	public LiveData<Void> isDeviceReady() {
		return mOnDeviceReady;
	}

	public LiveData<String> getConnectionState() {
		return mConnectionState;
	}

	public LiveData<Boolean> isConnected() {
		return mIsConnected;
	}

	public LiveData<Boolean> getDoorlockState() {
		return mDoorlockState;
	}

	public LiveData<Boolean> isSupported() {
		return mIsSupported;
	}


    private static final String APP_KEY_STRING = "NTcBbs7dvAs=";


    public BlinkyViewModel(@NonNull final Application application) {
		super(application);

		// Initialize the manager
		mBlinkyManager = new BlinkyManager(getApplication());
		mBlinkyManager.setGattCallbacks(this);


	}

	/**
	 * Connect to peripheral.
	 */
	public void connect(@NonNull final DiscoveredBluetoothDevice device) {
		Log.d("plusapps", "connect");
		// Prevent from calling again when called again (screen orientation changed)
		if (mDevice == null) {
			mDevice = device.getDevice();

			final LogSession logSession
					= Logger.newSession(getApplication(), null, device.getAddress(), device.getName());
			mBlinkyManager.setLogger(logSession);


			reconnect();
		}
	}





	/**
	 * Reconnects to previously connected device.
	 * If this device was not supported, its services were cleared on disconnection, so
	 * reconnection may help.
	 */
	public void reconnect() {
		Log.d("plusapps", "reconnect");
		if (mDevice != null) {
			mBlinkyManager.connect(mDevice)
					.retry(3, 100)
					.useAutoConnect(false)
					.enqueue();
		}
	}

	/**
	 * Disconnect from peripheral.
	 */
	private void disconnect() {
		Log.d("plusapps", "disconnect");
		mDevice = null;
		mBlinkyManager.disconnect().enqueue();
	}

	public void toggleDoorlock(final boolean isLock) {
		mBlinkyManager.send(isLock);
		mDoorlockState.setValue(isLock);
	}

	@Override
	protected void onCleared() {
		super.onCleared();
		if (mBlinkyManager.isConnected()) {
			disconnect();
		}
	}



	@Override
	public void onDoorlockStateChanged(@NonNull final BluetoothDevice device, final boolean on) {
		Log.d("smartsolity", "onDoorlockStateChanged");
		mDoorlockState.postValue(on);
	}

	@Override
	public void onDeviceConnecting(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onDeviceConnecting");
		mConnectionState.postValue(getApplication().getString(R.string.state_connecting));
	}

	@Override
	public void onDeviceConnected(@NonNull final BluetoothDevice device) {

		Log.d("smartsolity", "onDeviceConnected");

		mIsConnected.postValue(true);
		mConnectionState.postValue(getApplication().getString(R.string.state_discovering_services));
	}

	@Override
	public void onDeviceDisconnecting(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onDeviceDisconnecting");
		mIsConnected.postValue(false);
	}

	@Override
	public void onDeviceDisconnected(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onDeviceDisconnected");
		mIsConnected.postValue(false);
	}

	@Override
	public void onLinkLossOccurred(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onLinkLossOccurred");
		mIsConnected.postValue(false);
	}

	@Override
	public void onServicesDiscovered(@NonNull final BluetoothDevice device,
									 final boolean optionalServicesFound) {
		Log.d("smartsolity", "onServicesDiscovered");
		mConnectionState.postValue(getApplication().getString(R.string.state_initializing));
	}

	@Override
	public void onDeviceReady(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onDeviceReady");
		mIsSupported.postValue(true);
		mConnectionState.postValue(null);
		mOnDeviceReady.postValue(null);
	}

	@Override
	public void onBondingRequired(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onBondingRequired");
		// Blinky does not require bonding
	}

	@Override
	public void onBonded(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onBonded");
		// Blinky does not require bonding
	}

	@Override
	public void onBondingFailed(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onBondingFailed");
		// Blinky does not require bonding
	}

	@Override
	public void onError(@NonNull final BluetoothDevice device,
						@NonNull final String message, final int errorCode) {
		// TODO implement
		Log.d("smartsolity", "onError: " + message);
	}

	@Override
	public void onDeviceNotSupported(@NonNull final BluetoothDevice device) {
		Log.d("smartsolity", "onDeviceNotSupported");
		mConnectionState.postValue(null);
		mIsSupported.postValue(false);
	}

	@Override
	public void onButtonStateChanged(@NonNull BluetoothDevice device, boolean pressed) {

	}

	public void authDoorlock() {
		mBlinkyManager.authDoorlock();
	}
}
