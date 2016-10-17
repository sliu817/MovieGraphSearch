package db;

/**
 * 
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


import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

import bean.Actor;
import bean.Director;
import bean.Genre;
import bean.Movie;
import bean.User;

public class CypherRecommender {

	
	private static final String DB_PATH = "/home/fograinwind/workspace/neo4jAdvanced/database/graphDB";
	String resultString;
	String columnsString;
	String nodeResult;
	String rows = "";

	 public static ArrayList<Movie> getMovies(String title){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {
			 	System.out.println("title"+title);
				ExecutionEngine engine = new ExecutionEngine(db);
				
				//String query = "Match (m:Movie{title:\""+title+"\"}) return m.movieId,m.title,m.url, m.year, m.country";
				String query = "Match (m:Movie) Where m.title=~ \"(?i)"+title+".*\" return m.movieId,m.title,m.url, m.year, m.country";
				System.out.print(query);
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static String getStarByTitle(String title){
		 String star = " ";
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u) where m.title=\""+title+"\"  return avg(r.stars)";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
		
					star = row.get("avg(r.stars)").toString();				
								
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return star;
	 }
	 
	 public static String getRatedCount(String title){
		 String count = " ";
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u) where m.title=\""+title+"\" return count(r)";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
		
					count = row.get("count(r)").toString();				
								
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return count;
	 }
	 
	 public static String getUserRatedCount(String userId){
		 String count = " ";
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u:User) where u.userId= "+userId+" return count(m)";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
		
					count = row.get("count(m)").toString();				
								
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return count;
	 }
	 
	 public static ArrayList<Genre> getGenresByTitle(String title){
		 ArrayList<Genre> list = new ArrayList<Genre>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)-[r:IS_GENRE]->(g) where m.title=\""+title+"\"  return m.movieId,g.genre";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Genre g = null;
					String movieId = row.get("m.movieId").toString();
					String genre = row.get("g.genre").toString();				
			
					g = new Genre(Integer.valueOf(movieId),genre);
					list.add(g);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 
	 public static ArrayList<Director> getDirectorsByTitle(String title){
		 ArrayList<Director> list = new ArrayList<Director>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (d)-[r:DIRECTED]->(m:Movie) where m.title=\""+title+"\" return m.movieId, d.directorId, d.directorName";
				//String query = "Match (d)-[r:DIRECTED]->(m:Movie{title:'Toy story'}) return d.directorName";
				
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Director d = null;
					String mId = row.get("m.movieId").toString();
					String dId = row.get("d.directorId").toString();	
					String name = row.get("d.directorName").toString();				
					d = new Director(Integer.valueOf(mId), dId, name);
					list.add(d);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static ArrayList<Actor> getActorsByTitle(String title){
		 ArrayList<Actor> list = new ArrayList<Actor>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (a)-[r:ACTS_IN]->(m:Movie) where m.title=\""+title+"\"  return m.movieId, a.actorId,a.actorName limit 10";
				//String query = "Match (a)-[r:ACTS_IN]->(m:Movie{title:'Toy story'}) return a.actorName";
				
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Actor a = null;
					String mId = row.get("m.movieId").toString();
					String aId = row.get("a.actorId").toString();			
					String name = row.get("a.actorName").toString();				
					//System.out.println(name);
					a = new Actor(Integer.valueOf(mId), aId, name);
					list.add(a);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static ArrayList<ArrayList<User>> getUsersByTitle(String title){
		 ArrayList<ArrayList<User>> allLists= new ArrayList<ArrayList<User>>();
		 ArrayList<User> list = null;
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "MATCH (o)<-[g:HAS_OCCUPATION]-user-[r:RATED]->(n:Movie) where n.title=\""+title+"\" AND r.stars =5 "+
								" RETURN user.userId, user.gender, user.age,o.occupation, user.zipCode limit 5";
				//String query = "Match (a)-[r:ACTS_IN]->(m:Movie{title:'Toy story'}) return a.actorName";	
				ExecutionResult result = engine.execute(query);
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				list = new ArrayList<User>();
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					User u = null;
					String id = row.get("user.userId").toString();	
					String gender = row.get("user.gender").toString();
					String age = row.get("user.age").toString();
					String occ = row.get("o.occupation").toString();
					String zipCode = row.get("user.zipCode").toString();		
					System.out.println(id);
					u = new User(Integer.valueOf(id), gender, Integer.valueOf(age), occ, zipCode);
					list.add(u);
				}		
				allLists.add(list);
				
				String query2 = "MATCH (o)<-[g:HAS_OCCUPATION]-user-[r:RATED]->(n:Movie) where n.title=\""+title+"\" AND r.stars =4 "+
						" RETURN user.userId, user.gender, user.age,o.occupation, user.zipCode limit 5";
				//String query = "Match (a)-[r:ACTS_IN]->(m:Movie{title:'Toy story'}) return a.actorName";	
				ExecutionResult result2 = engine.execute(query2);
				ResourceIterator<Map<String, Object>> ret2 = result2.iterator();
				list = new ArrayList<User>();
				while(ret2.hasNext()){
					Map<String, Object> row = ret2.next();
					User u = null;
					String id = row.get("user.userId").toString();	
					String gender = row.get("user.gender").toString();
					String age = row.get("user.age").toString();
					String occ = row.get("o.occupation").toString();
					String zipCode = row.get("user.zipCode").toString();		
					System.out.println(id);
					u = new User(Integer.valueOf(id), gender, Integer.valueOf(age), occ, zipCode);
					list.add(u);
				}		
				allLists.add(list);
				
				String query3 = "MATCH (o)<-[g:HAS_OCCUPATION]-user-[r:RATED]->(n:Movie) where n.title=\""+title+"\" AND r.stars =1 "+
						" RETURN user.userId, user.gender, user.age,o.occupation, user.zipCode limit 5";
				ExecutionResult result3 = engine.execute(query3);
				ResourceIterator<Map<String, Object>> ret3 = result3.iterator();
				list = new ArrayList<User>();
				while(ret3.hasNext()){
					Map<String, Object> row = ret3.next();
					User u = null;
					String id = row.get("user.userId").toString();	
					String gender = row.get("user.gender").toString();
					String age = row.get("user.age").toString();
					String occ = row.get("o.occupation").toString();
					String zipCode = row.get("user.zipCode").toString();		
					System.out.println(id);
					u = new User(Integer.valueOf(id), gender, Integer.valueOf(age), occ, zipCode);
					list.add(u);
				}		
				allLists.add(list);

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return allLists;
	 }
	 
	 public static ArrayList<Movie> getSimilarByCF(String title){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "MATCH (m:Movie)<-[r:RATED]-user-[o:RATED]->stuff where m.title=\""+title+"\" AND r.stars>4 and o.stars>4 "+
				               "RETURN stuff.movieId,stuff.title,stuff.url, stuff.year, avg(o.stars), count(o), (avg(o.stars)*count(o)) as scoring order by scoring desc limit 5";
				
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("stuff.movieId").toString();				
					String mtitle = row.get("stuff.title").toString();
					String url = row.get("stuff.url").toString();
					String year = row.get("stuff.year").toString();

					m = new Movie(Integer.valueOf(id), mtitle, url, year);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 } 
	 
	 public static ArrayList<Movie> getMoviesByGenre(String movieId, String genre){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "MATCH (film:Movie)<-[r:RATED]-user-[o:RATED]->stuff,"+
						" film-[:IS_GENRE]->gen<-[:IS_GENRE]-stuff where film.movieId= "+movieId+" and r.stars>3  and o.stars>3 and gen.genre='"+genre+"' "+
						"RETURN stuff.movieId, stuff.title,stuff.url, stuff.year, count(*) ORDER BY count(*) DESC, stuff.title limit 10";
				
				
				/*String query = "MATCH (film:Movie{title:\""+title+"\"})<-[r:rated]-user-[o:rated]->stuff,"+
						" film-[:IS_GENRE]->gen<-[:IS_GENRE]-stuff where user.age<10 and r.stars>3  and o.stars>3 and gen.genre='"+genre+"' "+
						"RETURN stuff.movieId, stuff.title, count(*) ORDER BY count(*) DESC, stuff.title limit 10";*/
				ExecutionResult result = engine.execute(query);
			
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("stuff.movieId").toString();				
					String mtitle = row.get("stuff.title").toString();
					String url = row.get("stuff.url").toString();
					String year = row.get("stuff.year").toString();

					m = new Movie(Integer.valueOf(id), mtitle, url, year);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 } 
	 public static ArrayList<Movie> getMoviesByDirector(String directorId){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (d:Director)-[r:DIRECTED]->(m:Movie) where d.directorId=\""+directorId+"\"  return m.movieId,m.title,m.url, m.year, m.country limit 5";
				//String query = "Match (d:Director{directorId:'john_lasseter'})-[r:DIRECTED]->(m:Movie) return m.movieId,m.title,m.url, m.year, m.country";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static ArrayList<Movie> getMoviesByActor(String aId){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (a:Actor)-[r:ACTS_IN]->(m:Movie) where a.actorId=\""+aId+"\" return m.movieId,m.title,m.url, m.year, m.country limit 5";
				//String query = "Match (d:Director{directorId:'john_lasseter'})-[r:DIRECTED]->(m:Movie) return m.movieId,m.title,m.url, m.year, m.country";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static User getUserByuId(String userId){
		 User user = null;
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "MATCH (o)<-[g:HAS_OCCUPATION]-(u:User) where u.userId="+userId+
								" RETURN u.userId, u.gender, u.age,o.occupation, u.zipCode limit 5";
				//String query = "Match (a)-[r:ACTS_IN]->(m:Movie{title:'Toy story'}) return a.actorName";	
				ExecutionResult result = engine.execute(query);
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					String id = row.get("u.userId").toString();	
					String gender = row.get("u.gender").toString();
					String age = row.get("u.age").toString();
					String occ = row.get("o.occupation").toString();
					String zipCode = row.get("u.zipCode").toString();		
					//System.out.println(name);
					user = new User(Integer.valueOf(id), gender, Integer.valueOf(age), occ, zipCode);
					
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return user;
	 }
	 
	 public static ArrayList<Movie> getRatedMoviesByUser(String userId){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u:User) return m.movieId,m.title,m.url, m.year, m.country limit 5";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 
	 public static ArrayList<Movie> getRecommendMoviesByUser(String userId){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u:User) where r.stars=5 return m.movieId,m.title,m.url, m.year, m.country limit 5";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static ArrayList<Movie> getSameAgeMoviesByUser(String userId, String age){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u:User) where u.age="+age+" and r.stars>3 return m.movieId,m.title,m.url, m.year, m.country limit 5";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 public static ArrayList<Movie> getSameOccupationMoviesByUser(String userId, String occ){
		 ArrayList<Movie> list = new ArrayList<Movie>();
		 GraphDatabaseService db = new GraphDatabaseFactory().newEmbeddedDatabase(DB_PATH);
		 try {

				ExecutionEngine engine = new ExecutionEngine(db);
				
				String query = "Match (m:Movie)<-[r:RATED]-(u:User)-[:HAS_OCCUPATION]->(o:Occupation) where  o.occupation=\""+occ+"\" and r.stars>3 "+
								"return m.movieId,m.title,m.url, m.year, m.country limit 5";
				//String query = "Match (m:Movie{title:'Toy story'}) return m.movieId,m.title,m.url,m.year, m.country";
				//String query = "Match (m:Movie) where m.movieId < 5 return m.movieId,m.title";
				ExecutionResult result = engine.execute(query);
				
				////ResourceIterator<Object> retId = result.columnAs("m.movieId");
				//ResourceIterator<Object> retTitle = result.columnAs("m.title");
				ResourceIterator<Map<String, Object>> ret = result.iterator();
				
				while(ret.hasNext()){
					Map<String, Object> row = ret.next();
					Movie m = null;
					String id = row.get("m.movieId").toString();				
					String mtitle = row.get("m.title").toString();
					String url = row.get("m.url").toString();
					String year = row.get("m.year").toString();
					String country = row.get("m.country").toString();
					m = new Movie(Integer.valueOf(id), mtitle, url, year, country);
					list.add(m);				
				}		

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				db.shutdown();
			}
		 
		 return list;
	 }
	 
	 
	 public static void main(String[] args) {
		/* ArrayList<Movie>
			list = getRatedMoviesByUser("1");
		 
			 for(Movie m : list){
				 System.out.println(m.getTitle().toString());
			 }
			 System.out.println();
			 
			*/
			
		
	 
		 ArrayList<ArrayList<User>> users = getUsersByTitle("Toy story");
	 
		 for(ArrayList<User> list: users){
			 System.out.println("list");
			 for(User u :list)
				 System.out.println("uid"+u.getUserID());
		 }
		 System.out.println();
	 }
}
