package br.cefetmg.vitor.broker_client.view;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class InputView extends JPanel {

	private String strLabel;
	
	private JLabel label;
	private Component inputComponent;
	
	private int countPanel;
	
	public InputView(String strLabel, Component inputComponent) {
		
		this.strLabel = strLabel;
		this.inputComponent = inputComponent;
		
		instanceLabel();
		
		setLayout(new GridBagLayout());
		
		JPanel pLabel = new JPanel();
		addPanelRow(pLabel);
		
		pLabel.add(label);
		
		JPanel pInput = new JPanel();
		addPanelRow(pInput);
		
		pInput.add(inputComponent);
	}
	
	private void instanceLabel() {
		
		label = new JLabel(strLabel);
		
	}
	
	private void addPanelRow(JPanel panel) {
		
		GridBagConstraints constraints = new GridBagConstraints();
		constraints.gridx = 0;
		constraints.gridy = countPanel++;
		
		this.add(panel, constraints);
	}
}
