package Exercise1.Task4;

import se.lth.cs.realtime.semaphore.CountingSem;
import se.lth.cs.realtime.semaphore.MutexSem;
import se.lth.cs.realtime.semaphore.Semaphore;

/**
 * The buffer.
 */
class Buffer {
	Semaphore mutex; // For mutual exclusion blocking.
	Semaphore free; // For buffer full blocking.
	Semaphore avail; // For blocking when no data is available.
	String buffData; // The actual buffer.
    int putIndex, getIndex, size;
    String[] ringBuffer;

	Buffer() {
        putIndex = getIndex = 0;
        size = 8;
        ringBuffer = new String[size];
		mutex = new MutexSem();
		free = new CountingSem(8);
		avail = new CountingSem();
	}

	void putLine(String input) {
		free.take(); // Wait for buffer empty.
		mutex.take(); // Wait for exclusive access.
        ringBuffer[putIndex] = input;
        putIndex = ++putIndex % size;
		mutex.give(); // Allow others to access.
		avail.give(); // Allow others to get line.
	}

	String getLine() {
		// Exercise 2 ...
		// Here you should add code so that if the buffer is empty, the
		// calling process is delayed until a line becomes available.
		// A caller of putLine hanging on buffer full should be released.
		// ...
		avail.take();
        mutex.take();
        String data = ringBuffer[getIndex];
        getIndex = ++getIndex % size;
        mutex.give();
        free.give();
        return data;
	}
}
