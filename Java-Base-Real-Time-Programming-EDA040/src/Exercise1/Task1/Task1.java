package Exercise1.Task1;

import se.lth.cs.realtime.semaphore.MutexSem;
import se.lth.cs.realtime.semaphore.Semaphore;

public class Task1 extends Thread {
    private int number;
    private Semaphore semaphore;

    public Task1(int number, Semaphore semaphore) {
        this.number = number;
        this.semaphore = semaphore;
    }

    public void run() {
        while (true) {
            semaphore.take();
            System.out.print(number);
            semaphore.give();

            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Semaphore semaphore = new MutexSem();
        Thread one = new Task1(1, semaphore);
        Thread two = new Task1(2, semaphore);
        one.start();
        two.start();
    }
}
