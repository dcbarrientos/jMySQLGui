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
 * CTableModel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 1 de ago. de 2016, 2:57:59 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.util.TreeMap;
import java.util.Vector;

import javax.swing.table.AbstractTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	private Vector<Object[]> datos;
	private String[] headers;
	private boolean[] editableCells;
	private Class<?>[] classes;
	private TreeMap<Integer, Object[]> datosOriginales;
	private Vector<Integer> datosNuevos;
	
	public CTableModel(Vector<Object[]> datos, String[] headers){
		this.datos = datos;
		this.headers = headers;
		resetDatos();
	}
	
	public CTableModel(){
		this.datos = null;
		this.headers = null;
		resetDatos();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return datos.elementAt(rowIndex)[columnIndex];
	}
	
	@Override
	public void setValueAt(Object value, int rowIndex, int columnIndex){
		//Verifico si el registro identificado por el rowIndex ya fue editado.
		//Si no fue editado, get devuelve null, en caso contrario devuelve
		//el registro con sus valores originales.
		if(datosOriginales.get(rowIndex) == null){
			Object[] registro = new Object[headers.length];
			for(int i = 0; i < headers.length; i++)
				registro[i] =  datos.elementAt(rowIndex)[i];
			datosOriginales.put(rowIndex, registro);
		}
		
		Object[] tmp = new Object[headers.length];
		tmp = datos.elementAt(rowIndex);
		tmp[columnIndex] = value;
		datos.setElementAt(tmp, rowIndex);

		fireTableCellUpdated(rowIndex, columnIndex);
	}
	
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
	public boolean isCellEditable(int rowIndex, int columnIndex){
		return editableCells[columnIndex];
	}

	private void resetDatos(){
		this.datosOriginales = new TreeMap<Integer, Object[]>();
		this.datosNuevos = new Vector<Integer>();
		
		if(editableCells == null){
			editableCells = new boolean[headers.length];
			for(int i = 0; i < headers.length; i++)
				editableCells[i] = false;
		}
	}
	
	/**
	 * Especifico qué celdas son editables y cuáles no.
	 * @param editableCells Un array tipo boolean que tiene tantos elementos
	 *        como columnas tiene el ResultSet. True para las columnas que
	 *        son editables.
	 */
	public void setEditableCells(boolean[] editableCells){
		this.editableCells = editableCells;
	}
	
	/**
	 * Especifico el tipo de datos de cada columna del ResultSet
	 * @param classes Un array con la definición de la clase de cada columna.
	 */
	public void setClasses(Class<?>[] classes){						
		this.classes = classes;
	}
	
	public boolean isEdited(){
		if(datosOriginales == null && datosNuevos == null)
			return false;
		
		return (!datosOriginales.isEmpty() && !datosNuevos.isEmpty());
	}
	
	public void setEdited(boolean edited){
		if(!edited)
			datosOriginales = new TreeMap<Integer, Object[]>();
	}
	
	public TreeMap<Integer, Object[]> getDatosOriginales(){
		return datosOriginales;
	}
		
	/**
	 * @return the datos
	 */
	
	public Vector<Object[]> getDatos() {
		return datos;
	}


	/**
	 * @return the headers
	 */
	
	public String[] getHeaders() {
		return headers;
	}

	/**
	 * @param headers the headers to set
	 */
	
	public void setHeaders(String[] headers) {
		this.headers = headers;
	}

	
	/**
	 * Devuelve el tipo de dato que contiene una columna del ResultSet.
	 * @param columnIndex Indice de la column de la cual quiero saber su tipo de dato.
	 * @return Devuelve el tipo de dato que contiene una columna dada.
	 */
	
	public  Class<?> getColumnClass(int columnIndex){
		if(classes != null)
			return classes[columnIndex];
		
		return String.class;
	}
	
	public void addRow(){
		Object[] registro = new Object[headers.length];
		datos.addElement(registro);
		datosNuevos.add(datos.size() - 1);
		
		fireTableDataChanged();
	}
	
	public Vector<Integer> getDatosNuevos(){
		return datosNuevos;
	}
	
	public boolean isNewRow(int row){
		for(int i = 0; i < datosNuevos.size(); i++)
			if(datosNuevos.get(i) == row)
				return true;
		
		return false;
	}
	
	public void resetDatosOriginales(){
		datosOriginales = new TreeMap<Integer, Object[]>();
	}
	
}
