package ar.com.dcbarrientos.jmysqlgui.ui;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JCheckBox;

public class PanelOptions extends JPanel{
	public PanelOptions() {
		initComponents();
	}
	private void initComponents() {
		
		panelGeneralOptions = new JPanel();
		panelGeneralOptions.setBorder(new TitledBorder(null, " General Options ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel = new JPanel();
		panel.setBorder(new TitledBorder(null, " Row Options ", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
						.addComponent(panelGeneralOptions, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelGeneralOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(166, Short.MAX_VALUE))
		);
		
		comboBox_1 = new JComboBox();
		
		lblNewLabel = new JLabel("New label");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(60)
					.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(155, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(comboBox_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(47, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		lblPackKeys = new JLabel("Pack keys:");
		
		lblTablePassword = new JLabel("Table password:");
		
		comboBox = new JComboBox();
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		lblAutoIncrement = new JLabel("Auto increment:");
		
		chckbxDelayKeyUpdates = new JCheckBox("Delay key updates");
		GroupLayout gl_panelGeneralOptions = new GroupLayout(panelGeneralOptions);
		gl_panelGeneralOptions.setHorizontalGroup(
			gl_panelGeneralOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGeneralOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGeneralOptions.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPackKeys)
						.addComponent(lblTablePassword)
						.addComponent(lblAutoIncrement))
					.addGap(22)
					.addGroup(gl_panelGeneralOptions.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxDelayKeyUpdates)
						.addGroup(gl_panelGeneralOptions.createParallelGroup(Alignment.LEADING, false)
							.addComponent(textField_1, Alignment.TRAILING)
							.addComponent(textField)
							.addComponent(comboBox, 0, 237, Short.MAX_VALUE)))
					.addContainerGap(155, Short.MAX_VALUE))
		);
		gl_panelGeneralOptions.setVerticalGroup(
			gl_panelGeneralOptions.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelGeneralOptions.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelGeneralOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblPackKeys)
						.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelGeneralOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTablePassword)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelGeneralOptions.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblAutoIncrement))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(chckbxDelayKeyUpdates)
					.addContainerGap(9, Short.MAX_VALUE))
		);
		panelGeneralOptions.setLayout(gl_panelGeneralOptions);
		setLayout(groupLayout);
	}
	private static final long serialVersionUID = 1L;
	private JPanel panelGeneralOptions;
	private JLabel lblPackKeys;
	private JLabel lblTablePassword;
	private JComboBox comboBox;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblAutoIncrement;
	private JCheckBox chckbxDelayKeyUpdates;
	private JPanel panel;
	private JComboBox comboBox_1;
	private JLabel lblNewLabel;
}
