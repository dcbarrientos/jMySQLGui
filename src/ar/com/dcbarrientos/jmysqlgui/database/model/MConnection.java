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
 * MConnection.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 28 de jul. de 2016, 11:51:12 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database.model;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class MConnection {
	String host;
	String user;
	String pass;
	int port;
	String database;
	
	public static final String HOST = "localhost";
	public static final String USER = "root";
	public static final int PUERTO = 3306;

	public static final String URL = "jdbc:mysql://";
	public static final String DRIVER = "com.mysql.jdbc.Driver";	
	
	/**
	 * @param host
	 * @param user
	 * @param pass
	 * @param port
	 * @param database
	 */
	public MConnection(String host, String user, String pass, int port, String database) {
		super();
		this.host = host;
		this.user = user;
		this.pass = pass;
		this.port = port;
		this.database = database;
	}
	/**
	 * @return the host
	 */
	public String getHost() {
		return host;
	}
	/**
	 * @param host the host to set
	 */
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the pass
	 */
	public String getPass() {
		return pass;
	}
	/**
	 * @param pass the pass to set
	 */
	public void setPass(String pass) {
		this.pass = pass;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return the database
	 */
	public String getDatabase() {
		return database;
	}
	/**
	 * @param database the database to set
	 */
	public void setDatabase(String database) {
		this.database = database;
	}
	
	public String getUrlExtendida(){
		String txtUrl = URL + host + ":" + Integer.toString(port) + "/" + database;

		return txtUrl;
	}

	
}
