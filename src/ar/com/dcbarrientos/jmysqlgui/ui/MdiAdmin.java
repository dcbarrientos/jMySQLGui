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

import java.util.ResourceBundle;

import javax.swing.JPanel;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import javax.swing.JSplitPane;
import java.awt.BorderLayout;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class MdiAdmin extends JPanel{
	private static final long serialVersionUID = 1L;
	private CConnection conection;
	private ResourceBundle resource;
	
	public MdiAdmin(ResourceBundle resource){
		this.resource = resource;
		setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		add(splitPane, BorderLayout.CENTER);
		
		initComponents();
	}
	
	private void initComponents(){
		
	}
	
	public void setConnection(CConnection connection){
		this.conection = connection;
	}
}
