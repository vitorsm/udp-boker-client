package br.cefetmg.vitor.broker_client.view.clients;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JTextField;

import br.cefetmg.vitor.broker_client.ViewConstants;
import br.cefetmg.vitor.broker_client.view.InputView;

public class ClientsDataView extends JPanel {

	private JTextField textClientsAmount;
	private JTextField textTime;
	private JTextField textMaximumMesageAmount;
	private JTextField textIpAddress;
	
	private InputView inputClientsAmount;
	private InputView inputTime;
	private InputView inputMaximumMessageAmount;
	private InputView inputIpAddress;
	
	private JPanel pCenter;
	
	public ClientsDataView() {
		
		setLayout(new BorderLayout());
	
		pCenter = new JPanel();
		add(pCenter, BorderLayout.CENTER);
		
		textClientsAmount = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputClientsAmount = new InputView("Qtd clientes", textClientsAmount);
		pCenter.add(inputClientsAmount);
		
		textTime = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputTime = new InputView("Enviar msg a cada (ms)", textTime);
		pCenter.add(inputTime);
		
		textMaximumMesageAmount = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputMaximumMessageAmount = new InputView("Qtd max mensagens", textMaximumMesageAmount);
		pCenter.add(inputMaximumMessageAmount);
		
		textIpAddress = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputIpAddress = new InputView("Endeco ip para clientes enviar", textIpAddress);
		pCenter.add(inputIpAddress);
	}

	public JTextField getTextClientsAmount() {
		return textClientsAmount;
	}

	public JTextField getTextTime() {
		return textTime;
	}
	
	public JTextField getTextMaximumMessageAmount() {
		return textMaximumMesageAmount;
	}
	
	public JTextField getTextIpAddress() {
		return textIpAddress;
	}
}
