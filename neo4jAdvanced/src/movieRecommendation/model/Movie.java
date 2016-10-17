package movieRecommendation.model;
import java.util.List;

public class Movie {

	private Integer movieId;
	private String title;
	private String imdbID;
	private String spanishTitle;
	private String imdbPictureURL;
	private String year;

	
	public Movie(){}
	
	public Movie(Integer movieId, String title,  String imdbPictureURL, String year) {
		this.movieId = movieId;
		this.title = title;
		this.imdbPictureURL = imdbPictureURL;
		this.year = year;
		
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
	
	public String toString(){
		return "Movie [movieId=" + movieId + ", title=" + title
				+ ", URL=" + imdbPictureURL + ", year=" + year + "]";
	}
	

	
}
