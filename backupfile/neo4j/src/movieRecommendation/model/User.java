package movieRecommendation.model;


public class User {

	
	/*	new File(DATA+'users.dat').eachLine {def line ->
	def components = line.split('::');
	def userVertex = g.addVertex(['type':'User', 'userId':components[0].toInteger(), 'gender':components[1], 'age':components[2].toInteger()]);
	def occupation = occupations[components[3].toInteger()];
	def hits = g.V('occupation',occupation).iterator();
	def occupationVertex = hits.hasNext() ? hits.next() : g.addVertex(['type':'Occupation', 'occupation':occupation]);
	g.addEdge(userVertex, occupationVertex, 'hasOccupation');*/
	
	private Integer userID;
	private String gender;
	private Integer age;
	private String occupation;
	private String zipCode;
    private String state;

	public User(Integer userID, String gender, Integer age, String occupation,
			String zipCode, String state) {
		this.userID = userID;
		this.gender = gender;
		this.age = age;
		this.occupation = occupation;
		this.zipCode = zipCode;
		this.state = state;
	}


	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [userID=" + userID + ", gender=" + gender + ", age=" + age
				+ ", occupation=" + occupation + ", zipCode=" + zipCode
				+ ", state=" + state + "]";
	}
}

