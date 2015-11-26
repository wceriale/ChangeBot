import java.util.*;
import java.util.logging.Level;
import java.io.*;

public class thread {
	
	// Grabs available processes 
	// Threads (for loop bounds) should equal available processors for
	// max speed
	// ERROR WITH INTEL HYPERTHREADED PROCESSERS
	// RETURNS TWICE DESIRED VALUE, NO WAY TO TEST D:
	// 
	// I've tested it a lot. It is more efficient to just have
	// the number of available processors even with the Intel problem.
	// Also it is much slower if you have available processors + 1
	public static final int NUM_THREADS = Runtime.getRuntime().availableProcessors();
	
	public static void main(String[] args) {
		
		
		// Remove Annoying Output
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		
		
		System.out.println(NUM_THREADS + " Processors Available");
		
		int peopleWanted = promptForPeople();
		long startTime = System.nanoTime(); // Starting Time
		
		// needed to test if all threads have ended
		List<Thread> threads = new ArrayList<Thread>();
		
		// Divides wanted people by how many threads we'll use
		int[] peoplePerThread = new int[NUM_THREADS];
		
		// used to actually generate the correct number of people
		// and maximizes efficiency by having minimum number
		// of people per thread. -g
		for(int i = 0; i < NUM_THREADS; i++) {
			int peeps = peopleWanted / (NUM_THREADS - i);
			peoplePerThread[i] = peeps;
			if(peopleWanted % (NUM_THREADS - i) != 0) {
				peopleWanted--;
				peoplePerThread[i]++;
			}
			peopleWanted -= peeps;
		}
		// DEBUG: checking if it has the right number of people per thread.
		// eventually can get rid of array builder for loop and just compute
		// in thread starter for loop. -g
		System.out.println(Arrays.toString(peoplePerThread));
		for(int i = 0; i < NUM_THREADS; i++) {
			if (peoplePerThread[i] > 0) {
				threads.add(new Thread(new FakePeople(peoplePerThread[i])));
				threads.get(i).start();
			}
		}
		
		// Loop finishes when all threads are done
		for(int i = 0; i < threads.size(); i++) {
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// Prints all people at the very end :D
		System.out.println(FakePeople.listOfFakePeople);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		long endTime = System.nanoTime();
		// Timestamp of how long processing took in seconds
		double timeElapsed = (endTime - startTime) / 1000000000.0;
		System.out.println("Took " + timeElapsed + " seconds"); 

	}
	
	// prompts user for number of people to generate
	// and returns it.
	// 
	// honestly looks pretty nasty. Error checking just for kicks.
	// Got a better way to do it? -g
	public static int promptForPeople() {
		Scanner console = new Scanner(System.in);
		System.out.print("number of people to generate? " );
		boolean checkInput = true;
		int people = 0;
		while (checkInput) {
			while (!console.hasNextInt()) {
				String throwAway = console.next();
				System.out.print("invalid input. please enter an integer greater than zero. ");
			} 
			people = console.nextInt();
			if(people > 0) {
				checkInput = false;
			} else {
				System.out.print("invalid input. please enter an integer greater than zero. ");
			}
		}
		return people;
	}
}
