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
 * CopyTable.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 5 de set. de 2016, 9:40:54 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;

import ar.com.dcbarrientos.jmysqlgui.database.CQuery;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CopyTable extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private static final int COPY_STRUCTRUE_DATA = 1;
	
	private boolean isOk = false; 
	
	private Connection connection;
	private ResourceBundle resource;
	private JPanel panel;
	private JLabel lblOldTable;
	private JTextField txtNewTableName;
	private JButton btnCancel;
	private JButton btnOk;

	private String databaseName;
	private String tableName;
	private JLabel lblCopy;
	private JComboBox<String> cbCopy;
	
	public CopyTable(Principal principal, Connection connection, ResourceBundle resource, String databaseName, String tableName){
		super(principal, true);
		this.databaseName = databaseName;
		this.tableName = tableName;
		this.connection = connection;
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		btnCancel = new JButton(resource.getString("CopyTable.btnCancel"));
		btnCancel.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				isOk = false;
				dispose();
			}
		});
		
		btnOk = new JButton(resource.getString("CopyTable.btnOk"));
		btnOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				copyTable();
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(btnOk, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnOk))
					.addContainerGap(191, Short.MAX_VALUE))
		);
		
		String strTableName = String.format(resource.getString("CopyTable.lblOldTable"), databaseName, tableName);
		lblOldTable = new JLabel(strTableName);
		//lblOldTable.setText(resource.getString("CopyTable.lblOldTable"));
		
		txtNewTableName = new JTextField(tableName + "_copy");
		txtNewTableName.setColumns(10);
		
		lblCopy = new JLabel(resource.getString("CopyTable.lblCopy"));
		
		String[] cbCopyValues = {resource.getString("CopyTable.copyStructure"), resource.getString("CopyTable.copyStructureData")};
		cbCopy = new JComboBox<String>(cbCopyValues);
		
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(txtNewTableName, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
						.addComponent(lblOldTable, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblCopy)
							.addGap(10)
							.addComponent(cbCopy, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblOldTable)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(txtNewTableName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbCopy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCopy, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(15, Short.MAX_VALUE))
		);
		
		
		panel.setLayout(gl_panel);
		
		setTitle(resource.getString("CopyTable.title"));
		getContentPane().setLayout(groupLayout);
		setResizable(false);
		setPreferredSize(new Dimension(440, 180));		
		pack();
		setLocationRelativeTo(null);
	}
	
	public boolean showDialog(){
		setVisible(true);
		return isOk;
	}
		
	public void copyTable(){
		//Copy structure
		CQuery query = new CQuery(connection);
		isOk = true;
		String sqlTxt = String.format(resource.getString("CopyTable.sqlCopyStructure"), txtNewTableName.getText(), databaseName, tableName);
		int result = query.executeUpdate(sqlTxt); 
		if(result == CQuery.ERROR){
			isOk = false;
			JOptionPane.showMessageDialog(null, query.getErrCode() + ": " + query.getErrMsg(), resource.getString("CopyTable.title"), JOptionPane.ERROR_MESSAGE);
		}
		
		//Copy data
		if(isOk && cbCopy.getSelectedIndex() == COPY_STRUCTRUE_DATA){
			sqlTxt = String.format(resource.getString("CopyTable.sqlCopyData"), txtNewTableName.getText(), databaseName, tableName);
			result = query.executeUpdate(sqlTxt); 
			if(result == ERROR){
				isOk = false;
				JOptionPane.showMessageDialog(null, query.getErrCode() + ": " + query.getErrMsg(), resource.getString("CopyTable.title"), JOptionPane.ERROR_MESSAGE);
			}
		}

		if(isOk){
			dispose();
		}
	}
}
