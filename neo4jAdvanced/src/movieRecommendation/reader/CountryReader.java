package movieRecommendation.reader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import movieRecommendation.model.Country;

public class CountryReader {
	 	private final File fFile;
	 
		/**
		   Constructor.
		   @param aFileName full name of an existing, readable file.
		  */
		  public CountryReader(String aFileName){
		    fFile = new File(aFileName);  	    
		  }
		  
		  /** Template method that calls {@link #processLine(String)}.  */
		  public final List<Country> processLineByLine() throws FileNotFoundException {
		    //Note that FileReader is used, not File, since File is not Closeable
		    Scanner scanner = new Scanner(new FileReader(fFile));
		    List<Country> countries= new ArrayList<Country>();
		    try {
		      //first use a Scanner to get each line
		      while ( scanner.hasNextLine() ){
		    	  countries.add(processLine( scanner.nextLine() ));
		      }
		    }
		    finally {
		      //ensure the underlying stream is always closed
		      //this only has any effect if the item passed to the Scanner
		      //constructor implements Closeable (which it does in this case).
		      scanner.close();
		      
		    }
		    return countries;
		  }
		  
		  protected Country processLine(String aLine){
		    //use a second Scanner to parse the content of each line 
		    Scanner scanner = new Scanner(aLine);
		    scanner.useDelimiter("\t");
		    //5::Father of the Bride Part II (1995)::Comedy

		    if ( scanner.hasNext() ){
		    
		      String movieId = scanner.next();
		  	  System.out.println(movieId);
		  
		      String country = scanner.next();
		      return new Country(Integer.valueOf(movieId), country);
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
			  	CountryReader cr = new CountryReader("database/data/countries.dat");
			  	List<Country> list = cr.processLineByLine();
			  	for(Country c : list){
			  		System.out.println(c.toString());
			  	}
		  }
}
