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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.ResourceBundle;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
		jtDatos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
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
		
		deleteRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DeleteRecord.gif"));
		jbBorrar = new JButton();
		jbBorrar.setToolTipText(resource.getString("TableDataTab.jbBorrar.tooltip"));
		jbBorrar.setIcon(deleteRecordIcon);
		jbBorrar.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbBorrar.accessiblename"));
		
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
				jbCancelarMouseClicked(e);
			}
		});
		
		refreshDataIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/RefreshData.gif"));
		jbRefresh = new JButton();
		jbRefresh.setToolTipText(resource.getString("TableDataTab.jbRefresh.tooltip"));
		jbRefresh.setIcon(refreshDataIcon);
		jbRefresh.getAccessibleContext().setAccessibleName(resource.getString("TableDataTab.jbRefresh.accessiblename"));
		jbRefresh.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				jbRefreshMouseClicked(e);
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
		this.databaseName = databaseName;
		this.tableName = tableName;
		cargarDatos();
	}

	private void cargarDatos(){
		int cReg = -1;
		String sqlTxt = "SELECT * FROM `" + databaseName + "`.`" + tableName + "`";
		sqlTxt += getLimite();
		
		CQuery query = new CQuery(connection);
		if((cReg = query.executeQuery(sqlTxt)) > 0){
			CTableModel model = new CTableModel(query.getDatos(), query.getHeaders());
			
			boolean[] editableCells = new boolean[query.getHeaders().length];
			for(int i = 0; i < query.getHeaders().length; i++)
				editableCells[i] = true;
			
			model.setEditableCells(editableCells);
			jtDatos.setModel(model.getTableModel());
			etiqueta.setText("Tabla: " + tableName + " (" + cReg + " registros).");
		}
		query.cerrar();
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
		jbAceptar.setEnabled(enabled);
		jbCancelar.setEnabled(enabled);
	}

	private void jbEditarMouseClicked(MouseEvent e){
		setEditEnabled(true);
	}

	private void jbAceptarMouseClicked(MouseEvent e){
		setEditEnabled(false);
	}
	
	private void jbCancelarMouseClicked(MouseEvent e){
		setEditEnabled(false);
	}

	private void jbRefreshMouseClicked(MouseEvent e){
		cargarDatos();
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
}
