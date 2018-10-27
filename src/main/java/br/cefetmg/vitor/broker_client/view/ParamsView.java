package br.cefetmg.vitor.broker_client.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import br.cefetmg.vitor.broker_client.view.clients.ClientsDataView;
import br.cefetmg.vitor.broker_client.view.sendMessage.MessageDataView;

public class ParamsView extends JPanel {
	
	private MessageDataView messageSendView;
	private ClientsDataView clientsDataView;
	
	private JButton btSendMessage;
	private JButton btCreateClients;
	
	public ParamsView() {
		
		setLayout(new BorderLayout());
		
		JPanel pMessageInputs = new JPanel();
		add(pMessageInputs, BorderLayout.NORTH);
		
		messageSendView = new MessageDataView();
		pMessageInputs.add(messageSendView);
		
		JPanel pClientsInputs = new JPanel();
		add(pClientsInputs, BorderLayout.CENTER);
		
		clientsDataView = new ClientsDataView();
		pClientsInputs.add(clientsDataView);
		
		JPanel pBtSendMessage = new JPanel();
		add(pBtSendMessage, BorderLayout.SOUTH);
		
		btSendMessage = new JButton("Enviar");
		pBtSendMessage.add(btSendMessage);
		
		btCreateClients = new JButton("Criar clientes e enviar");
		pBtSendMessage.add(btCreateClients);
		
	}
	
	public JButton getBtSendMessage() {
		return this.btSendMessage;
	}
	
	public JButton getBtCreateClients() {
		return this.btCreateClients;
	}
	
	public MessageDataView getMessageSendView() {
		return this.messageSendView;
	}
	
	public ClientsDataView getClientsDataView() {
		return this.clientsDataView;
	}
}
