/**
 * Class Monitor
 * To synchronize dining philosophers.
 *
 * @author Serguei A. Mokhov, mokhov@cs.concordia.ca
 */
public class Monitor {
	// STUDENT
	int count;
	boolean[] eating;
	boolean speaking = false;

	/**
	 * Constructor
	 */
	public Monitor(int piNumberOfPhilosophers) {
		// STUDENT
		count = piNumberOfPhilosophers;
		if(count > 0){
			eating = new boolean[count];
		}

//		else{
//			System.out.println(piNumberOfPhilosophers + " is not a positive decimal integer.");
//		}
	}

	/**
	 * Grants request (returns) to eat when both chopsticks/forks are available.
	 * Else forces the philosopher to wait()
	 */
	public synchronized void pickUp(final int piTID) {
		// STUDENT
		try {
			// While either of my neighbours are eating, I cannot eat
			while (eating[(piTID) % count] || eating[(piTID + (count - 2)) % count]) {
				wait();
			}
			// When both neighbours are done eating, I start eating
			eating[piTID-1] = true;
		} catch (InterruptedException e) {
			System.err.println("Monitor.pickUp():");
//			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * When a given philosopher's done eating, they put the chopstiks/forks down
	 * and let others know they are available.
	 */
	public synchronized void putDown(final int piTID) {
		// STUDENT
		eating[piTID-1] = false;
		notifyAll();
	}

	/**
	 * Only one philopher at a time is allowed to philosophy
	 * (while she is not eating).
	 */
	public synchronized void requestTalk() {
		// STUDENT
		try {
			// I cannot talk as long as someone else is speaking
			while (speaking) {
				wait();
			}
			// When no one else is talking, I can start talking
			speaking = true;
		} catch (InterruptedException e) {
			System.err.println("Monitor.requestTalk():");
//			DiningPhilosophers.reportException(e);
			System.exit(1);
		}
	}

	/**
	 * When one philosopher is done talking stuff, others
	 * can feel free to start talking.
	 */
	public synchronized void endTalk() {
		// STUDENT
		speaking = false;
		notifyAll();
	}
}