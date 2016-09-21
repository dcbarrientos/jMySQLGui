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
 * TableEditor.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 12 de set. de 2016, 10:13:13 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.ResourceBundle;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ar.com.dcbarrientos.jmysqlgui.database.CIndex;
import ar.com.dcbarrientos.jmysqlgui.database.CMySQL;
import ar.com.dcbarrientos.jmysqlgui.database.CNewTableField;
import ar.com.dcbarrientos.jmysqlgui.database.CQuery;
import ar.com.dcbarrientos.jmysqlgui.ui.table.NewTableModel;
import ar.com.dcbarrientos.jmysqlgui.ui.table.NewTableRenderer;
import ar.com.dcbarrientos.jmysqlgui.ui.table.TableEditorIndexColumnsModel;
import ar.com.dcbarrientos.jmysqlgui.ui.table.TableEditorIndexModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class TableEditor extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private static final int OK = 0;
	private static final int INDEX_TYPE_COLUMN = 1;
	
	private MdiAdmin admin;
	private Connection connection;
	private ResourceBundle resource;
	private CMySQL mySql;
	private LinkedHashMap<String, boolean[]> dataType;
	//private Vector<CIndex>indices;
	private String databaseName;
	private String tableName;
	private NewTableModel tableStructureModel;
	private DataTypeComboModel dataTypeModel;
	private TableEditorIndexModel tableIndexesModel;
	TableEditorIndexColumnsModel tableEditorIndexColumnsModel;
	
	private JPanel panel;
	private JLabel lblNewLabel;
	private JLabel lblTableName;
	private JTextField txtTableName;
	private JLabel lblSchema;
	private JLabel lblSchemaValue;
	private JLabel lblCollation;
	private JComboBox<String> cbCollation;
	private JLabel lblEngine;
	private JComboBox<String> cbEngine;
	private JLabel lblComments;
	private JScrollPane scrollPane;
	private JTextArea txtComments;
	private JTabbedPane tabbedPane;
	private JButton btnOK;
	private JButton btnCancel;
	private JPanel panelColumnas;
	private JScrollPane scrollPane_1;
	private JPanel panel_2;
	private JButton btnAdd;
	private JLabel lblColumns;
	private JButton btnDelete;
	private JButton btnUp;
	private JButton btnDown;
	private JPanel panel_3;
	private JLabel lblColumnName;
	private JLabel lblCollation_1;
	private JTextField txtColumnName;
	private JComboBox<String> cbFieldCollation;
	private JLabel lblDataType;
	private JComboBox<String> cbDataType;
	private JLabel lblDefault;
	private JTextField txtDefault;
	private JLabel lblComments_1;
	private JScrollPane scrollPane_2;
	private JTextArea txtColumnComments;
	private JLabel lblLength;
	private JTextField txtLength;
	private JCheckBox chPrimaryKey;
	private JCheckBox chNotNull;
	private JCheckBox chUnique;
	private JCheckBox chBinary;
	private JCheckBox chUnsigned;
	private JCheckBox chZeroFill;
	private JCheckBox chAutoIncrement;
	private JButton btnQuery;
	private JTable tableStructure;
	
	// Indexes panel
	private JPanel panelIndexes;	
	private JPanel panelIndexOptions;
	private JLabel lblStorageTypes;
	private JLabel lblKeyBlockSize;
	private JLabel lblParser;
	private JTextField txtParser;
	private JTextField txtKeyBlockSize;
	private JComboBox cbStorageType;
	private JPanel panelIndexComment;
	private JPanel panelIndexesList;
	private JPanel panelBotones;
	private JButton btnAddIndex;
	private JButton btnDeleteIndex;
	private JScrollPane scrollPaneIndexesList;
	private JPanel panelIndexColumns;
	private JTable tableIndexes;
	private JScrollPane scrollPaneIndexColumns;
	private JTable tableIndexColumns;
	private JScrollPane scrollPaneIndexComment;
	private JTextArea txtrIndexcomment;	
	private JComboBox<String> cbIndexesList; 
	
	
	public TableEditor(MdiAdmin admin, Connection connection, ResourceBundle resource, String databaseName){
		super(admin.getPrincipal(), true);
		this.admin = admin;
		this.connection = connection;
		this.resource = resource;
		this.databaseName = databaseName;		
		
		mySql = new CMySQL(this.connection);
		
		initComponents();
	}
	
	private void initComponents() {
		setMinimumSize(new Dimension(700, 600));
		
		panel = new JPanel();
		panel.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		btnOK = new JButton("Ok");
		
		btnCancel = new JButton("Cancel");
		
		btnQuery = new JButton("Query");
		btnQuery.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				createQuery();
			}
		});
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnQuery)
							.addGap(79)
							.addComponent(btnOK, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 79, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 142, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnOK)
						.addComponent(btnQuery))
					.addContainerGap())
		);

		tabbedPane.addTab("Columns", null, getColumnsPanel(), null);
		tabbedPane.addTab("Indexes", null, getIndexesPanel(), null);

		lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(TableEditor.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/table_edit.png")));
		
		lblTableName = new JLabel("Table Name:");
		
		txtTableName = new JTextField();
		txtTableName.setColumns(10);
		
		lblSchema = new JLabel("Schema:");
		
		lblSchemaValue = new JLabel("New label");
		
		lblCollation = new JLabel("Collation:");
		
		cbCollation = new JComboBox<String>();
		lblCollation.setLabelFor(cbCollation);
		
		lblEngine = new JLabel("Engine:");
		
		cbEngine = new JComboBox<String>();
		lblEngine.setLabelFor(cbEngine);
		
		lblComments = new JLabel("Comments:");
		
		scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblTableName)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtTableName, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblCollation)
									.addGap(18, 18, Short.MAX_VALUE)
									.addComponent(cbCollation, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblSchema)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblSchemaValue))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblEngine)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbEngine, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(98, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(lblComments)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblTableName)
								.addComponent(txtTableName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblSchema)
								.addComponent(lblSchemaValue))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblEngine)
								.addComponent(cbEngine, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblCollation)
								.addComponent(cbCollation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblComments)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		txtComments = new JTextArea();
		scrollPane.setViewportView(txtComments);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(groupLayout);
		
		pack();
		setLocationRelativeTo(null);
		initValues();
	}
	
	private JPanel getIndexesPanel(){
		panelIndexes = new JPanel();
		panelIndexOptions = new JPanel();
		panelIndexOptions.setBorder(new TitledBorder(null, " Index options ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		lblStorageTypes = new JLabel("Storage type:");
		
		lblKeyBlockSize = new JLabel("Key block size:");
		
		lblParser = new JLabel("Parser:");
		
		txtParser = new JTextField();
		txtParser.setColumns(10);
		
		txtKeyBlockSize = new JTextField();
		txtKeyBlockSize.setColumns(10);
		
		cbStorageType = new JComboBox();
		
		panelIndexComment = new JPanel();
		panelIndexComment.setBorder(new TitledBorder(null, " Index comment", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panelIndexComment.setLayout(new BorderLayout(0, 0));
		GroupLayout gl_panelIndexOptions = new GroupLayout(panelIndexOptions);
		gl_panelIndexOptions.setHorizontalGroup(
			gl_panelIndexOptions.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelIndexOptions.createSequentialGroup()
					.addContainerGap(17, Short.MAX_VALUE)
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblStorageTypes)
						.addComponent(lblKeyBlockSize)
						.addComponent(lblParser))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.LEADING, false)
						.addComponent(txtParser, Alignment.TRAILING)
						.addComponent(txtKeyBlockSize, Alignment.TRAILING)
						.addComponent(cbStorageType, Alignment.TRAILING, 0, 122, Short.MAX_VALUE))
					.addContainerGap())
				.addComponent(panelIndexComment, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
		);
		gl_panelIndexOptions.setVerticalGroup(
			gl_panelIndexOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndexOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbStorageType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStorageTypes))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtKeyBlockSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblKeyBlockSize))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelIndexOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(txtParser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblParser))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelIndexComment, GroupLayout.DEFAULT_SIZE, 190, Short.MAX_VALUE))
		);
		
		scrollPaneIndexComment = new JScrollPane();
		panelIndexComment.add(scrollPaneIndexComment, BorderLayout.CENTER);
		
		txtrIndexcomment = new JTextArea();
		scrollPaneIndexComment.setViewportView(txtrIndexcomment);
		panelIndexOptions.setLayout(gl_panelIndexOptions);
		
		panelIndexesList = new JPanel();
		panelIndexesList.setBorder(new TitledBorder(null, " Indexes", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panelIndexColumns = new JPanel();
		panelIndexColumns.setBorder(new TitledBorder(null, " Index Columns", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panelIndexes = new GroupLayout(panelIndexes);
		gl_panelIndexes.setHorizontalGroup(
			gl_panelIndexes.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelIndexes.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelIndexesList, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelIndexColumns, GroupLayout.DEFAULT_SIZE, 205, Short.MAX_VALUE)
					.addGap(3)
					.addComponent(panelIndexOptions, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addGap(14))
		);
		gl_panelIndexes.setVerticalGroup(
			gl_panelIndexes.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panelIndexes.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelIndexes.createParallelGroup(Alignment.TRAILING)
						.addComponent(panelIndexesList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_panelIndexes.createParallelGroup(Alignment.BASELINE)
							.addComponent(panelIndexOptions, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
							.addComponent(panelIndexColumns, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)))
					.addContainerGap())
		);
		panelIndexColumns.setLayout(new BorderLayout(0, 0));
		
		scrollPaneIndexColumns = new JScrollPane();
		panelIndexColumns.add(scrollPaneIndexColumns, BorderLayout.CENTER);
		
		tableIndexColumns = new JTable();
		
		//tableIndexColumns.setDefaultRenderer(JCheckBox.class, new TableEditorIndexColumnsRenderer());
		
		
		scrollPaneIndexColumns.setViewportView(tableIndexColumns);
		
		panelBotones = new JPanel();
		FlowLayout fl_panelBotones = (FlowLayout) panelBotones.getLayout();
		fl_panelBotones.setAlignment(FlowLayout.LEFT);
		
		btnAddIndex = new JButton("Add");
		btnAddIndex.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				addNewIndex();
			}
		});
		panelBotones.add(btnAddIndex);
		
		btnDeleteIndex = new JButton("Delete");
		panelBotones.add(btnDeleteIndex);
		
		scrollPaneIndexesList = new JScrollPane();
		GroupLayout gl_panelIndexesList = new GroupLayout(panelIndexesList);
		gl_panelIndexesList.setHorizontalGroup(
			gl_panelIndexesList.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelIndexesList.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelIndexesList.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPaneIndexesList, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE)
						.addComponent(panelBotones, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 215, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelIndexesList.setVerticalGroup(
			gl_panelIndexesList.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelIndexesList.createSequentialGroup()
					.addComponent(panelBotones, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPaneIndexesList, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
		);
		
		tableIndexes = new JTable();
		tableIndexesModel = new TableEditorIndexModel();
		tableIndexes.setModel(tableIndexesModel);
		tableIndexes.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				selectIndex();
				System.out.println("Hay un cambio en la seleccion");
				
			}
		});
		
		scrollPaneIndexesList.setViewportView(tableIndexes);
		panelIndexesList.setLayout(gl_panelIndexesList);
		panelIndexes.setLayout(gl_panelIndexes);
				
		return panelIndexes;
	}
	
	private JPanel getColumnsPanel(){
		panelColumnas = new JPanel();

		scrollPane_1 = new JScrollPane();
		
		panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GroupLayout gl_panelColumnas = new GroupLayout(panelColumnas);
		gl_panelColumnas.setHorizontalGroup(
			gl_panelColumnas.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelColumnas.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelColumnas.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_panelColumnas.setVerticalGroup(
			gl_panelColumnas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelColumnas.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		tableStructure = new JTable();
		tableStructure.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		tableStructureModel = new NewTableModel();
		tableStructure.setModel(tableStructureModel);
		tableStructure.setDefaultRenderer(JLabel.class, new NewTableRenderer());
		
		scrollPane_1.setViewportView(tableStructure);
		
		lblColumnName = new JLabel("Column name:");
		
		lblCollation_1 = new JLabel("Collation:");
		
		txtColumnName = new JTextField();
		txtColumnName.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(txtColumnName.getText(), tableStructure.getSelectedRow(), CNewTableField.NAME_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		txtColumnName.setColumns(10);
		
		cbFieldCollation = new JComboBox<String>();
		cbFieldCollation.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(cbFieldCollation.getSelectedItem(), selectedRow, CNewTableField.COLLATION_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		
		lblDataType = new JLabel("Data type:");
		
		cbDataType = new JComboBox<String>();
		cbDataType.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if(cbDataType.getSelectedIndex() >= 0){
					setAttributes(dataType.get(cbDataType.getSelectedItem()));
					
					int selectedRow = tableStructure.getSelectedRow();
					if(selectedRow >= 0 && cbDataType.getSelectedIndex() >= 0){
						if(txtLength.isEnabled())
							tableStructure.setValueAt(cbDataType.getSelectedItem() + "(" + txtLength.getText() + ")", selectedRow, CNewTableField.DATA_TYPE_INDEX);
						else
							tableStructure.setValueAt(cbDataType.getSelectedItem(), selectedRow, CNewTableField.DATA_TYPE_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
					}
				}
			}
		});
		
		lblDefault = new JLabel("Default:");
		
		txtDefault = new JTextField();
		txtDefault.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(txtDefault.getText(), selectedRow, CNewTableField.DEFAULT_VALUE_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		txtDefault.setColumns(10);
		
		lblComments_1 = new JLabel("Comments:");
		
		scrollPane_2 = new JScrollPane();
		
		lblLength = new JLabel("Length:");
		
		txtLength = new JTextField();
		txtLength.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(cbDataType.getSelectedItem() + "(" + txtLength.getText() + ")", selectedRow, CNewTableField.DATA_TYPE_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		txtLength.setColumns(10);
		
		chPrimaryKey = new JCheckBox("Primary key");
		chPrimaryKey.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(chPrimaryKey.isSelected(), tableStructure.getSelectedRow(), CNewTableField.PRIMARY_KEY_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
				
				if(chPrimaryKey.isSelected()){
					chNotNull.setSelected(true);
				}
			}
		});
		
		chNotNull = new JCheckBox("Not Null");
		chNotNull.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(chNotNull.isSelected(), tableStructure.getSelectedRow(), CNewTableField.NOT_NULL_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		
		chUnique = new JCheckBox("Unique");
		chUnique.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(chUnique.isSelected(), tableStructure.getSelectedRow(), CNewTableField.UNIQUE_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		
		chBinary = new JCheckBox("Binary");
		chBinary.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(chBinary.isSelected(), tableStructure.getSelectedRow(), CNewTableField.BINARY_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		
		chUnsigned = new JCheckBox("Unsigned");
		chUnsigned.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(chUnsigned.isSelected(), tableStructure.getSelectedRow(), CNewTableField.UNSIGNED_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		
		chZeroFill = new JCheckBox("Zero Fill");
		chZeroFill.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(chZeroFill.isSelected(), tableStructure.getSelectedRow(), CNewTableField.ZERO_FILL_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		
		chAutoIncrement = new JCheckBox("Auto Ingrement");
		chAutoIncrement.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){				
					tableStructure.setValueAt(chAutoIncrement.isSelected(), tableStructure.getSelectedRow(), CNewTableField.AUTO_INCREMENT_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(lblColumnName)
								.addComponent(lblCollation_1))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(txtColumnName)
								.addComponent(cbFieldCollation, 0, 215, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_3.createSequentialGroup()
									.addComponent(lblDataType)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbDataType, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblLength)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(txtLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_3.createSequentialGroup()
									.addComponent(lblDefault)
									.addGap(18)
									.addComponent(txtDefault)))
							.addGap(35))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblComments_1)
							.addGap(18)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_3.createSequentialGroup()
									.addGap(0, 0, Short.MAX_VALUE)
									.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
										.addComponent(chPrimaryKey)
										.addComponent(chBinary))
									.addGap(41)
									.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
										.addComponent(chNotNull)
										.addComponent(chUnsigned))
									.addGap(36)
									.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
										.addComponent(chUnique)
										.addComponent(chZeroFill))
									.addGap(41))
								.addGroup(gl_panel_3.createSequentialGroup()
									.addGap(0, 0, Short.MAX_VALUE)
									.addComponent(chAutoIncrement)
									.addGap(230))))))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColumnName)
						.addComponent(txtColumnName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDataType)
						.addComponent(cbDataType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLength)
						.addComponent(txtLength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCollation_1)
						.addComponent(cbFieldCollation, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDefault)
						.addComponent(txtDefault, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(lblComments_1)
						.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
							.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 99, Short.MAX_VALUE)
							.addGroup(gl_panel_3.createSequentialGroup()
								.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
									.addComponent(chPrimaryKey)
									.addComponent(chUnique)
									.addComponent(chNotNull))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
									.addComponent(chBinary)
									.addComponent(chZeroFill)
									.addComponent(chUnsigned))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(chAutoIncrement))))
					.addContainerGap())
		);
		
		txtColumnComments = new JTextArea();
		txtColumnComments.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int selectedRow = tableStructure.getSelectedRow();
				if(selectedRow >= 0){
					tableStructure.setValueAt(txtColumnComments.getText(), selectedRow, CNewTableField.COMMENTS_INDEX);
					tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				}
			}
		});
		scrollPane_2.setViewportView(txtColumnComments);
		panel_3.setLayout(gl_panel_3);
		
		lblColumns = new JLabel("Columns:");
		panel_2.add(lblColumns);
		
		btnAdd = new JButton("Add");
		btnAdd.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				newRecord();
				emptyFields();
			}
		});
		panel_2.add(btnAdd);
		
		btnDelete = new JButton("Delete");
		panel_2.add(btnDelete);
		
		btnUp = new JButton("Up");
		panel_2.add(btnUp);
		
		btnDown = new JButton("Down");
		panel_2.add(btnDown);
		panelColumnas.setLayout(gl_panelColumnas);
		
		return panelColumnas;
				
	}
	
	private void initValues(){
		dataType = mySql.getDataType();
		//indices = new Vector<CIndex>();
		
		lblSchemaValue.setText(databaseName);
		
		String defaultCollation = mySql.getDefaultCollation();
		DefaultComboBoxModel<String> collationsModel = new DefaultComboBoxModel<String>(mySql.getCollations());
		cbCollation.setModel(collationsModel);
		cbCollation.setSelectedItem(defaultCollation);
		
		String defaultEngine = mySql.getDefaultEngine();
		DefaultComboBoxModel<String> enginesModel = new DefaultComboBoxModel<String>(mySql.getEngines());
		cbEngine.setModel(enginesModel);
		cbEngine.setSelectedItem(defaultEngine);
		
		DefaultComboBoxModel<String> fieldCollationsModel = new DefaultComboBoxModel<String>(mySql.getCollations());
		cbFieldCollation.setModel(fieldCollationsModel);
		cbFieldCollation.setSelectedIndex(-1);

		dataTypeModel = new DataTypeComboModel();
		cbDataType.setModel(dataTypeModel);
		
		tableEditorIndexColumnsModel = new TableEditorIndexColumnsModel();
		tableEditorIndexColumnsModel.addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				if(tableIndexColumns.getRowCount()>0){
					//Paso los datos del modelo de las columnas del indice al modelo del indice.
					int fila = tableIndexes.getSelectedRow();
					tableIndexesModel.setFields(fila, tableEditorIndexColumnsModel.getDatos());
					//tableIndexes.setRowSelectionInterval(fila, fila);
					//System.out.println(tableIndexColumns.getValueAt(tableEditorIndexColumnsModel.getEditedRow(), 1));
					//System.out.println("\t" + tableIndexColumns.getValueAt(tableEditorIndexColumnsModel.getEditedRow(), 0));
				}
			}
		});
		//tableEditorIndexColumnsModel.setFields(tableStructureModel.getFields());
		//tableIndexColumns.setModel(tableEditorIndexColumnsModel);
		
		//Descomentar
		cbIndexesList = new JComboBox<String>(CIndex.getIndexesList());
		tableIndexes.getColumnModel().getColumn(INDEX_TYPE_COLUMN).setCellEditor(new DefaultCellEditor(cbIndexesList));
		
	}

	public void setTableName(String tableName){
		this.tableName = tableName;
		loadTableDefinition();
	}
	
	public int showDialog(){
		int respuesta = OK;
		setVisible(true);
		
		return respuesta;
	}
	
	private void setAttributes(boolean[] attribute){
		txtLength.setEnabled(attribute[CMySQL.ATTRIBUTE_LENGTH]);
		if(!txtLength.isEnabled())
			txtLength.setText("");
		chAutoIncrement.setEnabled(attribute[CMySQL.ATTRIBUTE_AUTO_INCREMENT]);
		if(!chAutoIncrement.isEnabled())
			chAutoIncrement.setSelected(false);
		chBinary.setEnabled(attribute[CMySQL.ATTRIBUTE_BINARY]);
		if(!chBinary.isEnabled())
			chBinary.setSelected(false);
		chZeroFill.setEnabled(attribute[CMySQL.ATTRIBUTE_ZEROFILL]);
		if(!chZeroFill.isEnabled())
			chZeroFill.setSelected(false);
		chUnsigned.setEnabled(attribute[CMySQL.ATTRIBUTE_UNSIGNED]);
		if(!chUnsigned.isEnabled())
			chUnsigned.setEnabled(false);
	}

	private void newRecord(){
		tableStructureModel.addNew();
		tableStructure.setRowSelectionInterval(tableStructure.getRowCount()-1, tableStructure.getRowCount()-1);
	}
	
	private void emptyFields(){
		txtColumnName.setText("");
		cbDataType.setSelectedItem(null);
		txtLength.setText("");
		cbFieldCollation.setSelectedIndex(-1);
		txtDefault.setText("");
		txtColumnComments.setText("");
		chAutoIncrement.setSelected(false);
		chBinary.setSelected(false);
		chNotNull.setSelected(false);
		chPrimaryKey.setSelected(false);
		chUnique.setSelected(false);
		chUnsigned.setSelected(false);
		chZeroFill.setSelected(false);
	}
	
	private void createQuery(){
		/*
		data_type 
					[NOT NULL | NULL] 
				  [DEFAULT default_value]
			      [AUTO_INCREMENT] 
			      [COMMENT 'string']
			      [COLUMN_FORMAT {FIXED|DYNAMIC|DEFAULT}]
			      [STORAGE {DISK|MEMORY|DEFAULT}]
			    		  
			    		  [UNIQUE [KEY] | [PRIMARY] KEY]			    		  
			      [reference_definition]
			      */
		/*
		CREATE TABLE `Nombre de la tabla` (
				`Columna 1` INT(3) AUTO_INCREMENT AS (dfe) VIRTUAL COMMENT 'Comentario del campo' COLLATE 'big5_bin'
			)
			COMMENT='Comentario de la tabla'
			COLLATE='latin1_swedish_ci'
			ENGINE=InnoDB
			;
			*/
		String query = String.format("CREATE TABLE `%s`.`%s` (\n", databaseName, txtTableName.getText());
		String linea = "";
		
		//Columns definition
		for(int i = 0; i < tableStructure.getRowCount(); i++){
			linea = "\t`" + ((JLabel)tableStructure.getValueAt(i, CNewTableField.NAME_INDEX)).getText() + "` ";
			linea += tableStructure.getValueAt(i, CNewTableField.DATA_TYPE_INDEX) + " ";
			if((boolean)tableStructure.getValueAt(i, CNewTableField.NOT_NULL_INDEX))
				linea += "NOT NULL ";
			else
				linea += "NULL ";
			if((boolean)tableStructure.getValueAt(i, CNewTableField.AUTO_INCREMENT_INDEX))
				linea += "AUTO_INCREMENT ";

			if((boolean)tableStructure.getValueAt(i, CNewTableField.UNSIGNED_INDEX))
				linea += "UNSIGNED ";

			if((boolean)tableStructure.getValueAt(i, CNewTableField.ZERO_FILL_INDEX))
				linea += "ZEROFILL ";

			if((boolean)tableStructure.getValueAt(i, CNewTableField.BINARY_INDEX))
				linea += "BINARY ";			
			
			if(((String)tableStructure.getValueAt(i, CNewTableField.DEFAULT_VALUE_INDEX)).length() > 0)
				linea += "DEFAULT '" + (String)tableStructure.getValueAt(i, CNewTableField.DEFAULT_VALUE_INDEX) + "' ";
			
			if(((String)tableStructure.getValueAt(i, CNewTableField.COMMENTS_INDEX)).length() > 0)
				linea += "COMMENT '" + tableStructure.getValueAt(i, CNewTableField.COMMENTS_INDEX) + "' ";
			
			if(((String)tableStructure.getValueAt(i, CNewTableField.COLLATION_INDEX)).length() > 0)
				linea += "COLLATE '" + (String)tableStructure.getValueAt(i, CNewTableField.COLLATION_INDEX) + "' ";
			
			if(i < tableStructure.getRowCount() -1)
				linea += ", ";
			
			query += linea + "\n";
		}
		
		//TODO definicion de indices y tras cosas.
		
		
		query += ")\n";
		
		//Table options
		query += "COLLATE='" + cbCollation.getSelectedItem() + "'\n";
		query += "ENGINE=" + cbEngine.getSelectedItem() + "\n";

		if(txtComments.getText().length() > 0)
			query += "COMMENT='" + txtComments.getText() + "'\n";
/*		
		  | AUTO_INCREMENT [=] value
		  | AVG_ROW_LENGTH [=] value
		  | [DEFAULT] CHARACTER SET [=] charset_name
		  | CHECKSUM [=] {0 | 1}
		  | COMPRESSION [=] {'ZLIB'|'LZ4'|'NONE'}
		  | CONNECTION [=] 'connect_string'
		  | DATA DIRECTORY [=] 'absolute path to directory'
		  | DELAY_KEY_WRITE [=] {0 | 1}
		  | ENCRYPTION [=] {'Y' | 'N'}
		  | INDEX DIRECTORY [=] 'absolute path to directory'
		  | INSERT_METHOD [=] { NO | FIRST | LAST }
		  | KEY_BLOCK_SIZE [=] value
		  | MAX_ROWS [=] value
		  | MIN_ROWS [=] value
		  | PACK_KEYS [=] {0 | 1 | DEFAULT}
		  | PASSWORD [=] 'string'
		  | ROW_FORMAT [=] {DEFAULT|DYNAMIC|FIXED|COMPRESSED|REDUNDANT|COMPACT}
		  | STATS_AUTO_RECALC [=] {DEFAULT|0|1}
		  | STATS_PERSISTENT [=] {DEFAULT|0|1}
		  | STATS_SAMPLE_PAGES [=] value
		  | TABLESPACE tablespace_name
		  | UNION [=] (tbl_name[,tbl_name]...)
*/
		
		query += ";";
		System.out.println(query);
	}
	
	private void loadTableDefinition(){
		String sqlTxt = String.format(resource.getString("TableEditor.sqlShowCreate"), databaseName, tableName);
		CQuery query = new CQuery(connection);
		if(query.executeQuery(sqlTxt) > 0){
			try {
				query.getResultSet().next();
				String showTxt = query.getResultSet().getString("Create table");
				txtTableName.setText(tableName);
				
				processColumnsDefinition(showTxt.substring(showTxt.indexOf('(') + 1, showTxt.lastIndexOf(')')).trim());
				processOptions(showTxt.substring(showTxt.lastIndexOf(')') + 1, showTxt.length()).trim());
				tableEditorIndexColumnsModel.setFields(tableStructureModel.getFields());
				tableIndexColumns.setModel(tableEditorIndexColumnsModel);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else{
			System.out.println(query.getErrCode() + query.getErrMsg());
			
		}
	}
	
	private void processColumnsDefinition(String text){
		String[] lineas = text.split("\n");
		for(int i = 0; i < lineas.length; i++){
			String linea = lineas[i].trim();
			
			//Es una definicion de columna.
			if(linea.startsWith("`")){
				newRecord();
				int selectedRow = tableStructure.getSelectedRow();
				
				//Nombre de la columna
				int index = linea.indexOf('`')+1;
				int index2 = linea.indexOf('`', index);
				tableStructure.setValueAt(linea.substring(index, index2), tableStructure.getSelectedRow(), CNewTableField.NAME_INDEX);
				tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				
				//Tipo de dato
				index = index2 + 2;
				index2 = linea.indexOf(' ', index);
				if(index2 == -1)
					index2 = linea.length()-1;
				
				tableStructure.setValueAt(linea.substring(index, index2).toUpperCase(), tableStructure.getSelectedRow(), CNewTableField.DATA_TYPE_INDEX);
				tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
				
				linea = linea.substring(index2 + 1, linea.length()).trim();
				if(linea.endsWith(","))
					linea = linea.substring(0, linea.length()-1);
				
				String[] tokens = linea.split(" ");
				int x = 0;
				while(x < tokens.length){
					switch (tokens[x].toUpperCase()){
					case "NOT":
						x++;
						tableStructure.setValueAt(true, selectedRow, CNewTableField.NOT_NULL_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);
						break;
					case "NULL":
						tableStructure.setValueAt(false, selectedRow, CNewTableField.NOT_NULL_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);						
						break;
					case "DEFAULT":
						x++;
						tableStructure.setValueAt(tokens[x], selectedRow, CNewTableField.DEFAULT_VALUE_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);						
						break;
					case "AUTO_INCREMENT":
						tableStructure.setValueAt(true, selectedRow, CNewTableField.AUTO_INCREMENT_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);																		
						break;
					case "BINARY":
						tableStructure.setValueAt(true, selectedRow, CNewTableField.BINARY_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);																		
						break;
					case "UNSIGNED":
						tableStructure.setValueAt(true, selectedRow, CNewTableField.UNSIGNED_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);																		
						break;
					case "ZEROFILL":
						tableStructure.setValueAt(true, selectedRow, CNewTableField.ZERO_FILL_INDEX);
						tableStructure.setRowSelectionInterval(selectedRow, selectedRow);																		
						break;
					}
					x++;
				}
			}else{
				//TODO procesar datos de la definicion que no son columnas, como keys y otras
				//No es columna
				//Clave primaria
				CIndex cIndex = new CIndex();
				if(linea.toUpperCase().startsWith(CIndex.PRIMARY_TYPE_NAME.toUpperCase())){
					cIndex.setName(CIndex.PRIMARY_TYPE_NAME.toUpperCase());
					cIndex.setType(CIndex.PRIMARY_TYPE_NAME);
				}
				
				String txtColumns = linea.substring(linea.indexOf("(")+1, linea.lastIndexOf(")"));
				String columnTokens[] = txtColumns.split(",");
				int indice = 0;
				while(indice < columnTokens.length){
					//Separar por espacio para ver si es desc o asc
					Object[] indexTemp = new Object[CIndex.FIELD_ATTRIBUTES];
					indexTemp[0] = columnTokens[indice].substring(1, columnTokens[indice].length()-1);
					cIndex.addField(indexTemp);
					indice++;
				}
				tableIndexesModel.addIndex(cIndex);
				System.out.println(linea);
			}
		}
	}
	
	private void processOptions(String text){
		String[] tokens = text.split(" ");
		int i = 0;
		while(i < tokens.length){
			String[] x = tokens[i].split("=");
			switch(x[0]){
			case "ENGINE":
				cbEngine.setSelectedItem(x[1]);
				break;
			case "COLLATE":
				cbCollation.setSelectedItem(x[1]);
				break;
			case "COMMENT":
				if(!x[1].trim().endsWith("'")){
					txtComments.setText(x[1].substring(1, x[1].length()));

					i++;
					while(i < tokens.length && !tokens[i].endsWith("'")){
						txtComments.append(" " + tokens[i]);
						i++;
					}
					if(tokens[i].endsWith("'"))
						txtComments.append(tokens[i].substring(0, tokens[i].length()-1));
				}else{
					txtComments.setText(x[1].substring(1, x[1].length()-1));
				}
				break;
				//TODO faltan otras opciones de una tabla.
			}
			i++;
		}
	}
	
	private void addNewIndex(){
		tableIndexesModel.addIndex();
		tableIndexes.setRowSelectionInterval(tableIndexes.getRowCount()-1, tableIndexes.getRowCount()-1);

		tableEditorIndexColumnsModel.setFields(tableStructureModel.getFields());
		tableIndexColumns.setModel(tableEditorIndexColumnsModel);
		tableIndexColumns.repaint();
	}
	
	private void selectIndex(){
		//int x = tableIndexes.getSelectedRow();
		if(tableIndexes.getSelectedRow() > -1)
			tableEditorIndexColumnsModel.setDatos(tableIndexesModel.getFields(tableIndexes.getSelectedRow()));
		tableIndexColumns.repaint();
	}

	class DataTypeComboModel extends AbstractListModel<String> implements ComboBoxModel<String>{
		private static final long serialVersionUID = 1L;
		private String seleccion = null;
		
		@Override
		public int getSize() {
			return dataType.size();
		}

		@Override
		public String getElementAt(int index) {
			int i=0;
			if(index<dataType.size()){
				for(String elemento: dataType.keySet()){
					if(i == index)
						return elemento;
					i++;
				}
			}

			return null;
		}

		@Override
		public void setSelectedItem(Object anItem) {
			seleccion = (String)anItem;
			System.out.println("Seleccion: " + seleccion);
			fireContentsChanged(this, -1, -1);
		}

		@Override
		public Object getSelectedItem() {
			return seleccion;
		}
	}

}
