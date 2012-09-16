package todo;

import se.lth.cs.realtime.RTInterrupted;

public class Ticker extends Thread {
    private Monitor monitor;
    private long waitTime = System.currentTimeMillis();

    public Ticker(Monitor m) {
        monitor = m;
    }

    @Override
    public void run() {
        long diff;

        while (monitor.isAlive()) {
            while ((diff = waitTime - System.currentTimeMillis()) > 0) {
                try {
                    Thread.sleep(diff);
                } catch (InterruptedException e) {
                    throw new RTInterrupted(e.toString());
                }
            }

            waitTime += 1000 + diff;
            monitor.increment();
        }
    }
}
