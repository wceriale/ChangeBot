/**
 * 
 */

/**
 * @author Will
 *
 */



public  class Person {
	String fName;
	String lName;
	String email;
	String country = "UnitedStates";
	String address;
	String zip;
	
	public Person(	String fName,
					String lName,
					String email,
					String address,
					String zip) {
		this.address = address;
		this.lName = lName;
		this.fName = fName;
		this.zip = zip;
		this.email = email;

	}
	
	public void update (String fName,
					String lName,
					String email,
					String address,
					String zip) {
		this.address = address;
		this.lName = lName;
		this.fName = fName;
		this.zip = zip;
		this.email = email;
	}
	
	public String toString() {
		return fName + " " + lName + "\n" + address + "\n" + zip + "\n" + country + "\n" + email;
	}
	
}
