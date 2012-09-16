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

        new Ticker(monitor).start();
//        new Thread(new Interrupt(monitor)).start();
        new ButtonWatcher(input, monitor).start();
    }

    public void terminate() {
        monitor.kill();
    }

	// The AlarmClock thread is started by the simulator. No
	// need to start it by yourself, if you do you will get
	// an IllegalThreadStateException. The implementation
	// below is a simple alarmclock thread that beeps upon 
	// each keypress. To be modified in the lab.
	public void run() {
        while (monitor.isAlive()) {
            yield();
        }
	}
}
