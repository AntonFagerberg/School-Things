package lift;

public class Monitor {
    private final static int FLOORS = 7;
    private int here = 0;
    private int there = 0;
    private int load = 0;
    private int direction = 1;
    private int[] waitEntry = new int[FLOORS];
    private int[] waitExit = new int[]{-1, -1, -1, -1};
    private LiftView liftView;

    public Monitor(LiftView liftView) {
        this.liftView = liftView;
    }

    public synchronized int move() {
        if (here == FLOORS - 1)
            direction = -1;
        else if (here == 0)
            direction = 1;

        there += direction;
        return there;
    }

    private synchronized int waitSum() {
        int waitSum = 0;

        for (int count : waitEntry)
            waitSum += count;

        return waitSum;
    }

    public synchronized int stop() {
        here = there;
        notifyAll();

        while ((load <= 0 && waitSum() == 0) || (waitEntry[here] > 0 && load < 4) || (waitExit[0] == here || waitExit[1] == here || waitExit[2] == here || waitExit[3] == here)) {
            try { wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

        return here;
    }

    public synchronized void call(int from, int to) {
        waitEntry[from]++;
        liftView.drawLevel(from, waitEntry[from]);
        notifyAll();

        while (here != there || here != from || load >= 4) {
            try { wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

        int waitIndex = 0;
        while (waitExit[waitIndex] >= 0)
            waitIndex++;

        waitExit[waitIndex] = to;
        liftView.drawLift(here, ++load);
        liftView.drawLevel(here, --waitEntry[from]);
        notifyAll();

        while (here != there || here != to) {
            try { wait(); }
            catch (InterruptedException e) { e.printStackTrace(); }
        }

        waitExit[waitIndex] = -1;
        liftView.drawLift(here, --load);
        notifyAll();
    }
}
