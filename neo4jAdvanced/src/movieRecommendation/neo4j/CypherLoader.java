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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import movieRecommendation.model.Actor;
import movieRecommendation.model.Country;
import movieRecommendation.model.Director;
import movieRecommendation.model.Genre;
import movieRecommendation.model.Movie;
import movieRecommendation.model.Rating;
import movieRecommendation.model.User;
import movieRecommendation.reader.ActorReader;
import movieRecommendation.reader.CountryReader;
import movieRecommendation.reader.DirectorReader;
import movieRecommendation.reader.GenresReader;
import movieRecommendation.reader.MovieReader;
import movieRecommendation.reader.RatingReader;
import movieRecommendation.reader.UserReader;


public class CypherLoader {
	private static final String DB_PATH = "/home/fograinwind/workspace/neo4jAdvanced/database/graphDB";
	private static final String DATA_PATH = "database/data/";
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
		
		//Movie Nodes
		try {
			createMovies(db);
			addCountry(db);
			
		} catch (FileNotFoundException e) {
			System.err.println("ERR- movies.dat missing from directory"
					+ DATA_PATH);
		}
		
		// Actor Nodes
		try {
			createActors(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- actors.dat missing from directory"
					+ DATA_PATH);
		}	
		
		// Director Nodes
		try {
			createDirectors(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- directors.dat missing from directory"
							+ DATA_PATH);
		}
		
		// Genres Nodes
		try {
			createGenres(db);
		} catch (FileNotFoundException e) {
			System.err.println("ERR- genres.dat missing from directory"
							+ DATA_PATH);
		}	
		// User Nodes
		try {
			createOccupation(db);
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
				params.put("url", m.getImdbPictureURL());
				params.put("year", m.getYear());
				//System.out.println(m.getTitle());
				
				String query = "CREATE (n:Movie{movieId :{movieId}, title :{title}, url :{url}, year :{year}}) return n";
				engine.execute(query, params);
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
	
	@SuppressWarnings("deprecation")
	private void createActors(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the actors.dat file and returns a list
		// to feed the graph
		ActorReader parse = new ActorReader(DATA_PATH +"actors.dat");
		List<Actor> actors = parse.processLineByLine();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();

		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (Actor a : actors) {
				// Create ACTOR node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("actorId", a.getActorId());
				params.put("name", a.getActorName());
				params.put("ranking", a.getRanking());
				params.put("movieId", a.getMovieId());
				//System.out.println(a.getMovieId()+a.getActorName());;
				String query = "MERGE (a:Actor {actorId:{actorId}, actorName: {name}}) ON CREATE SET a.created = timestamp() RETURN a";
				engine.execute(query, params);
				String query3 = "MATCH (a:Actor {actorId:{actorId}}),(m:Movie{movieId:{movieId}}) CREATE (a)-[r:ACTS_IN]->(m) return r";		
				engine.execute(query3, params);	
							
			}						

			System.out
					.println("Actors transaction marked as success - Commit ["
							+ actors.size() + "] actors nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing actors nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Actors transaction end");
			transaction.close();
		}

	}

	private void addCountry(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the countries.dat file and returns a list of POJO used
		// to feed the graph
		CountryReader parse = new CountryReader(DATA_PATH +"countries.dat");
		List<Country> countries = parse.processLineByLine();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();

		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (Country c : countries) {
				// add country property to movie node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("movieId", c.getMovieId());
				params.put("country", c.getCountry());
				
				String query3 = "MATCH (m:Movie {movieId: {movieId}}) SET m.country = {country} RETURN m";		
				engine.execute(query3, params);						
			}
					
			System.out
					.println("Country transaction marked as success - Commit ["
							+ countries.size() + "] countries nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing countries nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Countries transaction end");
			transaction.close();
		}

	}
	
	@SuppressWarnings("deprecation")
	private void createDirectors(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the directors.dat file and returns a list of POJO used
		// to feed the graph
		DirectorReader parse = new DirectorReader(DATA_PATH +"directors.dat");
		List<Director> directors = parse.processLineByLine();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();

		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (Director d : directors) {
				// Create Movie node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("movieId", d.getMovieId());
				params.put("directorId", d.getDirectorId());
				params.put("name", d.getDirectorName());
				
				String query = "MERGE (d:Director{directorId : {directorId}, directorName :{name}}) ON CREATE SET d.created = timestamp() RETURN d";
				engine.execute(query, params);

				String query3 = "MATCH (d:Director {directorId:{directorId}}),(m:Movie{movieId:{movieId}}) CREATE (d)-[r:DIRECTED]->(m) return r;";		
				engine.execute(query3, params);	
							
			}							
			System.out
					.println("Directors transaction marked as success - Commit ["
							+ directors.size() + "] directors nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing directors nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("directors transaction end");
			transaction.close();
		}

	}
	
	
	@SuppressWarnings("deprecation")
	private void createGenres(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the genres.dat file and returns a list of POJO used
		// to feed the graph
		GenresReader parse = new GenresReader(DATA_PATH +"genres.dat");
		List<Genre> genres = parse.processLineByLine();

		// Actual Neo4J manipulation starts here
		// Start transaction
		Transaction transaction = db.beginTx();

		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			
			for (Genre g : genres) {
				// Create Genre node
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("mId", g.getMovieId());
				params.put("genre", g.getGenre());
				
				String query = "MERGE (g:Genre{genre:{genre}}) ON CREATE SET g.created = timestamp() RETURN g";
				engine.execute(query, params);
				
				String query3 = "MATCH (m:Movie {movieId:{mId}}),(g:Genre{genre: {genre}}) CREATE (m)-[r:IS_GENRE]->(g) return r";		
				engine.execute(query3,params);	
							
			}	

			System.out
					.println("Genres transaction marked as success - Commit ["
							+ genres.size() + "] genres nodes");
			transaction.success();
		} catch (Exception e) {
			System.err
					.println("ERR- Sthg went wrong when writing Genres nodes - transaction marked as failure"
							+ e.toString());
			transaction.failure();
		} finally {
			System.out.println("Genres transaction end");
			transaction.close();
		}

	}

	
	private void createUsers(GraphDatabaseService db)
			throws FileNotFoundException {

		UserReader parser = new UserReader(DATA_PATH + "users.dat");
		List<User> users = parser.processLineByLine();
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
				
				String query = "CREATE (n:User {userId:{uId}, gender:{gender}, age:{age}, zipCode:{zip} })";
				engine.execute(query, params);

				params.put("occ", u.getOccupation());
				
				String query3 = "MATCH (u:User{userId: {uId}}),(o:Occupation{occupation:{occ}}) CREATE (u)-[r:HAS_OCCUPATION]->(o) return r";		
				engine.execute(query3, params);
				
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
		Transaction transaction = db.beginTx();
		try {
			ExecutionEngine engine = new ExecutionEngine(db);
			for (Rating r : ratings) {
				
				String query= "MATCH (u:User{userId:"+r.getUserId()+"}),(m:Movie{movieId:"+r.getMovieId()+"}) CREATE (u)-[r:RATED{stars:"+r.getNbStars()+"}]->(m) return r;";		
				engine.execute(query);
	
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
	
	@SuppressWarnings("deprecation")
	private void createOccupation(GraphDatabaseService db)
			throws FileNotFoundException {
		// This parser reads the occupation.dat file and returns a list of POJO used
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
	
}
