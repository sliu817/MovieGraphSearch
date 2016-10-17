package bean;

public class Actor {
	private Integer movieId;
	private String actorId;
	private String actorName;
	private Integer ranking;
	
	public Actor(){}
	
	public Actor(Integer mId, String aId, String aName, Integer rank){
		this.movieId = mId;
		this.actorId = aId;
		this.actorName = aName;
		this.ranking = rank;
	}
	
	public Actor( Integer mId, String aId, String aName){
		this.movieId = mId;
		this.actorId = aId;
		this.actorName = aName;

	}
	
	public void setMovieId(Integer mId){
		this.movieId = mId;
	}
	
	public Integer getMovieId(){
		return movieId;
	}
	
	public void setActorId(String aId){
		this.actorId = aId;
	}
	
	public String getActorId(){
		return actorId;
	}
	
	public void setActorName(String aName){
		this.actorName = aName;
	}
	
	public String getActorName(){
		return actorName;
	}
	
	public void setRanking(Integer rank){
		this.ranking = rank;
	}
	
	public Integer getRanking(){
		return ranking;
	}
	
	public String toString(){
		return "Actor [actorId=" + actorId + ", actorName=" + actorName
				+ ", ranking=" + ranking + "]";
	}
}
