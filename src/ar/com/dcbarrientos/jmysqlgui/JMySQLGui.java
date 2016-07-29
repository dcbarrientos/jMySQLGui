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
 * JMySQLGui.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 27 de jul. de 2016, 11:11:42 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import ar.com.dcbarrientos.jmysqlgui.ui.Connection;
import ar.com.dcbarrientos.jmysqlgui.ui.Principal;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class JMySQLGui {
	static String language = "";
	static String country = "";
	
	public JMySQLGui(String language, String country){
		Locale currentLocale = null;
		
		if(language.length() > 0){
			currentLocale = new Locale(language, country);
		}else{
			currentLocale = Locale.getDefault();
		}
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ResourceBundle resource = ResourceBundle.getBundle("ar.com.dcbarrientos.jmysqlgui.resource.JMySQLGui", currentLocale);
		Principal principal = new Principal(resource);
		principal.setVisible(true);
		
		Connection connection = new Connection(principal, true);
		CConnection cconnection = connection.showDialog();

		if(cconnection!=null && cconnection.isConnected()){
			principal.setCConnection(cconnection);
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if(args.length == 2){
			language = args[0];
			country = args[1];
		}
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new JMySQLGui(language, country);
			}
		});

	}

}
