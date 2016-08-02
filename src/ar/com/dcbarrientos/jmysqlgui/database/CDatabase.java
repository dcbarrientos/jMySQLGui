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
 * Created on 29 de jul. de 2016, 1:05:05 p.�m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

import ar.com.dcbarrientos.jmysqlgui.ui.MdiAdmin;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CDatabase {
	private Connection connection = null;
	private MdiAdmin admin;
	private String name;
	private int tableCount;
	private int databaseSize;
	
	private HashMap<String, CTabla> tablas;
	
	public static final int BYTES = 0;
	public static final int KB = 1;
	public static final int MB = 2;
	public static final int GB = 3;
	
	
	/**
	 * Constructar del controlador que se encarga de cargar la informaci�n de la base de datos inigresada.
	 * @param admin Administrador para poder publicar los mensajes en el cuadro inferior.
	 * @param name Nombre de la base de datos a cargar.
	 * @param connection Conexi�n abierto.
	 */
	public CDatabase(MdiAdmin admin, String name, Connection connection){
		this.connection = connection;
		this.name = name;
		this.admin = admin;
		cargarInformacion();
	}
	
	/**
	 * Se carga la informaci�n de la base de datos especificada en el constructor.
	 */
	public void cargarInformacion(){
		String sql = "SHOW TABLE STATUS FROM  `" + name + "`";
		CQuery query = new CQuery(connection);
		tablas = new HashMap<String, CTabla>();
		
		if(query.executeQuery(sql) > 0){
			ResultSet result = query.getResultSet();
			CTabla tabla;
			tableCount = 0;
			databaseSize = 0;
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
					databaseSize += result.getInt(7);
					tableCount++;
				}
			} catch (SQLException e) {
				admin.addError(e.getErrorCode() + ": " + e.getMessage(), sql);
			}			
		}
		query.cerrar();
		admin.addSQL(sql);
		
	}
	
	/**
	 * @return Devuelve una estructura de CTablas con la informaci�n de las tablas de la base de datos.
	 */
	public HashMap<String, CTabla> getTablas(){
		return tablas;
	}
	
	/**
	 * @return Devuelve el nombre de la base de datos.
	 */
	public String getName(){
		return name;
	}
	
	/**
	 * @return Devuelve la cantidad de tablas en la base de datos.
	 */
	public int getTableCount(){
		return tableCount;
	}

	/**
	 * Devuelve el tama�o total de la base de datos.
	 */
	public int getDatabaseSize(){
		return databaseSize;
	}
	
	public void mostrar(){
		for(Map.Entry<String, CTabla> elemento: tablas.entrySet()){
			System.out.println("\t" + elemento.getKey());
		}
	}
	
	/**
	 * Cuando se le ingresa un valor en bytes devuelve su equivalente en la unidad m�s
	 * grande posible. 1024 bytes devolver�a 1 kb.
	 * @param nro Valor en bytes para calcular su equivalente.
	 * @return Devuelve el equibalente m�s grande posible.
	 */
	public static String getTableSize(int nro){
		float size = nro;
		int cons = 1024;
		int unidad = 0;
		
		while(size > cons){
			size /= cons;
			unidad++;
		}

		DecimalFormat dec = new DecimalFormat("0.00");
		if(unidad == BYTES)
			return dec.format(size).concat(" B.");
		else if(unidad == KB)
			return dec.format(size).concat(" Kb.");
		else if(unidad == MB)
			return dec.format(size).concat(" Mb.");
	
		return dec.format(size).concat(" Gb.");
	}

}
