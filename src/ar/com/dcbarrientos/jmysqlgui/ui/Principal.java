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
 * Principal.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 27 de jul. de 2016, 11:12:36 p. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class Principal extends JFrame {
	private ResourceBundle resource;
	private CConnection cconnection;
	private boolean isConnected;
	
	//JDesktopPane desktop;
	JPanel desktop;
	private JMenuBar menuPrincipal;
	private JMenu jMenuFile;
	private JMenuItem jMenuFileConnection;
	private JMenuItem jMenuFileClose;
	private JSeparator jMenuFileSeparator1;
	private JMenuItem jMenuFileExit;
	private JMenu jMenuOpciones;

	private JMenu jMenuHelp;
	private JMenuItem jMenuHelpAboutMySqlFront;

	private JMenu jMenuTools;
	private JMenuItem jMenuToolsRefresh;
	private JSeparator jMenuToolsSeparator1;
	private JMenuItem jMenuToolsCreateDatabase;
	private JMenuItem jMenuToolsCreateTable;
	private JMenuItem jMenuToolsDropDatabase;
	private JMenuItem jMenuToolsDropTable;
	private JSeparator jMenuToolsSeparator2;
	private JMenu jMenuToolsFlush;
	private JSeparator jMenuToolsSeparator3;
	private JMenuItem jMenuToolsUserManager;
	private JMenuItem jMenuToolsTableDiagnostic;
	private JMenuItem jMenuToolsFlushHosts;
	private JMenuItem jMenuToolsFlushLogs;
	private JMenuItem jMenuToolsFlushPrivileges;
	private JMenuItem jMenuToolsFlushTables;
	private JMenuItem jMenuToolsFlushTablesReadLock;
	private JMenuItem jMenuToolsFlushStatus;
	
	private JMenu jMenuImport;
	private JMenuItem jMenuImportTextFile;
	private JMenuItem jMenuImportODBC;
	private JSeparator jMenuImportSeparator1;
	private JMenuItem jMenuImportExportTables;
	private JSeparator jMenuImportSeparator2;
	private JMenu jMenuImportCopyData;
	private JMenuItem jMenuImportCopyDataCSV;
	private JMenuItem jMenuImportCopyDataHTML;
	private JMenu jMenuImportSaveData;
	private JMenuItem jMenuImportSaveDataCSV;
	private JMenuItem jMenuImportSaveDataHTML;
	
	public ImageIcon addFieldIcon;
	public ImageIcon closeIcon;
	public ImageIcon connectionIcon;
	public ImageIcon createDatabaseIcon;
	public ImageIcon createTableIcon;
	public ImageIcon dataIcon;
	public ImageIcon databaseIcon;
	public ImageIcon database2Icon;
	public ImageIcon databaseServerIcon;
	public ImageIcon dropDatabaseIcon;
	public ImageIcon dropTableIcon;
	public ImageIcon exportTablesIcon;
	public ImageIcon funcionesIcon;
	public ImageIcon helpIcon;
	public ImageIcon printIcon;
	public ImageIcon queryIcon;
	public ImageIcon importTextIcon;
	public ImageIcon insertRecordIcon;
	public ImageIcon openIcon;
	public ImageIcon privilegesIcon;
	public ImageIcon refreshIcon;
	public ImageIcon runIcon;
	public ImageIcon tableIcon;
	public ImageIcon tableDiagnosticIcon;
	public ImageIcon userManagerIcon;
	public ImageIcon viewDataIcon;
	public ImageIcon copyCSVIcon;
	public ImageIcon copyHTMLIcon;
	public ImageIcon saveCSVIcon;
	public ImageIcon saveHTMLIcon;
	public ImageIcon copyTableIcon;
	public ImageIcon emptyTableIcon;
	public ImageIcon tablePropertiesIcon;
	public ImageIcon primerRecordIcon;
	public ImageIcon anteriorRecordIcon;
	public ImageIcon siguienteRecordIcon;
	public ImageIcon ultimoRecordIcon;
	public ImageIcon insertDataRecordIcon;
	public ImageIcon deleteRecordIcon;
	public ImageIcon editRecordIcon;
	public ImageIcon aceptarRecordIcon;
	public ImageIcon cancelarRecordIcon;
	public ImageIcon refreshDataIcon;
	public ImageIcon dropFieldIcon;
	public ImageIcon campoPrimarioIcon;
	public ImageIcon campoSecundarioIcon;
	public ImageIcon campoNormalIcon;
	public ImageIcon registroSiguienteIcon;
	public ImageIcon registroAnteriorIcon;
	public ImageIcon registroIndicesIcon;	

	private String currentLookAndFeel;
	private String currentLookAndFeelClass;
	
	private ButtonGroup lafMenuGroup;
	
	private JPopupMenu jPopupMenu1;
	private JMenuItem jMenuPrintList;
	private JMenuItem jMenuRefresh;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Principal(ResourceBundle resource){
		this.resource = resource;
		
		initComponents();
	}
	
	private void initComponents(){
		isConnected = false;
		loadImages();
		setTitle(resource.getString("App.Title")+ " " + resource.getString("App.Version"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH);
		setJMenuBar(getMenuPrincipal());
		updateEstadoConexion();
		
		desktop = new JPanel();
		desktop.setBackground(Color.RED);
		desktop.setLayout(new BorderLayout());
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(desktop, BorderLayout.CENTER);
		
		//desktop = new JDesktopPane();
		//desktop.setLayout(new BorderLayout());
		//setContentPane(desktop);
			
	}
	
	private JMenuBar getMenuPrincipal(){
		menuPrincipal = new JMenuBar();
		menuPrincipal.setVisible(true);

		jMenuOpciones = new JMenu();
		jMenuOpciones.setVisible(true);
		jMenuOpciones.setText("Opciones");

		jMenuOpciones.add(crearLafMenu());
		
//		themesMenu = crearThemeMenu();/		
//		jMenuOpciones.add(themesMenu);

		menuPrincipal.add(getMenuFile());
		menuPrincipal.add(getMenuTools());
		menuPrincipal.add(getMenuImport());
		menuPrincipal.add(jMenuOpciones);
		menuPrincipal.add(getMenuHelp());
				
		jMenuPrintList = new JMenuItem();
		jMenuPrintList.setVisible(true);
		jMenuPrintList.setText("Print List...");
		jMenuPrintList.setToolTipText("Print list");

		jMenuRefresh = new JMenuItem();
		jMenuRefresh.setVisible(true);
		jMenuRefresh.setText("Refresh");

		jPopupMenu1 = new JPopupMenu();
		jPopupMenu1.setLabel("jPopupMenu1");
		jPopupMenu1.add(jMenuPrintList);
		jPopupMenu1.add(jMenuRefresh);
					
		jMenuFileExit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){				
				salir();
			}
		});

		jMenuFileConnection.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//TODO nuevaConexion();
			}
		});
		jMenuFileClose.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//TODO cerrarConexion();
			}
		});
		jMenuToolsRefresh.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//TODO refresh();
			}
		});
		jMenuToolsCreateDatabase.addActionListener(new ActionListener()	{
			public void actionPerformed(ActionEvent e){
				//TODO nuevaDatabase();
			}
		});
		jMenuToolsCreateTable.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//TODO nuevaTabla();
			}
		});
		jMenuToolsDropDatabase.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//TODO borrarDatabase();
			}
		});
		
		return menuPrincipal;
	}

	private JMenu getMenuHelp(){
		jMenuHelpAboutMySqlFront = new JMenuItem();
		jMenuHelpAboutMySqlFront.setVisible(true);
		jMenuHelpAboutMySqlFront.setText("About MySQL IDE");
		
		jMenuHelp = new JMenu();
		jMenuHelp.setVisible(true);
		jMenuHelp.setText("Help");
		jMenuHelp.add(jMenuHelpAboutMySqlFront);
		
		return jMenuHelp;
	}
	
	private JMenu getMenuImport(){
		jMenuImportSeparator1 = new JSeparator();
		jMenuImportSeparator2 = new JSeparator();

		jMenuImportTextFile = new JMenuItem();
		jMenuImportTextFile.setVisible(true);
		jMenuImportTextFile.setText("Import Textfile");
		jMenuImportTextFile.setToolTipText("");
		jMenuImportTextFile.setIcon(importTextIcon);
		
		jMenuImportODBC = new JMenuItem();
		jMenuImportODBC.setVisible(true);
		jMenuImportODBC.setText("ODBC Import");
		jMenuImportODBC.setToolTipText("Import Tables through ODBC.");

		jMenuImportExportTables = new JMenuItem();
		jMenuImportExportTables.setVisible(true);
		jMenuImportExportTables.setText("Export Tables...");
		jMenuImportExportTables.setToolTipText("Export tables...");
		jMenuImportExportTables.setIcon(exportTablesIcon);
		
		jMenuImportCopyDataCSV = new JMenuItem();
		jMenuImportCopyDataCSV.setVisible(true);
		jMenuImportCopyDataCSV.setText("as CSV-Data");
		jMenuImportCopyDataCSV.setToolTipText("Copy data as CSV-Data");
		jMenuImportCopyDataCSV.setIcon(copyCSVIcon);

		jMenuImportCopyDataHTML = new JMenuItem();
		jMenuImportCopyDataHTML.setVisible(true);
		jMenuImportCopyDataHTML.setText("as HTML-Data");
		jMenuImportCopyDataHTML.setToolTipText("Copy data as HTML-Data");
		jMenuImportCopyDataHTML.setIcon(copyHTMLIcon);

		jMenuImportCopyData = new JMenu();
		jMenuImportCopyData.setVisible(true);
		jMenuImportCopyData.setText("Copy data");
		jMenuImportCopyData.setToolTipText("");
		jMenuImportCopyData.add(jMenuImportCopyDataCSV);
		jMenuImportCopyData.add(jMenuImportCopyDataHTML);

		jMenuImportSaveDataCSV = new JMenuItem();
		jMenuImportSaveDataCSV.setVisible(true);
		jMenuImportSaveDataCSV.setText("as CSV-Data");
		jMenuImportSaveDataCSV.setToolTipText("Save data as CSV-Data");
		jMenuImportSaveDataCSV.setIcon(saveCSVIcon);

		jMenuImportSaveDataHTML = new JMenuItem();
		jMenuImportSaveDataHTML.setVisible(true);
		jMenuImportSaveDataHTML.setText("as HTML-Data");
		jMenuImportSaveDataHTML.setToolTipText("Save data as HTML-Data");
		jMenuImportSaveDataHTML.setIcon(saveHTMLIcon);
		
		jMenuImportSaveData = new JMenu();
		jMenuImportSaveData.setVisible(true);
		jMenuImportSaveData.setText("Save data");
		jMenuImportSaveData.setToolTipText("");
		jMenuImportSaveData.add(jMenuImportSaveDataCSV);
		jMenuImportSaveData.add(jMenuImportSaveDataHTML);

		jMenuImport = new JMenu();
		jMenuImport.setVisible(true);
		jMenuImport.setText("Import/Export");
		jMenuImport.add(jMenuImportTextFile);
		jMenuImport.add(jMenuImportODBC);
		jMenuImport.add(jMenuImportSeparator1);
		jMenuImport.add(jMenuImportExportTables);
		jMenuImport.add(jMenuImportSeparator2);
		jMenuImport.add(jMenuImportCopyData);
		jMenuImport.add(jMenuImportSaveData);
		
		return jMenuImport;
	}
	
	private JMenu getMenuTools(){
		jMenuToolsSeparator1 = new JSeparator();
		jMenuToolsSeparator2 = new JSeparator();
		jMenuToolsSeparator3 = new JSeparator();

		jMenuTools = new JMenu();
		jMenuTools.setVisible(true);
		jMenuTools.setText("Tools");

		jMenuToolsRefresh = new JMenuItem();
		jMenuToolsRefresh.setVisible(true);
		jMenuToolsRefresh.setText("Refresh");
		jMenuToolsRefresh.setToolTipText("Refresh");
		jMenuToolsRefresh.setIcon(refreshIcon);
		
		jMenuToolsCreateDatabase = new JMenuItem();
		jMenuToolsCreateDatabase.setVisible(true);
		jMenuToolsCreateDatabase.setText("Create Database...");
		jMenuToolsCreateDatabase.setToolTipText("Create a new database");
		jMenuToolsCreateDatabase.setIcon(createDatabaseIcon);
		
		jMenuToolsCreateTable = new JMenuItem();
		jMenuToolsCreateTable.setVisible(true);
		jMenuToolsCreateTable.setText("Create Table...");
		jMenuToolsCreateTable.setToolTipText("Create a new table.");
		jMenuToolsCreateTable.setIcon(createTableIcon);
		
		jMenuToolsDropDatabase = new JMenuItem();
		jMenuToolsDropDatabase.setVisible(true);
		jMenuToolsDropDatabase.setText("Drop Database...");
		jMenuToolsDropDatabase.setToolTipText("Drop database.");
		jMenuToolsDropDatabase.setIcon(dropDatabaseIcon);
		
		jMenuToolsDropTable = new JMenuItem();
		jMenuToolsDropTable.setVisible(true);
		jMenuToolsDropTable.setText("Drop Table...");
		jMenuToolsDropTable.setToolTipText("Drop table.");
		jMenuToolsDropTable.setIcon(dropTableIcon);
		
		jMenuToolsFlushHosts = new JMenuItem();
		jMenuToolsFlushHosts.setVisible(true);
		jMenuToolsFlushHosts.setText("Hosts");
		jMenuToolsFlushHosts.setToolTipText("Flush hosts.");
		
		jMenuToolsFlushLogs = new JMenuItem();
		jMenuToolsFlushLogs.setVisible(true);
		jMenuToolsFlushLogs.setText("Logs");
		jMenuToolsFlushLogs.setToolTipText("Flush logs.");
		
		jMenuToolsFlushPrivileges = new JMenuItem();
		jMenuToolsFlushPrivileges.setVisible(true);
		jMenuToolsFlushPrivileges.setText("Privileges");
		jMenuToolsFlushPrivileges.setToolTipText("Flush priviliges");
		jMenuToolsFlushPrivileges.setIcon(privilegesIcon);
		
		jMenuToolsFlushTables = new JMenuItem();
		jMenuToolsFlushTables.setVisible(true);
		jMenuToolsFlushTables.setText("Tables");
		jMenuToolsFlushTables.setToolTipText("Flush tables");
		
		jMenuToolsFlushTablesReadLock = new JMenuItem();
		jMenuToolsFlushTablesReadLock.setVisible(true);
		jMenuToolsFlushTablesReadLock.setText("Tables with read lock");
		jMenuToolsFlushTablesReadLock.setToolTipText("Flush tables with read lock");
		
		jMenuToolsFlushStatus = new JMenuItem();
		jMenuToolsFlushStatus.setVisible(true);
		jMenuToolsFlushStatus.setText("Status");
		jMenuToolsFlushStatus.setToolTipText("Flush status.");

		jMenuToolsFlush = new JMenu();
		jMenuToolsFlush.setVisible(true);
		jMenuToolsFlush.setText("Flush");
		jMenuToolsFlush.add(jMenuToolsFlushHosts);
		jMenuToolsFlush.add(jMenuToolsFlushLogs);
		jMenuToolsFlush.add(jMenuToolsFlushPrivileges);
		jMenuToolsFlush.add(jMenuToolsFlushTables);
		jMenuToolsFlush.add(jMenuToolsFlushTablesReadLock);
		jMenuToolsFlush.add(jMenuToolsFlushStatus);
		
		jMenuToolsUserManager = new JMenuItem();
		jMenuToolsUserManager = new JMenuItem();
		jMenuToolsUserManager.setVisible(true);
		jMenuToolsUserManager.setText("User Manager");
		jMenuToolsUserManager.setToolTipText("UserManager");
		jMenuToolsUserManager.setIcon(userManagerIcon);
		
		jMenuToolsTableDiagnostic = new JMenuItem();
		jMenuToolsTableDiagnostic.setVisible(true);
		jMenuToolsTableDiagnostic.setText("Table Diagnostic");
		jMenuToolsTableDiagnostic.setToolTipText("Optimize, repair and analyse tables.");
		jMenuToolsTableDiagnostic.setIcon(tableDiagnosticIcon);

		jMenuTools.add(jMenuToolsRefresh);
		jMenuTools.add(jMenuToolsSeparator1);
		jMenuTools.add(jMenuToolsCreateDatabase);
		jMenuTools.add(jMenuToolsCreateTable);
		jMenuTools.add(jMenuToolsDropDatabase);
		jMenuTools.add(jMenuToolsDropTable);
		jMenuTools.add(jMenuToolsSeparator2);
		jMenuTools.add(jMenuToolsFlush);
		jMenuTools.add(jMenuToolsSeparator3);
		jMenuTools.add(jMenuToolsUserManager);
		jMenuTools.add(jMenuToolsTableDiagnostic);
		
		return jMenuTools;
	}
	
	private JMenu getMenuFile(){
		jMenuFileConnection = new JMenuItem();
		jMenuFileConnection.setVisible(true);
		jMenuFileConnection.setText("Connection");
		jMenuFileConnection.setToolTipText("Establish connection with MySql Host");
		jMenuFileConnection.setIcon(connectionIcon);
		//jMenuFileConnection.setIcon(new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Connection.GIF")));
		
		jMenuFileClose = new JMenuItem();
		jMenuFileClose.setVisible(true);
		jMenuFileClose.setText("Close");
		jMenuFileClose.setIcon(closeIcon);
		
		jMenuFileSeparator1 = new JSeparator();
		jMenuFileSeparator1.setVisible(true);
		
		jMenuFileExit = new JMenuItem();
		jMenuFileExit.setVisible(true);
		jMenuFileExit.setText("Exit");
		jMenuFileExit.setToolTipText("Exit application.");		
		jMenuFileExit.setMnemonic('x');

		jMenuFile = new JMenu();
		jMenuFile.setVisible(true);
		jMenuFile.setText("File");		

		jMenuFile.add(jMenuFileConnection);
		jMenuFile.add(jMenuFileClose);
		jMenuFile.add(jMenuFileSeparator1);
		jMenuFile.add(jMenuFileExit);		

		return jMenuFile;
	}
	
	public JMenu crearLafMenu(){
		// ***** create laf switcher menu 
		//JMenuItem mi;
		JRadioButtonMenuItem mi;
		lafMenuGroup = new ButtonGroup();

		JMenu lafMenu = (JMenu) menuPrincipal.add(new JMenu(resource.getString("LafMenu.laf_label")));
	    //lafMenu.setMnemonic(getMnemonic("LafMenu.laf_mnemonic"));
		lafMenu.getAccessibleContext().setAccessibleDescription(resource.getString("LafMenu.laf_accessible_description"));
		
		currentLookAndFeel = UIManager.getLookAndFeel().getName();
		for(UIManager.LookAndFeelInfo lafi : UIManager.getInstalledLookAndFeels()){
			mi = createLafMenuItem(lafi);
			if(currentLookAndFeel.equals(lafi.getName())){
				currentLookAndFeelClass = lafi.getClassName(); 
				mi.setSelected(true);
			}
			lafMenu.add(mi);
		}
		return lafMenu;
	}
	
	public JRadioButtonMenuItem createLafMenuItem(LookAndFeelInfo laf)
	{
		JRadioButtonMenuItem mi = new JRadioButtonMenuItem(laf.getName());
		lafMenuGroup.add(mi);
		mi.getAccessibleContext().setAccessibleDescription(laf.getName());
		mi.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				setLookAndFeel(laf);
			}
		});
		//mi.addActionListener(new ChangeLookAndFeelAction(this, laf));
		
		mi.setEnabled(isAvailableLookAndFeel(laf));
		return mi;
	}	
	
	/**
	 * Stores the current L&F, and calls updateLookAndFeel, below
	 */
	public void setLookAndFeel(LookAndFeelInfo laf){
		if(!currentLookAndFeel.equals(laf.getName())){	
			currentLookAndFeel = laf.getName();
			currentLookAndFeelClass = laf.getClassName();
			updateLookAndFeel();
		}
	}

	/**
	* A utility function that layers on top of the LookAndFeel's
	* isSupportedLookAndFeel() method. Returns true if the LookAndFeel
	* is supported. Returns false if the LookAndFeel is not supported
	* and/or if there is any kind of error checking if the LookAndFeel
	* is supported.
	*
	* The L&F menu will use this method to detemine whether the various
	* L&F options should be active or inactive.
	*
	*/
	protected boolean isAvailableLookAndFeel(LookAndFeelInfo laf){
		try{ 
			Class<?> lnfClass = Class.forName(laf.getClassName());
			LookAndFeel newLAF = (LookAndFeel)(lnfClass.newInstance());
			return newLAF.isSupportedLookAndFeel();
		}catch(Exception e){ 
			return false;
		}
	}

	/**
	 * Sets the current L&F on each demo module
	 */
	public void updateLookAndFeel(){
		try{	
			UIManager.setLookAndFeel(currentLookAndFeelClass);
			SwingUtilities.updateComponentTreeUI(this);
			/*TODO
			if(mdiAdmin != null){
				mdiAdmin.updateUI();
				mdiAdmin.updateIG();
			}*/
		}catch (Exception ex){	
			System.out.println("Failed loading L&F: " + currentLookAndFeel);
			System.out.println(ex);
		}
	}

	/**
	 * Cargo los iconos.
	 */
	void loadImages(){
		addFieldIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/AddField.gif"));
		closeIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Close.gif"));
		connectionIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Connection.gif"));
		createDatabaseIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/CreateDatabase.gif"));
		createTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/CreateTable.gif"));
		dataIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Table2.gif"));
		//databaseIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Database.gif"));
		//database2Icon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/database2.gif"));
		//databaseServerIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DatabaseServer.gif"));
		dropDatabaseIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DropDatabase.gif"));
		dropTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DropTable.gif"));
		exportTablesIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/ExportTables.gif"));
		funcionesIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Funciones.gif"));
		helpIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Help.gif"));
		printIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Print.gif"));
		//queryIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Query.gif"));
		importTextIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/ImportText.gif"));
		insertRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/InsertRecord.gif"));
		openIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Open.gif"));
		privilegesIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Privileges.gif"));
		refreshIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Refresh.gif"));
		runIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Run.gif"));
		//tableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/table.gif"));
		tableDiagnosticIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/TableDiagnostic.gif"));
		userManagerIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/UserManager.gif"));
		viewDataIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/ViewData.gif"));
		copyCSVIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/CopyCSV.gif"));
		copyHTMLIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/CopyHTML.gif"));
		saveCSVIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/SaveCSV.gif"));
		saveHTMLIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/SaveHTML.gif"));
		copyTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Copytable.gif"));
		emptyTableIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Emptytable.gif"));
		tablePropertiesIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/TableProperties.gif"));
		primerRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Primero.gif"));
		anteriorRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Anterior.gif"));
		siguienteRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Siguiente.gif"));
		ultimoRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/Ultimo.gif"));
		insertDataRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/InsertarRegistro.gif"));
		deleteRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/DeleteRecord.gif"));
		editRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/EditRecord.gif"));
		aceptarRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/AceptarEdit.gif"));
		cancelarRecordIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/CancelarEdit.gif"));
		refreshDataIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/RefreshData.gif"));
		dropFieldIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/dropField.gif"));
		campoPrimarioIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/campo_primario.gif"));
		campoSecundarioIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/campo_secundario.gif"));
		campoNormalIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/campo_normal.gif"));
		registroSiguienteIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/reg_siguiente.gif"));
		registroAnteriorIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/reg_anterior.gif"));
		registroIndicesIcon = new ImageIcon(Principal.class.getResource("/ar/com/dcbarrientos/jmysqlgui/images/indices.gif"));
	}
	
	private void salir(){
		setVisible(false);
		dispose();
	}
	
	public ResourceBundle getResource(){
		return resource;
	}
	
	/**
	 * Procedimiento que se llama cuando se hace una nueva connection.
	 * 
	 * @param cconnection
	 */
	public void setCConnection(CConnection cconnection){
		this.cconnection = cconnection;
		conectar();
	}
	
	public void conectar(){
		MdiAdmin admin = new MdiAdmin(resource, cconnection);
		admin.setConnection(cconnection);
		desktop.add(admin, BorderLayout.CENTER);
		//add(admin, BorderLayout.CENTER);
		isConnected = true;
		updateMenuBar();
		refresh();
	}
	
	private void refresh(){
		revalidate();
		repaint();
	}
	
	private void updateMenuBar(){
		jMenuFileConnection.setEnabled(!isConnected);
		jMenuFileClose.setEnabled(isConnected);
		jMenuImport.setEnabled(isConnected);
		jMenuTools.setEnabled(isConnected);
	}
	
	private void updateEstadoConexion(){
		updateMenuBar();
	}
}
