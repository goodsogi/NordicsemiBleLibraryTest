package no.nordicsemi.android.blinky.viewmodels;

import java.util.HashMap;

/**
 * This class includes a small subset of standard GATT attributes for demonstration purposes.
 */
public class SampleGattAttributes {
    private static HashMap<String, String> attributes = new HashMap();

    // Characteristic Define : "00002900-0000-1000-8000-00805f9b34fb" "Characteristic Extended Properties"
// Characteristic Define : "00002901-0000-1000-8000-00805f9b34fb" "Characteristic User Description"
// Characteristic Define : "00002902-0000-1000-8000-00805f9b34fb" "Client Characteristic Configuration"
// Characteristic Define : "00002903-0000-1000-8000-00805f9b34fb" "Server Characteristic Configuration"
// Characteristic Define : "00002904-0000-1000-8000-00805f9b34fb" "Characteristic Presentation Format"
// Characteristic Define : "00002905-0000-1000-8000-00805f9b34fb" "Characteristic Aggregate Format"
// Characteristic Define : "00002906-0000-1000-8000-00805f9b34fb" "Valid Range"
// Characteristic Define : "00002907-0000-1000-8000-00805f9b34fb" "External Report Reference"
// Characteristic Define : "00002908-0000-1000-8000-00805f9b34fb" "Report Reference"
// Characteristic Define : "00002909-0000-1000-8000-00805f9b34fb" "Number of Digitals"
// Characteristic Define : "0000290a-0000-1000-8000-00805f9b34fb" "Value Trigger Setting"
// Characteristic Define : "0000290b-0000-1000-8000-00805f9b34fb" "Environmental Sensing Configuration"
// Characteristic Define : "0000290c-0000-1000-8000-00805f9b34fb" "Environmental Sensing Measurement"
// Characteristic Define : "0000290d-0000-1000-8000-00805f9b34fb" "Environmental Sensing Trigger Setting"
// Characteristic Define : "0000290e-0000-1000-8000-00805f9b34fb" "Time Trigger Setting"
    public static final String CLIENT_CHARACTERISTIC_2900_NAME          = "00002900-0000-1000-8000-00805F9B34FB";
    public static final String CLIENT_CHARACTERISTIC_2901               = "00002901-0000-1000-8000-00805F9B34FB";
    public static final String CLIENT_CHARACTERISTIC_2902_CONFIG        = "00002902-0000-1000-8000-00805F9B34FB";
    public static final String CLIENT_CHARACTERISTIC_2904_INTERVAL      = "00002904-0000-1000-8000-00805F9B34FB";
    public static final String CLIENT_CHARACTERISTIC_2905_RANGE         = "00002905-0000-1000-8000-00805F9B34FB";

    public static final String SERVICE_TEST_16                          = "0000FE8B-0000-1000-8000-00805F9B34FB";
    public static final String SERVICE_GATEWAY                          = "000018AA-0000-1000-8000-00805F9B34FB";
    public static final String CHARACTERISTIC_GATEWAY                   = "00002AAA-0000-1000-8000-00805F9B34FB";

    public static final String SERVICE_GATEWAY_NAME                     = "Gateway Service";
    public static final String CHARACTERISTIC_GATEWAY_NAME              = "Gateway characteristic";

    public static final String SERVICE_TEST_128                         = "6E400001-B5A3-F393-E0A9-E50E24DCCA9E";
    public static final String SERVICE_TANK_MAIN                        = "48400001-B5A3-F393-E0A9-E50E24DCCA9E";
    public static final String CHARATERISTIC_TANK_WRITE                 = "48400002-B5A3-F393-E0A9-E50E24DCCA9E";
    public static final String CHARATERISTIC_TANK_READ                  = "48400003-B5A3-F393-E0A9-E50E24DCCA9E";

    public static final String SERVICE_TANK_MAIN_NAME                   = "TANK Service";
    public static final String CHARATERISTIC_TANK_WRITE_NAME            = "TANK Write";
    public static final String CHARATERISTIC_TANK_READ_NAME             = "TANK Read";

    static {
        // Gateway Service
        attributes.put(SERVICE_GATEWAY, "Gateway Service");
        // Gateway characteristic
        attributes.put(CHARACTERISTIC_GATEWAY, "Gateway characteristic");

        // TANK Service
        attributes.put(SERVICE_TANK_MAIN, SERVICE_TANK_MAIN_NAME);
        // TANK write
        attributes.put(CHARATERISTIC_TANK_WRITE, CHARATERISTIC_TANK_WRITE_NAME);
        // TANK Read
        attributes.put(CHARATERISTIC_TANK_READ, CHARATERISTIC_TANK_READ_NAME);
    }

    public static String lookup(String uuid, String defaultName) {
        String name = attributes.get(uuid);
        return name == null ? defaultName : name;
    }
}

