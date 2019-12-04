package no.nordicsemi.android.blinky.viewmodels;

import java.util.ArrayList;
import java.util.Calendar;



public class DoorlockUtil {

    private static final boolean D = true;
    //private static final boolean D = false;

    public static final byte[] AES_KEY = {'H', '-', 'G', 'A', 'N', 'G', ' ', 'B', 'L', 'E', ' ', 'M', 'O', 'D', 'E', '1'};
    public static final byte[] IV_DEF = {(byte) 0x14, (byte) 0x7e, (byte) 0x6f, (byte) 0x16, (byte) 0x31, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
            (byte) 0xa0, (byte) 0xf7, (byte) 0x15, (byte) 0x88, (byte) 0xa9, (byte) 0xcf, (byte) 0x4f, (byte) 0x91};
    public static byte[] IV_BUFF = {(byte) 0x14, (byte) 0x7e, (byte) 0x6f, (byte) 0x16, (byte) 0x31, (byte) 0xae, (byte) 0xd2, (byte) 0xa6,
            (byte) 0xa0, (byte) 0xf7, (byte) 0x15, (byte) 0x88, (byte) 0xa9, (byte) 0xcf, (byte) 0x4f, (byte) 0x91};

    public static final byte STATE_CONNECT = 'C';
    public static final byte STATE_DISCONNECT = 'D';
    public static final byte STATE_ACTION = 'A';

    public static final byte COMM_STX = 'H';
    public static final byte RECV_SUCCESS = (byte) 0x00;
    public static final byte RECV_FAIL = (byte) 0x01;
    public static final byte RECV_NONE = (byte) 0x02;
    public static final byte RECV_NONE_SERIAL = (byte) 0x03;
    public static final byte RECV_CMD_IDX = (byte) 0x80;
    public static final byte RECV_ERROR = (byte) 0xFF;
    public static final byte PASSWORD_TYPE_USER = (byte) 0x01;
    public static final byte PASSWORD_TYPE_MASTER = (byte) 0x02;
    public static final byte PASSWORD_ADDRESS_AUTO = (byte) 0x00;
    public static final byte PASSWORD_ADDRESS_ALL = (byte) 0xFF;

    public static final byte CMD_DOORLOCK_ACK_CHECK = (byte) 0x0A;   // ACK 확인
    public static final byte CMD_DOORLOCK_RF_POWER = (byte) 0x0D;   // RF 출력설정(1~8)
    public static final byte CMD_DOORLOCK_TYPE = (byte) 0x0F;   // 종류(BLE : 0x1101)
    public static final byte CMD_DOORLOCK_TYPE_ACK = (byte) 0x8F;   // 종류 응답
    public static final byte CMD_DOORLOCK_KEY_SET = (byte) 0x10;   // 암호키교환
    public static final byte CMD_DOORLOCK_APP_SET = (byte) 0x11;   // App 인증
    public static final byte CMD_DOORLOCK_REG_SET = (byte) 0x12;   // 등록요청
    public static final byte CMD_DOORLOCK_REG_DEL = (byte) 0x13;   // 등록삭제
    public static final byte CMD_DOORLOCK_OPEN = (byte) 0x21;   // 문열기
    public static final byte CMD_DOORLOCK_OPEN_ACK = (byte) 0xA1;   // 문열기 응답
    public static final byte CMD_DOORLOCK_CLOSE = (byte) 0x22;   // 문닫기
    public static final byte CMD_DOORLOCK_CLOSE_ACK = (byte) 0xA2;   // 문닫기 응답
    public static final byte CMD_DOORLOCK_PWD_SET = (byte) 0x23;   // 비밀번호 등록
    public static final byte CMD_DOORLOCK_PWD_SET_ACK = (byte) 0xA3;   // 비밀번호 등록 응답
    public static final byte CMD_DOORLOCK_PWD_DEL = (byte) 0x24;   // 비밀번호 삭제
    public static final byte CMD_DOORLOCK_PWD_DEL_ACK = (byte) 0xA4;   // 비밀번호 삭제 응답
    public static final byte CMD_DOORLOCK_SECURITY = (byte) 0x25;   // 외출/방범
    public static final byte CMD_DOORLOCK_SECURITY_ACK = (byte) 0xA5;   // 외출/방범 응답
    public static final byte CMD_DOORLOCK_DOUBLE_CLOSE = (byte) 0x26;   // 이중잠금
    public static final byte CMD_DOORLOCK_DOUBLE_CLOSE_ACK = (byte) 0xA6;   // 이중잠금 응답
    public static final byte CMD_DOORLOCK_SOUND = (byte) 0x27;   // 음량
    public static final byte CMD_DOORLOCK_SOUND_ACK = (byte) 0xA7;   // 음량 응답
    public static final byte CMD_DOORLOCK_ONETIME_PWD = (byte) 0x28;   // 일회용 비밀번호
    public static final byte CMD_DOORLOCK_ONETIME_PWD_ACK = (byte) 0xA8;   // 일회용 비밀번호 응답
    public static final byte CMD_DOORLOCK_VISITOR_PWD_SET = (byte) 0x29;   // 방문자 비밀번호 발급
    public static final byte CMD_DOORLOCK_VISITOR_PWD_SET_ACK = (byte) 0xA9; // 방문자 비밀번호 발급 응답
    public static final byte CMD_DOORLOCK_VISITOR_PWD_DEL = (byte) 0x2A;   // 방문자 비밀번호 폐기
    public static final byte CMD_DOORLOCK_VISITOR_PWD_DEL_ACK = (byte) 0xAA; // 방문자 비밀번호 폐기 응답
    public static final byte CMD_DOORLOCK_KEEP_OPEN = (byte) 0x2B;   // 항시열림모드
    public static final byte CMD_DOORLOCK_KEEP_OPEN_ACK = (byte) 0xAB;   // 항시열림모드 응답
    public static final byte CMD_DOORLOCK_FAKE_NUMBER_MODE = (byte) 0x2C;   // 허수기능설정
    public static final byte CMD_DOORLOCK_FAKE_NUMBER_MODE_ACK = (byte) 0xAC; // 허수기능설정 응답
    public static final byte CMD_DOORLOCK_SMART_TOUCH = (byte) 0x2D;   // 스마트터치
    public static final byte CMD_DOORLOCK_SMART_TOUCH_ACK = (byte) 0xAD;   // 스마트터치 응답
    public static final byte CMD_DOORLOCK_GET_STATE = (byte) 0x51;   // 상태요청
    public static final byte CMD_DOORLOCK_GET_STATE_ACK = (byte) 0xD1;   // 상태요청 응답
    public static final byte CMD_DOORLOCK_GET_KEYINFO = (byte) 0x52;   // 등록정보요청
    public static final byte CMD_DOORLOCK_GET_KEYINFO_ACK = (byte) 0xD2;   // 등록정보요청 응답
    public static final byte CMD_DOORLOCK_EVENT_OPEN = (byte) 0x53;   // 문열림이벤트
    public static final byte CMD_DOORLOCK_EVENT_OPEN_ACK = (byte) 0xD3;   // 문열림이벤트 응답
    public static final byte CMD_DOORLOCK_EVENT_CLOSE = (byte) 0x54;   // 문닫힘이벤트
    public static final byte CMD_DOORLOCK_EVENT_CLOSE_ACK = (byte) 0xD4;   // 문닫힘이벤트 응답
    public static final byte CMD_DOORLOCK_EVENT_OTHER = (byte) 0x55;   // 기타이벤트
    public static final byte CMD_DOORLOCK_EVENT_OTHER_ACK = (byte) 0xD5;   // 기타이벤트 응답
    public static final byte CMD_DOORLOCK_GET_DEVICE = (byte) 0x57;   // 제품정보요청
    public static final byte CMD_DOORLOCK_EVENT_REG = (byte) 0x58;   // 도어락등록모드이벤트
    public static final byte CMD_DOORLOCK_GET_FUNCTION = (byte) 0x5A;   // 도어락기능조회
    public static final byte CMD_DOORLOCK_GET_FUNCTION_ACK = (byte) 0xDA;   // 도어락기능조회 응답
    public static final byte CMD_DOORLOCK_GET_VISITOR_PWD = (byte) 0x5B;   // 방문자전용 비밀번호조회
    public static final byte CMD_DOORLOCK_GET_VISITOR_PWD_ACK = (byte) 0xDB;   // 방문자전용 비밀번호조회 응답
    public static final byte CMD_DOORLOCK_GET_VERSION = (byte) 0x0F;   // 버전 조회
    public static final byte CMD_DOORLOCK_GET_VERSION_ACK = (byte) 0x8F;   // 버전 조회 응답
    public static final byte CMD_DOORLOCK_DFU_START = (byte) 0x70;   // 버전 조회
    public static final byte CMD_DOORLOCK_DFU_START_ACK = (byte) 0xF0;   // 버전 조회 응답
    public static final byte CMD_DOORLOCK_SET_REGMODE = (byte) 0x2E;   // 등록 진입 요청
    public static final byte CMD_DOORLOCK_SET_REGMODE_ACK = (byte) 0xAE;   // 등록 진입 요청 응답
    public static final byte CMD_DOORLOCK_GET_CARDFINGER_INFO = (byte) 0x30;  // 인증정보 등록 조회
    public static final byte CMD_DOORLOCK_GET_CARDFINGER_INFO_ACK = (byte) 0XB0; // 인증정보 등록 조회
    public static final byte CMD_DOORLOCK_DELETE_CARDFINGER_INFO = (byte) 0X31; // 인증정보 삭제
    public static final byte CMD_DOORLOCK_DELETE_CARDFINGER_INFO_ACK = (byte) 0XB1; // 인증정보 삭제 응답
    public static final byte CMD_DOORLOCK_REG_MODE_EVENT = (byte) 0x58; // 도어락 등록 모드 이벤트 응답
    public static final byte CMD_DOORLOCK_GET_VISITOR_REGINFO = (byte) 0x5C;	// 방문자 전용 출입 등록정보 조회
    public static final byte CMD_DOORLOCK_GET_VISITOR_REGINFO_ACK = (byte) 0x5D;	// 방문자 전용 출입 등록정보 조회 응답
    public static final byte CMD_DOORLOCK_GET_SAVEDLOG = (byte) 0x5D;	// 저장된 이력 조회 요청
    public static final byte CMD_DOORLOCK_GET_SAVEDLOG_ACK = (byte) 0xDD;	// 저장된 이력 조회 응답	

    public static byte sendCMD = 0;

    public static final byte MODULE_PHONE = (byte) 0x01, MODULE_AI = (byte) 0x02, MODULE_OTHER = (byte) 0x03;
    public static final byte DOORLOCK_STATE_OPEN = (byte) 0x00, DOORLOCK_STATE_CLOSE = (byte) 0x01;
    public static final byte SMART_OPEN = (byte) 0x01;

    public static byte[] reqKey = new byte[8];

    public static void mf_v_setReqKey(byte[] key) {
        for (byte i = 0; i < reqKey.length; i++)
            reqKey[i] = key[i];
    }

    public static byte[] mf_cp_getTime() {
        byte idx = 0;
        byte[] timeBuff = new byte[7];

        Calendar cal = Calendar.getInstance();
        byte[] tmpTear = new byte[4];
        tmpTear = ByteUtil.mf_cp_Int2ByteArray(cal.get(Calendar.YEAR));
        timeBuff[idx++] = tmpTear[2];
        timeBuff[idx++] = tmpTear[3];
        timeBuff[idx++] = (byte) (cal.get(Calendar.MONTH) + 1);
        timeBuff[idx++] = (byte) cal.get(Calendar.DAY_OF_MONTH);
        timeBuff[idx++] = (byte) cal.get(Calendar.HOUR_OF_DAY);
        timeBuff[idx++] = (byte) cal.get(Calendar.MINUTE);
        timeBuff[idx++] = (byte) cal.get(Calendar.SECOND);

        return timeBuff;
    }

    public static byte mf_c_setTimeKey(byte[] appKey, byte[] buff, byte idx) {
        byte[] timeBuff = new byte[7];
        timeBuff = mf_cp_getTime();
        for (byte i = 0; i < timeBuff.length; i++)
            buff[idx++] = timeBuff[i];
        for (byte i = 0; i < appKey.length; i++)
            buff[idx++] = appKey[i];

        return idx;
    }

    public static byte[] mf_cp_getKeyInit(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_KEY_SET;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    public static byte[] mf_cp_getAppInit(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_APP_SET;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    public static byte[] mf_cp_getRegInit(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 16 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_REG_SET;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = MODULE_PHONE;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }

    public static byte[] mf_cp_getRegDel(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 16 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_REG_DEL;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = MODULE_PHONE;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }


    public static byte[] mf_cp_getmoduleInfo(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_TYPE;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }


    public static byte[] mf_cp_getOpen(byte[] appKey, byte smartFlag, byte address, byte time) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_OPEN;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x13;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = smartFlag;
        buff[cn++] = address;
        buff[cn++] = time;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

//       public static byte[] mf_cp_getOpen(byte[] appKey, byte smartFlag, byte address) {
//        byte cn = 0;
//        byte[] buff = new byte[3 + 15 + 1 + 1 + 1];
//        short add = 0;
//
//        sendCMD = CMD_DOORLOCK_OPEN;
//
//        buff[cn++] = COMM_STX;
//        buff[cn++] = (byte) 0x12;
//        buff[cn++] = sendCMD;
//        //--------------------------------------------------------------------
//        cn = mf_c_setTimeKey(appKey, buff, cn);
//        buff[cn++] = smartFlag;
//        buff[cn++] = address;
//
//        for (byte i = 1; i < cn; i++) {
//            add += +buff[i];
//        }
//        buff[cn++] = (byte) add;
//        return buff;
//    }

    public static byte[] mf_cp_getClose(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_CLOSE;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }



    public static byte[] mf_cp_getSound(byte[] appKey, boolean onoff, byte val) {
        byte cn = 0;
        byte[] buff;

        buff = new byte[3 + 15 + 1 + 1 + 1];

        short add = 0;

        sendCMD = CMD_DOORLOCK_SOUND;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (buff.length - 3);
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        if (onoff) {
            buff[cn++] = (byte) 0x01;
            buff[cn++] = 0x01;
        } else {
            buff[cn++] = (byte) 0x00;
            buff[cn++] = val;
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

//    public static byte[] mf_cp_getSound(byte[] appKey, boolean onoff, byte val) {
//        byte cn = 0;
//        byte[] buff;
//        Logger.e(TAG,"ONOFF:" +onoff);
//        if (onoff) {
//            buff = new byte[3 + 15 + 1 + 1];
//        } else {
//            buff = new byte[3 + 15 + 1 + 1 + 1];
//        }
//        short add = 0;
//
//        sendCMD = CMD_DOORLOCK_SOUND;
//
//        buff[cn++] = COMM_STX;
//        buff[cn++] = (byte) (buff.length - 3);
//        buff[cn++] = sendCMD;
//        //--------------------------------------------------------------------
//        cn = mf_c_setTimeKey(appKey, buff, cn);
//        if (onoff) {
//            buff[cn++] = (byte) 0x01;
//        } else {
//            buff[cn++] = (byte) 0x00;
//            buff[cn++] = val;
//        }
//
//        for (byte i = 1; i < cn; i++) {
//            add += +buff[i];
//        }
//        buff[cn++] = (byte) add;
//        return buff;
//    }

    public static byte[] mf_cp_getSecurity(byte[] appKey, boolean onoff) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_SECURITY;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        if (onoff) {
            buff[cn++] = (byte) 0x01;
        } else {
            buff[cn++] = (byte) 0x00;
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }

    public static byte[] mf_cp_getDoubleClose(byte[] appKey, byte val) {

        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_DOUBLE_CLOSE;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = val;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }

    public static byte[] mf_cp_getKeepOpen(byte[] appKey, boolean onoff) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_KEEP_OPEN;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        if (onoff) {
            buff[cn++] = (byte) 0x01;
        } else {
            buff[cn++] = (byte) 0x00;
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    public static byte[] mf_cp_getFakeNumber(byte[] appKey, boolean onoff) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_FAKE_NUMBER_MODE;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        if (onoff) {
            buff[cn++] = (byte) 0x01;
        } else {
            buff[cn++] = (byte) 0x00;
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    public static byte[] mf_cp_getPw(byte[] appKey, byte type, byte address, String pw) {

        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1 + 1 + pw.length()];

        short add = 0;

        sendCMD = CMD_DOORLOCK_PWD_SET;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (buff.length - 3);
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;
        buff[cn++] = address;
        for (byte i = 0; i < pw.length(); i++) {
            buff[cn++] = pw.getBytes()[i];
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


    public static byte[] mf_cp_setRegMode(byte[] appKey, int count, String pw) {

        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1 + pw.length()];

        short add = 0;

        sendCMD = CMD_DOORLOCK_SET_REGMODE;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (buff.length - 3);
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        byte pwcount = (byte)count;
        buff[cn++] = pwcount;
        for (byte i = 0; i < pw.length(); i++) {
            buff[cn++] = pw.getBytes()[i];
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


    public static byte[] mf_cp_getPwDel(byte[] appKey, byte type, byte address, String pw) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1 + 1 + pw.length()];
        short add = 0;

        sendCMD = CMD_DOORLOCK_PWD_DEL;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (buff.length - 3);
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;
        buff[cn++] = address;
        for (byte i = 0; i < pw.length(); i++) {
            buff[cn++] = pw.getBytes()[i];
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }


    public static byte[] mf_cp_getKeyInfo(byte[] appKey, byte type, String address) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1 + 1 ];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_KEYINFO;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x12;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;
        byte addr = (byte) Integer.parseInt(address);
        buff[cn++] = addr;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }

//    public static byte[] mf_cp_getOneTimePw(byte[] appKey, String onetimepw) {
//        byte cn = 0;
//        byte[] buff = new byte[3 + 15 + onetimepw.length() + 1];
//        short add = 0;
//
//        sendCMD = CMD_DOORLOCK_ONETIME_PWD;
//
//        buff[cn++] = COMM_STX;
//        buff[cn++] = (byte) (1 + 15 + onetimepw.length());
//        buff[cn++] = sendCMD;
//        //--------------------------------------------------------------------
//        cn = mf_c_setTimeKey(appKey, buff, cn);
//        for (byte i = 0; i < onetimepw.length(); i++) {
//            buff[cn++] = onetimepw.getBytes()[i];
//        }
//
//        for (byte i = 1; i < cn; i++) {
//            add += +buff[i];
//        }
//        buff[cn++] = (byte) add;
//        return buff;
//
//    }
//


    public static byte[] mf_cp_getOneTimePw(byte[] appKey, String onetimepw, String startDate, String endDate) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 8 + onetimepw.length() + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_ONETIME_PWD;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (1 + 15 + 8 + onetimepw.length());
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);



//        int startyear = Integer.parseInt(startDate.substring(0, 2), 16);
//        int startmonth = Integer.parseInt(startDate.substring(2, 4),16);
//        int startday = Integer.parseInt(startDate.substring(4, 6),16);
//        int starthour = Integer.parseInt(startDate.substring(6, 8),16);
//
//        Log.e(TAG,"SSS"+startyear+startmonth+startday+starthour);
//
//
//        int endyear = Integer.parseInt(endDate.substring(0, 2),16);
//        int endmonth = Integer.parseInt(endDate.substring(2, 4),16);
//        int endday = Integer.parseInt(endDate.substring(4, 6),16);
//        int endhour = Integer.parseInt(endDate.substring(6, 8),16);


        byte startyear =  (byte) Integer.parseInt(startDate.substring(0, 2));
        byte startmonth = (byte) Integer.parseInt(startDate.substring(2, 4));
        byte startday = (byte) Integer.parseInt(startDate.substring(4, 6));
        byte starthour = (byte) Integer.parseInt(startDate.substring(6, 8));

        byte endyear = (byte) Integer.parseInt(endDate.substring(0, 2));
        byte endmonth = (byte) Integer.parseInt(endDate.substring(2, 4));
        byte endday = (byte) Integer.parseInt(endDate.substring(4, 6));
        byte endhour = (byte) Integer.parseInt(endDate.substring(6, 8));
//
//
//        String starty = Integer.toHexString(startyear);
//        String startm = Integer.toHexString(startmonth);
//        String startd = Integer.toHexString(startday);
//        String starth = Integer.toHexString(starthour);
//
//        String endy = Integer.toHexString(endyear);
//        String endm = Integer.toHexString(endmonth);
//        String endd = Integer.toHexString(endday);
//        String endh = Integer.toHexString(endhour);



            buff[cn++] = startyear;
            buff[cn++] = startmonth;
            buff[cn++] = startday;
            buff[cn++] = starthour;
            buff[cn++] = endyear;
            buff[cn++] = endmonth;
            buff[cn++] = endday;
            buff[cn++] = endhour;



//        buff[cn++] = (byte) startmonth;
//        buff[cn++] = (byte) startday;
//        buff[cn++] = (byte) starthour;
//        buff[cn++] = (byte) endyear;
//        buff[cn++] = (byte) endmonth;
//        buff[cn++] = (byte) endday;
//        buff[cn++] = (byte) endhour;





//        buff[cn++] = (byte) Integer.parseInt(startDate.substring(0,2),16);
//        buff[cn++] = (byte) Integer.parseInt(startDate.substring(2,4),16);
//        buff[cn++] = (byte) Integer.parseInt(startDate.substring(4,6),16);
//        buff[cn++] = (byte) Integer.parseInt(startDate.substring(6,8),16);
//
//        buff[cn++] = (byte) Integer.parseInt(endDate.substring(0,2),16);
//        buff[cn++] = (byte) Integer.parseInt(endDate.substring(2,4),16);
//        buff[cn++] = (byte) Integer.parseInt(endDate.substring(4,6),16);
//        buff[cn++] = (byte) Integer.parseInt(endDate.substring(6,8),16);




        for (byte i = 0; i < onetimepw.length(); i++) {
            buff[cn++] = onetimepw.getBytes()[i];
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i+1), 16));
        }
        return data;
    }

    public static byte[] mf_cp_getVisitorPw(byte[] appKey, String startDate, String endDate, String day, String hour, String visitorPw) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 10 + visitorPw.length() +1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_VISITOR_PWD_SET;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (buff.length - 3);
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = (byte)0x00;

        byte startyear =  (byte) Integer.parseInt(startDate.substring(0, 2));
        byte startmonth = (byte) Integer.parseInt(startDate.substring(2, 4));
        byte startday = (byte) Integer.parseInt(startDate.substring(4, 6));
        byte starthour = (byte) Integer.parseInt(hour.substring(0,2));

        byte endyear = (byte) Integer.parseInt(endDate.substring(0, 2));
        byte endmonth = (byte) Integer.parseInt(endDate.substring(2, 4));
        byte endday = (byte) Integer.parseInt(endDate.substring(4, 6));
        byte endhour = (byte) Integer.parseInt(hour.substring(2,4));

        byte date = (byte) Integer.parseInt(day);

        buff[cn++] = startyear;
        buff[cn++] = startmonth;
        buff[cn++] = startday;
        buff[cn++] = endyear;
        buff[cn++] = endmonth;
        buff[cn++] = endday;

        buff[cn++] = date;
        buff[cn++] = starthour;
        buff[cn++] = endhour;

        for (byte i = 0; i < visitorPw.length(); i++) {
            buff[cn++] = visitorPw.getBytes()[i];
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


    public static byte[] mf_cp_getDelVisitorPw(byte[] appKey, byte type) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_VISITOR_PWD_DEL;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    //20190807
    public static byte[] mf_cp_getDelVisitorPw(byte[] appKey, byte type, String pw) {
        byte cn = 0;
        byte[] buff;
        if (type == (byte) 0x00)
            buff = new byte[3 + 15 + 1 + 1 + pw.length()];
        else
            buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_VISITOR_PWD_DEL;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) (buff.length - 3);
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;
        if (type == (byte) 0x00) {
            for (byte i = 0; i < pw.length(); i++) {
                buff[cn++] = pw.getBytes()[i];
            }
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;

        return buff;
    }



    public static byte[] mf_cp_getDoorlock_Function(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_FUNCTION;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


    public static byte[] mf_cp_getCardFinger_Info(byte[] appKey, byte type) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_CARDFINGER_INFO;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


    public static byte[] mf_cp_deleteCardFinger_Info(byte[] appKey, byte type ,byte method , ArrayList<String> del_state) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1 +13 +1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_DELETE_CARDFINGER_INFO;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x1F;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = type;
        buff[cn++] = method;


        for(int a=0;a<13;a++) {
            byte state =  (byte) Integer.parseInt(del_state.get(a));
            buff[cn++] = state;
        }

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }



//    public static byte[] mf_cp_getDoorlockDFU(byte[] appKey) {
//
//        byte cn = 0;
//        byte[] buff = new byte[3 + 15 + 1];
//        short add = 0;
//
//        sendCMD = CMD_DOORLOCK_DFU_MODE_INIT;
//
//        buff[cn++] = COMM_STX;
//        buff[cn++] = (byte) 0x10;
//        buff[cn++] = sendCMD;
//        //--------------------------------------------------------------------
//        cn = mf_c_setTimeKey(appKey, buff, cn);
//
//        for (byte i = 1; i < cn; i++) {
//            add += +buff[i];
//        }
//        buff[cn++] = (byte) add;
//        return buff;
//    }

    public static byte[] mf_cp_getDoorlockState(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_STATE;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }
    
    public static byte[] mf_cp_getVersion(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_VERSION;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


    public static byte[] mf_cp_DfuStart(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_DFU_START;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }


	public static byte[] mf_cp_getVisitRegInfo(byte[] appKey, byte address) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_VISITOR_REGINFO;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
		buff[cn++] = address;
        buff[cn++] = (byte) add;
        return buff;		
	}


	public static byte[] mf_cp_getSavedLog(byte[] appKey) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1];
        short add = 0;

        sendCMD = CMD_DOORLOCK_GET_SAVEDLOG;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x10;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;		
	}
    

    public static byte[] mf_cp_getSmartTouch(byte[] appKey, byte time) {
        byte cn = 0;
        byte[] buff = new byte[3 + 15 + 1 + 1];
        short add = 0;

        if ((time < 5) || (time > 30))
            time = 5;

        sendCMD = CMD_DOORLOCK_SMART_TOUCH;

        buff[cn++] = COMM_STX;
        buff[cn++] = (byte) 0x11;
        buff[cn++] = sendCMD;
        //--------------------------------------------------------------------
        cn = mf_c_setTimeKey(appKey, buff, cn);
        buff[cn++] = time;

        for (byte i = 1; i < cn; i++) {
            add += +buff[i];
        }
        buff[cn++] = (byte) add;
        return buff;
    }

    public static byte[] mf_cp_EncodeData(byte[] iv, byte[] data) {
        byte[] buff;
//        if (D) Logger.d(TAG, "mf_cp_EncodeData IV : " + ByteUtil.mf_str_toHexString(iv));
        AES cipher = new AES(AES_KEY, iv);
        buff = cipher.CBC_encrypt(data);
        return buff;
    }

    public static byte[] mf_cp_DecodeData(byte[] iv, byte[] data) {
        byte[] buff;
//        if (D) Logger.d(TAG, "mf_cp_DecodeData IV : " + ByteUtil.mf_str_toHexString(iv));
        AES cipher = new AES(AES_KEY, iv);
        buff = cipher.CBC_decrypt(data);
        return buff;
    }


    /////////////////////////////////////////////////////////////////////////////////////////////////////////

    //uint32_t gBeaconSeed[16] = {0x00, };
//uint32_t BeaconIndex = 0;
//#define HG_DLL_ADV_HASH 0x02;
//
//void HashInit(uint8_t HashType, uint64_Seed)
//{
//    if(HashType == HG_DDL_ADV_HASH)
//    {
//        uint32_t Hi = ((Seed >> 32) & 0x00000000FFFFFFFF);
//        uint32_t Lo = (Seed & 0x00000000FFFFFFFF);
//
//        gBeaconSeed[00] = (Hi & 0x0000F000);
//        gBeaconSeed[01] = (Hi & 0x000000F0);
//        gBeaconSeed[02] = (Hi & 0x00F00000);
//        gBeaconSeed[03] = (Hi & 0x0000000F);
//        gBeaconSeed[04] = (Hi & 0x000F0000);
//        gBeaconSeed[05] = (Hi & 0x00000F00);
//        gBeaconSeed[06] = (Hi & 0x000000FF);
//        gBeaconSeed[07] = (Hi & 0x0000FF00);
//
//        gBeaconSeed[08] = (Lo & 0x0000000F);
//        gBeaconSeed[09] = (Lo & 0x000000F0);
//        gBeaconSeed[10] = (Lo & 0x00F00000);
//        gBeaconSeed[11] = (Lo & 0x000F0000);
//        gBeaconSeed[12] = (Lo & 0x0000F000);
//        gBeaconSeed[13] = (Lo & 0x00000F00);
//        gBeaconSeed[14] = (Lo & 0x000000FF);
//        gBeaconSeed[15] = (Lo & 0x0000FF00);
//
//        BeaconIndex = 0;
//    }
//}
    private static long[] gBeaconSeed = new long[16];
    private static long BeaconIndex = 0;
    private static final byte HG_DLL_ADV_HASH = (byte) 0x02;

    public static void mf_v_HashInit(byte HashType, long hi, long lo) {
        if (HashType == HG_DLL_ADV_HASH) {
            long SeedHi = (hi & 0x00000000FFFFFFFF);
            long SeedLo = (lo & 0x00000000FFFFFFFF);

            gBeaconSeed[0] = (SeedHi & 0x0000F000); // 0x3000
            gBeaconSeed[1] = (SeedHi & 0x000000F0); // 0x90
            gBeaconSeed[2] = (SeedHi & 0x00F00000); // 0x900000
            gBeaconSeed[3] = (SeedHi & 0x0000000F); // 0x2
            gBeaconSeed[4] = (SeedHi & 0x000F0000); // 0xD0000
            gBeaconSeed[5] = (SeedHi & 0x00000F00); // 0x700
            gBeaconSeed[6] = (SeedHi & 0x000000FF); // 0x92
            gBeaconSeed[7] = (SeedHi & 0x0000FF00); // 0x3700

            gBeaconSeed[8] = (SeedLo & 0x0000000F); // 0xB
            gBeaconSeed[9] = (SeedLo & 0x000000F0); // 0xD0
            gBeaconSeed[10] = (SeedLo & 0x00F00000); // 0x700000
            gBeaconSeed[11] = (SeedLo & 0x000F0000); // 0xF0000
            gBeaconSeed[12] = (SeedLo & 0x0000F000); // 0x1000
            gBeaconSeed[13] = (SeedLo & 0x00000F00); // 0x100
            gBeaconSeed[14] = (SeedLo & 0x000000FF); // 0xDB
            gBeaconSeed[15] = (SeedLo & 0x0000FF00); // 0x1100
            BeaconIndex = 0;
        }
    }

    public static void mf_v_HashInit(byte HashType, long seed) {
        if (HashType == HG_DLL_ADV_HASH) {
            long SeedHi = ((seed >> 32) & 0x00000000FFFFFFFF);
            long SeedLo = (seed & 0x00000000FFFFFFFF);

            gBeaconSeed[0] = (SeedHi & 0x0000F000);
            gBeaconSeed[1] = (SeedHi & 0x000000F0);
            gBeaconSeed[2] = (SeedHi & 0x00F00000);
            gBeaconSeed[3] = (SeedHi & 0x0000000F);
            gBeaconSeed[4] = (SeedHi & 0x000F0000);
            gBeaconSeed[5] = (SeedHi & 0x00000F00);
            gBeaconSeed[6] = (SeedHi & 0x000000FF);
            gBeaconSeed[7] = (SeedHi & 0x0000FF00);

            gBeaconSeed[8] = (SeedLo & 0x0000000F);
            gBeaconSeed[9] = (SeedLo & 0x000000F0);
            gBeaconSeed[10] = (SeedLo & 0x00F00000);
            gBeaconSeed[11] = (SeedLo & 0x000F0000);
            gBeaconSeed[12] = (SeedLo & 0x0000F000);
            gBeaconSeed[13] = (SeedLo & 0x00000F00);
            gBeaconSeed[14] = (SeedLo & 0x000000FF);
            gBeaconSeed[15] = (SeedLo & 0x0000FF00);
            BeaconIndex = 0;
        }
    }

    //uint32_t Well512(uint8_t HashType)
//{
//    if(HashType == HG_DDL_ADV_HASH)
//    {
//        uint32_t A = gBeaconSeed[BeaconIndex];
//        uint32_t C = gBeaconSeed[(BeaconIndex + 13) & 15];
//        uint32_t B = A ^ C ^ (A << 16) ^ (C << 15);
//        uint32_t D;
//
//        C = gBeaconSeed[(BeaconIndex + 9) & 15];
//        C ^= (C >> 11);
//        A = gBeaconSeed[BeaconIndex] = B ^ C;
//        D = A ^ ((A << 5) & 0xDA442D20UL);
//        BeaconIndex = (BeaconIndex + 15) & 15;
//        A = gBeaconSeed[BeaconIndex];
//
//        gBeaconSeed[BeaconIndex] = A ^ B ^ D ^ (A << 2) ^ (B << 18) ^ (C <<28);
//
//        return gBeaconSeed[BeaconIndex];
//    }
//}
    public static long mf_l_Well512(byte HashType) {
        long rec = 0;
        if (HashType == HG_DLL_ADV_HASH) {
            long A = gBeaconSeed[(int) BeaconIndex];
            long C = gBeaconSeed[((int) BeaconIndex + 13) & 15];
            long B = A ^ C ^ (A << 16) ^ (C << 15);
            long D;

            C = gBeaconSeed[((int) BeaconIndex + 9) & 15];
            C ^= (C >> 11);
            A = gBeaconSeed[(int) BeaconIndex] = B ^ C;
            D = A ^ ((A << 5) & 0xDA442D20L);
            BeaconIndex = ((int) BeaconIndex + 15) & 15;
            A = gBeaconSeed[(int) BeaconIndex];

            gBeaconSeed[(int) BeaconIndex] = A ^ B ^ D ^ (A << 2) ^ (B << 18) ^ (C << 28);

            //return gBeaconSeed[BeaconIndex];
            rec = gBeaconSeed[(int) BeaconIndex];
        }
        return rec;
    }

    //void HG_BLE_ADV_Decrypt(uint32_t Key, uint8_t *pData)
//{
//#define KEY_C1 52845
//#define KEY_C2 22719
//    uint8_t Loop;
//    uint8_t PreviousBlock;
//    for (Loop = 12; Loop < 20; Loop++)
//    {
//        PreviousBlock = pData[Loop];
//
//        pData[Loop] = pData[Loop] ^ Key >> 8;
//        Key = (PreviousBlock + Key) * KEY_C1 + KEY_C2;
//    }
//}
    public static void mf_v_Decrypt(long Key, byte[] pData) {
        final int KEY_C1 = 52845;
        final int KEY_C2 = 22719;
        short Loop;
        short PreviousBlock;
        byte[][] data = new byte[8][2];
        for (Loop = 12; Loop < 20; Loop++) {
//            PreviousBlock = pData[Loop];
//
//            pData[Loop] = (byte)(pData[Loop] ^ Key >> 8);
//            Key = (PreviousBlock + Key) * KEY_C1 + KEY_C2;

            data[Loop - 12][1] = pData[Loop];
            PreviousBlock = ByteUtil.mf_s_toShort(data[Loop - 12]);

            pData[Loop] = (byte) (PreviousBlock ^ Key >> 8);
            Key = (PreviousBlock + Key) * KEY_C1 + KEY_C2;
        }
    }

    public static void mf_v_testWell512() {
//        byte[] orgAddr = {(byte)0xDB,(byte)0x11,(byte)0x7F,(byte)0x92,(byte)0x37,(byte)0x9D};
//        byte[] raw1 = {
//                (byte)0x02,(byte)0x01,(byte)0x06,(byte)0x02,(byte)0x0A,(byte)0x04,(byte)0x0D,(byte)0xFF,(byte)0xFF,(byte)0xFF,
//                (byte)0x48,(byte)0x47,(byte)0x9E,(byte)0x45,(byte)0x82,(byte)0xB6,(byte)0x8C,(byte)0x9D,(byte)0x93,(byte)0x4F,
//                (byte)0x0A,(byte)0x08,(byte)0x48,(byte)0x47,(byte)0x5F,(byte)0x39,(byte)0x32,(byte)0x33,(byte)0x37,(byte)0x39,
//                (byte)0x44,(byte)0x11,(byte)0x07,(byte)0x9E,(byte)0xCA,(byte)0xDC,(byte)0x24,(byte)0x0E,(byte)0xE5,(byte)0xA9,
//                (byte)0xE0,(byte)0x93,(byte)0xF3,(byte)0xA3,(byte)0xB5,(byte)0x01,(byte)0x00,(byte)0x40,(byte)0x48,(byte)0x00,
//                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};
//        byte[] raw2 = {
//                (byte)0x02,(byte)0x01,(byte)0x06,(byte)0x02,(byte)0x0A,(byte)0x04,(byte)0x0D,(byte)0xFF,(byte)0xFF,(byte)0xFF,
//                (byte)0x48,(byte)0x47,(byte)0x9E,(byte)0x4F,(byte)0xA8,(byte)0x62,(byte)0x1A,(byte)0x00,(byte)0xFB,(byte)0x3B,
//                (byte)0x0A,(byte)0x08,(byte)0x48,(byte)0x47,(byte)0x5F,(byte)0x39,(byte)0x32,(byte)0x33,(byte)0x37,(byte)0x39,
//                (byte)0x44,(byte)0x11,(byte)0x07,(byte)0x9E,(byte)0xCA,(byte)0xDC,(byte)0x24,(byte)0x0E,(byte)0xE5,(byte)0xA9,
//                (byte)0xE0,(byte)0x93,(byte)0xF3,(byte)0xA3,(byte)0xB5,(byte)0x01,(byte)0x00,(byte)0x40,(byte)0x48,(byte)0x00,
//                (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00};

        byte[] orgAddr = {(byte) 0xEB, (byte) 0x77, (byte) 0x8F, (byte) 0x18, (byte) 0x2C, (byte) 0x64};
        byte[] raw1 = {
                (byte) 0x02, (byte) 0x01, (byte) 0x06, (byte) 0x02, (byte) 0x0A, (byte) 0x04, (byte) 0x0D, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0x48, (byte) 0x47, (byte) 0xC5, (byte) 0xE8, (byte) 0xEB, (byte) 0xD8, (byte) 0x90, (byte) 0x0E, (byte) 0xE0, (byte) 0x9F,
                (byte) 0x0A, (byte) 0x08, (byte) 0x48, (byte) 0x47, (byte) 0x5F, (byte) 0x31, (byte) 0x38, (byte) 0x32, (byte) 0x43, (byte) 0x36,
                (byte) 0x34, (byte) 0x11, (byte) 0x07, (byte) 0x9E, (byte) 0xCA, (byte) 0xDC, (byte) 0x24, (byte) 0x0E, (byte) 0xE5, (byte) 0xA9,
                (byte) 0xE0, (byte) 0x93, (byte) 0xF3, (byte) 0xA3, (byte) 0xB5, (byte) 0x01, (byte) 0x00, (byte) 0x40, (byte) 0x48, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};
        byte[] raw2 = {
                (byte) 0x02, (byte) 0x01, (byte) 0x06, (byte) 0x02, (byte) 0x0A, (byte) 0x04, (byte) 0x0D, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF,
                (byte) 0x48, (byte) 0x47, (byte) 0xC3, (byte) 0x49, (byte) 0x46, (byte) 0x34, (byte) 0x1A, (byte) 0x60, (byte) 0xFB, (byte) 0x15,
                (byte) 0x0A, (byte) 0x08, (byte) 0x48, (byte) 0x47, (byte) 0x5F, (byte) 0x31, (byte) 0x38, (byte) 0x32, (byte) 0x43, (byte) 0x36,
                (byte) 0x34, (byte) 0x11, (byte) 0x07, (byte) 0x9E, (byte) 0xCA, (byte) 0xDC, (byte) 0x24, (byte) 0x0E, (byte) 0xE5, (byte) 0xA9,
                (byte) 0xE0, (byte) 0x93, (byte) 0xF3, (byte) 0xA3, (byte) 0xB5, (byte) 0x01, (byte) 0x00, (byte) 0x40, (byte) 0x48, (byte) 0x00,
                (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00};

//        byte[] buffHi = new byte[4];
//        byte[] buffLo = new byte[4];
//        buffLo[1] = addr[2];
//        buffLo[2] = addr[1];
//        buffLo[3] = addr[0];
//        buffHi[1] = addr[5];
//        buffHi[2] = addr[4];
//        buffHi[3] = addr[3];

        byte[] addr = new byte[6];
        addr[0] = orgAddr[5];
        addr[1] = orgAddr[4];
        addr[2] = orgAddr[3];
        addr[3] = orgAddr[2];
        addr[4] = orgAddr[1];
        addr[5] = orgAddr[0];

        byte[][] address = new byte[6][2];
        short[] tmpAddr = new short[6];
        for (int i = 0; i < 6; i++) {
            address[i][1] = addr[i];
            tmpAddr[i] = ByteUtil.mf_s_toShort(address[i]);
        }
        long HashHi = ((tmpAddr[5] << 16) | (tmpAddr[4] << 8) | tmpAddr[3]);
        long HashLo = ((tmpAddr[2] << 16) | (tmpAddr[1] << 8) | tmpAddr[0]);
//        long seed = ((long)HashHi << 32) | HashLo;
//        DoorlockUtil.mf_v_HashInit(HG_DLL_ADV_HASH, seed);
//        int HashHi = ((tmpAddr[5] * 0x10000) | (tmpAddr[4] * 0x100) | tmpAddr[3]);
//        int HashLo = ((tmpAddr[2] * 0x10000) | (tmpAddr[1] * 0x100) | tmpAddr[0]);
//        long seed = (HashHi * 0x100000000L) | HashLo;
        mf_v_HashInit(HG_DLL_ADV_HASH, HashHi, HashLo);
        long BeaconKey = mf_l_Well512(HG_DLL_ADV_HASH);

        mf_v_Decrypt(BeaconKey, raw1);

        mf_v_Decrypt(BeaconKey, raw2);

    }

//uint32_t BeaconKey = 0;
//uint32_t HashHi = 0;
//uint32_t HashLo = 0;
//
//if (!memcmp(&Data.data[10], "HG", 2))
//{// "HG"
//    HashHi = (Data.address.addr[5] << 16) | (Data.address.addr[4] << 8) | Data.address.addr[3];
//    HashLo = (Data.address.addr[2] << 16) | (Data.address.addr[1] << 8) | Data.address.addr[0];
//    HashInit(HG_DLL_ADV_HASH, ((uint64_t)(HashHi) << 32 | HashLo);
//    BeaconKey = Well512(HG_DLL_ADV_HASH);
//    HG_BLE_ADV_Decrypt(BeaconKey, Data.data);
//}

//Connect AES 128 CBC암호화
//byte[] AES_KEY = {'H','-','G','A','N','G',' ','B','L','E',' ','M','O','D','E','1'};
//Iv Key(16Byte)
}
