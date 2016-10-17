package bean;


public class Movie {

	private Integer movieId;
	private String title;
	private String imdbID;
	private String spanishTitle;
	private String imdbPictureURL;
	private String year;
	private String country;

	
	public Movie(){}
	
	
	public Movie(Integer movieId, String title,  String imdbPictureURL, String year) {
		this.movieId = movieId;
		this.title = title;
		this.imdbPictureURL = imdbPictureURL;
		this.year = year;
		
	}
	
	public Movie(Integer movieId, String title,  String imdbPictureURL, String year, String country) {
		this.movieId = movieId;
		this.title = title;
		this.imdbPictureURL = imdbPictureURL;
		this.year = year;
		this.country = country;
		
	}
	public Integer getMovieId() {
		return movieId;
	}
	
	public void setMovieId(Integer movieId) {
		this.movieId = movieId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getImdbPictureURL() {
		return imdbPictureURL;
	}

	public void setImdbPictureURL(String imdbPictureURL) {
		this.imdbPictureURL = imdbPictureURL;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	public String toString(){
		return "Movie [movieId=" + movieId + ", title=" + title
				+ ", URL=" + imdbPictureURL + ", year=" + year + "]";
	}

	

	
}
