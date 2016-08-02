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
 * Host.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 31 de jul. de 2016, 6:45:47 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class HostTab extends JPanel{
	private static final long serialVersionUID = 1L;
	//private MdiAdmin admin;
	private CConnection cconnection;
	private ResourceBundle resource;
	
	private JLabel lblServer;
	private JTabbedPane tabbedPane;
	
	/**
	 * Constructor de la solapa Host.
	 * @param cconnection Conexión abierta
	 * @param resource ResourceBundle que contiene los strings de la aplicación.
	 */
	public HostTab(CConnection cconnection, ResourceBundle resource){
		super();
		//this.admin = admin;
		this.cconnection = cconnection;
		this.resource = resource;
		
		initComponents();
	}
	
	/**
	 * Inicializo la interfaz gráfica.
	 */
	private void initComponents(){
		lblServer = new JLabel();
		lblServer.setText("  " + cconnection.getHost() + " " + resource.getString("Host.lblServer") + " " + cconnection.getVersion());
		lblServer.setForeground(Color.white);
		lblServer.setBackground(Color.BLACK);
		lblServer.setOpaque(true);
		
		tabbedPane = new JTabbedPane();
		
		VariablesTab variables = new VariablesTab(cconnection, resource);
		tabbedPane.addTab(resource.getString("Variables.title") + " (" + variables.getCantidadVariables() + ")", variables);
		
		ProcessListTab process = new ProcessListTab(cconnection, resource);
		tabbedPane.addTab(resource.getString("ProcessList.title") + " (" + process.getCantidadProcesos() + ")", process);
		
		StatusTab status = new StatusTab(cconnection, resource);
		tabbedPane.addTab(resource.getString("Status.title") + " (" + status.getCantidadStatus() + ")", status);
		
		EstadisticasTab estadisticas = new EstadisticasTab(cconnection, resource);
		tabbedPane.addTab(resource.getString("Estadisticas.title") + " (" + estadisticas.getCantidadEstadisticas() + ")", estadisticas);

		setLayout(new BorderLayout());
		add(lblServer, BorderLayout.NORTH);
		add(tabbedPane, BorderLayout.CENTER);
	}
	
}
