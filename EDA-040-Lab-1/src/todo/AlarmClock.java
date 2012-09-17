package todo;
import done.*;
import se.lth.cs.realtime.semaphore.Semaphore;

public class AlarmClock extends Thread {

	private static ClockInput	input;
	private static ClockOutput	output;
	private static Semaphore	sem;
    private Monitor monitor;

    public AlarmClock(ClockInput i, ClockOutput o) {
        input = i;
        output = o;
        sem = input.getSemaphoreInstance();
        monitor = new Monitor(input, output);

//        new Thread(new Interrupt(monitor)).start();
        new Ticker(monitor).start();
        new ButtonWatcher(input, monitor).start();
    }

    public void terminate() {
        monitor.kill();
    }

    public void run() {

    }
}
