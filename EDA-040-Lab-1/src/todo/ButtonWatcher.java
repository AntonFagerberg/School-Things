package todo;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ButtonWatcher extends Thread {
    private ClockInput input;
    private Semaphore inputSemaphore;
    private Monitor monitor;

    public ButtonWatcher(ClockInput i, Monitor m) {
        input = i;
        inputSemaphore = input.getSemaphoreInstance();
        monitor = m;
    }

    @Override
    public void run() {
        while (monitor.isAlive()) {
            inputSemaphore.take();
            switch (input.getChoice()) {
                case ClockInput.SET_TIME:
                    monitor.setTime(input.getValue());
                    break;
                case ClockInput.SET_ALARM:
                    monitor.setAlarm(input.getValue());
                    break;
                case ClockInput.SHOW_TIME:
                    monitor.disableAlarm();
                    break;
            }
        }
    }
}
