package bean;

public class Director {
	private Integer movieId;
	private String directorId;
	private String directorName;
	
	public Director(){}
	
	public Director(Integer mId, String dId, String dName){
		this.movieId = mId;
		this.directorId = dId;
		this.directorName = dName;
	}
	
	public Director(String dName){
		this.directorName = dName;
	}
	
	public Integer getMovieId() {
		return movieId;
	}
	
	public String getDirectorId() {
		return directorId;
	}
	
	public String getDirectorName(){
		return directorName;
	}
	
	public void setDirectorName(String dName){
		this.directorName = dName;
	}
	
	public String toString(){
		return "Director [mId=" + movieId 
				+ ", directorName=" + directorName + "]";
	}
}
