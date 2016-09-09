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
 * Help.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 6 de set. de 2016, 3:23:00 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import ar.com.dcbarrientos.jmysqlgui.database.CQuery;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class HelpDialog extends JDialog{
	private static final long serialVersionUID = 1L;
	
	private static final int COLUMN_NAME = 1;
	private static final int COLUMN_IS_CATEGORY = 2;
	private static final String HELP_ROOT = "CONTENTS";
	private static final String YES_OPTION = "Y";
	
	private JSplitPane splitPane;
	private JPanel panel;
	private JSplitPane splitPane_1;
	private JPanel panel_1;
	private JPanel panel_2;
	private JLabel lblCommand;
	private JLabel lblDescription;
	private JScrollPane scrollPane;
	private JTextPane txtDescription;
	private JLabel lblExapmple;
	private JScrollPane scrollPane_1;
	private JTextPane txtExample;
	private JPanel panel_3;
	private JPanel panel_4;
	private JTextField txtFilter;
	private JButton btnDeleteFilter;
	private JScrollPane scrollPane_2;
	private JTree tree;
	private JPanel panel_5;
	private JButton btnCerrar;

	private Connection connection;
	private ResourceBundle resource;
	private String command;
	DefaultMutableTreeNode originalRoot;
	DefaultMutableTreeNode filteredRoot;
	
	private FilteredTreeModel model;
	DefaultTreeModel newModel;
	
	private HashMap<String, TreeNode[]> map;
	
	public HelpDialog(Connection connection, ResourceBundle resource ){
		this.connection = connection;
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		setMinimumSize(new Dimension(600, 450));
		splitPane = new JSplitPane();
		getContentPane().add(splitPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		splitPane.setLeftComponent(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_4 = new JPanel();
		panel.add(panel_4, BorderLayout.NORTH);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		txtFilter = new JTextField();
		txtFilter.addKeyListener(new KeyListener() {						
			@Override
			public void keyReleased(KeyEvent e) {
				applyFilter();
			}

			@Override
			public void keyTyped(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
			}
		});

		panel_4.add(txtFilter, BorderLayout.CENTER);
		txtFilter.setColumns(10);
		
		btnDeleteFilter = new JButton("");
		btnDeleteFilter.setContentAreaFilled(false);
		btnDeleteFilter.setBorderPainted(false);
		btnDeleteFilter.setIcon(new ImageIcon(HelpDialog.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/delete-icon.png")));
		btnDeleteFilter.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e){
				txtFilter.setText("");
				applyFilter();
			}
		});
		panel_4.add(btnDeleteFilter, BorderLayout.EAST);
		
		scrollPane_2 = new JScrollPane();
		panel.add(scrollPane_2, BorderLayout.CENTER);
		
		tree = new JTree();
		tree.setRootVisible(false);
		DefaultTreeCellRenderer treeRenderer = new DefaultTreeCellRenderer(){
			private static final long serialVersionUID = 1L;
			
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				Component c = super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

				return c;
			}			
		}; 
		treeRenderer.setOpenIcon(new ImageIcon(HelpDialog.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Book-opened-icon.png")));
		treeRenderer.setClosedIcon(new ImageIcon(HelpDialog.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Book-closed-icon.png")));
		treeRenderer.setLeafIcon(new ImageIcon(HelpDialog.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Document-icon.png")));
		tree.setCellRenderer(treeRenderer);
		
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				treeValueChanged(e);
			}
		});
		scrollPane_2.setViewportView(tree);
		
		splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		panel_1 = new JPanel();
		splitPane_1.setLeftComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel_1.add(scrollPane, BorderLayout.CENTER);
		
		txtDescription = new JTextPane();
		txtDescription.setFont(new Font("Courier New", Font.PLAIN, 11));
		scrollPane.setViewportView(txtDescription);
		
		panel_3 = new JPanel();
		panel_1.add(panel_3, BorderLayout.NORTH);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		lblCommand = new JLabel("Command");
		lblCommand.setFont(new Font("Tahoma", Font.BOLD, 13));
		panel_3.add(lblCommand, BorderLayout.NORTH);
		
		lblDescription = new JLabel(resource.getString("HelpDialog.lblDescription"));
		panel_3.add(lblDescription, BorderLayout.SOUTH);
		
		panel_2 = new JPanel();
		splitPane_1.setRightComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		lblExapmple = new JLabel(resource.getString("HelpDialog.lblExapmple"));
		panel_2.add(lblExapmple, BorderLayout.NORTH);
		
		scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		txtExample = new JTextPane();
		txtExample.setFont(new Font("Courier New", Font.PLAIN, 11));
		scrollPane_1.setViewportView(txtExample);
		
		panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		getContentPane().add(panel_5, BorderLayout.SOUTH);
		
		btnCerrar = new JButton(resource.getString("HelpDialog.btnCerrar"));
		panel_5.add(btnCerrar);
		
		setTitle(resource.getString("HelpDialog.title"));
		loadHelp();
		pack();
		setLocationRelativeTo(null);
	}
	
	public void applyFilter(){
		DefaultMutableTreeNode newRoot = new DefaultMutableTreeNode("root");
		
		newModel = new DefaultTreeModel(newRoot);
		
		for(String key: map.keySet()){
			if(key.indexOf(txtFilter.getText().toUpperCase()) >= 0){
				TreeNode[] nodos = map.get(key);
				
				addNodes(newModel, newRoot, nodos, 1, key);
			}
		}
		tree.setModel(null);
		tree.setModel(newModel);
		expandAll();
		tree.repaint();

	}
	
	private void addNodes(DefaultTreeModel newModel, DefaultMutableTreeNode padre, TreeNode[] nodos, int nivel, String value){
		if(nivel < nodos.length){
			int nodeIndex = getNodeByName(padre, nodos[nivel].toString());
			DefaultMutableTreeNode nodo; 
			if(nodeIndex < 0){
				nodo = new DefaultMutableTreeNode(nodos[nivel].toString());
				int index = getPos(padre, nodo.toString());
				newModel.insertNodeInto(nodo, padre, index);
			}else{
				nodo = (DefaultMutableTreeNode) padre.getChildAt(nodeIndex);
			}
			
			addNodes(newModel, nodo, nodos, ++nivel, value);
			
		}else{
			DefaultMutableTreeNode nodo = new DefaultMutableTreeNode(value);
			int index = getPos(padre, nodo.toString());
			newModel.insertNodeInto(nodo, padre, index);
		}
	}
	
	private int getPos(DefaultMutableTreeNode padre, String value){
		int pos = 0;
		int resultado;
		
		if(padre.getChildCount() > 0){
			for(int i = 0; i < padre.getChildCount(); i++){
				resultado = padre.getChildAt(i).toString().compareTo(value);
				if(resultado >= 0){
					return i;
				}
			}
			pos = padre.getChildCount();
		}
		
		return pos;
	}
	
	private int getNodeByName(DefaultMutableTreeNode padre, String nodeName){
		for(int i = 0; i < padre.getChildCount(); i++){
			if(padre.getChildAt(i).toString().toLowerCase().equals(nodeName.toLowerCase())){
				return i;
			}
		}
		return -1;
	}

	private void expandAll(){
		for(int i = 0; i < tree.getRowCount(); i++){
			tree.expandRow(i);
		}
	}
	
	private void treeValueChanged(TreeSelectionEvent e){
		DefaultMutableTreeNode selNode = (DefaultMutableTreeNode)tree.getLastSelectedPathComponent();
		if(selNode != null && selNode.isLeaf())
			showDescription(selNode.toString());
	}
	
	private void loadHelp(){
		originalRoot = new DefaultMutableTreeNode();
		model = new FilteredTreeModel(originalRoot);
		map = new HashMap<String, TreeNode[]>();

		tree.setModel(getRama(model, originalRoot, HELP_ROOT));
	}
	
	private DefaultTreeModel getRama(FilteredTreeModel treeModel, DefaultMutableTreeNode padre, String rama){
		String sqlTxt = resource.getString("HelpDialog.sqlTxt");
		CQuery query = new CQuery(connection);
		
		if(query.executeQuery(String.format(sqlTxt, rama)) != CQuery.ERROR){
			Vector<Object[]> datos = query.getDatos();
			DefaultMutableTreeNode ramaNode;
			for(Object[] elemento: datos){
				ramaNode = new DefaultMutableTreeNode(elemento[COLUMN_NAME]);
				boolean isCategory = ((String)elemento[COLUMN_IS_CATEGORY]).equals(YES_OPTION);
			
				treeModel.insertNodeInto(ramaNode, padre, padre.getChildCount(), isCategory);
				
				if(isCategory){
					getRama(treeModel, ramaNode, (String)elemento[COLUMN_NAME]);
				}else{
					map.put((String)elemento[COLUMN_NAME], padre.getPath());
				}
			}
		}
		query.cerrar();
		
		return treeModel;
	}
	
	public void search(String command){
		this.command = command;

		lblCommand.setText(this.command);	
		showDescription(this.command);

		TreePath path = find(command);
		if(path != null){
			tree.setSelectionPath(path);
			tree.scrollPathToVisible(path);
		}
	}
	
	private void showDescription(String command){
		lblCommand.setText(command);
		CQuery query = new CQuery(connection);
		String sqlTxt = String.format(resource.getString("HelpDialog.sqlTxt"), command);
		if(query.executeQuery(sqlTxt) != CQuery.ERROR){
			Vector<Object[]> datos = query.getDatos();
			txtDescription.setText((String)(datos.get(0)[COLUMN_NAME]));
			String example = (String)(datos.get(0)[COLUMN_IS_CATEGORY]);
			if(example.length() > 0)
				txtExample.setText(example);
			else
				txtExample.setText(resource.getString("HelpDialog.noExample"));
			
		}	
	}
	
	private TreePath find(String command){
		@SuppressWarnings("unchecked")
		Enumeration<DefaultMutableTreeNode> e = originalRoot.depthFirstEnumeration();
		while(e.hasMoreElements()){
			DefaultMutableTreeNode n = e.nextElement();

			if(n.toString().equalsIgnoreCase(command)){
				return new TreePath(n.getPath());
			}
		}
		return null;
	}

	//*************************************************************************
	// FilteredTreeModel
	//*************************************************************************
	private class FilteredTreeModel extends DefaultTreeModel{
		private static final long serialVersionUID = 1L;

		public FilteredTreeModel(TreeNode root) {
			super(root);
		}


		@Override		
		public int getChildCount(Object parent) {
			DefaultMutableTreeNode padre = (DefaultMutableTreeNode)parent;

			return padre.getChildCount();
		}
		
		public void insertNodeInto(MutableTreeNode newChild, DefaultMutableTreeNode parent, int index, boolean isCategory){
			super.insertNodeInto(newChild, parent, index);

			if(!isCategory){
				map.put(newChild.toString(), parent.getPath());
			}
		}
	}
}
