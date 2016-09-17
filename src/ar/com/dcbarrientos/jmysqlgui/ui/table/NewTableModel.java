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
 * TableStructureModel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 14 de set. de 2016, 4:09:57 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui.table;

import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;

import ar.com.dcbarrientos.jmysqlgui.database.CNewTableField;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class NewTableModel extends AbstractTableModel{
	private static final long serialVersionUID = 1L;
	Vector<CNewTableField> fields;
	
	String[] headers = {"Column Name", "Data type", "Pk", "NN", "UQ", "BI", 
			"UN", "ZF", "AI", "Default", "Collation", "Comment"};
	
	Class<?>[] clases = {JLabel.class, String.class, Boolean.class, Boolean.class, 
			Boolean.class, Boolean.class, Boolean.class, Boolean.class, 
			Boolean.class, String.class, String.class, String.class, };
	

	public NewTableModel() {
		fields = new Vector<CNewTableField>();
	}
	
	@Override
	public String getColumnName(int index){
		return headers[index];
	}
	
	@Override
	public int getRowCount() {
		return fields.size();
	}

	@Override
	public int getColumnCount() {
		return CNewTableField.FIELD_COUNT;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
        return clases[columnIndex];
    }
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return fields.elementAt(rowIndex).getValue(columnIndex);
	}
	
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		fields.elementAt(rowIndex).setValue(aValue, columnIndex);
		
		fireTableDataChanged();
	}
	
	public void addNew(){
		CNewTableField registro = new CNewTableField();
		
		fields.addElement(registro);
		fireTableDataChanged();
		
	}

}
