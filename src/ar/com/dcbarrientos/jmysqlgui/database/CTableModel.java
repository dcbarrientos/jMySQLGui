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

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CTableModel{
	private Object[][] datos;
	private String[] headers;
	
	public CTableModel(Object[][] datos, String[] headers){
		this.datos = datos;
		this.headers = headers;
	}
	
	public CTableModel(){
		this.datos = null;
		this.headers = null;
	}
	
	public TableModel getTableModel(){
		TableModel tm = null;
		
		if(datos != null && headers != null){
			tm = new AbstractTableModel() {
				private static final long serialVersionUID = 1L;

				@Override
				public Object getValueAt(int rowIndex, int columnIndex) {
					return datos[rowIndex][columnIndex];
				}
				
				@Override
				public int getRowCount() {
					return datos.length;
				}
				
				@Override
				public int getColumnCount() {
					return headers.length;
				}
				
				@Override
				public String getColumnName(int columnIndex){
					return headers[columnIndex];
				}
			};
		}
		
		return tm;
	}

	/**
	 * @return the datos
	 */
	public Object[][] getDatos() {
		return datos;
	}

	/**
	 * @param datos the datos to set
	 */
	public void setDatos(Object[][] datos) {
		this.datos = datos;
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

	
	
	
}
