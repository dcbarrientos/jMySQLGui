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

import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

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
	private MdiAdmin admin;
	private ResourceBundle resource;
	private TreePath treePath;
	private boolean nodoEncontrado;					//Uso esta variable como bandera para buscar un nodo.
	private boolean procesarEvento;
	
	private DefaultTreeModel treeModel;
	private DefaultMutableTreeNode id;
	private DefaultMutableTreeNode dbs;
	private DefaultMutableTreeNode users;
	private DefaultTreeCellRenderer renderer = new DefaultTreeCellRenderer();
	
	public static final int ROOT_INDEX = 0;
	public static final int USER_INDEX = 1;
	public static final int DATABASE_INDEX = 2;
	public static final int TABLE_INDEX = 3;
	
	
	/*
	public DatabaseTree(Connection connection, String user){
		super();
		//this.connection = connection;
		this.user = user;
		initComponents();
	}
	*/
	
	/**
	 * Constructor del panel que contiene las bases de datos y sus respectivas tablas. Este
	 * panel se muestra en el cuadro superior a la izquierda con un JTree. 
	 * @param admin Padre de este panel.
	 * @param user User y host de la conexion.
	 * @param databases Estructura con las bases de datos del servidor.
	 * @param resource ResourceBundle que contiene los strings de la aplicación.
	 */
	public DatabaseTree(MdiAdmin admin, String user, Vector<CDatabase> databases, ResourceBundle resource){
		super();
		/*
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				databaseTreeMouseClicked(e);
			}
		});*/
		this.admin = admin;
		this.user = user;
		this.databases = databases;
		this.resource = resource;
		procesarEvento = true;
		
		initComponents();
	}

	/**
	 * Inicilizo la interfaz gráfica.
	 */
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
		this.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				databaseTreeValueChanged(e);
			}
		});
	}
	
	/**
	 * Genero los nodos que compondrán el JTree.
	 */
	private void cargarDatos(){
		DefaultMutableTreeNode dbNode;
		DefaultMutableTreeNode tbNode;
		
		for(CDatabase db : databases){
			//Genero el nodo con el nombre de la base de datos.
			dbNode = new DefaultMutableTreeNode(db.getName());
			treeModel.insertNodeInto(dbNode, dbs, dbs.getChildCount());
			if(db.getTableCount() == 0){
				tbNode = new DefaultMutableTreeNode(resource.getString("DatabaseTree.nodeEmpty"));
				treeModel.insertNodeInto(tbNode, dbNode, dbNode.getChildCount());
			}else{
				//Genero los nodos con las tablas correspondientes a la base de datos de arriba.
				HashMap<String, CTabla> tablas = db.getTablas();
				SortedSet<String> keys = new TreeSet<String>(tablas.keySet());
				for(String key : keys){
					tbNode = new DefaultMutableTreeNode(key);
					treeModel.insertNodeInto(tbNode, dbNode, dbNode.getChildCount());
				}
			}
		}
	}
	
	/**
	 * Setter de user.
	 * @param user Valor que tendrá user (user@host).
	 */
	public void setUser(String user){
		this.user = user;
	}
	
	/**
	 * Setter de la estructura con las bases de datos.
	 * @param databases Bases de datos del servidor.
	 */
	public void setDatabases(Vector<CDatabase> databases){
		procesarEvento = false;
		this.databases = databases;
		vaciar();
		cargarDatos();
		this.setModel(treeModel);
		
		revalidate();
		refresh();
		procesarEvento = true;
	}
	
	/**
	 * Procedimiento que maneja el evneto de click en el JTree.
	 * @param e Evento disparado.
	 */
	public void databaseTreeMouseClicked(MouseEvent e){
		//Busco la linea seleccionada.
		int selRow = getRowForLocation(e.getX(), e.getY());
		if(selRow > 0){
			treePath = getPathForLocation(e.getX(), e.getY());
			if(isDatabase(treePath))
				admin.setSelectedDatabase(treePath.getPathComponent(DATABASE_INDEX).toString(), true);
			else if(isTable(treePath))
				admin.setSelectedTable(treePath.getPathComponent(DATABASE_INDEX).toString(), treePath.getPathComponent(TABLE_INDEX).toString(), MdiAdmin.TABLE_INFO_TAB_INDEX);
		}
	}
	
	public void databaseTreeValueChanged(TreeSelectionEvent e){
		if(procesarEvento){
			treePath = e.getPath();
			
			if(isDatabase(treePath))
				admin.setSelectedDatabase(treePath.getPathComponent(DATABASE_INDEX).toString(), true);
			else if(isTable(treePath))
				admin.setSelectedTable(treePath.getPathComponent(DATABASE_INDEX).toString(), treePath.getPathComponent(TABLE_INDEX).toString(), MdiAdmin.TABLE_INFO_TAB_INDEX);
		}
	}
	
	/**
	 * Verifica si el nodo ingresado es de tipo usuario
	 * @param treePath Nodo seleccionado
	 * @return Verdadero si es un nodo tipo user
	 */
	private boolean isUser(TreePath treePath){
		return (treePath.getPathCount() == 1);
	}
		
	/**
	 * Verifica si el nodo ingresado es de tipo database
	 * @param treePath Nodo seleccionado
	 * @return Verdadero si es un nodo tipo database
	 */
	private boolean isDatabase(TreePath treePath){
		return (treePath.getPathCount() == 3);
	}
	
	/**
	 * Verifica si el nodo ingresado es de tipo tabla
	 * @param treePath Nodo seleccionado
	 * @return Verdadero si es un nodo tipo tabla
	 */
	private boolean isTable(TreePath treePath){
		return (treePath.getPathCount() == 4);
	}
	
	/**
	 * Vacia el JTree.
	 */
	private void vaciar(){
		dbs.removeAllChildren();
		users.removeAllChildren();
		treeModel.reload();
	}
	
	/**
	 * Actualiza el JTree. 
	 */
	public void refresh(){
		vaciar();
		cargarDatos();
		expandRow(1);

		revalidate();		
		repaint();
	}
		
	public void selectRoot(){
		setSelectionRow(0);
	}
	
	/**
	 * Selecciona un nodo a partir de su nombre
	 * @param nombre Nombre del nodo a seleccionar.
	 */
	public void seleccionarPorNombre(String nombre){
		nodoEncontrado = false;
		int row = indexByNode(id, nombre);
		if(nodoEncontrado){
			expandRow(row);
			setSelectionRow(row);
		}else
			setSelectionRow(ROOT_INDEX);
	}

	
	/**
	 * Busca el nodo que contiene el texto nombre. Debo poner encontrado en false
	 * antes del primer llamado.
	 * @param node Nodo a partir de dónde se inicia la búsqueda.
	 * @param nombre Nombre del nodo a buscar.
	 * @return Devuelve el índice del nodo a buscar.
	 */
	private int indexByNode(DefaultMutableTreeNode node, String nombre){
		DefaultMutableTreeNode elemento;
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = node.breadthFirstEnumeration();
		
		int i = 1;
		e.nextElement();		
		while(e.hasMoreElements() && !nodoEncontrado){
			elemento = (DefaultMutableTreeNode)e.nextElement();
			if(nombre.equals(elemento.getUserObject().toString())){
				nodoEncontrado = true;
				return i;
			}else if(!elemento.isLeaf()){
				i += indexByNode(elemento, nombre);
			}else
				i++;
		}
		
		return i;
	}

	/**
	 * Selecciono en el tree la base de datos especificada en nombre.
	 * @param nombre Nombre de la base de datos a seleccionar.
	 */
	public void selectDatabaseByName(String nombre){
		//Cierro todos los nodos.
		collapseRow(ROOT_INDEX);
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = dbs.breadthFirstEnumeration();
		DefaultMutableTreeNode elemento;
		
		int i = 2;
		if(e.hasMoreElements()){
			//El primer elemento es el nodo que contiene las bases de datos, no lo tomo en cuenta.
			e.nextElement();
			while(e.hasMoreElements()){
				//elemento contendrá cada base de datos.
				elemento = (DefaultMutableTreeNode)e.nextElement();
				if(nombre.equals(elemento.getUserObject().toString())){
					//Espando hasta el nodo base de datos incluído, todas las bases de datos
					//quedan a la vista.
					this.expandPath(new TreePath(dbs.getPath()));
					
					//Selecciono la base de datos por su indice.
					setSelectionRow(i);
					//Expando el node de la base de datos elegida.
					this.expandPath(getSelectionPath());
					
					revalidate();
					repaint();
					
					return;
				}
				i++;
			}
		}
	} 

	
}
