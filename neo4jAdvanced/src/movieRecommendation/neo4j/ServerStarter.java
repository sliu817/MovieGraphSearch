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

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.kernel.AbstractGraphDatabase;
import org.neo4j.server.WrappingNeoServerBootstrapper;




public class ServerStarter extends JFrame implements ActionListener {
	
	private static final String DB_PATH = "/home/fograinwind/workspace/neo4jAdvanced/database/graphDB";
	
	private JLabel 	sourceLabel, targetLabel;
	private JTextField sourceText, targetText;
	private JButton loadBt;
	

    public static void main( String[] args )
    {
        new ServerStarter("Movie Recommendation");
    }

    public ServerStarter(String title){
    	super(title);
    	sourceLabel = new JLabel("source", SwingConstants.RIGHT);
    	targetLabel = new JLabel("target", SwingConstants.RIGHT);
    	
    	sourceText = new JTextField(100);
    	targetText = new JTextField(100);
    	
    	loadBt = new JButton("Load");
    	
    	Container c = this.getContentPane();
    	c.setLayout(new BorderLayout());
    	JPanel centerPanel = new JPanel();
    	centerPanel.add(sourceLabel);
    	
    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}