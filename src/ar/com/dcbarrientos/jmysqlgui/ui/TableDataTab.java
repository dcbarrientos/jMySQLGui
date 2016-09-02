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
 * Datos.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 9 de ago. de 2016, 11:26:22 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Iterator;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;

import ar.com.dcbarrientos.jmysqlgui.database.CQuery;
import ar.com.dcbarrientos.jmysqlgui.database.CTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class TableDataTab extends JPanel{
	private static final long serialVersionUID = 1L;

	private MdiAdmin admin;
	private Connection connection;
	private ResourceBundle resource;
	private CQuery query;
	private CTableModel model;
	
	private JLabel etiqueta;
	private JTable jtDatos;
	private JScrollPane scroll;
	
	private JToolBar toolbar;
	private JButton jbPrimero;
	private JButton jbAnterior;
	private JButton jbSiguiente;
	private JButton jbUltimo;
	private JButton jbInsertar;
	private JButton jbBorrar;
	private JButton jbEditar;
	private JButton jbAceptar;
	private JButton jbCancelar;
	private JButton jbRefresh;
	private JCheckBox jcLimite;
	private JTextField txtLimiteInferior;
	private JTextField txtLimiteSuperior;
	private JButton jbOk;
	
	private ImageIcon primerRecordIcon; 
	private ImageIcon anteriorRecordIcon; 
	private ImageIcon siguienteRecordIcon;
	private ImageIcon ultimoRecordIcon; 
	private ImageIcon insertDataRecordIcon; 
	private ImageIcon deleteRecordIcon; 
	private ImageIcon editRecordIcon; 
	private ImageIcon aceptarRecordIcon; 
	private ImageIcon cancelarRecordIcon; 
	private ImageIcon refreshDataIcon;
	
	private String databaseName;
	private String tableName;
	
	public TableDataTab(MdiAdmin admin, Connection connection, ResourceBundle resource){
		super();
		this.admin = admin;
		this.connection = connection;
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		etiqueta = new JLabel("Etiqueta Base de datos.");
		
		jtDatos = new JTable();
		
		jtDatos.setCellSelectionEnabled(true);
		jtDatos.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		KeyStroke tabStroke = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
		jtDatos.getActionMap().put("tab", tabAction());
		jtDatos.getInputMap(JComponent.WHEN_FOCUSED).put(tabStroke, "tab");
		
		
		scroll = new JScrollPane(jtDatos);
		
		setLayout(new BorderLayout(0, 0));
		add(etiqueta, BorderLayout.NORTH);
		add(scroll, BorderLayout.CENTER);
		add(getDataToolBar(), BorderLayout.SOUTH);
		setLimitieEnabled(false);
		setEditEnabled(false);
	}
	
	private JToolBar getDataToolBar(){
		toolbar = new JToolBar();
		
		primerRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Primero.gif"));
		jbPrimero = new JButton();
		jbPrimero.setToolTipText(resource.getString("TableDataTab.jbPriemro.tooltip"));
		jbPrimero.setIcon(primerRecordIcon);
		jbPrimero.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbPrimero.accessiblename"));
		jbPrimero.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbPrimeroMouseClicked(e);
			}
		});
		
		anteriorRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Anterior.gif"));
		jbAnterior = new JButton();
		jbAnterior.setToolTipText(resource.getString("TableDataTab.jbAnterior.tooltip"));
		jbAnterior.setIcon(anteriorRecordIcon);
		jbAnterior.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbAnterior.accessiblename"));
		jbAnterior.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbAnteriorMouseClicked(e);
			}
		});
		
		siguienteRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Siguiente.gif"));
		jbSiguiente = new JButton();
		jbSiguiente.setToolTipText(resource.getString("TableDataTab.jbSiguiente.tooltip"));
		jbSiguiente.setIcon(siguienteRecordIcon);
		jbSiguiente.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbSiguiente.accessiblename"));
		jbSiguiente.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbSiguienteMouseClicked(e);
			}
		});
		
		ultimoRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Ultimo.gif"));
		jbUltimo = new JButton();
		jbUltimo.setToolTipText(resource.getString("TableDataTab.jbUltimo.tooltip"));
		jbUltimo.setIcon(ultimoRecordIcon);
		jbUltimo.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbUltimo.accessiblename"));
		jbUltimo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbUltimoMouseClicked(e);
			}
		});
		
		insertDataRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/InsertarRegistro.gif"));
		jbInsertar = new JButton();
		jbInsertar.setToolTipText(resource.getString("TableDataTab.jbInsertar.tooltip"));
		jbInsertar.setIcon(insertDataRecordIcon);
		jbInsertar.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbInsertar.accessiblename"));
		jbInsertar.setVisible(false);
		
		deleteRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DeleteRecord.gif"));
		jbBorrar = new JButton();
		jbBorrar.setToolTipText(resource.getString("TableDataTab.jbBorrar.tooltip"));
		jbBorrar.setIcon(deleteRecordIcon);
		jbBorrar.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbBorrar.accessiblename"));
		jbBorrar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				deleteRow();
			}
		});
		
		editRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/EditRecord.gif"));
		jbEditar = new JButton();
		jbEditar.setToolTipText(resource.getString("TableDataTab.jbEditar.tooltip"));
		jbEditar.setIcon(editRecordIcon);
		jbEditar.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbEditar.accessiblename"));
		jbEditar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbEditarMouseClicked(e);
			}
		});
		jbEditar.setVisible(false);
		
		aceptarRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/AceptarEdit.gif"));
		jbAceptar = new JButton();
		jbAceptar.setToolTipText(resource.getString("TableDataTab.jbAceptar.tooltip"));
		jbAceptar.setIcon(aceptarRecordIcon);
		jbAceptar.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbAceptar.accessiblename"));
		jbAceptar.addMouseListener(new MouseAdapter(){
			public void mouseClicked(MouseEvent e){
				jbAceptarMouseClicked(e);
			}
		});
		
		cancelarRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/CancelarEdit.gif"));
		jbCancelar = new JButton();
		jbCancelar.setToolTipText(resource.getString("TableDataTab.jbCancelar.tooltip"));
		jbCancelar.setIcon(cancelarRecordIcon);
		jbCancelar.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbCancelar.tooltip"));
		jbCancelar.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				refreshTable();
			}
		});
		
		refreshDataIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/RefreshData.gif"));
		jbRefresh = new JButton();
		jbRefresh.setToolTipText(resource.getString("TableDataTab.jbRefresh.tooltip"));
		jbRefresh.setIcon(refreshDataIcon);
		jbRefresh.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbRefresh.accessiblename"));
		jbRefresh.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				refreshTable();
			}
		});
		
		jcLimite = new JCheckBox(resource.getString("TableDataTab.jcLimite.title"));
		jcLimite.setSelected(false);
		jcLimite.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				setLimitieEnabled(jcLimite.isSelected());
			}
		});

		txtLimiteInferior = new JTextField("0");
		txtLimiteInferior.setToolTipText(resource.getString("TableDataTab.txtLimiteInferior.tooltip"));
		
		txtLimiteSuperior = new JTextField("100");
		txtLimiteSuperior.setToolTipText(resource.getString("TableDataTab.txtLimiteSuperior.tooltip"));
		
		jbOk = new JButton(resource.getString("TableDataTab.jbOk.title"));
		jbOk.setToolTipText(resource.getString("TableDataTab.jbOk.tooltip"));
		jbOk.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbOk.accessiblename"));
		jbOk.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbOkMouseClicked(e);
			}
		});
		
		toolbar.add(jbPrimero);
		toolbar.add(jbAnterior);
		toolbar.add(jbSiguiente);
		toolbar.add(jbUltimo);
		toolbar.addSeparator();
		toolbar.add(jbInsertar);
		toolbar.add(jbBorrar);
		toolbar.add(jbEditar);
		toolbar.add(jbAceptar);
		toolbar.add(jbCancelar);
		toolbar.add(jbRefresh);
		toolbar.addSeparator();
		toolbar.add(jcLimite);
		toolbar.add(txtLimiteInferior);
		toolbar.add(txtLimiteSuperior);
		toolbar.add(jbOk);
		
		return toolbar;
	}
	
	public void setSelectedTable(String databaseName, String tableName){
		if(model != null && model.isEdited()){
			if(JOptionPane.showConfirmDialog(null, resource.getString("TableDataTab.notSavedError"), resource.getString("TableDataTab.title"), JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
				guardarDatos();
			}
		}
		
		this.databaseName = databaseName;
		this.tableName = tableName;
		cargarDatos();
	}

	private void cargarDatos(){
		int cReg = -1;
		String sqlTxt = "SELECT * FROM `" + databaseName + "`.`" + tableName + "`";
		sqlTxt += getLimite();
		
		query = new CQuery(connection);
		if((cReg = query.executeQuery(sqlTxt)) > 0){
			model = new CTableModel(query.getDatos(), query.getHeaders());
			
			boolean[] editableCells = new boolean[query.getHeaders().length];
			for(int i = 0; i < query.getHeaders().length; i++)
				editableCells[i] = true;
			
			//Defino los campos editabes. En este caso, todos.
			model.setEditableCells(editableCells);
			//Defino el tipo de campo que voy a usar.
			model.setClasses(query.getResultSetClasses());

			jtDatos.setModel(model);
			jtDatos.getModel().addTableModelListener(new TableModelListener() {				
				@Override
				public void tableChanged(TableModelEvent e) {
					editResultSetCell(e);
				}
			});
			etiqueta.setText("Tabla: " + tableName + " (" + cReg + " registros).");
		}
		query.cerrar();
		setEditEnabled(false);
	}
	
	private String getLimite(){
		String strLimite = "";
		if(jcLimite.isSelected()){
			if(txtLimiteInferior.getText().length() > 0){
				strLimite = "LIMIT " + txtLimiteInferior.getText();
				if(txtLimiteSuperior.getText().length() > 0)
					strLimite += ", " + txtLimiteSuperior.getText();
			}
		}
		
		return strLimite;
	}
	
	private void setLimitieEnabled(boolean activado){
		txtLimiteInferior.setEnabled(activado);
		txtLimiteSuperior.setEnabled(activado);
		
		cargarDatos();
	}
	
	private void jbOkMouseClicked(MouseEvent e){
		cargarDatos();
	}

	private void setEditEnabled(boolean enabled){
		jbEditar.setEnabled(!enabled);
		jbBorrar.setEnabled(!enabled);
		jbAceptar.setEnabled(enabled);
		jbCancelar.setEnabled(enabled);
	}

	private void jbEditarMouseClicked(MouseEvent e){
		
	}

	private void jbAceptarMouseClicked(MouseEvent e){
		guardarDatos();
	}
	
	private void refreshTable(){
		model.setEdited(false);
		cargarDatos();
		setEditEnabled(false);
	}
	
	private void jbSiguienteMouseClicked(MouseEvent e){
		if(jtDatos.getSelectedRow() < jtDatos.getRowCount()-1)
			jtDatos.setRowSelectionInterval(jtDatos.getSelectedRow()+1, jtDatos.getSelectedRow()+1);
	}
	
	private void jbAnteriorMouseClicked(MouseEvent e){
		if(jtDatos.getSelectedRow() > 0)
			jtDatos.setRowSelectionInterval(jtDatos.getSelectedRow() - 1, jtDatos.getSelectedRow() - 1);
	}
	
	private void jbPrimeroMouseClicked(MouseEvent e){
		jtDatos.setRowSelectionInterval(0, 0);
	}
	
	private void jbUltimoMouseClicked(MouseEvent e){
		jtDatos.setRowSelectionInterval(jtDatos.getRowCount()-1, jtDatos.getRowCount()-1);
	}
	
	public void editResultSetCell(TableModelEvent e){
		setEditEnabled(true);
		
	}
	
	public void guardarDatos(){
		//Verifico si se editaron celdas.
		if(model.getDatosOriginales().size() >= 0){
			if(updadteRows(databaseName, tableName, model.getHeaders(), model.getDatos(), model.getDatosOriginales()) != CQuery.ERROR){
				//model.setEdited(false);
				//cargarDatos();
				//setEditEnabled(false);
				
				//refreshTable();
				model.resetDatosOriginales();
			}else{
				admin.addMessage(query.getErrCode() + ": " + query.getErrMsg());
				JOptionPane.showMessageDialog(null, query.getErrCode() + ": " + query.getErrMsg(), resource.getString("TableDataTab.error.title"), JOptionPane.ERROR_MESSAGE);
			}
		}
		//Verifico si se agregaron filas.
		if(model.getDatosNuevos().size() > 0){
			if(insertRows(databaseName, tableName, model.getHeaders(), model.getDatos(), model.getDatosNuevos()) != CQuery.ERROR){
				model.setEdited(false);
			}else{
				admin.addMessage(query.getErrCode() + ": " + query.getErrMsg());
				JOptionPane.showMessageDialog(null, query.getErrCode() + ": " + query.getErrMsg(), resource.getString("TableDataTab.error.title"), JOptionPane.ERROR_MESSAGE);
			}
		}
		refreshTable();
	}
	
	private AbstractAction tabAction(){
		AbstractAction tab = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				int rowIndex, columnIndex;
				
				if(jtDatos.getSelectedColumn() == jtDatos.getColumnCount()-1 && jtDatos.getSelectedRow() < jtDatos.getRowCount() - 1){
					rowIndex = jtDatos.getSelectedRow() + 1;
					columnIndex = 0;
				}else if(jtDatos.getSelectedRow() == jtDatos.getRowCount() - 1 && jtDatos.getSelectedColumn() == jtDatos.getColumnCount() - 1){
					model.addRow();
					rowIndex = jtDatos.getRowCount() - 1;
					columnIndex = 0;
				}else{
					rowIndex = jtDatos.getSelectedRow();
					columnIndex = jtDatos.getSelectedColumn() + 1;
					
				}
						
				jtDatos.changeSelection(rowIndex, columnIndex, false, false);
				jtDatos.editCellAt(rowIndex, columnIndex);
			}
		};
		
		return tab;
	}

	private int updadteRows(String databaseName, String tableName, String[] columnNames, Vector<Object[]> newDatos, TreeMap<Integer, Object[]> oldDatos){
		CQuery query = new CQuery(connection);
		
		int result = 0;

		String sqlTxt1 = String.format("UPDATE `%s`.`%s` SET", databaseName, tableName);		
		String sqlValue = " `%s`='%s'";															
		String sqlTxt2 = "";
		Iterator<Integer> it = oldDatos.keySet().iterator();
		
		while(it.hasNext()){
			int r = it.next();
			if(!model.isNewRow(r) && !equalsRegistros(newDatos.get(r), oldDatos.get(r))){
				String sqlTxt = sqlTxt1;
				for(int c = 0; c < columnNames.length; c++){
					sqlTxt += String.format(sqlValue, columnNames[c], String.valueOf(newDatos.get(r)[c]));
					sqlTxt2 += String.format(sqlValue, columnNames[c],String.valueOf(oldDatos.get(r)[c]));
					if(c < columnNames.length - 1){
						sqlTxt += ",";
						sqlTxt2 += " AND";
					}
				}
				sqlTxt += " WHERE " + sqlTxt2 + ";";
				System.out.println(sqlTxt);
				int modificados = query.executeUpdate(sqlTxt);
				if(modificados > 0)
					result += modificados;
				else{
					//TODO No se pudo hacer el update, verificar el error.
				}
			}
		}
		query.cerrar();
		
		return result;
	}	

	
	private int insertRows(String databaseName, String tableName, String[] columnNames, Vector<Object[]> datos, Vector<Integer> datosNuevos){
		CQuery query = new CQuery(connection);
		
		int result = 0;
		
		String sqlTxt1 = "INSERT INTO `%s`.`%s` (";
		Object[] registro;
		
		for(int index : datosNuevos){
			String sqlTxt = String.format(sqlTxt1, databaseName, tableName);
			String valores = "";
			registro = datos.get(index);
			//Verifico si la fila está vacía, así la ignoro.
			if(!isEmptyRegistro(registro)){
				for(int i = 0; i < registro.length; i++){
					if(registro[i] != null){
						sqlTxt += "`" + jtDatos.getColumnName(i) + "`, ";
						valores += "'" + registro[i] + "', ";
					}
				}
				sqlTxt = sqlTxt.substring(0, sqlTxt.length() - 2);
				sqlTxt += ") VALUES (" + valores.substring(0, valores.length() - 2) + ");";

				int modificados = query.executeUpdate(sqlTxt);
				if(modificados > 0)
					result += modificados;
				else{
					//TODO No se pudo hacer el update, verificar el error.
				}
				
			}
		}
		query.cerrar();
		
		return result;
	}
	
	private void deleteRow(){
		
		if(jtDatos.getSelectedRow() >= 0){
			CQuery query = new CQuery(connection);

			int respuesta = JOptionPane.showConfirmDialog(null, resource.getString("TableDataTab.deleteQuestion"), resource.getString("TableDataTab.title"), JOptionPane.YES_NO_OPTION);
			if(respuesta == JOptionPane.YES_OPTION){
				String sqlTxt = "DELETE FROM `%s`.`%s` WHERE %s;";
				String valores = "";
				String registro = "`%s`='%s'";
				
				for(int i = 0; i < jtDatos.getColumnCount(); i++){
					valores += String.format(registro, jtDatos.getColumnName(i), jtDatos.getValueAt(jtDatos.getSelectedRow(), i));
					if(i < jtDatos.getColumnCount() - 1)
						valores += " AND ";
				}
				
				sqlTxt = String.format(sqlTxt, databaseName, tableName, valores);
				int modificados = query.executeUpdate(sqlTxt);
				if(modificados < 0){
					JOptionPane.showMessageDialog(null, query.getErrCode() + ": " + query.getErrMsg(), resource.getString("TableDataTab.title"), JOptionPane.ERROR_MESSAGE);
				}
			}
			query.cerrar();
			refreshTable();
		}
	}
	
	private boolean equalsRegistros(Object[] registro1, Object[] registro2){
		if(registro1.length != registro2.length)
			return false;
		for(int i = 0; i < registro1.length; i++){
			if(!registro1[i].equals(registro2[i]))
				return false;
		}
		
		return true;
	}
	
	private boolean isEmptyRegistro(Object[] registro){
		for(int i=0; i<registro.length; i++){
			if(registro[i] != null)
				return false;
		}
		
		return true;
	}	
}

