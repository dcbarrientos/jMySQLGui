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
 * Funciones.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 3 de ago. de 2016, 7:16:36 p.�m. 
 */

package ar.com.dcbarrientos.jmysqlgui;

import java.text.DecimalFormat;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class Funciones {
	public static final int BYTES = 0;
	public static final int KB = 1;
	public static final int MB = 2;
	public static final int GB = 3;
	
	
	/**
	 * Cuando se le ingresa un valor en bytes devuelve su equivalente en la unidad m�s
	 * grande posible. 1024 bytes devolver�a 1 kb.
	 * @param nro Valor en bytes para calcular su equivalente.
	 * @return Devuelve el equibalente m�s grande posible.
	 */	
	public static String getExtendedSize(double nro){
		double size = nro;
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
