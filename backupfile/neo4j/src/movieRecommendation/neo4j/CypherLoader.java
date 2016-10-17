/**
 * Copyright (C) 2014 Sha Liu   liusha817@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package movieRecommendation.neo4j;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import movieRecommendation.model.Movie;
import movieRecommendation.model.Rating;
import movieRecommendation.model.User;
import movieRecommendation.reader.MovieReader;
import movieRecommendation.reader.RatingReader;
import movieRecommendation.reader.UserReader;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseBuilder;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.factory.GraphDatabaseSettings;
public class CypherLoader {
	private static final String DB_PATH = "/home/fograinwind/workspace/neo4j/target/movieDB";
	private static final String DATA_PATH = "target/data/";
	String resultString;
	String columnsString;
	String nodeResult;
	String rows = "";

	public static void main(String[] args) {
		CypherLoader javaQuery = new CypherLoader();
		javaQuery.run();
	}

	void run() {

		// Create a graphDB with auto indexing of userId and movieId
		GraphDatabaseService db = new GraphDatabaseFactory()
		.newEmbeddedDatabase(DB_PATH);

		//Please note that we did *not* create an index on title, see the "IndexCreator" class
		
		// Movie Nodes
		try {
			createOccupation(db);
			createGenera(db);
			createMovies(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- movies.dat missing from directory"
					+ DATA_PATH);
		}

		// User Nodes
		try {
			createUsers(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- users.dat missing from directory"
					+ DATA_PATH);
		}
		// Rating relations
		try {
			createRatings(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- ratings.dat missing from directory"
					+ DATA_PATH);
		}

		db.shutdown();
	}
	
	@SuppressWarnings("deprecation")
	private void createOccupation(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the MovieLens file and returns a list of POJO used
		// to feed the graph
		UserReader parser = new UserReader(DATA_PATH + "users.dat");
		ArrayList<String> occupation = parser.getOccupation();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();
		
		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (String occ : occupation) {
				// Create Movie node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("occupation", occ);

				String query = "CREATE (n:Occupation {occupation :{occupation}})";					
				engine.execute(query, params);			
			}
			System.out
					.println("Occupation transaction marked as success - Commit ["
							+ occupation.size() + "] occupation nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing occupation nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Occupation transaction end");
			transaction.close();
		}

	}
	
	@SuppressWarnings("deprecation")
	private void createGenera(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the MovieLens file and returns a list of POJO used
		// to feed the graph
		MovieReader parser = new MovieReader(DATA_PATH + "movies.dat");
		ArrayList<String> generas = parser.getGeneras();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();
		
		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (String ge : generas) {
				// Create Movie node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("genera", ge);

				String query = "CREATE (n:Genera{genera :{genera}})";					
				engine.execute(query, params);			
			}
			System.out
					.println("Genera transaction marked as success - Commit ["
							+ generas.size() + "] genera nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing genera nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("genera transaction end");
			transaction.close();
		}

	}
	

	@SuppressWarnings("deprecation")
	private void createMovies(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the MovieLens file and returns a list of POJO used
		// to feed the graph
		MovieReader parser = new MovieReader(DATA_PATH + "movies.dat");
		List<Movie> movies = parser.processLineByLine();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();

		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (Movie m : movies) {
				// Create Movie node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("movieId", m.getMovieId());
				params.put("title", m.getTitle());
				//System.out.println(m.getTitle());
				
				String query = "CREATE (n:Movie{movieId :{movieId}, title :{title} })";
				engine.execute(query,params);
				
				//Create if needed the generas
				for (String g : m.getGeneras()) {
					String query3 = "MATCH (m:Movie{title:\""+m.getTitle()+"\"}),(g:Genera{genera:\""+g+"\"}) CREATE (m)-[r:hasGenera]->(g) return r;";		
				engine.execute(query3);
				}
				
			}
			
		
			System.out
					.println("Movies transaction marked as success - Commit ["
							+ movies.size() + "] movies nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing movies nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Movies transaction end");
			transaction.close();
		}

	}

	private void createUsers(GraphDatabaseService db)
			throws FileNotFoundException {

		UserReader parser = new UserReader(DATA_PATH + "users.dat");
		List<User> users = parser.processLineByLine();
		Date date = new java.util.Date();
		// Start transaction
		Transaction transaction = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			for (User u : users) {
				// Create User node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("uId", u.getUserID());
				params.put("gender", u.getGender());
				params.put("age", u.getAge());
				params.put("zip", u.getZipCode());
				//String query = "CREATE (n{type:'User', userId:{uId}, gender:{gender}, age:{age}, zipCode:{zip} })";
				String query = "CREATE (n:User {userId:{uId}, gender:{gender}, age:{age}, zipCode:{zip} })";
				ExecutionResult result = engine.execute(query, params);

				// Create the relationship with occupation, create the
				// occupation itself if needed
				// We can re-use the same params
				params.put("occ", u.getOccupation());
				//System.out.println(u.getOccupation());
				
				String query3 = "MATCH (u:User{userId:"+u.getUserID()+"}),(o:Occupation{occupation:\""+u.getOccupation()+"\"}) CREATE (u)-[r:hasOccupation]->(o) return r;";		
				engine.execute(query3);
				// TODO Create the state relationship
			}
			System.out.println("Users transaction marked as success - Commit ["
					+ users.size() + "] users nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing users nodes for User - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("User transaction end");
			transaction.close();
		}

	}

	private void createRatings(GraphDatabaseService db)
			throws FileNotFoundException {

		RatingReader parser = new RatingReader(DATA_PATH + "ratings.dat");

		List<Rating> ratings = parser.processLineByLine();

		// Start transaction
		int i = 0;
		Transaction transaction = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			for (Rating r : ratings) {
				i++;
				
				String query3 = "MATCH (u:User{userId:"+r.getUserId()+"}),(m:Movie{movieId:"+r.getMovieId()+"}) CREATE (u)-[r:rated{stars:"+r.getNbStars()+"}]->(m) return r;";		
				ExecutionResult result = engine.execute(query3);
				if(i%100 == 0 && result != null)
					System.out.println(i);
			}
			System.out
					.println("Ratings transaction marked as success - Commit ["
							+ ratings.size() + "] ratings relations");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing ratings - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Ratings transaction end");
			transaction.close();
		}

	}
}
