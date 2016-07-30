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
 * CQuery.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 29 de jul. de 2016, 1:13:34 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

import java.sql.ResultSetMetaData;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CQuery {
	private Connection connection;
	private String errMsg;				//Mensaje con el error generado
	private int errCode;				//Codigo del error
	private Statement st;
	private ResultSet result;
	private ResultSetMetaData meta;		//Información de la consulta realizada.
	private int rowCount;					//cantidad de registros devueltos por la consulta.
	private int columnCount;			//Cantidad de columnas que tiene la consulta.
	private Object[][] datos;			//Datos devueltos por la consulta.
	
	public CQuery(Connection connection){
		this.connection = connection;
	}
	
	/**
	 * Ejecuta una consulta.
	 * @param sqlTxt SQL a ejecutar
	 * @return Cantidad de registros devueltos con esta consulta. -1 Si hubo un error
	 *         el cual está especificado en errCode y errMsg.
	 */
	public int executeQuery(String sqlTxt){
		rowCount = -1;
		
		try {
			st = connection.createStatement();
			result = st.executeQuery(sqlTxt);
			
			//Obtengo la cantidad de filas devuelta por la consulta.
			result.last();
			rowCount = result.getRow();
			result.beforeFirst();			
			
			//Obtengo la cantidad de columnas que tiene la consulta.
			meta = result.getMetaData();
			meta.getColumnCount();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
			cerrar();
		}
		
		return rowCount;
	}
	
	public void cerrar(){
		try {
			if(result != null)
				result.close();
			if(st != null)
				st.close();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
	}
	
	public Object[][] getDatos(){
		datos = new Object[rowCount][columnCount];
		int f = 0;
		try {
			while(result.next()){
				for(int c = 0; c < columnCount; c++)
					datos[f][c] = result.getObject(c+1);
				f++;
			}
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}		
		
		return datos;		
	}
	
	private String[] getHeaders(){
		String[] headers = new String[columnCount];
		try{
			for(int c = 0; c < columnCount; c++)
				headers[c] = meta.getColumnName(c+1);
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
			return null;
		}
		
		return headers;
	}
	
	public TableModel getModel(){
		String[] headers = getHeaders();
		datos =getDatos();
		TableModel tm = new AbstractTableModel(){
			private static final long serialVersionUID = 1L;

			@Override
			public int getRowCount() {
				return rowCount;
			}

			@Override
			public int getColumnCount() {
				return columnCount;
			}

			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return datos[rowIndex][columnIndex];
			}
			
			@Override
			public String getColumnName(int columnIndex){
				return headers[columnIndex];
			}
		};
		
		return tm;
	}
	
	private void error(int code, String msg){
		errCode = code;
		errMsg = msg;
	}
	
	public int getErrCode(){
		return errCode;
	}
	
	public String getErrMsg(){
		return errMsg;
	}
	
	public ResultSet getResultSet(){
		return result;
	}
}
