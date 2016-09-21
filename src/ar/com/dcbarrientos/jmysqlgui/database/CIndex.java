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
 * CIndex.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 19 de set. de 2016, 10:38:44 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import java.util.Vector;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CIndex {
	public static final int PRIMARY_TYPE = 0;
	public static final int KEY_TYPE = 1;
	public static final int UNIQUE_TYPE = 2;
	public static final int FULLTEXT_TYPE = 3;
	public static final int SPATIAL_TYPE = 4;

	public static final String PRIMARY_TYPE_NAME = "Primary key";
	public static final String KEY_TYPE_NAME = "Key";
	public static final String UNIQUE_TYPE_NAME = "Unique";
	public static final String FULLTEXT_TYPE_NAME = "Fulltext";
	public static final String SPATIAL_TYPE_NAME = "Spatial";
	
	public static final int FIELD_COLUMN  = 0;
	public static final int TYPE_COLUMN = 1;
	public static final int ORDER_COLUMN = 2;
	
	public static final int FIELD_ATTRIBUTES = 3; //Cantidad de atributos que va a tener cada cambpo 
													//que compone el indice
	public static final int FIELD_COLUMN_NAME = 0;
	public static final int FIELD_NUMBER = 1;
	public static final int FIELD_ORDER = 2;
	
	private String name;
	private String type;
	//Este arreglo tiene el nombre, el nro y el orden.
	private Vector<Object[]> fields;
	private int order;

	public CIndex(){
		fields = new Vector<Object[]>();
		order = 0;
	}
	
	public CIndex(String name, String type){
		this.name = name;
		this.type = type;
		fields = new Vector<Object[]>();
		order = 0;
	}

	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		this.name = name;
	}
	
	public String getType(){
		return type;
	}
	
	public void setType(String type){
		this.type = type;
	}
	
	public void addElement(String fieldName, int fieldType, String fieldOrder){
		Object[] elemento = new Object[FIELD_ATTRIBUTES];
		elemento[FIELD_COLUMN] = fieldName;
		elemento[TYPE_COLUMN] = fieldType;
		elemento[ORDER_COLUMN] = fieldOrder;
		fields.addElement(elemento);
	}
		
	public String getFieldName(int index){
		return (String)fields.elementAt(index)[FIELD_COLUMN];
	}
	
	public int getFieldType(int index){
		return (int)fields.elementAt(index)[TYPE_COLUMN];
	}
	
	public String getFieldOrder(int index){
		return (String)fields.elementAt(index)[ORDER_COLUMN];
	}
	
	public static String[] getIndexesList(){
		return new String[]{PRIMARY_TYPE_NAME, KEY_TYPE_NAME, UNIQUE_TYPE_NAME, FULLTEXT_TYPE_NAME, SPATIAL_TYPE_NAME};
	}
	
	public void setFields(Vector<Object[]> fields){
		this.fields = fields;
	}
	
	public Vector<Object[]> getFields(){
		return this.fields;
	}
	
	public void addField(Object[] field){
		order++;
		field[FIELD_NUMBER] = order; 
		fields.addElement(field);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CIndex [name=" + name + ", type=" + type + ", fields=" + fields.toString() + "]";
	}

	
}
