package movieRecommendation.neo4j;

import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;
  
public class IndexCreator {
    private static final String DB_PATH = "/home/fograinwind/workspace/neo4jAdvanced/database/graphDB";
  
    public static void main(String args[]) {
        /*
         * When I loaded the graph, I created auto_index nodes on userId and
         * movieId but I want to be able to search by Movie name, so I have to
         * create an index aftewards, hence this class
         */
    	System.out.println("Starting database...");
    	
       GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
          
       {
    	   IndexDefinition indexDefinition;
    	   try(Transaction tx = graphDb.beginTx())
    	   {
    		   Schema schema = graphDb.schema();
    		   indexDefinition = schema.indexFor(DynamicLabel.label("User")).on("userId").create();
    		   tx.success();	   
    	   }
    	   try(Transaction tx = graphDb.beginTx())
           {
        	   Schema schema = graphDb.schema();
        	   schema.awaitIndexOnline(indexDefinition,10, TimeUnit.SECONDS);	   
           }
       }
       
    	
       
       
       
       
       
       
       System.out.println("Shutting down database...");
       graphDb.shutdown();
   }
       
      
    
} 
