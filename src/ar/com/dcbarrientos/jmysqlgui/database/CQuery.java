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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

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
	private boolean customHeaders;		//Verdadero si se uso setHeaders.
	private String[] headers;			//Nombres de las columnas.
	private PreparedStatement ps;
	
	/**
	 * Constructor del objeto CQuery el cual maneja las consultas.
	 * @param connection Conexión abierta.
	 */
	public CQuery(Connection connection){
		this.connection = connection;
		customHeaders = false;
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
			rowCount = getRowCount(result);
			
			//Obtengo la cantidad de columnas que tiene la consulta.
			meta = result.getMetaData();
			columnCount = meta.getColumnCount();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
			cerrar();
		}
		
		return rowCount;
	}
	
	public int executeUpdate(String sqlTxt){
		rowCount = -1;
		try{
			st = connection.createStatement();
			rowCount = st.executeUpdate(sqlTxt);
		}catch(SQLException e){
			error(e.getErrorCode(), e.getMessage());
		}
		
		try {
			st.close();
		} catch (SQLException e) {
		}
		
		return rowCount;
	}
	
	public boolean execute(String sqlTxt){
		boolean result = false;
		try {
			st = connection.createStatement();
			result = st.execute(sqlTxt);
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
		try {
			st.close();
		} catch (SQLException e) {
		}
		
		return result;
	}
	
	private int getRowCount(ResultSet r){
		int rows = -1; 
		try {
			r.last();
			rows = r.getRow();
			r.beforeFirst();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
		
		return rows;
	}
	
	/**
	 * Cierra la consulta, y el statement generados.
	 */
	public void cerrar(){
		try {
			if(result != null)
				result.close();
			if(st != null)
				st.close();
			if(ps != null)
				ps.close();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
	}
	
	/**
	 * A partir de un ResultSet generado en executeQuery se genera un array con
	 * los datos resultantes.
	 * @return Devuelve un array con los datos de la consulta.
	 */
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
	
	public Vector<String> getStringSet(int c){
		Vector<String> vDatos = new Vector<String>();
		try {
			while(result.next()){
				vDatos.addElement(result.getString(c));
			}
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}		
		return vDatos;
	}
	
	/**
	 * @return Devuelve un array con los nombres de los campos resultantes de la consulta.
	 */
	public String[] getHeaders(){
		if(!customHeaders){
			headers = new String[columnCount];
			try{
				for(int c = 0; c < columnCount; c++)
					headers[c] = meta.getColumnName(c+1);
			} catch (SQLException e) {
				error(e.getErrorCode(), e.getMessage());
				return null;
			}
		}
		return headers;
	}

	/**
	 * Carga en las variables que almacenan los errores.
	 * @param code Código del error.
	 * @param msg Mensaje del error correspondiente al código.
	 */
	private void error(int code, String msg){
		errCode = code;
		errMsg = msg;
	}
	
	/**
	 * @return Devuelve el código del error.
	 */
	public int getErrCode(){
		return errCode;
	}
	
	/**
	 * @return  Devuelve el mensaje del error.
	 */
	public String getErrMsg(){
		return errMsg;
	}
	
	/**
	 * @return Devuelve el ResultSet con el resultado de la consulta.
	 */
	public ResultSet getResultSet(){
		return result;
	}
	
	public PreparedStatement setPrepareStatement(String sql){
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return ps;
	}

	public boolean setString(int orden, String valor){
		try {
			ps.setString(orden, valor);
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean setInt(int orden, int valor){
		try {
			ps.setInt(orden, valor);
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
			return false;
		}
		return true;
	}

	public boolean executePreparedStatement(){
		try {
			return ps.execute();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
		return false;
	}
	
	public int executeQueryPreparedStatement(){
		int rowCount = -1;
		System.out.println(ps.toString());
		try {
			result = ps.executeQuery();
			
			rowCount = getRowCount(result);
			
			meta = result.getMetaData();
			columnCount = meta.getColumnCount();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
			cerrar();
		}
		
		return rowCount;
	}
}
