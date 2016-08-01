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
 * Status.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 31 de jul. de 2016, 8:47:00 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import ar.com.dcbarrientos.jmysqlgui.database.CQuery;
import ar.com.dcbarrientos.jmysqlgui.database.CTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class StatusTab extends JPanel {
	private static final long serialVersionUID = 1L;
	private CConnection cconnection;
	private ResourceBundle resource;
	private int cantidadStatus; 		//Cantidad de variables.
	
	JScrollPane scroll;
	JTable tableStatus;
	
	public StatusTab(CConnection cconnection, ResourceBundle resource){
		super();
		this.cconnection = cconnection;
		this.resource = resource;
		cantidadStatus = 0;
		
		initComponents();
	}
	
	private void initComponents(){
		
		tableStatus= new JTable();
		String sql = "SHOW STATUS;";
		
		String[] headers = {resource.getString("Status.header1"), resource.getString("Status.header2")};
		
		CQuery query = new CQuery(cconnection.getConnection());
		//query.setHeaders(headers);
		cantidadStatus = query.executeQuery(sql); 
		if(cantidadStatus > 0){
			CTableModel cTableModel = new CTableModel(query.getDatos(), headers);
			tableStatus.setModel(cTableModel.getTableModel());
		}
		query.cerrar();
		
		scroll = new JScrollPane();
		scroll.setViewportView(tableStatus);
		
		setLayout(new BorderLayout());
		add(scroll);
	}
	
	public int getCantidadStatus(){
		return cantidadStatus;
	}
}
