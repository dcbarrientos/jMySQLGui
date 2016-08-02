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
import ar.com.dcbarrientos.jmysqlgui.database.CTableModel;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class QueryEditorTab extends JPanel{
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
		
	private CConnection cconnection;
	private ResourceBundle resource;
	private MdiAdmin admin;
	
	/**
	 * Construye un Panel con el editor de Consultas.
	 * @parma admin JPanel padre. Es necesario para mostrar mensajes en el panel inferior.
	 * @param cconnection Conexión abierta.
	 * @param resource ResourceBundle con los string de la aplicación.
	 */
	public QueryEditorTab(MdiAdmin admin, CConnection cconnection, ResourceBundle resource)
	{
		super();
		this.admin = admin;
		this.cconnection = cconnection;
		this.resource = resource;
		initComponents();
	} 
	
	/**
	 * Inicializo la interfaz gráfica.
	 */
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
		lblDescription.setBackground(new Color(112, 146, 190));

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
	
	/**
	 * Ejecuta la consulta del editor de SQL
	 */
	private void executeQuery(){
		String sql = getSQLText();
		CQuery query = new CQuery(cconnection.getConnection());
		if(query.executeQuery(sql) > 0){
			CTableModel cTableModel = new CTableModel(query.getDatos(), query.getHeaders());
			jtblSqlResult.setModel(cTableModel.getTableModel());
			jtblSqlResult.repaint();
			admin.addSQL(sql);
		}else{
			admin.addError(query.getErrCode() + ": " + query.getErrMsg(), sql);
		}
		query.cerrar();
		
	}
	
	/**
	 * @return Devuelve la consulta a ejecutar.
	 */
	private String getSQLText(){
		return textArea.getText();
	}
}
