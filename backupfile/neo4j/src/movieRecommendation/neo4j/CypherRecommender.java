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

package movieRecommendation.neo4j;

import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.cypher.javacompat.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class CypherRecommender {

	private static final String DB_PATH = "/home/fograinwind/workspace/neo4j/target/movieData";
	String resultString;
	String columnsString;
	String nodeResult;
	String rows = "";

	public static void main(String[] args) {
		CypherRecommender javaQuery = new CypherRecommender();
		javaQuery.run();
	}

	void run() {
		GraphDatabaseService db = new GraphDatabaseFactory()
				.newEmbeddedDatabase(DB_PATH);

		try {

			ExecutionEngine engine = new ExecutionEngine(db);
			
			String query = "Match (m:User) return count(m)";
			ExecutionResult result = engine.execute(query);
			ResourceIterator<Object> ret = result.columnAs("count(m)");
			while(ret.hasNext())
				System.out.println(ret.next());


		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.shutdown();
		}
	}
}