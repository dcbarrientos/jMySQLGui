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
 * IndexesPanel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 16 de set. de 2016, 12:37:47 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class IndexesPanel extends JPanel{
	public IndexesPanel() {
		initComponents();
	}
	private void initComponents() {
		
		panelIndexes = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelIndexes, GroupLayout.DEFAULT_SIZE, 722, Short.MAX_VALUE)
					.addGap(255))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelIndexes, GroupLayout.PREFERRED_SIZE, 278, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		panelTableIndexes = new JPanel();
		panelTableIndexes.setBorder(new TitledBorder(null, " Indexes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelTableIndexes.setLayout(new BorderLayout(0, 0));
		
		scrollPaneIndexes = new JScrollPane();
		panelTableIndexes.add(scrollPaneIndexes, BorderLayout.CENTER);
		
		tableIndexes = new JTable();
		scrollPaneIndexes.setViewportView(tableIndexes);
		
		panelButtonIndexes = new JPanel();
		FlowLayout fl_panelButtonIndexes = (FlowLayout) panelButtonIndexes.getLayout();
		fl_panelButtonIndexes.setAlignment(FlowLayout.LEFT);
		panelTableIndexes.add(panelButtonIndexes, BorderLayout.NORTH);
		
		btnAddIndex = new JButton("Add");
		panelButtonIndexes.add(btnAddIndex);
		
		btnDeleteIndex = new JButton("Delete");
		panelButtonIndexes.add(btnDeleteIndex);
		
		panelIndexColumns = new JPanel();
		panelIndexColumns.setBorder(new TitledBorder(null, " Index Columns", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelIndexColumns.setLayout(new BorderLayout(0, 0));
		
		scrollPaneIndexColumns = new JScrollPane();
		panelIndexColumns.add(scrollPaneIndexColumns, BorderLayout.CENTER);
		
		tableIndexColums = new JTable();
		scrollPaneIndexColumns.setViewportView(tableIndexColums);
		
		panelIndexOptions = new JPanel();
		panelIndexOptions.setBorder(new TitledBorder(null, " Index options ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		cbIndexOptionsStorageType = new JComboBox();
		
		lblStorageType = new JLabel("Storage type:");
		
		txtKeyBlockSize = new JTextField();
		txtKeyBlockSize.setColumns(10);
		
		lblKeyBlockSize = new JLabel("Key block size:");
		
		txtParser = new JTextField();
		txtParser.setColumns(10);
		
		lblParser = new JLabel("Parser:");
		
		panelIndexOptionsComment = new JPanel();
		panelIndexOptionsComment.setBorder(new TitledBorder(null, " Index comment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panelIndexOptions = new GroupLayout(panelIndexOptions);
		gl_panelIndexOptions.setHorizontalGroup(
			gl_panelIndexOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndexOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panelIndexOptions.createSequentialGroup()
							.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblStorageType)
								.addComponent(lblKeyBlockSize)
								.addComponent(lblParser))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.LEADING, false)
								.addComponent(txtParser, Alignment.TRAILING)
								.addComponent(txtKeyBlockSize, Alignment.TRAILING)
								.addComponent(cbIndexOptionsStorageType, Alignment.TRAILING, 0, 122, Short.MAX_VALUE)))
						.addComponent(panelIndexOptionsComment, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelIndexOptions.setVerticalGroup(
			gl_panelIndexOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndexOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbIndexOptionsStorageType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStorageType))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtKeyBlockSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKeyBlockSize))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtParser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblParser))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelIndexOptionsComment, GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
					.addContainerGap())
		);
		panelIndexOptionsComment.setLayout(new BorderLayout(0, 0));
		
		scrollPaneIndexComment = new JScrollPane();
		panelIndexOptionsComment.add(scrollPaneIndexComment, BorderLayout.CENTER);
		
		txtIndexOptionsComment = new JTextArea();
		scrollPaneIndexComment.setViewportView(txtIndexOptionsComment);
		panelIndexOptions.setLayout(gl_panelIndexOptions);
		GroupLayout gl_panelIndexes = new GroupLayout(panelIndexes);
		gl_panelIndexes.setHorizontalGroup(
			gl_panelIndexes.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelIndexes.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelTableIndexes, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panelIndexColumns, GroupLayout.PREFERRED_SIZE, 225, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelIndexOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelIndexes.setVerticalGroup(
			gl_panelIndexes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndexes.createSequentialGroup()
					.addGroup(gl_panelIndexes.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(panelTableIndexes, 0, 0, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panelIndexes.createParallelGroup(Alignment.BASELINE)
							.addComponent(panelIndexColumns, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)
							.addComponent(panelIndexOptions, GroupLayout.DEFAULT_SIZE, 348, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panelIndexes.setLayout(gl_panelIndexes);
		setLayout(groupLayout);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 2965482883362110620L;
	private JPanel panelTableIndexes;
	private JScrollPane scrollPaneIndexes;
	private JTable tableIndexes;
	private JPanel panelIndexColumns;
	private JScrollPane scrollPaneIndexColumns;
	private JTable tableIndexColums;
	private JPanel panelIndexOptions;
	private JPanel panelButtonIndexes;
	private JButton btnAddIndex;
	private JButton btnDeleteIndex;
	private JComboBox cbIndexOptionsStorageType;
	private JLabel lblStorageType;
	private JTextField txtKeyBlockSize;
	private JLabel lblKeyBlockSize;
	private JTextField txtParser;
	private JLabel lblParser;
	private JPanel panelIndexOptionsComment;
	private JScrollPane scrollPaneIndexComment;
	private JTextArea txtIndexOptionsComment;
	private JPanel panelIndexes;
}
