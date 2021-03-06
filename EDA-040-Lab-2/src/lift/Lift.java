package lift;

public class Lift extends Thread {
    private Monitor monitor;
    private LiftView liftView;

    public Lift(Monitor monitor, LiftView liftView) {
        this.monitor = monitor;
        this.liftView = liftView;
    }

    public void run() {
        int here = monitor.stop();

        while (true) {
            liftView.moveLift(here, monitor.move());
            here = monitor.stop();
        }
    }
}
