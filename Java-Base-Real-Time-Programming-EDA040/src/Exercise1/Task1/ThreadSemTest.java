package Exercise1.Task1;

import se.lth.cs.realtime.semaphore.MutexSem;
import se.lth.cs.realtime.semaphore.Semaphore;

public class ThreadSemTest extends Thread {
    private Semaphore semaphore;
    private int threadIndex;

    public ThreadSemTest(int threadIndex, Semaphore semaphore) {
        this.threadIndex = threadIndex;
        this.semaphore = semaphore;
    }

    public void run() {
        while(true) {
            semaphore.take();
            for (int i = 0; i < 4; i++) {
                System.out.println(threadIndex + ": " + i);
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            semaphore.give();
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new MutexSem();
        Thread one = new ThreadSemTest(1, semaphore);
        Thread two = new ThreadSemTest(2, semaphore);
        one.start();
        two.start();
    }
}
