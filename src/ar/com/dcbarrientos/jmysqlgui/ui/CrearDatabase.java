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
 * CrearDatabase.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 5 de ago. de 2016, 11:09:35 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import ar.com.dcbarrientos.jmysqlgui.database.CQuery;

import java.awt.Dimension;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CrearDatabase extends JDialog{
	private static final long serialVersionUID = 1L;

	private JPanel panel;
	private JLabel lblDatabaseName;
	private JTextField txtDatabaseName;
	private JButton btnOk;
	private JButton btnCancel;
	
	private Principal principal;
	private Connection connection;
	private ResourceBundle resource;
	private boolean success;
	
	public CrearDatabase(Principal principal, Connection connection, ResourceBundle resource) {
		super(principal, true);
		this.principal = principal;
		this.connection = connection;
		this.resource = resource;
		success = false;
		
		initComponents();
	}
	private void initComponents() {
		setMinimumSize(new Dimension(347, 169));
		
		panel = new JPanel();
		panel.setBounds(10, 11, 308, 73);
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		btnOk = new JButton(resource.getString("CrearDatabase.btnOk"));
		btnOk.setBounds(130, 95, 89, 23);
		btnOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				btnOkMouseClicked(e);
			}
		});
		
		btnCancel = new JButton(resource.getString("CrearDatabase.btnCancel"));
		btnCancel.setBounds(229, 95, 89, 23);
		btnCancel.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				btnCancelMouseClicked(e);
			}
		});
		
		lblDatabaseName = new JLabel(resource.getString("CrearDatabase.lblDatabaseName"));
		lblDatabaseName.setBounds(12, 13, 216, 14);
		
		txtDatabaseName = new JTextField();
		txtDatabaseName.setBounds(12, 33, 284, 20);
		txtDatabaseName.setColumns(10);
		
		getContentPane().setLayout(null);
		getContentPane().add(panel);
		panel.setLayout(null);
		panel.add(txtDatabaseName);
		panel.add(lblDatabaseName);
		getContentPane().add(btnOk);
		getContentPane().add(btnCancel);
		
		setTitle(resource.getString("CrearDatabase.title"));
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		//pack();
		setLocationRelativeTo(principal);

	}
	
	/**
	 * Procedimiento que crea una base de datos.
	 * @param nombre Nombre de la base de datos a crear.
	 * @return Verdadero si se pudo crear la base de datos.
	 */
	private boolean crearDatabase(String nombre){
		String sqlTxt = "CREATE DATABASE `" + nombre + "`";
		CQuery query = new CQuery(connection);
		if(query.executeUpdate(sqlTxt) > 0)
			return true;
		
		JOptionPane.showMessageDialog(null, query.getErrCode() + ": " + query.getErrMsg(), resource.getString("CrearDatabase.errorTitle"), JOptionPane.ERROR_MESSAGE);
		
		return false;
	}
	
	
	private void btnOkMouseClicked(MouseEvent e){
		if(txtDatabaseName.getText().length() > 0){
			success = crearDatabase(txtDatabaseName.getText());
			if(success)
			{	setVisible(false);
				dispose();
			}
		}		
	}
	
	private void btnCancelMouseClicked(MouseEvent e){
		setVisible(false);
		dispose();
	}
	
	public boolean showDialog(){
		setVisible(true);
		return success;
	}
	
}
