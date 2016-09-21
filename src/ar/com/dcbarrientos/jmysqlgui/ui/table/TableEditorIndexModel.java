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
 * TableEditorIndexModel.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 19 de set. de 2016, 11:03:45 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui.table;

import java.util.Vector;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import ar.com.dcbarrientos.jmysqlgui.database.CIndex;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class TableEditorIndexModel extends AbstractTableModel implements ListSelectionListener{
	private static final long serialVersionUID = 1L;
	private final int COLUMN_NAME = 0;
	private final int COLUMN_TYPE = 1;

	private String[] headers = {"Index Name", "Type"};
	
	private Vector<CIndex>indexes;
	
	public TableEditorIndexModel(){
		indexes = new Vector<CIndex>();
	}
	
	@Override
	public int getRowCount() {
		return indexes.size();
	}

	@Override
	public int getColumnCount() {
		return headers.length;
	}

	@Override
	public String getColumnName(int columnIndex){
		return headers[columnIndex];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if(columnIndex == COLUMN_NAME)
			return indexes.elementAt(rowIndex).getName();
		if(columnIndex == COLUMN_TYPE)
			return indexes.elementAt(rowIndex).getType();
		
		return null;
	}
	
	@Override
	public boolean isCellEditable(int row, int column){
		return true;
	}

	public void setValueAt(Object aValue, int rowIndex, int columnIndex){
		if(columnIndex == COLUMN_NAME)
			indexes.elementAt(rowIndex).setName((String)aValue);
		if(columnIndex == COLUMN_TYPE)
			indexes.elementAt(rowIndex).setType((String) aValue);
			
		
		fireTableDataChanged();
	}
	
	public void addIndex(){
		CIndex newIndex = new CIndex();
		indexes.addElement(newIndex);
		fireTableDataChanged();
	}
	
	public void addIndex(CIndex index){
		indexes.addElement(index);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		// TODO Auto-generated method stub
		System.out.println("hubo un cambio");
	}
	
	public void setFields(int rowIndex, Vector<Object[]> fields){
		indexes.elementAt(rowIndex).setFields(fields);
		System.out.println(indexes.elementAt(rowIndex).toString());
	}
	
	public Vector<Object[]> getFields(int rowIndex){
		if(rowIndex > -1){
			return indexes.elementAt(rowIndex).getFields();
		}
		return null;
	}
}
