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
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.Vector;

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
import javax.swing.table.TableColumn;

import ar.com.dcbarrientos.jmysqlgui.Funciones;
import ar.com.dcbarrientos.jmysqlgui.database.CDatabase;
import ar.com.dcbarrientos.jmysqlgui.database.CTabla;
import ar.com.dcbarrientos.jmysqlgui.database.CTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class DatabaseTab extends JPanel{
	private static final long serialVersionUID = 1L;
	
	private static final int COLUMN_TABLE_NAME = 0;

	//Iconos
	private ImageIcon viewDataIcon;
	private ImageIcon tablePropertiesIcon;
	private ImageIcon insertRecordIcon;
	private ImageIcon emptyTableIcon;
	private ImageIcon dropTableIcon;
	private ImageIcon copyTableIcon;
	
	//Interfaz gráfica.
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
	private ResourceBundle resource;
	//private String databaseName;
	private int cantidadFilas = 0;
	private int filaSeleccionada = 0;
	//private int baseActual = 0;
	private Object[] longValues;
	private CDatabase database;


	/**
	 * Constructor de panel para la solapa Database que contiene todas las tablas de 
	 * la base de datos.
	 * @param admin Padre de la tab que va a contener este panel.
	 * @param connection conexión abierta.
	 */
	public DatabaseTab(MdiAdmin admin, Connection connection, ResourceBundle resource) 
	{
		super();
		this.admin = admin;
		this.connection = connection;
		this.resource = resource;
		
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
					filaSeleccionada = -1;
				else //Selecciono fila.
					filaSeleccionada = lsm.getMinSelectionIndex();
        
				//System.out.println("fila seleccionada " + filaSeleccionada);
        		actualizarToolBar();
			}
		});
	}

	/**
	 * Selecciona una base de datos. Carga todas las tablas en una JTable.
	 * @param database Objeto CDatabase seleccionada.
	 */
	public void setDatabase(CDatabase database){
		this.database = database;
		CTableModel cTableModel= new CTableModel(getDatos(), getHeaders());

		jLabel1.setText("  Base de datos " + database.getName() + ": " + cantidadFilas + " tabla(s)");
		jTable1.setModel(cTableModel);

	}
	
 	private boolean mShown = false;
  	
	public void addNotify() 
	{
		super.addNotify();
		
		if (mShown)
			return;

		mShown = true;
	}

	/**
	 * Genera un array con todas las tablas y sus respectivos datos.
	 * @return array de tablas.
	 */
	private Vector<Object[]> getDatos(){
		HashMap<String, CTabla> tablas = database.getTablas();
		SortedSet<String> keys = new TreeSet<String>(tablas.keySet());
		
		Vector<Object[]> datos = new Vector<Object[]>();
		Object[] registro;
		cantidadFilas = tablas.size();
		
		for(String key: keys){
			CTabla elemento = tablas.get(key);
			registro = new Object[getHeaders().length];
			registro[0] = elemento.getName();
			registro[1] = elemento.getTipo();
			registro[2] = elemento.getRow_format();
			registro[3] = elemento.getRows();
			registro[4] = elemento.getAvg_row_length();
			registro[5] = Funciones.getExtendedSize(elemento.getData_length());
			registro[6] = elemento.getMax_data_length();
			registro[7] = elemento.getIndex_length();
			registro[8] = elemento.getData_free();
			registro[9] = elemento.getAuto_increment();
			registro[10] = elemento.getCreate_time();
			registro[11] = elemento.getUpdate_time();
			registro[12] = elemento.getCheck_time();
			registro[13] = elemento.getCreate_options();
			registro[14] = elemento.getComment();
			datos.addElement(registro);
		}
		
		return datos;
	}
	
	/**
	 * @return Devuelve un vector con los nombres de los campos devueltos con getDatos();
	 */
	private String[] getHeaders(){
		String[] headers = {"Name", "Type", "Row_format", "Rows", "Avg_row_length", 
				"Data_length", "Max_data_length", "Index_length", "Data_free", "Auto_increment", 
				"Create_time", "Update_time", "Check_time", "Create_options", "Comment"};
		
		return headers;
	}

	
	/**
	 * Establece la visibilidad de este panel
	 * @param visible Verdadero si el panel es visible.
	 */
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
	
	/**
	 * Crea la barra de herramientas de este panel. 
	 */
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
	
	
	/**
	 * Habilita o deshabilita ciertos botones de la barra de herramientas dependiendo
	 * de si hay una tabla seleccionada o no.
	 */
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
	
	/**
	 * Hace visible la solapa que contiene los datos de la tabla seleccionada.
	 */
	public void mostrarDatos()
	{		
		String tableName = getSelectedTableName();
		admin.setSelectedTable(database.getName(), tableName, MdiAdmin.TABLE_DATA_TAB_INDEX);
	}
	
	/**
	 * Hace visible la solapa que contiene la estructura de la tabla seleccionada.
	 */
	public void mostrarEstructura()
	{
		String tableName = getSelectedTableName();
		admin.setSelectedTable(database.getName(), tableName, MdiAdmin.TABLE_INFO_TAB_INDEX);
	}
	
	private String getSelectedTableName(){
		return (String) jTable1.getValueAt(filaSeleccionada, COLUMN_TABLE_NAME);
	}
	
	/**
	 * Carga la ventana hace la copia de la tabla seleccionada.
	 */
	public void copiarTabla()
	{
		//String nombreTabla = (String) jTable1.getValueAt(filaSeleccionada, 0);
		CopyTable copyTable = new CopyTable(admin.getPrincipal(), connection, resource, database.getName(), getSelectedTableName());
		if(copyTable.showDialog())
			admin.refreshAll();
	}
	
	/**
	 * Elimina todos los datos de la tabla seleccionada.
	 */
	public void vaciarTabla()
	{
		//TODO todavia no hice este proc.
		//principal.vaciarTabla();
		if(admin.emptyTable(database.getName(), getSelectedTableName()))
			admin.refreshAll();
	}
	
	/**
	 * Elimina la tabla seleccionada de la base de datos.
	 */
	public void borrarTabla()
	{
		if(admin.dropTable(database.getName(), getSelectedTableName())){
			admin.refreshAll();
		}
	}
	
	/**
	 * @return Devuelve el nobre de la/s tabla/s seleccionada/s.
	 */
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
	
	/**
	 * @return Devuelve la cantidad de tablas seleccionadas.
	 */
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
	/**
	 * Estable el ancho que debe tener cada columna dependiendo del contenido.
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
	
	/**
	 * @return Devuelve un array con los índices de las tablas seleccionadas.
	 */
	public int[] getSelectedTables(){
		return jTable1.getSelectedRows();
	}
	
	/**
	 * @param index Indice de la tabla de la cual quiero obtener el nombre.
	 * @return Nombre de la tabla que está en la posición index.
	 */
	public String getDatabaseAt(int index){
		return (String)jTable1.getValueAt(index, 0);
	}

}
