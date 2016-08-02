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
 * CConection.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 28 de jul. de 2016, 11:50:13 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CConnection {
	private String host;
	private String user;
	private String pass;
	private int port;
	private String database;
	
	public static final String HOST = "localhost";
	public static final String USER = "root";
	public static final int PUERTO = 3306;

	public static final String URL = "jdbc:mysql://";
	public static final String DRIVER = "com.mysql.jdbc.Driver";	

	private Connection connection;
	private CQuery query;
	
	//Interffaz de errores.
	private int errCode;
	private String errMsg;
	private boolean isConnected;
	
	/**
	 * Constructor de CConnection
	 */
	public CConnection(){
		isConnected = false;
	}
	
	/**
	 * Intenta establecer una conexión con el servidor de bases de datos.
	 * @param host Host de la base de datos
	 * @param user Usuario de la base de datos 
	 * @param pass Password del usuario
	 * @param port Puerto del servidor de bases de datos
	 * @param database Nombre/s de la/s base/s de datos.
	 * @return Verdadero si se pudo establecer la conexión.
	 */
	public boolean conectar(String host, String user, String pass, int port, String database){
		isConnected = false;
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.port = port;
		this.database = database;

		try {						
			Class.forName(DRIVER).newInstance();
			connection = DriverManager.getConnection(getUrlExtendida(), this.user, this.pass);
			isConnected = true;
		} catch (SQLException e){
			error(e.getErrorCode(), e.getMessage());
		} catch (InstantiationException e) {
			error(-1, e.getMessage());
		} catch (IllegalAccessException e) {
			error(-2, e.getMessage());
		} catch (ClassNotFoundException e) {
			error(-3, e.getMessage());
		}
		
		return isConnected;
	}
	
	/**
	 * Se le comunica a la conexión abierta la selección de una tabla
	 * @param databaseName Nombre de la base de datos seleccionada.
	 * @return Verdadero si se ejecuto exitosamente la selección.
	 */
	public boolean setSelectedDatabase(String databaseName){
		try {
			connection.setCatalog(databaseName);
			return true;
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
		
		return false;
	}
	
	/**
	 * Se almancenan en las variables de error los contenidos del mismo.
	 * @param code Código de error generado.
	 * @param msg Mensaje del error correspondiente al código.
	 */
	private void error(int code, String msg){
		this.errCode = code;
		this.errMsg = msg;
	}
	
	/**
	 * @return Devuelve el código de error generado.
	 */
	public int getErrCode(){
		return errCode;
	}
	
	/**
	 * @return Devuelve el mensaje del error generado.
	 */
	public String getErrorMsg(){
		return errMsg;
	}
	
	/**
	 * @return Devuelve la base de datos seleccionada en la conexión abierta. Vacío si
	 * no hay base de datos seleccionada.
	 */
	public String getSelectedDatabase(){
		String dbName = "";
		try {
			dbName = connection.getCatalog();
		} catch (SQLException e) {
			error(e.getErrorCode(), e.getMessage());
		}
		return dbName;
	}

	/**
	 * @return Devuelve la url de la conexión a la base de datos de tipo jdbc:subprotocol:subname 
	 */
	private String getUrlExtendida(){
		String txtUrl = URL + host + ":" + Integer.toString(port) + "/" + database;

		return txtUrl;
	}
	
	
	//Getters and Setters.
	public Connection getConnection(){
		return connection;
	}
	
	public boolean isConnected(){
		return isConnected;
	}
	
	public String getUserName(){
		return user;
	}
	
	public String getHost(){
		return host;
	}

	
	/**
	 * @return Devuelve la version de MySQL Server.
	 */
	public String getVersion(){
		String sql = "SELECT version()";
		String version = "";
		query = new CQuery(connection);
		
		if(query.executeQuery(sql)>0){
			try {
				query.getResultSet().next();
				version = query.getResultSet().getString(1);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return version;
	}
	
}
