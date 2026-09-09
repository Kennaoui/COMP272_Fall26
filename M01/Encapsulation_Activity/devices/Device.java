package devices;

public class Device {

    public void status() {
        System.out.println("Inside status()");
    }

    
    public class Screen {
        public void show() {
            System.out.println("Inside Screen.show()");
        }
    }

    public void testInsideDevice() {
        // TEST 6: Inner-class test from its enclosing class.
        // Screen is declared in Device, package devices.
        // Screen screen = new Screen();
        // screen.show();
    }
}
