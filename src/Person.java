/**
 * 
 */

/**
 * @author Will
 *
 */


public  class Person extends Thread {
	private String[] lines;
	
	public Person(String[] lines) {
		if(lines == null)
			throw new IllegalArgumentException();
		this.lines = lines;
	}
	
	public void run() {
		for(int i = 0; i < lines.length; i++) {
			System.out.println(lines[i]);
		}
		System.out.print(this.getName() + " is done");
		this.interrupt();
	}
}
