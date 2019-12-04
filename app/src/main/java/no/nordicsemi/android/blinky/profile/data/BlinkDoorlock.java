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

package no.nordicsemi.android.blinky.profile.data;

import android.util.Base64;
import android.util.Log;

import no.nordicsemi.android.ble.data.Data;
import no.nordicsemi.android.blinky.viewmodels.ByteUtil;
import no.nordicsemi.android.blinky.viewmodels.DoorlockUtil;

public final class BlinkDoorlock {
    private static final byte SMART_OPEN_FLAG_LOCK = 0x01;
    private static final byte SMART_OPEN_FLAG_UNLOCK = 0x00;

    private static final String APP_KEY_STRING = "NTcBbs7dvAs=";

    public static Data lock() {

        byte second = 7;

        byte[] mAppKey = Base64.decode(APP_KEY_STRING, Base64.NO_WRAP);

        byte[] mAppIV = new byte[16];
        for (int i = 0; i < 8; i++)
            mAppIV[i] = mAppKey[i];

        byte address = 0x00;

        byte[] datas = DoorlockUtil.mf_cp_getOpen(mAppKey, SMART_OPEN_FLAG_LOCK, address, (byte) second);
        byte[] sendDatas = DoorlockUtil.mf_cp_EncodeData(mAppIV, datas);

        return Data.from(sendDatas.toString());
    }

    public static Data unlock() {

        byte[] mAppKey = Base64.decode(APP_KEY_STRING, Base64.NO_WRAP);

        byte[] mAppIV = new byte[16];
        for (int i = 0; i < 8; i++)
            mAppIV[i] = mAppKey[i];

        byte address = 0x00;

        byte[] datas = DoorlockUtil.mf_cp_getClose(mAppKey);
        byte[] sendDatas = DoorlockUtil.mf_cp_EncodeData(mAppIV, datas);

        return Data.from(sendDatas.toString());
    }


    public static Data getCertificationRequest() {


        byte[] mAppKey = Base64.decode(APP_KEY_STRING, Base64.NO_WRAP);

        byte[] mAppIV = new byte[16];
        for (int i = 0; i < 8; i++)
            mAppIV[i] = mAppKey[i];

        byte[] datas = DoorlockUtil.mf_cp_getAppInit(mAppKey);
        byte[] sendDatas = DoorlockUtil.mf_cp_EncodeData(mAppIV, datas);

        return Data.from(sendDatas.toString());
    }

    public static Data getExchangeKeyRequest() {

        byte[] mAppKey = Base64.decode(APP_KEY_STRING, Base64.NO_WRAP);

        byte[] sendDatas = DoorlockUtil.mf_cp_getKeyInit(mAppKey);
        //new Data와 같이 생성자에 넣어야 됨
        Data data = new Data(sendDatas);
        Log.d("plusapps", String.format("getExchangeKeyRequest() : %s", ByteUtil.mf_str_toHexString(data.getValue())));
        return data;

    }
}
