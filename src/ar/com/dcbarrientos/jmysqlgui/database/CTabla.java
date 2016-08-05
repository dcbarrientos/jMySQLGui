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
 * CTabla.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 29 de jul. de 2016, 1:08:39 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CTabla {
	private final static int FIELD = 1;
	private final static int TYPE = 2;
	private final static int COLLATION = 3;
	private final static int NULLABLE = 4;
	private final static int DEFUALT = 5;
	private final static int EXTRA = 6;
	private final static int PRIVILIGIES = 7;
	private final static int COMMENT = 8;

	
	private Connection connection = null;
	private String name;
	private String Tipo;
	private String Row_format;
	private int Rows;
	private int Avg_row_length;
	private double Data_length;
	private double Max_data_length;
	private double Index_length;
	private double Data_free;
	private int Auto_increment;
	private String Create_time;
	private String Update_time;
	private String Check_time;
	private String Create_options;
	private String Comment;
	
	private Vector<CField> campos;
	private int cantidadCampos;
	
	private String databaseName;
	
	/**
	 * Constructor de la estructura CTable que contiene toda la información de una tabla.
	 * @param name Nombre de la tabla.
	 * @param databaseName Nombre de la base de datos a la que pertenece.
	 * @param connection Conexión abierta.
	 */
	public CTabla(String name, String databaseName, Connection connection){
		this.name = name;
		this.databaseName = databaseName;
		this.connection = connection;
	}
	
	private void cargarInformacion(){
		String sql = "SHOW FULL COLUMNS FROM `" + databaseName + "`.`" + name + "`";
		CQuery query = new CQuery(connection);
		
		campos = new Vector<CField>();
		cantidadCampos = query.executeQuery(sql);
		if(cantidadCampos > 0){
			CField campo;
			try {
				while(query.getResultSet().next()){
					campo = new CField();
					campo.setField(query.getResultSet().getString(FIELD));
					campo.setField(query.getResultSet().getString(TYPE));
					campo.setField(query.getResultSet().getString(COLLATION));
					campo.setField(query.getResultSet().getString(NULLABLE));
					campo.setField(query.getResultSet().getString(DEFUALT));
					campo.setField(query.getResultSet().getString(EXTRA));
					campo.setField(query.getResultSet().getString(PRIVILIGIES));
					campo.setField(query.getResultSet().getString(COMMENT));
					campos.addElement(campo);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public Vector<CField> getCampos(){
		if(campos == null)
			cargarInformacion();
		return campos;
	}
	
	public String getDatabaseName(){
		return databaseName;
	}
	
	/**
	 * @return Devuelve el nombre de la tabla
	 */
	public String getName(){
		return this.name;
	}


	/**
	 * @return the tipo
	 */
	public String getTipo() {
		return Tipo;
	}


	/**
	 * @param tipo the tipo to set
	 */
	public void setTipo(String tipo) {
		Tipo = tipo;
	}


	/**
	 * @return the row_format
	 */
	public String getRow_format() {
		return Row_format;
	}


	/**
	 * @param row_format the row_format to set
	 */
	public void setRow_format(String row_format) {
		Row_format = row_format;
	}


	/**
	 * @return the rows
	 */
	public int getRows() {
		return Rows;
	}


	/**
	 * @param rows the rows to set
	 */
	public void setRows(int rows) {
		Rows = rows;
	}


	/**
	 * @return the avg_row_length
	 */
	public int getAvg_row_length() {
		return Avg_row_length;
	}


	/**
	 * @param avg_row_length the avg_row_length to set
	 */
	public void setAvg_row_length(int avg_row_length) {
		Avg_row_length = avg_row_length;
	}


	/**
	 * @return the data_length
	 */
	public double getData_length() {
		return Data_length;
	}


	/**
	 * @param data_length the data_length to set
	 */
	public void setData_length(double data_length) {
		Data_length = data_length;
	}


	/**
	 * @return the max_data_length
	 */
	public double getMax_data_length() {
		return Max_data_length;
	}


	/**
	 * @param max_data_length the max_data_length to set
	 */
	public void setMax_data_length(double max_data_length) {
		Max_data_length = max_data_length;
	}


	/**
	 * @return the index_length
	 */
	public double getIndex_length() {
		return Index_length;
	}


	/**
	 * @param index_length the index_length to set
	 */
	public void setIndex_length(double index_length) {
		Index_length = index_length;
	}


	/**
	 * @return the data_free
	 */
	public double getData_free() {
		return Data_free;
	}


	/**
	 * @param data_free the data_free to set
	 */
	public void setData_free(double data_free) {
		Data_free = data_free;
	}


	/**
	 * @return the auto_increment
	 */
	public int getAuto_increment() {
		return Auto_increment;
	}


	/**
	 * @param auto_increment the auto_increment to set
	 */
	public void setAuto_increment(int auto_increment) {
		Auto_increment = auto_increment;
	}


	/**
	 * @return the create_time
	 */
	public String getCreate_time() {
		return Create_time;
	}


	/**
	 * @param create_time the create_time to set
	 */
	public void setCreate_time(String create_time) {
		Create_time = create_time;
	}


	/**
	 * @return the update_time
	 */
	public String getUpdate_time() {
		return Update_time;
	}


	/**
	 * @param update_time the update_time to set
	 */
	public void setUpdate_time(String update_time) {
		Update_time = update_time;
	}


	/**
	 * @return the check_time
	 */
	public String getCheck_time() {
		return Check_time;
	}


	/**
	 * @param check_time the check_time to set
	 */
	public void setCheck_time(String check_time) {
		Check_time = check_time;
	}


	/**
	 * @return the create_options
	 */
	public String getCreate_options() {
		return Create_options;
	}


	/**
	 * @param create_options the create_options to set
	 */
	public void setCreate_options(String create_options) {
		Create_options = create_options;
	}


	/**
	 * @return the comment
	 */
	public String getComment() {
		return Comment;
	}


	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		Comment = comment;
	}
	
	
}
