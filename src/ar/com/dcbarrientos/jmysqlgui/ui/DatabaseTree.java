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
 * DatabaseTree.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 29 de jul. de 2016, 5:05:30 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import ar.com.dcbarrientos.jmysqlgui.database.CDatabase;
import ar.com.dcbarrientos.jmysqlgui.database.CTabla;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class DatabaseTree extends JTree{
	private static final long serialVersionUID = 1L;
	private Vector<CDatabase> databases;			//Estructura de base de datos.
	private String user;							//Usuario conectado a la base de datos
	//private Connection connection;						//Conexion abierta.
	//private MdiAdmin admin;
	private ResourceBundle resource;
	
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode id;
	private DefaultMutableTreeNode dbs;
	private DefaultMutableTreeNode users;
	private DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	
	public DatabaseTree(Connection connection, String user){
		super();
		//this.connection = connection;
		this.user = user;
		initComponents();
	}
	
	public DatabaseTree(String user, Vector<CDatabase> databases, ResourceBundle resource){
		super();
		//this.admin = admin;
		this.user = user;
		this.databases = databases;
		this.resource = resource;
		
		initComponents();
	}

	private void initComponents(){
		id = new DefaultMutableTreeNode(user);
		treeModel = new DefaultTreeModel(id);
		dbs = new DefaultMutableTreeNode(resource.getString("DatabaseTree.nodeDatabases"));
		treeModel.insertNodeInto(dbs, id, id.getChildCount());
		users = new DefaultMutableTreeNode(resource.getString("DatabaseTree.nodeUsers"));
		treeModel.insertNodeInto(users, id, id.getChildCount());
		
		cargarDatos();
		ImageIcon tableIcon = new ImageIcon(DatabaseTree.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/table.gif"));
		ImageIcon databaseIcon = new ImageIcon(DatabaseTree.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Database.gif"));
		ImageIcon database2Icon = new ImageIcon(DatabaseTree.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/database2.gif"));
		
		renderer.setLeafIcon(tableIcon);
		renderer.setOpenIcon(databaseIcon);
		renderer.setClosedIcon(database2Icon);
		
		this.setCellRenderer(renderer);
		this.setModel(treeModel);
	}
	
	private void cargarDatos(){
		DefaultMutableTreeNode dbNode;
		DefaultMutableTreeNode tbNode;
		
		for(CDatabase db : databases){
			//Genero el nodo con el nombre de la base de datos.
			dbNode = new DefaultMutableTreeNode(db.getName());
			treeModel.insertNodeInto(dbNode, dbs, dbs.getChildCount());
			
			//Genero los nodos con las tablas correspondientes a la base de datos de arriba.
			HashMap<String, CTabla> tablas = db.getTablas();
			SortedSet<String> keys = new TreeSet<String>(tablas.keySet());
			for(String key : keys){
				tbNode = new DefaultMutableTreeNode(key);
				treeModel.insertNodeInto(tbNode, dbNode, dbNode.getChildCount());
			}			 
		}
	}
	
	public void setUser(String user){
		this.user = user;
	}
	
	public void setDatabases(Vector<CDatabase> databases){
		this.databases = databases;
	}
}
