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
import java.util.Vector;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CMySQL {
	private Connection connection;
	private int errCode;
	private String errMsg;
	
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
		CQuery query = new CQuery(connection);
		String sqlTxt = "SELECT * FROM `information_schema`.`COLLATIONS`";

		if(query.executeQuery(sqlTxt) > 0){
			return query.getStringSet(1);
		}		
		query.cerrar();
		return null;
	}
	
	/**
	 * Devuelve el valor por defecto de Collation en la base de datos.
	 * @return Valor de Collation por defecto. null en caso de error.
	 */
	public String getDefaultCollation(){
		String defaultCollation = null;
		String sqlTxt = "SHOW VARIABLES LIKE 'collation_server';";
		CQuery query = new CQuery(connection);
		if(query.executeQuery(sqlTxt) >= 0){
			try {
				query.getResultSet().next();
				defaultCollation = query.getResultSet().getString("Value");
			} catch (SQLException e) {
				error(e.getErrorCode(), e.getMessage());
			}
		}
		query.cerrar();
		
		return defaultCollation;
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
}
