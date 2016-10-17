package movieRecommendation.model;

public class Genre {
	private Integer movieId;
	private String genre;
	
	public Genre(){}
	
	public Genre(Integer mId, String genre){
		this.movieId = mId;
		this.genre = genre;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public void setGenre(String genre){
		this.genre = genre;
	}
	
	public Integer getMovieId() {
		return movieId;
	}
	
	public String toString(){
		return "Genre [mId=" + movieId 
				+ ", genre=" + genre + "]";
	}
}
