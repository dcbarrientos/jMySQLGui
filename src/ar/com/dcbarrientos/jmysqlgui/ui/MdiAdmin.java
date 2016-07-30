/*
 *  Copyright (C) 2016 Diego Barrientos <dc_barrientos@yahoo.com.ar>
 * 
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 * 
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

/** 
 * MdiAdmin.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 29 de jul. de 2016, 11:47:14 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import ar.com.dcbarrientos.jmysqlgui.database.CDatabase;
import ar.com.dcbarrientos.jmysqlgui.database.CQuery;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class MdiAdmin extends JPanel{
	private static final long serialVersionUID = 1L;
	private CConnection connection;
	private ResourceBundle resource;
	Vector<CDatabase> databases;
	String userName;
	
	public MdiAdmin(ResourceBundle resource, CConnection connection){
		this.resource = resource;
		this.connection = connection;
		setLayout(new BorderLayout(0, 0));
		
		initComponents();
	}
	
	private void initComponents(){
		cargarInformacion();

		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		
		JSplitPane panelSuperior = new JSplitPane();
		splitPane.setLeftComponent(panelSuperior);
		
		JScrollPane scrollTreeDatabase = new JScrollPane();
		panelSuperior.setLeftComponent(scrollTreeDatabase);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		panelSuperior.setRightComponent(tabbedPane);
		
		JPanel panelInferior = new JPanel();
		splitPane.setRightComponent(panelInferior);
setLocation(0, 0);		
setSize(600,600);
		DatabaseTree dbTree = new DatabaseTree(this, userName, databases);
		//dbTree.setUser(userName);
		//dbTree.setDatabases(databases);
		
		scrollTreeDatabase.setViewportView(dbTree);
	}
	
	private void cargarInformacion(){
		userName = getUser();
		databases = new Vector<CDatabase>();
		CQuery query = new CQuery(connection.getConnection());
		String sql = "SHOW DATABASES";
		CDatabase cDatabase;
		if(query.executeQuery(sql)> 0){
			try{
				ResultSet result = query.getResultSet();
				while(result.next()){					
					cDatabase = new CDatabase(result.getString("Database"), connection.getConnection());
					databases.addElement(cDatabase);
				}
			}catch(SQLException e){
				//TODO tratamiento error.
			}
		}
		query.cerrar();
		
		//mostrar();
	}
	
	private void mostrar(){
		for(CDatabase db : databases){
			System.out.println(db.getName());
			db.mostrar();
		}
	}
	
	public void setConnection(CConnection connection){
		this.connection = connection;
	}
	
	private String getUser(){
		userName = "";
		String sql = "SELECT USER()";
		CQuery query = new CQuery(connection.getConnection());
		if(query.executeQuery(sql)>0){
			try {
				query.getResultSet().next();
				userName = query.getResultSet().getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		query.cerrar();
		
		return userName;
	}
	
	public ResourceBundle getResource(){
		return resource;
	}
}
