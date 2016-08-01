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
 * Variables.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 31 de jul. de 2016, 7:22:14 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.util.ResourceBundle;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import ar.com.dcbarrientos.jmysqlgui.database.CQuery;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class Variables extends JPanel{
	private static final long serialVersionUID = 1L;
	private CConnection cconnection;
	private ResourceBundle resource;
	private int cantidadVariables; 		//Cantidad de variables.
	
	JScrollPane scroll;
	JTable tableVariables;
	
	public Variables(CConnection cconnection, ResourceBundle resource){
		super();
		this.cconnection = cconnection;
		this.resource = resource;
		cantidadVariables = 0;
		
		initComponents();
	}
	
	private void initComponents(){
		
		tableVariables = new JTable();
		String sql = "SHOW VARIABLES;";
		
		String[] headers = {resource.getString("Variables.header1"), resource.getString("Variables.header2")};
		
		CQuery query = new CQuery(cconnection.getConnection());
		query.setHeaders(headers);
		cantidadVariables = query.executeQuery(sql); 
		if(cantidadVariables > 0){
			tableVariables.setModel(query.getModel());			
		}
		query.cerrar();
		
		scroll = new JScrollPane();
		scroll.setViewportView(tableVariables);
		
		setLayout(new BorderLayout());
		add(scroll);
	}
	
	public int getCantidadVariables(){
		return cantidadVariables;
	}
}
