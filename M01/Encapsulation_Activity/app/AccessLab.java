package app;

import devices.Device;

public class AccessLab {

    private void secret() {
        System.out.println("Inside secret()");
    }

    public static void main(String[] args) {
        // TEST 1: Same class test, call secret() from its own class.
        // AccessLab accessLab = new AccessLab();
        // accessLab.secret();
        
        // TEST 2: Same file test, Both classes are in this file and in package app.
        // LocalHelper helper = new LocalHelper();
        // helper.message();

        // TEST 3: Different-file and different-package test.
        // Device device = new Device();
        // device.status();

        // TEST 4: Inheritance test.
        // First make AccessLab extend Device and set status() to protected.
        // AccessLab is in package app; Device is in package devices.
        // Uncomment and test only one call at a time.
        // AccessLab child = new AccessLab();
        // child.status();
        // Device parent = new Device();
        // parent.status();

        // TEST 5: Inner-class test from another class.
        // Screen is an inner class of Device in package devices.
        // AccessLab is in package app.
        // Device device = new Device();
        // Device.Screen screen = device.new Screen();
        // screen.show();

        // Call TEST 6, which is written inside Device.
        // Device device = new Device();
        // device.testInsideDevice();
    }
}

class LocalHelper {

    public void message() {
        System.out.println("Inside LocalHelper.message()");
    }


}
