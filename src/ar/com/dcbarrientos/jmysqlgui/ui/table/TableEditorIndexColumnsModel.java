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
 * TableEditorIndexColumnsModel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 19 de set. de 2016, 1:12:51 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui.table;

import java.util.Vector;

import javax.swing.table.AbstractTableModel;

import ar.com.dcbarrientos.jmysqlgui.database.CIndex;
import ar.com.dcbarrientos.jmysqlgui.database.CNewTableField;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class TableEditorIndexColumnsModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	
	public static final int COLUMN_SELECTED = 0;
	public static final int COLUMN_NAME = 1;
	public static final int COLUMN_NUMBER = 2;
	public static final int COLUMN_ORDER = 3;
	
	private String[] headers = {"", "Column", "#", "Order"};
	private Vector<Object[]> datos;
	private Vector<CIndex> indexes;
	private Class<?>[] clases = {Boolean.class, String.class, String.class, String.class};
	private int editedRow;
	
	@Override
	public int getRowCount() {
		return datos.size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}
	
	@Override
	public String getColumnName(int columnIndex){
		return headers[columnIndex];
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return datos.elementAt(rowIndex)[columnIndex];
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex){		
		datos.elementAt(rowIndex)[columnIndex] = aValue;
		editedRow = rowIndex;
		fireTableDataChanged();
	}
	
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex){
		if(columnIndex == 1)
			return false;
		return true;
	}
	
	public Class<?> getColumnClass(int columnIndex) {
		return clases[columnIndex];
	}
	
	public void setFields(Vector<CNewTableField> fields){
		datos = new Vector<Object[]>();
		
		for(CNewTableField elemento: fields){
			Object[] registro = new Object[headers.length];
			registro[1] = elemento.getName();
			datos.addElement(registro);
		}
		System.out.println("Datos cargados.");
		fireTableDataChanged();
	}
	
	public void setIndexesList(Vector<CIndex> indexes){
		this.indexes = indexes;
	}
	
	public Vector<CIndex> getIndexesList(){
		return indexes;
	}

	public Vector<Object[]> getDatos(){
		Vector<Object[]> selectedDatos = new Vector<Object[]>();
		for(Object[]elemento : datos){
			if(elemento[COLUMN_SELECTED] != null && (boolean)elemento[COLUMN_SELECTED]){
				Object[] t = new Object[CIndex.FIELD_ATTRIBUTES];
				t[CIndex.FIELD_COLUMN_NAME] = elemento[COLUMN_NAME];
				t[CIndex.FIELD_NUMBER] = elemento[COLUMN_NUMBER];
				t[CIndex.FIELD_ORDER] = elemento[COLUMN_ORDER];
				selectedDatos.addElement(t);
			}
		}
		//Devolver solo los true
		return selectedDatos;
	}
	
	public void setDatos(Vector<Object[]> newDatos){
		if(datos != null && datos.size()>0){
			for(int i = 0; i < datos.size(); i++){
				datos.elementAt(i)[COLUMN_SELECTED] = false;
				datos.elementAt(i)[COLUMN_NUMBER] = "";
			}
		}
		
		for(Object[] newDato : newDatos){
			int i = 0;
			while(i < datos.size()){
				Object[] dato = datos.elementAt(i); 
				if(((String)newDato[CIndex.FIELD_COLUMN]).equals(dato[1])){
					datos.elementAt(i)[COLUMN_SELECTED] = true;
					datos.elementAt(i)[COLUMN_NUMBER] = newDato[CIndex.FIELD_NUMBER];
					break;
					//TODO el resto de los datos de la tabla. 
				}
				i++;
			}
		}
		fireTableDataChanged();
	}
	
	public int getEditedRow(){
		return editedRow;
	}
}
