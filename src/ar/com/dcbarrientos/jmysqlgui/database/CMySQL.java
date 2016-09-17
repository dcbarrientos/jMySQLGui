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
 * CMySQL.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 8 de ago. de 2016, 9:43:07 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Vector;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CMySQL {
	private Connection connection;
	private int errCode;
	private String errMsg;
	
	public static final int ATTRIBUTE_COUNT = 6;
	public static final int ATTRIBUTE_LENGTH =0;
	public static final int ATTRIBUTE_DECIMAL =1;
	public static final int ATTRIBUTE_UNSIGNED =2;
	public static final int ATTRIBUTE_ZEROFILL =3;
	public static final int ATTRIBUTE_BINARY =4;
	public static final int ATTRIBUTE_AUTO_INCREMENT =5;
	
	/**
	 * Construye una instancia de CMySQL.
	 * @param connection Conexión abierta.
	 */
	public CMySQL(Connection connection){
		this.connection = connection;
	}

	/**
	 * Genera una lista de Collations disponibles en el servidor de bases de datos.
	 * @return Un Vector con la lista de Collations, null en caso de error.
	 */
	public Vector<String> getCollations(){
		String sqlTxt = "SELECT * FROM `information_schema`.`COLLATIONS`";

		return getStringSet(sqlTxt, "COLLATION_NAME");
	}
	
	/**
	 * Devuelve el valor por defecto de Collation en la base de datos.
	 * @return Valor de Collation por defecto. null en caso de error.
	 */
	public String getDefaultCollation(){
		String sqlTxt = "SHOW VARIABLES LIKE 'collation_server';";
		return getValue(sqlTxt, "Value");
	}

	public Vector<String> getEngines(){
		String sqlTxt = "SHOW ENGINES;";
		return getStringSet(sqlTxt, "Engine");
	}
	
	public String getDefaultEngine(){
		String sqlTxt = "SHOW variables like 'default_storage_engine';";
		return getValue(sqlTxt, "Value");
	}
	
	/**
	 * Procedimiento que crea una base de datos.
	 * @param nombre Nombre de la base de datos a crear.
	 * @return Verdadero si se pudo crear la base de datos.
	 */
	public boolean crearDatabase(String nombre, String collation){
		String sqlTxt = "CREATE DATABASE `" + nombre + "` /*!40100 COLLATE '" + collation + "' */";
		CQuery query = new CQuery(connection);
		if(query.executeUpdate(sqlTxt) > 0)
			return true;
		
		error(query.getErrCode(), query.getErrMsg());
		return false;
	}

	/**
	 * Almacena los valores del error generado en el servidor en sus respectivas varialbes.
	 * @param errCode Código del error generado.
	 * @param errMsg Mensaje del error generado.
	 */
	private void error(int errCode, String errMsg){
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	/**
	 * Devuelve el código del error generado en el servidor.
	 * @return
	 */
	public int getErrCode(){
		return errCode;
	}
	
	/**
	 * Devuelve el mensaje que explica el código del error generado en el error del servidor.
	 * @return
	 */
	public String getErrMsg(){
		return errMsg;
	}
	
	private Vector<String> getStringSet(String sqlTxt, String columnName){
		Vector<String> result = new Vector<String>();

		CQuery query = new CQuery(connection);
		if(query.executeQuery(sqlTxt) > CQuery.ERROR){
			result = query.getStringSet(columnName);
			
		}
		query.cerrar();
		
		return result;		
	}
	
	private String getValue(String sqlTxt, String columnName){
		String result = "";

		CQuery query = new CQuery(connection);
		if(query.executeQuery(sqlTxt) > CQuery.ERROR){
			try {
				query.getResultSet().next();
				result = query.getResultSet().getString(columnName);
			} catch (SQLException e) {
				error(e.getErrorCode(), e.getMessage());
			}
		}
		query.cerrar();
		
		return result;		
	}
	
	public LinkedHashMap<String, boolean[]> getDataType(){
		LinkedHashMap<String, boolean[]> dataType = new LinkedHashMap<String, boolean[]>();
		dataType.put("BIT", new boolean[]{true, false, false, false, false, false});
		dataType.put("TINYINT", new boolean[]{true, false, true, true, false, true});
		dataType.put("SMALLINT", new boolean[]{true, false, true, true, false, false});
		dataType.put("MEDIUMINT", new boolean[]{true, false, true, true, false, true});
		dataType.put("INT", new boolean[]{true, false, true, true, false, true});
		//dataType.put("INTEGER", new boolean[]{true, false, true, true, false, ?});
		dataType.put("BIGINT", new boolean[]{true, false, true, true, false, true});

		//dataType.put("REAL", new boolean[]{true, true, true, true, true, ?});
		dataType.put("DOUBLE", new boolean[]{false, false, true, true, false, false});
		dataType.put("FLOAT", new boolean[]{false, false, true, true, false, false});
		dataType.put("DECIMAL", new boolean[]{false, false, true, true, false, false});
		//dataType.put("NUMERIC", new boolean[]{true, true, true, true, false, ?});
		
		dataType.put("CHAR", new boolean[]{true, false, false, false, true, false});
		dataType.put("VARCHAR", new boolean[]{true, false, false, false, true, false});
		dataType.put("TINYTEXT", new boolean[]{false, false, false, false, true, false});
		dataType.put("TEXT", new boolean[]{true, false, false, false, true, false});
		dataType.put("MEDIUMTEXT", new boolean[]{false, false, false, false, true, false});
		dataType.put("LONGTEXT", new boolean[]{false, false, false, false, true, false});

		dataType.put("CHAR", new boolean[]{true, false, false, false, true, false});
		dataType.put("VARCHAR", new boolean[]{true, false, false, false, true, false});
		dataType.put("TINYTEXT", new boolean[]{false, false, false, false, true, false});
		dataType.put("TEXT", new boolean[]{true, false, false, false, true, false});
		dataType.put("MEDIUMTEXT", new boolean[]{false, false, false, false, true, false});
		dataType.put("LONGTEXT", new boolean[]{false, false, false, false, true, false});

		dataType.put("BINARY", new boolean[]{true, false, false, false, false, false});
		dataType.put("VARBINARY", new boolean[]{true, false, false, false, false, false});
		dataType.put("TINYBLOB", new boolean[]{false, false, false, false, false, false});
		dataType.put("BLOB", new boolean[]{false, false, false, false, false, false});
		dataType.put("MEDIUMBLOB", new boolean[]{false, false, false, false, false, false});
		dataType.put("LONGBLOB", new boolean[]{false, false, false, false, false, false});

		dataType.put("DATE", new boolean[]{false, false, false, false, false, false});
		dataType.put("TIME", new boolean[]{true, false, false, false, false, false});
		dataType.put("TIMESTAMP", new boolean[]{true, false, false, false, false, false});
		dataType.put("DATETIME", new boolean[]{true, false, false, false, false, false});
		dataType.put("YEAR", new boolean[]{false, false, false, false, false, false});

		//| ENUM(value1,value2,value3,...)
		//  | SET(value1,value2,value3,...)
		//  | JSON

		return dataType;
	};
	
	
}
