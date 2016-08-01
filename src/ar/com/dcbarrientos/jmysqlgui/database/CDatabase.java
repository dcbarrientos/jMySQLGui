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
 * Database.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 29 de jul. de 2016, 1:05:05 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CDatabase {
	private Connection connection = null;
	private String name;
	private int tableCount;
	
	private HashMap<String, CTabla> tablas;
	
	public CDatabase(String name, Connection connection){
		this.connection = connection;
		this.name = name;
		cargarInformacion();
	}
	
	public void cargarInformacion(){
		String sql = "SHOW TABLE STATUS FROM  `" + name + "`";
		CQuery query = new CQuery(connection);
		tablas = new HashMap<String, CTabla>();
		
		if(query.executeQuery(sql) > 0){
			ResultSet result = query.getResultSet();
			CTabla tabla;
			tableCount = 0;
			try {
				while(result.next()){					
					tabla = new CTabla(result.getString("Name"), name, connection);
					tabla.setTipo(result.getString(2));
					tabla.setRow_format(result.getString(4));
					tabla.setRows(result.getInt(5));
					tabla.setAvg_row_length(result.getInt(6));
					tabla.setData_length(result.getInt(7));
					tabla.setMax_data_length(result.getString(8));
					tabla.setIndex_length(result.getInt(9));
					tabla.setData_free(result.getInt(10));
					tabla.setAuto_increment(result.getInt(11));
					tabla.setCreate_time(result.getString(12));
					tabla.setUpdate_time(result.getString(13));
					tabla.setCheck_time(result.getString(14));
					tabla.setCreate_options(result.getString(17));
					tabla.setComment(result.getString(18));
					tablas.put(result.getString("Name"), tabla);
					tableCount++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}			
		}
		query.cerrar();
		
	}
	
	public HashMap<String, CTabla> getTablas(){
		return tablas;
	}
	
	public String getName(){
		return name;
	}
	
	public int getTableCount(){
		return tableCount;
	}
		
	public void mostrar(){
		for(Map.Entry<String, CTabla> elemento: tablas.entrySet()){
			System.out.println("\t" + elemento.getKey());
		}
	}
}
