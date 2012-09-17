package todo;

import done.ClockInput;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ButtonWatcher extends Thread {
    private ClockInput input;
    private Semaphore inputSemaphore;
    private Monitor monitor;
    private int setTime = -1;

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
                    setTime = input.getValue();
                    break;
                case ClockInput.SET_ALARM:
                    monitor.setAlarm(input.getValue());
                    setTime = -1;
                    break;
                case ClockInput.SHOW_TIME:
                    if (setTime > -1) {
                        monitor.setTime(setTime);
                        setTime = -1;
                    }
                    monitor.disableAlarm();
                    break;
            }
        }
    }
}
