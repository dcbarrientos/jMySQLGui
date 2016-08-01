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
 * DatabaseTab.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 1 de ago. de 2016, 1:39:50 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import ar.com.dcbarrientos.jmysqlgui.database.CDatabase;
import ar.com.dcbarrientos.jmysqlgui.database.CTabla;
import ar.com.dcbarrientos.jmysqlgui.database.CTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class DatabaseTab extends JPanel{
	private ImageIcon viewDataIcon;
	private ImageIcon tablePropertiesIcon;
	private ImageIcon insertRecordIcon;
	private ImageIcon emptyTableIcon;
	private ImageIcon dropTableIcon;
	private ImageIcon copyTableIcon;
	private static final long serialVersionUID = 1L;
	private JTable jTable1 = new JTable();
	private JLabel jLabel1 = new JLabel();
	private JToolBar toolbar = new JToolBar();
	private JButton jbViewData = new JButton();
	private JButton jbShowTableProperties = new JButton();
	private JButton jbInsertRecord = new JButton();
	private JButton jbEmptyTable = new JButton();
	private JButton jbDropTable = new JButton();
	private JButton jbCopyTable = new JButton();	
	private JScrollPane scrollpane;
	private MdiAdmin admin;
	
	private Connection connection;
	//private String databaseName;
	private int cantidadFilas = 0;
	private int filaSeleccionada = 0;
	//private int baseActual = 0;
	private Object[] longValues;
	private CDatabase database;


	public DatabaseTab(MdiAdmin admin, Connection connection) 
	{
		super();
		this.admin = admin;
		this.connection = connection;
		
		jTable1 = new JTable();
		
		scrollpane = new JScrollPane(jTable1);

		// Create the table
		jTable1.setAutoResizeMode(0);				
		jTable1.setVisible(true);
		jTable1.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		ListSelectionModel filaSM = jTable1.getSelectionModel();
		
		jLabel1.setOpaque(true);
		jLabel1.setForeground(Color.white);
		jLabel1.setBackground(Color.black);
		
		setLayout(new BorderLayout());
		setLocation(new Point(0, 0));
		
		crearToolBar();
		toolbar.setOrientation(JToolBar.VERTICAL);
		toolbar.setFloatable(false);
		actualizarToolBar();
		
		add(jLabel1, BorderLayout.NORTH);
		add(scrollpane, BorderLayout.CENTER);		
		add(toolbar, BorderLayout.WEST);
		
		filaSM.addListSelectionListener(new ListSelectionListener()
		{
			public void valueChanged(ListSelectionEvent e)
			{
				//Ignora las operaciones extra.
        if (e.getValueIsAdjusting()) return;
        
        ListSelectionModel lsm = (ListSelectionModel)e.getSource();
        if (lsm.isSelectionEmpty()) //No selecciono fila.
        {	filaSeleccionada = -1;
        } 
        else //Selecciono fila.
        {	filaSeleccionada = lsm.getMinSelectionIndex();
        }
        System.out.println("fila seleccionada " + filaSeleccionada);
        actualizarToolBar();
			}
		});
	}

	public void setDatabase(CDatabase database){
		this.database = database;
		CTableModel cTableModel= new CTableModel(getDatos(), getHeaders());
		jLabel1.setText("  Base de datos " + database.getName() + ": " + cantidadFilas + " tabla(s)");
		jTable1.setModel(cTableModel.getTableModel());

	}
	
 	private boolean mShown = false;
  	
	public void addNotify() 
	{
		super.addNotify();
		
		if (mShown)
			return;

		mShown = true;
	}

	private Object[][] getDatos(){	
		HashMap<String, CTabla> tablas = database.getTablas();
		SortedSet<String> keys = new TreeSet<String>(tablas.keySet());
		int columnCount = getHeaders().length;
		cantidadFilas = database.getTableCount();
		Object[][] datos = new Object[cantidadFilas][columnCount];
		int fila = 0;
		for(String key: keys){
			CTabla elemento = tablas.get(key);
			datos[fila][0] = elemento.getName();
			datos[fila][1] = elemento.getTipo();
			datos[fila][2] = elemento.getRow_format();
			datos[fila][3] = elemento.getRows();
			datos[fila][4] = elemento.getAvg_row_length();
			datos[fila][5] = elemento.getData_length();
			datos[fila][6] = elemento.getMax_data_length();
			datos[fila][7] = elemento.getIndex_length();
			datos[fila][8] = elemento.getData_free();
			datos[fila][9] = elemento.getAuto_increment();
			datos[fila][10] = elemento.getCreate_time();
			datos[fila][11] = elemento.getUpdate_time();
			datos[fila][12] = elemento.getCheck_time();
			datos[fila][13] = elemento.getCreate_options();
			datos[fila][14] = elemento.getComment();
			fila++;
		}
		
		return datos;
	}
	
	private String[] getHeaders(){
		String[] headers = {"Name", "Type", "Row_format", "Rows", "Avg_row_length", 
				"Data_length", "Max_data_length", "Index_length", "Data_free", "Auto_increment", 
				"Create_time", "Update_time", "Check_time", "Create_options", "Comment"};
		
		return headers;
	}

	public void setDVisible(boolean visible)
	{	setVisible(false);
		repaint();	
	}	
	/*
	public void updateDatabase(int idnombre)
	{	
		jTable1.setModel(new CTableModel(getDatos(), getHeaders()).getTableModel());
		setColumnWidth();
		//TODO Hacer la conexion con resource.
		jLabel1.setText("  Base de datos " + database.getName() + ": " + cantidadFilas + " tabla(s)");
		jTable1.repaint();
	}*/
	
	public void crearToolBar()
	{
		toolbar.add(jbViewData);
		toolbar.add(jbShowTableProperties);
		toolbar.add(jbInsertRecord);
		toolbar.add(jbEmptyTable);
		toolbar.add(jbDropTable);
		toolbar.add(jbCopyTable);

		viewDataIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/ViewData.gif"));
		jbViewData.setIcon(viewDataIcon);
		jbViewData.setToolTipText("View Data");
		//TODO manejar el inset0
		//jbViewData.setMargin(Principal.insets0);
		jbViewData.getAccessibleContext().setAccessibleName("View Data");
		jbViewData.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mostrarDatos();
			}
		});

		tablePropertiesIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/TableProperties.gif"));
		jbShowTableProperties.setIcon(tablePropertiesIcon);
		jbShowTableProperties.setToolTipText("Show Table Properties");		
		//TODO manejar el inset0
		//jbShowTableProperties.setMargin(Principal.insets0);
		jbShowTableProperties.getAccessibleContext().setAccessibleName("Show Table Properties");
		jbShowTableProperties.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				mostrarEstructura();
			}
		});

		insertRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/InsertRecord.gif"));
		jbInsertRecord.setIcon(insertRecordIcon);
		jbInsertRecord.setToolTipText("Insert record...");		
		//TODO manejar el inset0
		//jbInsertRecord.setMargin(Principal.insets0);
		jbInsertRecord.getAccessibleContext().setAccessibleName("Insert Record...");

		emptyTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Emptytable.gif"));
		jbEmptyTable.setIcon(emptyTableIcon);
		jbEmptyTable.setToolTipText("Empty table...");		
		//TODO manejar el inset0
		//jbEmptyTable.setMargin(Principal.insets0);
		jbEmptyTable.getAccessibleContext().setAccessibleName("Empty table...");
		jbEmptyTable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				vaciarTabla();
			}
		});

		dropTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DropTable.gif"));
		jbDropTable.setIcon(dropTableIcon);
		jbDropTable.setToolTipText("Drop table...");		
		//TODO manejar el inset0
		//jbDropTable.setMargin(Principal.insets0);
		jbDropTable.getAccessibleContext().setAccessibleName("Drop Table...");
		jbDropTable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				borrarTabla();
			}
		});

		copyTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Copytable.gif"));
		jbCopyTable.setIcon(copyTableIcon);
		jbCopyTable.setToolTipText("Copy table...");		
		//TODO manejar el inset0
		//jbCopyTable.setMargin(Principal.insets0);
		jbCopyTable.getAccessibleContext().setAccessibleName("Copy table...");
		jbCopyTable.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				copiarTabla();
			}
		});
	}
	
	public void actualizarToolBar()
	{
		int i = jTable1.getSelectedRow();
		boolean valor=false;
		
		if(i!=-1)
			valor = true;

		jbViewData.setEnabled(valor);				
		jbShowTableProperties.setEnabled(valor);
		jbInsertRecord.setEnabled(valor);
		jbEmptyTable.setEnabled(valor);
		jbDropTable.setEnabled(valor);
		jbCopyTable.setEnabled(valor);
	}
	
	public void mostrarDatos()
	{		
		String valor = (String) jTable1.getValueAt(filaSeleccionada, 0);
		//TODO Todavia no hice este proc.
		//principal.mostrarDatos(valor);
	}
	
	public void mostrarEstructura()
	{
		String valor = (String) jTable1.getValueAt(filaSeleccionada, 0);
		//TODO todavia no hice este proc
		//principal.mostrarEstructura(valor);
	}
	
	public void copiarTabla()
	{
		String nombreTabla = (String) jTable1.getValueAt(filaSeleccionada, 0);
		
		//TODO todavia no hice este objeto		
		//String nombreDatabase = principal.getSelectedDatabase();
		//CopyTable cpTable = new CopyTable(conexion, principal.principal, nombreDatabase, nombreTabla); //dcb dice

		
		//CopyTable cpTable = new CopyTable(conexion, principal, nombreDatabase, nombreTabla);
		//TODO descomentar
		//cpTable.setVisible(true);
	}
	
	public void vaciarTabla()
	{
		//TODO todavia no hice este proc.
		//principal.vaciarTabla();
	}
	
	public void borrarTabla()
	{
		//TODO todavia no hice este proc.
		//principal.borrarTabla();
	}
	
	public String getTablaSelected()
	{
		String valor = "";
		if (getNumeroSeleccionadas()==1)
			valor = (String) jTable1.getValueAt(filaSeleccionada, 0);
		else if (getNumeroSeleccionadas()>1)
		{	for(int i=0;i<getNumeroSeleccionadas(); i++)
			{	valor += (String) jTable1.getValueAt(jTable1.getSelectedRows()[i], 0);
				if(i<getNumeroSeleccionadas()-1)
					valor += ", ";
			}
		}
		return valor;
	}
	
	public int getNumeroSeleccionadas()
	{
		return jTable1.getSelectedRowCount();
	}
/*
	public void refresh()
	{
		updateDatabase(baseActual);		
	}	
*/
	public void setColumnWidth()
	{
		TableColumn column = null;
		Component comp = null;
		int headerWidth = 0;
		int cellWidth = 0;
		for (int i = 0; i < 15; i++) 
		{	column = jTable1.getColumnModel().getColumn(i);

			try 
			{	comp = jTable1.getDefaultRenderer(jTable1.getModel().getColumnClass(i)).getTableCellRendererComponent(jTable1, column.getHeaderValue(),false, false, -1, i);
				headerWidth = comp.getPreferredSize().width + 20;
			} 
			catch (NullPointerException e) 
			{
			}

			comp = jTable1.getDefaultRenderer(jTable1.getModel().getColumnClass(i)).getTableCellRendererComponent(jTable1, longValues[i],false, false, 0, i);
			cellWidth = comp.getPreferredSize().width + 20;

			column.setMinWidth(Math.max(headerWidth, cellWidth));
		}	
	}
	
	public int[] getSelectedTables(){
		return jTable1.getSelectedRows();
	}
	
	public String getDatabaseAt(int index){
		return (String)jTable1.getValueAt(index, 0);
	}

}
