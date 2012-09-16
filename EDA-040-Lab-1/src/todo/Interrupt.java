package todo;

import se.lth.cs.realtime.RTInterrupted;

public class Interrupt implements Runnable {
    private long waitTime = System.currentTimeMillis();
    private static Monitor monitor;

    public Interrupt(Monitor m) {
        monitor = m;
    }

    public static void oneTick() {
        monitor.increment();
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
            oneTick();
        }
    }
}
