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
 * QueryEditor.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 31 de jul. de 2016, 9:29:10 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JToolBar;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;

import org.fife.ui.autocomplete.AutoCompletion;
import org.fife.ui.autocomplete.DefaultCompletionProvider;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.SyntaxConstants;
import org.fife.ui.rtextarea.RTextScrollPane;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;
import ar.com.dcbarrientos.jmysqlgui.database.CQuery;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class QueryEditor extends JPanel{
	private static final long serialVersionUID = 1L;
	private JLabel lblDescription = new JLabel();
	private JSplitPane jSplitPane;
	private JTable jtblSqlResult;
	private RSyntaxTextArea textArea;
	private RTextScrollPane sp;
	private JScrollPane scroll;
	private JToolBar toolBar;
	private JButton btnNewButton;
	private JPanel panel;
		
//	private Principal principal;
	private CConnection cconnection;
	private ResourceBundle resource;
	
	public QueryEditor(CConnection cconnection, ResourceBundle resource)
	{
		super();
		
//		principal = princ;
		this.cconnection = cconnection;
		this.resource = resource;
		initComponents();
	} 
	
	private void initComponents(){
		Object[][] data = {{""}};
		String[] header = {""};
		jtblSqlResult = new JTable(data, header);
		
		jtblSqlResult.setAutoResizeMode(0);
		jtblSqlResult.setVisible(true);
		jtblSqlResult.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		scroll = new JScrollPane(jtblSqlResult);
		
		lblDescription.setText(resource.getString("QueryEditor.lblDescription"));
		lblDescription.setOpaque(true);
		lblDescription.setForeground(Color.white);
		lblDescription.setBackground(Color.black);

		textArea = new RSyntaxTextArea();
		textArea.setSyntaxEditingStyle(SyntaxConstants.SYNTAX_STYLE_SQL);
		
		DefaultCompletionProvider provider = new DefaultCompletionProvider();
				
		AutoCompletion ac = new AutoCompletion(provider);
		ac.install(textArea);		
		
		sp = new RTextScrollPane(textArea);
		textArea.setText("SELECT * FROM MIDBASE.PET;");
		
		jSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, sp, scroll);
		jSplitPane.setOneTouchExpandable(true);

		setLayout(new BorderLayout());
		setLocation(new Point(0, 0));
		
		add(lblDescription, BorderLayout.NORTH);
		add(jSplitPane, BorderLayout.CENTER);		
		
		panel = new JPanel();
		add(panel, BorderLayout.SOUTH);
		
		toolBar = new JToolBar();
		toolBar.setOrientation(SwingConstants.VERTICAL);
		add(toolBar, BorderLayout.WEST);
		
		btnNewButton = new JButton("Ex");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				executeQuery();
			}
		});
		toolBar.add(btnNewButton);
	}
	
	private void executeQuery(){
		CQuery query = new CQuery(cconnection.getConnection());
		if(query.executeQuery(textArea.getText()) > 0){
			jtblSqlResult.setModel(query.getModel());
			jtblSqlResult.repaint();
			//TODO Agregar la consulta en el cuadro de texto.
		}else{
			//TODO Mensaje de error
		}
		query.cerrar();
		
		/*
		CQuery query = new CQuery(con.getConexion(), principal.getCurrentDatabase());
		int cc = query.executeQuery(textArea.getText());
		if(cc < 0){
			jtblSqlResult.setModel(new DefaultTableModel());
			String err = "Error(" + query.getErrorCode() + "): " + query.getErrorMsg();
			principal.addMessage(err, textArea.getText());
		}else{		
			TableModel tm = query.getTableModel();
			if(tm != null)
				jtblSqlResult.setModel(tm);
			query.close();
			jtblSqlResult.repaint();
			principal.addMessageSql(textArea.getText());
		}*/
	}
}
