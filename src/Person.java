/**
 * 
 */

/**
 * @author Will
 * @author Garrett
 *
 */



public  class Person {
	private String fName;
	private String lName;
	private String email;
	private String country;
	private String address;
	private String zip;
	// All the information needed for a person
	
	// Sets values of a person
	public Person  (String fName,
					String lName,
					String email,
					String address,
					String zip) {
		
		this.address = address;
		this.lName = lName;
		this.fName = fName;
		this.zip = zip;
		this.email = email;
		this.country = "UnitedStates";
	}
	
	// Updates values
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
	
	// toString form - Contains \n
	public String toString() {
		return fName + " " + lName + "\n" + address + "\n" + zip + "\n" + country + "\n" + email;
	}
	
}
