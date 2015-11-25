import java.util.*;
import java.util.logging.Level;
import java.io.*;

public class thread {
	public static void main(String[] args) {
		
		int peopleWanted = 12;
		
		long startTime = System.nanoTime(); // Starting Time

		// Remove Annoying Output
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		
		
		// Grabs available processes 
		// Threads (for loop bounds) should equal available processors for
		// max speed
		// ERROR WITH INTEL HYPERTHREADED PROCESSERS
		// RETURNS TWICE DESIRED VALUE, NO WAY TO TEST D:
		int processors = Runtime.getRuntime().availableProcessors();
		System.out.println(processors + " Processors Available");
		
		// Needed to know when all threads are done
		Thread[] threads = new Thread[processors]; 
		
		// Divides wanted people by how many threads we'll use
		int peoplePerThread = peopleWanted/processors;
		
		for(int i = 0; i < processors; i++) {
			FakePeople pers = new FakePeople(peoplePerThread);
			threads[i] = pers;
			pers.start();
		}
		
		// Loop finishes when all threads are done
		for(int i = 0; i < threads.length; i++)
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		// Prints all people at the very end :D
		System.out.println(FakePeople.listOfFakePeople);
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println();

		// Timestamp of how long it took in nano seconds
		long endTime = System.nanoTime();
		System.out.println("Took "+(endTime - startTime) + " ns"); 

	}
}
