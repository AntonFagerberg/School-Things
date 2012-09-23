package exercise3.queue;

import se.lth.cs.realtime.RTInterrupted;

class YourMonitor {
	private int nCounters;
    private boolean[] freeCounter = new boolean[]{false, false, false};
    private int waitNumber = 0;
    private int serveNumber = 0;
    private int freeCounterIndex = -1;
    private DispData displayData = new DispData();
	// Put your attributes here...

	YourMonitor(int n) { 
		nCounters = n;
		// Initialize your attributes here...
	}

	/**
	 * Return the next queue number in the intervall 0...99. 
	 * There is never more than 100 customers waiting.
	 */
	synchronized int customerArrived() {
        waitNumber++;
        notifyAll();
        return waitNumber;
	}

	/**
	 * Register the clerk at counter id as free. Send a customer if any. 
	 */
	synchronized void clerkFree(int id) {
		freeCounter[id] = true;
        notifyAll();
	}

	/**
	 * Wait for there to be a free clerk and a waiting customer, then
	 * return the queue number of next customer to serve and the counter
	 * number of the engaged clerk.
	 */
	synchronized DispData getDisplayData() throws InterruptedException {
        freeCounterIndex = -1;

        while (freeCounterIndex < 0) {
            if (waitNumber > serveNumber) {
                int i = 0;
                while (freeCounterIndex < 0 && i < nCounters) {
                    if (freeCounter[i]) {
                        freeCounterIndex = i;
                        i = nCounters;
                    } else {
                        i++;
                    }
                }
            }

            if (freeCounterIndex < 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    throw new RTInterrupted(e);
                }
            }
        }

        freeCounter[freeCounterIndex] = false;

        displayData.counter = freeCounterIndex;
        displayData.ticket = ++serveNumber;

        return displayData;
	}
}
