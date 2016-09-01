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
 * ProcessList.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 31 de jul. de 2016, 8:26:56 p. m. 
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
public class ProcessListTab extends JPanel{
	private static final long serialVersionUID = 1L;
	private CConnection cconnection;
	private ResourceBundle resource;
	private int cantidadProcesos; 		//Cantidad de variables.
	
	JScrollPane scroll;
	JTable tableProcesos;
	
	/**
	 * Construye una lista de procesos del servidor.
	 * @param cconnection Conexión establecida.
	 * @param resource ResourceBundle en donde estan los string de la aplicación.
	 */
	public ProcessListTab(CConnection cconnection, ResourceBundle resource){
		super();
		this.cconnection = cconnection;
		this.resource = resource;
		cantidadProcesos = 0;
		
		initComponents();
	}
	
	/**
	 * Inicializo la interfaz gráfica.
	 */
	private void initComponents(){
		
		tableProcesos= new JTable();
		String sql = "SHOW PROCESSLIST;";
		
		String[] headers = {resource.getString("ProcessList.cabezaTabla1"), resource.getString("ProcessList.cabezaTabla2"), 
				resource.getString("ProcessList.cabezaTabla3"), resource.getString("ProcessList.cabezaTabla4"), 
				resource.getString("ProcessList.cabezaTabla5"), resource.getString("ProcessList.cabezaTabla6"), 
				resource.getString("ProcessList.cabezaTabla7"), resource.getString("ProcessList.cabezaTabla8")};
		
		CQuery query = new CQuery(cconnection.getConnection());
		//query.setHeaders(headers);
		cantidadProcesos = query.executeQuery(sql); 
		if(cantidadProcesos > 0){
			CTableModel cTableModel = new CTableModel(query.getDatos(), headers);
			tableProcesos.setModel(cTableModel);			
		}
		query.cerrar();
		
		scroll = new JScrollPane();
		scroll.setViewportView(tableProcesos);
		
		setLayout(new BorderLayout());
		add(scroll);
	}
	
	/**
	 * @return Devuelve la cantidad de procesos.
	 */
	public int getCantidadProcesos(){
		return cantidadProcesos;
	}

}
