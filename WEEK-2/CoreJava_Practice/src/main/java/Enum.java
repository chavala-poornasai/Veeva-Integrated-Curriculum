enum TrafficLight {
    RED, YELLOW, GREEN
}

public class Enum {
    public static void main(String[] args) {

        TrafficLight signal = TrafficLight.RED;

        switch (signal) {

            case RED:
                System.out.println("STOP");
                break;

            case YELLOW:
                System.out.println("WAIT");
                break;

            case GREEN:
                System.out.println("GO");
                break;
        }
    }
}