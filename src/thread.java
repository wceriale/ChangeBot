import java.util.*;
import java.util.logging.Level;
import java.io.*;

public class thread {
	public static void main(String[] args) {
		// Remove Annoying Output
		java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF); 
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
//		try {
//			File file = new File("./src/list.txt");
//			Scanner input = new Scanner(file);
//			Scanner count = new Scanner(file);
//			int lineC = 0;
//			while(count.hasNextLine()) {
//				lineC++;
//				count.nextLine();
//			}
//			System.out.println(lineC);
//			String[][] lines = new String[5][lineC/5];
//			System.out.println(lines[0].length);
//			
//			// Populates 2D Array
//			for(int i = 0; i < lines.length; i++) {
//				for(int j = 0; j < lines[i].length; j++)
//					lines[i][j] = input.nextLine();
//			}
//			for(int i = 0; i < 10; i++) {
//				Person pers = new Person(lines[i]);
//				pers.start();
//			}
			for(int i = 0; i < 100; i++) {
				FakePeople pers = new FakePeople(3);
				pers.start();
			}
			
			
			
//		} catch(FileNotFoundException e) {
//			System.out.println(e);
//		}
//		for (int i = 1; i <= 5; i++) {
//			Calculator calculator = new Calculator(i);
//			Thread thread = new Thread(calculator);
//			thread.start();
//		}
	}
}
