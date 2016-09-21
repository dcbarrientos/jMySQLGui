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
 * CRegistro.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 14 de set. de 2016, 4:11:10 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.database;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import ar.com.dcbarrientos.jmysqlgui.ui.Principal;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class CNewTableField {
	public static final int FIELD_COUNT = 12;
	
	public static final int NAME_INDEX = 0;
	public static final int DATA_TYPE_INDEX = 1;
	public static final int PRIMARY_KEY_INDEX = 2;
	public static final int NOT_NULL_INDEX = 3;
	public static final int UNIQUE_INDEX = 4;
	public static final int BINARY_INDEX = 5;
	public static final int UNSIGNED_INDEX = 6;
	public static final int ZERO_FILL_INDEX = 7;
	public static final int AUTO_INCREMENT_INDEX = 8;
	public static final int DEFAULT_VALUE_INDEX = 9;
	public static final int COLLATION_INDEX = 10;
	public static final int COMMENTS_INDEX = 11;
	
	JLabel name;
	String dataType;
	boolean primaryKey;
	boolean notNull;
	boolean unique;
	boolean binary;
	boolean zeroFill;
	boolean autoIncrement;
	boolean unsigned;
	String defaultValue;
	String collation;
	String comments;
	ImageIcon icono = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/campo_secundario.gif"));
	
	public CNewTableField(){
		super();
		this.name = new JLabel(icono, JLabel.LEFT);
		this.dataType = "";
		this.primaryKey = false;
		this.notNull = false;
		this.unique = false;
		this.binary = false;
		this.zeroFill = false;
		this.autoIncrement = false;
		this.unsigned = false;
		this.defaultValue = "";
		this.collation = "";
		this.comments = "";
		
	}
	
	public CNewTableField(String name, String dataType, boolean primaryKey, boolean notNull, boolean unique,
			boolean binary, boolean zeroFill, boolean autoIncrement, boolean unsigned, String defaultValue, 
			String collation, String comments) {
		super();
		this.name = new JLabel(name, icono, JLabel.LEFT);

		this.dataType = dataType;
		//this.length = length;
		this.primaryKey = primaryKey;
		this.notNull = notNull;
		this.unique = unique;
		this.binary = binary;
		this.zeroFill = zeroFill;
		this.autoIncrement = autoIncrement;
		this.unsigned = unsigned;
		this.defaultValue = defaultValue;
		this.collation = collation;
		this.comments = comments;
	}
	
	public Object getValue(int index){
		if(index == NAME_INDEX)
			return (Object)name;
		if(index == DATA_TYPE_INDEX)
			return (Object)dataType;
		if(index == PRIMARY_KEY_INDEX)
			return (Object)primaryKey;
		if(index == NOT_NULL_INDEX)
			return (Object)notNull;
		if(index == UNIQUE_INDEX)
			return (Object)unique;
		if(index == BINARY_INDEX)
			return (Object)binary;
		if(index == ZERO_FILL_INDEX)
			return (Object)zeroFill;
		if(index == AUTO_INCREMENT_INDEX)
			return (Object)autoIncrement;
		if(index == UNSIGNED_INDEX)
			return (Object)unsigned;
		if(index == DEFAULT_VALUE_INDEX)
			return (Object)defaultValue;
		if(index == COLLATION_INDEX)
			return (Object)collation;
		if(index == COMMENTS_INDEX)
			return (Object)comments;
		
		return null;
	}
	
	public void setValue(Object value, int index){
		if(index == NAME_INDEX){
			name = new JLabel(icono, JLabel.LEFT);
			name.setText((String)value);
		}
		if(index == DATA_TYPE_INDEX)
			dataType = (String)value;
		if(index == PRIMARY_KEY_INDEX)
			primaryKey = (boolean)value;
		if(index == NOT_NULL_INDEX)
			notNull = (boolean)value;
		if(index == UNIQUE_INDEX)
			unique = (boolean)value;
		if(index == BINARY_INDEX)
			binary = (boolean)value;
		if(index == ZERO_FILL_INDEX)
			zeroFill = (boolean)value;
		if(index == AUTO_INCREMENT_INDEX)
			autoIncrement = (boolean)value;
		if(index == UNSIGNED_INDEX)
			unsigned = (boolean)value;
		if(index == DEFAULT_VALUE_INDEX)
			defaultValue = (String)value;
		if(index == COLLATION_INDEX)
			collation = (String)value;
		if(index == COMMENTS_INDEX)
			comments = (String)value;			
	}
	
	public String getName(){
		return name.getText();
	}
}
