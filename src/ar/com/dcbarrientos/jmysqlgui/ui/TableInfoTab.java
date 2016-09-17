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
 * TableInfo.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 3 de ago. de 2016, 12:54:15 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ar.com.dcbarrientos.jmysqlgui.database.CDatabase;
import ar.com.dcbarrientos.jmysqlgui.database.CTabla;
import ar.com.dcbarrientos.jmysqlgui.ui.table.TableColumnsPanel;
import ar.com.dcbarrientos.jmysqlgui.ui.table.TableInfoPanel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class TableInfoTab extends JPanel{	
	private static final long serialVersionUID = 1L;
	private MdiAdmin admin;
	private Connection connection;
	private ResourceBundle resource;
	private CTabla tabla;
	
	private JTabbedPane tabbedPane;
	private TableInfoPanel panelInfo;
	private TableColumnsPanel panelColumns;
	private JPanel panelIndexes;
	
	public TableInfoTab(MdiAdmin admin, Connection connection, ResourceBundle resource){
		super();
		this.admin = admin;
		this.connection = connection;
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new BorderLayout(0, 0));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		add(tabbedPane, BorderLayout.CENTER);
		
		panelInfo = new TableInfoPanel(resource);
		tabbedPane.addTab(resource.getString("TableInfoTab.tabInfo"), null, panelInfo, null);
		
		panelColumns = new TableColumnsPanel(connection, resource);
		tabbedPane.addTab(resource.getString("TableInfoTab.tabColumns"), null, panelColumns, null);
		
		panelIndexes = new JPanel();
		tabbedPane.addTab("New tab", null, panelIndexes, null);
		
	}
	
	public void setSelectedTable(CDatabase database, String tableName){
		this.tabla = database.getTabla(tableName);
		panelInfo.setTabla(tabla);
		panelColumns.setTabla(database.getName(), tableName);
	}	
}
