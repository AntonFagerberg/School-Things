package lift;

public class Person extends Thread {
    private Monitor monitor;

    public  Person(Monitor monitor) {
        this.monitor = monitor;
    }

    private int randomFloor() {
        return (int) (Math.random() * 7);
    }

    public void run() {
        while (true) {
            int from = randomFloor();
            int to = randomFloor();

            while (from == to) {
                to = randomFloor();
            }

            try { Thread.sleep((int) (46000 * Math.random())); }
            catch (InterruptedException e) { e.printStackTrace(); }

            monitor.call(from, to);
        }
    }
}
