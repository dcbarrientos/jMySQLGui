package ar.com.dcbarrientos.jmysqlgui.ui.tableinfo;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import ar.com.dcbarrientos.jmysqlgui.Funciones;
import ar.com.dcbarrientos.jmysqlgui.database.CTabla;

public class TableInfoPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	CTabla tabla;
	
	
	JLabel lblTableIcon;
	JLabel lblTableName;
	JLabel lblTableDetails;
	JScrollPane scrollPane;	
	JPanel panel_1;
	JLabel lblEngine;
	JLabel lblEngineValue;
	JLabel lblRowFormat;
	JLabel lblRowFormatValue;
	JLabel lblColumnCount;	
	JLabel lblColumnCountValue;	
	JLabel lblTableRows;	
	JLabel lblTableRowsValue;	
	JLabel lblAVGRowLength;	
	JLabel lblAVGRowLengthValue;	
	JLabel lblDataLength;	
	JLabel lblDataLengthValue;	
	JLabel lblIndexLength;	
	JLabel lblIndexLengthValue;	
	JLabel lblMaxDataLength;	
	JLabel lblMaxDataValue;	
	JLabel lblDataFree;	
	JLabel lblDataFreeValue;	
	JLabel lblTableSize;	
	JLabel lblTableSizeValue;	
	JLabel lblFileFormat;	
	JLabel lblFileFormatValue;	
	JLabel lblDataPath;	
	JLabel lblDataPathValue;	
	JLabel lblUpdateTime;
	JLabel lblUpdateTimeValue;	
	JLabel lblCreateTime;	
	JLabel lblCreateTimeValue;	
	JLabel lblAutoincrement;	
	JLabel lblAutoincrementValue;	
	JLabel lblTableCollation;	
	JLabel lblTableCollationValue;	
	JLabel lblCreateOptions;	
	JLabel lblCreateOptionValue;	
	JLabel lblComment;	
	JLabel lblCommentValue;
	GroupLayout gl_panel_1;
	
	ResourceBundle resource;
	
	/**
	 * Create the panel.
	 */
	public TableInfoPanel(ResourceBundle resource){
		this.resource = resource;

		initComponents();
	}
	
	private void initComponents(){		
		lblTableIcon = new JLabel("icono Tabla");
		
		lblTableName = new JLabel("<html><body><u>Mapuche.clientes<u></body></html>");
		lblTableName.setBorder(null);
		lblTableName.setForeground(Color.DARK_GRAY);
		lblTableName.setFont(new Font("Tahoma", Font.PLAIN, 18));
		
		lblTableDetails = new JLabel("Table details:");
		lblTableDetails.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JPanel panel = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTableIcon)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblTableName))
						.addComponent(lblTableDetails))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTableName)
						.addComponent(lblTableIcon))
					.addGap(33)
					.addComponent(lblTableDetails)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		panel.add(scrollPane, BorderLayout.CENTER);
		
		panel_1 = new JPanel();
		panel_1.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setViewportView(panel_1);
		
		lblEngine = new JLabel(resource.getString("TableInfoPanel.lblEngine"));
		lblEngine.setHorizontalAlignment(SwingConstants.LEFT);		
		lblEngineValue = new JLabel("New label");
		
		lblRowFormat = new JLabel(resource.getString("TableInfoPanel.lblRowFormat"));
		lblRowFormat.setHorizontalAlignment(SwingConstants.LEFT);
		lblRowFormatValue = new JLabel("New label");
		
		lblColumnCount = new JLabel(resource.getString("TableInfoPanel.lblColumnCount"));
		lblColumnCountValue = new JLabel("New label");
		
		lblTableRows = new JLabel(resource.getString("TableInfoPanel.lblTableRows"));
		lblTableRowsValue = new JLabel("New label");
		
		lblAVGRowLength = new JLabel(resource.getString("TableInfoPanel.lblAVGRowLength"));
		lblAVGRowLengthValue = new JLabel("New label");
		
		lblDataLength = new JLabel(resource.getString("TableInfoPanel.lblDataLength"));
		lblDataLengthValue = new JLabel("New label");
		
		lblIndexLength = new JLabel(resource.getString("TableInfoPanel.lblIndexLength"));
		lblIndexLengthValue = new JLabel("New label");
		
		lblMaxDataLength = new JLabel(resource.getString("TableInfoPanel.lblMaxDataLength"));
		lblMaxDataValue = new JLabel("New label");
		
		lblDataFree = new JLabel(resource.getString("TableInfoPanel.lblDataFree"));
		lblDataFreeValue = new JLabel("New label");
		
		lblTableSize = new JLabel(resource.getString("TableInfoPanel.lblTableSize"));		
		lblTableSizeValue = new JLabel("New label");
		
		lblFileFormat = new JLabel(resource.getString("TableInfoPanel.lblFileFormat"));		
		lblFileFormatValue = new JLabel("New label");
		
		lblDataPath = new JLabel(resource.getString("TableInfoPanel.lblDataPath"));
		lblDataPathValue = new JLabel("New label");
		
		lblUpdateTime = new JLabel(resource.getString("TableInfoPanel.lblUpdateTime"));
		lblUpdateTimeValue = new JLabel("New label");
		
		lblCreateTime = new JLabel(resource.getString("TableInfoPanel.lblCreateTime"));
		lblCreateTimeValue = new JLabel("New label");
		
		lblAutoincrement = new JLabel(resource.getString("TableInfoPanel.lblAutoincrement"));
		lblAutoincrementValue = new JLabel("New label");
		
		lblTableCollation = new JLabel(resource.getString("TableInfoPanel.lblTableCollation"));
		lblTableCollationValue = new JLabel("New label");
		
		lblCreateOptions = new JLabel(resource.getString("TableInfoPanel.lblCreateOptions"));
		lblCreateOptionValue = new JLabel("New label");
		
		lblComment = new JLabel(resource.getString("TableInfoPanel.lblComment"));
		lblCommentValue = new JLabel("New label");
		
		gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblEngine)
								.addComponent(lblRowFormat)
								.addComponent(lblColumnCount)
								.addComponent(lblTableRows)
								.addComponent(lblAVGRowLength)
								.addComponent(lblDataLength))
							.addGap(45)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDataLengthValue)
								.addComponent(lblAVGRowLengthValue)
								.addComponent(lblTableRowsValue)
								.addComponent(lblColumnCountValue)
								.addComponent(lblRowFormatValue)
								.addComponent(lblEngineValue)
								.addComponent(lblIndexLengthValue)))
						.addComponent(lblIndexLength)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMaxDataLength)
								.addComponent(lblDataFree))
							.addGap(37)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblDataFreeValue)
								.addComponent(lblMaxDataValue)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTableSize)
								.addComponent(lblFileFormat)
								.addComponent(lblDataPath)
								.addComponent(lblUpdateTime)
								.addComponent(lblCreateTime)
								.addComponent(lblAutoincrement)
								.addComponent(lblTableCollation)
								.addComponent(lblCreateOptions)
								.addComponent(lblComment))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblCommentValue)
								.addComponent(lblCreateOptionValue)
								.addComponent(lblTableCollationValue)
								.addComponent(lblAutoincrementValue)
								.addComponent(lblCreateTimeValue)
								.addComponent(lblUpdateTimeValue)
								.addComponent(lblDataPathValue)
								.addComponent(lblFileFormatValue)
								.addComponent(lblTableSizeValue))))
					.addContainerGap(251, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblEngine)
						.addComponent(lblEngineValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblRowFormat)
						.addComponent(lblRowFormatValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblColumnCount)
						.addComponent(lblColumnCountValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTableRows)
						.addComponent(lblTableRowsValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAVGRowLength)
						.addComponent(lblAVGRowLengthValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataLengthValue)
						.addComponent(lblDataLength, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblIndexLength)
						.addComponent(lblIndexLengthValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaxDataLength)
						.addComponent(lblMaxDataValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataFree)
						.addComponent(lblDataFreeValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTableSize)
						.addComponent(lblTableSizeValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFileFormat)
						.addComponent(lblFileFormatValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDataPath)
						.addComponent(lblDataPathValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblUpdateTime)
						.addComponent(lblUpdateTimeValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCreateTime)
						.addComponent(lblCreateTimeValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAutoincrement)
						.addComponent(lblAutoincrementValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTableCollation)
						.addComponent(lblTableCollationValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblCreateOptions)
						.addComponent(lblCreateOptionValue))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblComment)
						.addComponent(lblCommentValue))
					.addGap(26))
		);
		panel_1.setLayout(gl_panel_1);
		setLayout(groupLayout);

	}
	
	public void setTabla(CTabla tabla){
		this.tabla = tabla;
		actualizarDatos();
	}
	
	private void actualizarDatos(){
		lblTableName.setText("<html><body><u>" + tabla.getDatabaseName() + "." + tabla.getName()  + "</u></body></html>");
		lblEngineValue.setText("<html><body><b>" + tabla.getTipo() + "</b></body></html>");
		lblRowFormatValue.setText("<html><body><b>" + tabla.getRow_format() + "</b></body></html>");
		lblTableRowsValue.setText("<html><body><b>" + tabla.getRows() + "</b></body></html>");
		lblAVGRowLengthValue.setText("<html><body><b>" + tabla.getAvg_row_length() + "</b></body></html>");
		lblDataLengthValue.setText("<html><body><b>" + Funciones.getExtendedSize(tabla.getData_length()) + "</b></body></html>");
		lblIndexLengthValue.setText("<html><body><b>" + Funciones.getExtendedSize(tabla.getIndex_length()) + "</b></body></html>");
		lblMaxDataValue.setText("<html><body><b>" + Funciones.getExtendedSize(tabla.getMax_data_length()) + "</b></body></html>");
		lblDataFreeValue.setText("<html><body><b>" + Funciones.getExtendedSize(tabla.getData_free()) + "</b></body></html>");
		lblUpdateTimeValue.setText("<html><body><b>" + tabla.getUpdate_time() + "</b></body></html>");
		lblCreateTimeValue.setText("<html><body><b>" + tabla.getCreate_time() + "</b></body></html>");
		lblAutoincrementValue.setText("<html><body><b>" + tabla.getAuto_increment() + "</b></body></html>");
		lblCreateOptionValue.setText("<html><body><b>" + tabla.getCreate_options() + "</b></body></html>");
		lblCommentValue.setText("<html><body><b>" + tabla.getComment() + "</b></body></html>");
		//lblColumnCountValue.setText("<html><body><b>" + tabla.get + "</b></body></html>");
		//lblTableSizeValue.setText("<html><body><b>" + tabla.get + "</b></body></html>");
		//lblFileFormatValue.setText("<html><body><b>" + tabla.get + "</b></body></html>");
		//lblDataPathValue.setText("<html><body><b>" + tabla.get + "</b></body></html>");
		//lblTableCollationValue.setText("<html><body><b>" + tabla.get + "</b></body></html>");				
	}
	
}
