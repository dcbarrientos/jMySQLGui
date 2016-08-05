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
 * TableColumnsPanel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 3 de ago. de 2016, 6:45:09 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui.tableinfo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ar.com.dcbarrientos.jmysqlgui.database.CQuery;
import ar.com.dcbarrientos.jmysqlgui.database.CTabla;
import ar.com.dcbarrientos.jmysqlgui.database.CTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class TableColumnsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTable table;
	private JPanel panel;
	private FlowLayout flowLayout;
	private JLabel lblCount;
	private JLabel lblCountValue;
	private JScrollPane scrollPane;
	
	private Connection connection;
	private ResourceBundle resource;
	private CTabla tabla;
	private int cantidadColumnas;
	
	public TableColumnsPanel(Connection connection, ResourceBundle resource){
		this.connection = connection;
		this.resource = resource;
		cantidadColumnas = 0;
		initComponents();
	}
	
	private void initComponents(){
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		flowLayout = (FlowLayout) panel.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		add(panel, BorderLayout.SOUTH);
		
		lblCount = new JLabel("Count:");
		panel.add(lblCount);
		
		lblCountValue = new JLabel("New label");
		panel.add(lblCountValue);
		
		scrollPane = new JScrollPane();
		add(scrollPane, BorderLayout.CENTER);
		
		table = new JTable();
		scrollPane.setViewportView(table);		
	}
	
	public void setTabla(String databaseName, String tableName){
		CQuery query = new CQuery(connection);
		String sqlTxt = "SHOW FULL COLUMNS FROM `" + databaseName + "`.`" + tableName + "`";
		cantidadColumnas = query.executeQuery(sqlTxt);
		if(cantidadColumnas > 0){
			String[] headers = {resource.getString("TableCollumnPanel.header1"), resource.getString("TableCollumnPanel.header2"),
					resource.getString("TableCollumnPanel.header1"), resource.getString("TableCollumnPanel.header2"),
					resource.getString("TableCollumnPanel.header3"), resource.getString("TableCollumnPanel.header4"),
					resource.getString("TableCollumnPanel.header5"), resource.getString("TableCollumnPanel.header6"),
					resource.getString("TableCollumnPanel.header7")};
			Object[][] datos = query.getDatos();
			CTableModel tm = new CTableModel(datos, headers);
			table.setModel(tm.getTableModel());
			lblCountValue.setText(Integer.toString(cantidadColumnas));
		}
	}
	
	public void setTabla(CTabla tabla){
		this.tabla = tabla;
	}
}
