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
	
	public static int ERROR = -1;
	
	private String errMsg;				//Mensaje con el error generado
	private int errCode;				//Codigo del error
	
	private Statement statement;
	private ResultSet result;
	private ResultSetMetaData meta;		//Información de la consulta realizada.
	private int rowCount;					//cantidad de registros devueltos por la consulta.
	private int columnCount;			//Cantidad de columnas que tiene la consulta.
	//private Object[][] datos;			//Datos devueltos por la consulta.
	private Vector<Object[]> datos;
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
			statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
			result = statement.executeQuery(sqlTxt);
			
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
		rowCount = ERROR;
		try{
			statement = connection.createStatement();
			rowCount = statement.executeUpdate(sqlTxt);
		}catch(SQLException e){
			error(e.getErrorCode(), e.getMessage());
		}
		
		try {
			statement.close();
		} catch (SQLException e) {
		}
		
		return rowCount;
	}
	
	public boolean execute(String sqlTxt){
		boolean result = false;
		try {
			statement = connection.createStatement();
			result = statement.execute(sqlTxt);
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
		try {
			statement.close();
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
			if(statement != null)
				statement.close();
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
	public Vector<Object[]> getDatos(){
		datos = new Vector<Object[]>();
		Object[] registro;
		try {
			while(result.next()){
				registro = new Object[columnCount];
				for(int c = 0; c < columnCount; c++)
					registro[c] = result.getObject(c + 1);

				datos.addElement(registro);
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
	
	/**
	 * Genera un nuevo PreparedStatement para la consulta abierta.
	 * @param sql Es la Sql plantilla para el PreparedStatement
	 * @return Devuelve un PreparedStatement generado a partir de la sql plantilla
	 *         ingresada.
	 */
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
	
	/**
	 * Procedimiento que genera un array con los tipos de datos de cada
	 * columna devuelta en el ResultSet.
	 * @return Array con el tipo de datos que contiene cada columna del 
	 *        ResultSet.
	 */
	public Class<?>[] getResultSetClasses(){
		Class<?>[] classes = new Class<?>[headers.length];
		for(int i = 0; i < columnCount; i++){
			try {
				classes[i] = Class.forName(meta.getColumnClassName(i + 1));
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			} catch (SQLException e) {
				error(e.getErrorCode(), e.getMessage());
				return null;
			}
		}
		
		
		return classes;
	}
	
	public boolean updateValue(Object valor, String clase, int rowIndex, int columnIndex){
		/*
		 * INSERT INTO `aaa`.`qqqq` (`tinyint`, `smallint`, `mediumint`, `int`, `bigint`, `bit`, `float`, 
		 * `double`, `decimal`, `char`, `varchar`, `tinytext`, `text`, `mediumtext`, `longtext`, `binary`, 
		 * `varbinary`, `tinyblob`, `blob`, `mediumblob`, `longblob`, `date`, `time`, `year`, `datetime`, 
		 * `timestamp`, `point`, `linestring`, `polygon`, `geomety`, `multipoint`, `multilinestring`, 
		 * `multipolygon`, `geometrycollection`, `enum`, `set`) 
		 * VALUES ('1', '2', '3', '4', '5', b'0', '7', '8', '9', 'a', 'a', 'a', 'a', 'a', 'a', '1', '2', 
		 * NULL, NULL, NULL, NULL, '2016-08-02', '17:40:00', '2016', '2016-08-10 04:23:32', '2016-08-10 13:13:27', 
		 * GeomFromText('POINT(10 20)',0), GeomFromText('LINESTRING(10 20,30 40)',2), GeomFromText('POLYGON((10 10,30 30,10 20,20 10))',0), 
		 * GeomFromText('POINT(10 20)',0), GeomFromText('MULTIPOINT(10 20,30 10)',0), 
		 * GeomFromText('MULTILINESTRING((10 20,20 10),(20 10,10 20))',0), GeomFromText('MULTIPOLYGON(((1 2,3 4,5 6,7 8)))',0), 
		 * GeomFromText('GEOMETRYCOLLECTION()',0), 'Y', 'Value B');
		 * */
		for(int i = 0; i < columnCount; i++){
			try {
				System.out.println("Clase: " + meta.getColumnClassName(i+1) + " Nombre: " + meta.getColumnName(i+1));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return true;
	}
	
	
}
