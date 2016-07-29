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
 * Connection.java
 *
 * Description:	    <Descripcion>
 * @author			Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 * Created on 28 de jul. de 2016, 10:28:41 a. m. 
 */

package ar.com.dcbarrientos.jmysqlgui.ui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;

import ar.com.dcbarrientos.jmysqlgui.database.CConnection;

/**
 * @author Diego Barrientos <dc_barrientos@yahoo.com.ar>
 *
 */
public class Connection extends JDialog{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    // Variables declaration - do not modify                     
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnConnect;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblComment;
    private javax.swing.JLabel lblDatabase;
    private javax.swing.JLabel lblHost;
    private javax.swing.JLabel lblPass;
    private javax.swing.JLabel lblPort;
    private javax.swing.JLabel lblUser;
    private javax.swing.JTextField txtDatabase;
    private javax.swing.JTextField txtHost;
    private javax.swing.JPasswordField txtPass;
    private javax.swing.JTextField txtPort;
    private javax.swing.JTextField txtUser;
    // End of variables declaration                   
	
    Principal principal;
    ResourceBundle resource;
    CConnection cconnection;
    
	public Connection(Principal principal, boolean modal){
		super(principal, modal);
		this.principal = principal;
		this.resource = principal.getResource();
		
		initComponents();
	}
	
	/**
	 * Create the dialog.
	 */
	private void initComponents() {
		cconnection = new CConnection();
		
        jPanel1 = new JPanel();
        lblHost = new JLabel();
        txtHost = new JTextField();
        txtUser = new JTextField();
        txtPort = new JTextField();
        txtPass = new JPasswordField();
        txtDatabase = new JTextField();
        lblUser = new JLabel();
        lblPort = new JLabel();
        lblPass = new JLabel();
        lblDatabase = new JLabel();
        lblComment = new JLabel();
        btnConnect = new JButton();
        btnConnect.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        		btnConnectMouseClicked(e);
        	}
        });
        btnCancel = new JButton();
        btnCancel.addMouseListener(new MouseAdapter() {
        	@Override
        	public void mouseClicked(MouseEvent e) {
        	}
        });
        
        setTitle(resource.getString("Connection.title"));
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblHost.setText(resource.getString("Connection.host"));
        txtHost.setText("localhost");

        lblUser.setText(resource.getString("Connection.user"));
        txtUser.setText("root");

        lblPort.setText(resource.getString("Connection.port"));
        txtPort.setText("3306");

        lblPass.setText(resource.getString("Connection.pass"));
        txtPass.setText("arrelins");

        lblDatabase.setText(resource.getString("Connection.database"));
        txtDatabase.setText("");

        lblComment.setText(resource.getString("Connection.comment"));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1Layout.setHorizontalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        				.addGroup(jPanel1Layout.createSequentialGroup()
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblPort)
        						.addComponent(lblUser)
        						.addComponent(lblHost)
        						.addComponent(lblPass)
        						.addComponent(lblDatabase))
        					.addGap(36)
        					.addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(txtHost, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        						.addComponent(txtUser, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        						.addComponent(txtDatabase)
        						.addComponent(txtPass)
        						.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(lblComment, GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE))
        			.addGap(18))
        );
        jPanel1Layout.setVerticalGroup(
        	jPanel1Layout.createParallelGroup(Alignment.LEADING)
        		.addGroup(jPanel1Layout.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblHost)
        				.addComponent(txtHost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblUser)
        				.addComponent(txtUser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblPort)
        				.addComponent(txtPort, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(txtPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblPass))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblDatabase)
        				.addComponent(txtDatabase, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(lblComment)
        			.addContainerGap())
        );
        jPanel1.setLayout(jPanel1Layout);

        btnConnect.setText(resource.getString("Connection.btnConectar"));
        btnConnect.addMouseListener(new MouseAdapter(){
        	public void mouseClicked(MouseEvent e){
        		btnConnectMouseClicked(e);
        	}
        });

        btnCancel.setText(resource.getString("Connection.btnCancelar"));
        btnCancel.addMouseListener(new MouseAdapter() {
        	public void mouseClicked(MouseEvent e){
        		btnCancelMouseClicked(e);
        	}
		});

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnConnect)
                        .addGap(5, 5, 5)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnConnect)
                    .addComponent(btnCancel))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(principal);
	}
	
	public CConnection showDialog(){
		setVisible(true);
		return cconnection;
	}

	public CConnection getCConnection(){
		return cconnection;
	}

	private void btnCancelMouseClicked(MouseEvent e){
		setVisible(false);
		dispose();
	}
	
	private void btnConnectMouseClicked(MouseEvent e){
		if(cconnection.conectar(txtHost.getText(), txtUser.getText(), new String(txtPass.getPassword()), Integer.parseInt(txtPort.getText()), txtDatabase.getText())){
			setVisible(false);
			dispose();
		}
	}
}
