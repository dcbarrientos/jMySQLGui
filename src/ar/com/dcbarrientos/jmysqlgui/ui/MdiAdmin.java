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

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import ar.com.dcbarrientos.jmysqlgui.database.CDatabase;
import ar.com.dcbarrientos.jmysqlgui.database.CQuery;
import javax.swing.JList;
import javax.swing.JOptionPane;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class MdiAdmin extends JPanel{	
	private static final long serialVersionUID = 1L;

	public final static int DATABASE_TAB_INDEX = 1;
	public final static int TABLE_INFO_TAB_INDEX = 2;
	public final static int TABLE_DATA_TAB_INDEX = 3;
	
	JList<String> lstSQL;
	DefaultListModel<String> lstSQLModel;
	JList<String> lstMensajes;
	DefaultListModel<String> lstMensajesModel;
	
	private CConnection connection;
	private ResourceBundle resource;
	private Vector<CDatabase> databases;
	private String userName;
	private String selectedDatabaseName;
	private String selectedTableName;
	DatabaseTree dbTree;
	private DatabaseTab dbTab;
	private TableInfoTab tableInfoTab;
	JTabbedPane tabbedPane;
	ImageIcon databaseIcon;
	
	public MdiAdmin(ResourceBundle resource, CConnection connection){
		this.resource = resource;
		this.connection = connection;
		setLayout(new BorderLayout(0, 0));
		
		initComponents();
	}
	
	private void initComponents(){
		JTabbedPane tabbedPane_1 = new JTabbedPane(JTabbedPane.BOTTOM);

		JScrollPane scrollPane = new JScrollPane();
		tabbedPane_1.addTab(resource.getString("MdiAdemin.lstSQLTab.title"), null, scrollPane, null);
		
		lstSQLModel = new DefaultListModel<String>();
		lstSQL = new JList<String>(lstSQLModel);
		scrollPane.setViewportView(lstSQL);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		tabbedPane_1.addTab(resource.getString("MdiAdemin.lstMensajesTab.title"), null, scrollPane_1, null);
		
		lstMensajes = new JList<String>();
		lstMensajesModel = new DefaultListModel<String>();
		lstMensajes.setModel(lstMensajesModel);
		scrollPane_1.setViewportView(lstMensajes);
		
		databaseIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Database.gif"));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		
		JSplitPane panelSuperior = new JSplitPane();
		splitPane.setLeftComponent(panelSuperior);
		
		JScrollPane scrollTreeDatabase = new JScrollPane();
		panelSuperior.setLeftComponent(scrollTreeDatabase);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		//Tab Host
		ImageIcon databaseServerIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DatabaseServer.gif"));
		tabbedPane.addTab(resource.getString("Host.title"), databaseServerIcon, new HostTab(connection, resource));
		
		//Tab Query Editor
		ImageIcon queryIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Query.gif"));
		tabbedPane.addTab(resource.getString("QueryEditor.title"), queryIcon, new QueryEditorTab(this, connection, resource));
		panelSuperior.setRightComponent(tabbedPane);
		
		
		JPanel panelInferior = new JPanel();
		splitPane.setRightComponent(panelInferior);
		panelInferior.setLayout(new BorderLayout(0, 0));

		panelInferior.add(tabbedPane_1);

		cargarInformacion();
		
		dbTree = new DatabaseTree(this, userName, databases, resource);
		
		dbTab = new DatabaseTab(this, connection.getConnection());
		tableInfoTab = new TableInfoTab(this, connection.getConnection(), resource);
		
		scrollTreeDatabase.setViewportView(dbTree);
	}
	
	/**
	 * Cargo la información de la base de datos.
	 */
	public void cargarInformacion(){
		userName = getUser();
		databases = new Vector<CDatabase>();
		CQuery query = new CQuery(connection.getConnection());
		String sql = "SHOW DATABASES";
		CDatabase cDatabase;
		if(query.executeQuery(sql)> 0){
			addSQL(sql);
			try{
				ResultSet result = query.getResultSet();
				while(result.next()){					
					cDatabase = new CDatabase(this, result.getString("Database"), connection.getConnection());
					databases.addElement(cDatabase);
				}
			}catch(SQLException e){
				addError(e.getErrorCode() + ": " + e.getMessage(), sql);
			}
		}
		query.cerrar();

	}
	
	public void setConnection(CConnection connection){
		this.connection = connection;
	}
	
	
	/**
	 * @return Devuelve el usuario@host de la conexion.
	 */
	private String getUser(){
		return connection.getUserName() + "@" + connection.getHost();
	}
	
	
	/**
	 * @return Devuelve el ResourceBundel que contiene los strings de la aplicacion.
	 */
	public ResourceBundle getResource(){
		return resource;
	}
	
	/**
	 * Prepara la solapa en donde se muestran las talas de la base de datos seleccionada.
	 * @param databaseName Nombre de la base de datos seleccionada
	 * @param visible True si muestra la solapa.
	 */
	public void setSelectedDatabase(String databaseName, boolean visible){
		this.selectedDatabaseName = databaseName; 
		connection.setSelectedDatabase(databaseName);
		
		dbTab.setDatabase(getDatabase(databaseName));
		
		while(tabbedPane.getTabCount()>3){
			tabbedPane.remove(TABLE_INFO_TAB_INDEX);
		}
		
		if(tabbedPane.getTabCount()<3)
			tabbedPane.insertTab(resource.getString("DatabaseTab.title") + databaseName, databaseIcon, dbTab, null, DATABASE_TAB_INDEX);
		
		tabbedPane.setTitleAt(DATABASE_TAB_INDEX, resource.getString("DatabaseTab.title") + databaseName);
		if(visible)
			tabbedPane.setSelectedIndex(DATABASE_TAB_INDEX);
		refresh();
	}
	
	
	/**
	 * Refresca la apariencia después que cambiaron los datos. 
	 */
	public void refresh(){
		revalidate();
		repaint();
	}
	
	/**
	 * Devuelve una base de datos.
	 * @param nombre de la base de datos a buscar en el vector de bases de datos.
	 * @return devuelve la base de datos con el nombre ingresado.
	 */
	private CDatabase getDatabase(String nombre){
		for(CDatabase elemento: databases){
			if(elemento.getName().equals(nombre)){
				return elemento;
			}
		}
		
		return null;
	}
	
	/**
	 * @return Devuelve la base de datos que está seleccionada.
	 */
	private String getSelectedDatabase(){
		return this.selectedDatabaseName;
	}
	
	/**
	 * Cambia la tabla seleccionada.
	 * @param databaseName Nombre de la base de datos en la que está la tabla seleccionada.
	 * @param tableName Nombre de la tabla seleccionada
	 * @param solapa a hacer visible.
	 */
	public void setSelectedTable(String databaseName, String tableName, int solapa){
		this.selectedDatabaseName = databaseName;
		this.selectedTableName = tableName;
		connection.setSelectedDatabase(databaseName);
		//TODO Informar a TableInfo que tabla debe mostrar.
		//TODO Hacer la solapa que muestra los datos.
		tableInfoTab.setSelectedTable(getDatabase(databaseName), tableName);
		
		if(tabbedPane.getTabCount()<3){
			setSelectedDatabase(databaseName, false);
		}
		if(tabbedPane.getTabCount()<4){
			tabbedPane.insertTab(resource.getString("TableInfoTab.title"), null, tableInfoTab, null, TABLE_INFO_TAB_INDEX);
		}
		tabbedPane.setTitleAt(TABLE_INFO_TAB_INDEX, resource.getString("TableInfoTab.title") + " " + tableName);
		tabbedPane.setSelectedIndex(solapa);
		refresh();
	}
	
	/**
	 * @return Devuelve la tabla seleccionada actualmente.
	 */
	public String getSelectedTable(){
		return this.selectedTableName;
	}
	
	/**
	 * Agrega un mensaje en la solapa mensajes del cuadro inferior.
	 * @param mensaje Mensaje a mostrar.
	 */
	public void addMessage(String mensaje){
		lstMensajesModel.addElement("[" + getUser() + "] " + mensaje);
	}
	
	/**
	 * Agrega un mensaje en la solapa SQL del cuadro inferior.
	 * @param sql SQL a mostrar.
	 */
	public void addSQL(String sql){
		lstSQLModel.addElement("[" + getUser() + "] " + sql);
	}
	
	/**
	 * Agrega un mensaje de error en la solapa mensajes y una sql en la solapa sql.
	 * @param error Mensaje a mostrar en solapa mensajes.
	 * @param sql SQL a mostrar en la solapa SQL.
	 */
	public void addError(String error, String sql){
		addMessage(error);
		addSQL(sql);
	}
	
	/**
	 * Actualiza la estructura que contiene la información de las bases de datos.
	 * Es conveniente llamar este procedimiento cuando realiza una operación sobre
	 * una base de datos (agregar, eliminar) o una tabla (agregar, eliminar, modificar).
	 */
	public void refreshNewDatabase(){
		cargarInformacion();
		dbTree.setDatabases(databases);
		refresh();
	}
	
	/**
	 * Procedimiento para eliminar una base de datos. Verifica si hay una base de datos seleccionada
	 * y pide confirmación para llevar a cabo la eliminación.
	 */
	public void dropDatabase(){
		if(getSelectedDatabase() != null){
			int r = JOptionPane.showConfirmDialog(null, resource.getString("DropDatabase.message") + " " + getSelectedDatabase() + "?", resource.getString("DropDatabase.title"), JOptionPane.YES_NO_OPTION);
			if(r == JOptionPane.YES_OPTION){
				CQuery query = new CQuery(connection.getConnection());
				//Si elimino la base de datos vuelve con valor 0;
				if(query.executeUpdate("DROP DATABASE `" + getSelectedDatabase() + "`") >= 0){
					String msg = getSelectedDatabase() + " " + resource.getString("DropDatabase.success");
					JOptionPane.showMessageDialog(null, msg, resource.getString("DropDatabase.title"), JOptionPane.INFORMATION_MESSAGE);
					//Actualiza la estructura de datos con la base de datos y el Tree con la lista de bases de datos y tablas.
					refreshNewDatabase();
				}else{
					String err = query.getErrCode() + ": " + query.getErrMsg();
					JOptionPane.showMessageDialog(null, err, resource.getString("DropDatabase.title"), JOptionPane.ERROR_MESSAGE);										
				}
			}
		}else{
			JOptionPane.showMessageDialog(null, resource.getString("DropDatabase.error.no_selection"), resource.getString("DropDatabase.title"), JOptionPane.ERROR_MESSAGE);
		}
	}
}
