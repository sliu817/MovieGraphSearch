package movieRecommendation.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import movieRecommendation.model.Genre;

public class GenresReader {
	private final File fFile;
	 
	/**
	   Constructor.
	   @param aFileName full name of an existing, readable file.
	  */
	  public GenresReader(String aFileName){
	    fFile = new File(aFileName);  	    
	  }
	  
	  /** Template method that calls {@link #processLine(String)}.  */
	  public final List<Genre> processLineByLine() throws FileNotFoundException {
	    //Note that FileReader is used, not File, since File is not Closeable
	    Scanner scanner = new Scanner(new FileReader(fFile));
	    List<Genre> genres= new ArrayList<Genre>();
	    try {
	      //first use a Scanner to get each line
	      while ( scanner.hasNextLine() ){
	    	  genres.add(processLine( scanner.nextLine() ));
	      }
	    }
	    finally {
	      //ensure the underlying stream is always closed
	      //this only has any effect if the item passed to the Scanner
	      //constructor implements Closeable (which it does in this case).
	      scanner.close();
	      
	    }
	    return genres;
	  }
	  
	  protected Genre processLine(String aLine){
	    //use a second Scanner to parse the content of each line 
	    Scanner scanner = new Scanner(aLine);
	    scanner.useDelimiter("\t");
	    //5::Father of the Bride Part II (1995)::Comedy

	    if ( scanner.hasNext() ){
	    
	      String movieId = scanner.next();
	  	  System.out.println(movieId);
	  
	      String genre = scanner.next();
	      return new Genre(Integer.valueOf(movieId), genre);
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
		  	GenresReader gr = new GenresReader("database/data/genres.dat");
		  	List<Genre> list = gr.processLineByLine();
		  	for(Genre g : list){
		  		System.out.println(g.toString());
		  	}
	  }
}
