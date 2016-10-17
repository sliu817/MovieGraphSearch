package movieRecommendation.model;

public class Country {

	private Integer movieId;
	private String country;
	
	public Country(){}
	
	public Country(Integer mId, String country){
		this.movieId = mId;
		this.country = country;
	}
	
	public String getCountry(){
		return country;
	}
	
	public void setCountry(String c) {
		this.country = c;
	}
	
	public String toString(){
		return "Country [mId=" + movieId 
				+ ", country=" + country + "]";
	}

	public Integer getMovieId() {
		return movieId;
	}
	
}
