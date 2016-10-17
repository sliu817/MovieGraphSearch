package movieRecommendation.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import movieRecommendation.model.Actor;

public class ActorReader {

	  private final File fFile;
	 
	/**
	   Constructor.
	   @param aFileName full name of an existing, readable file.
	  */
	  public ActorReader(String aFileName){
	    fFile = new File(aFileName);  	    
	  }
	  
	  /** Template method that calls {@link #processLine(String)}.  */
	  public final List<Actor> processLineByLine() throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
	    Scanner scanner = new Scanner(new FileReader(fFile));
	    List<Actor> actors= new ArrayList<Actor>();
	    try {
	      //first use a Scanner to get each line
	      while ( scanner.hasNextLine() ){
	    	  actors.add(processLine( scanner.nextLine() ));
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	      
	    }
	    return actors;
	  }
	  
	  protected Actor processLine(String aLine){
	    //use a second Scanner to parse the content of each line 
	    Scanner scanner = new Scanner(aLine);
	    scanner.useDelimiter("\t");
	    //5::Father of the Bride Part II (1995)::Comedy

	    if ( scanner.hasNext() ){
	      String movieId = scanner.next();
	      String actorId = scanner.next();
	      String actorName = scanner.next();
	      String ranking = scanner.next();
	      return new Actor(Integer.valueOf(movieId), actorId, actorName, Integer.valueOf(ranking));
	    }
	    else {
	      log("Empty or invalid line. Unable to process.");
	      return null;
	    }
	    //no need to call scanner.close(), since the source is a String
	  }
	  
	 
	  
	  private static void log(Object aObject){
	    System.out.println(String.valueOf(aObject));
	  }
	  
	  public static void main(String[]args) throws FileNotFoundException{
		  	ActorReader ar = new ActorReader("database/data/actors.dat");
		  	List<Actor> list = ar.processLineByLine();
		  	for(Actor a : list){
		  		System.out.println(a.toString());
		  	}
	  }
}
