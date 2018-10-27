package br.cefetmg.vitor.broker_client.view;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.cefetmg.vitor.broker_client.ViewConstants;
import br.cefetmg.vitor.broker_client.view.clients.ClientsDataView;
import br.cefetmg.vitor.broker_client.view.sendMessage.MessageDataView;
import br.com.intcode.graphsupport.screenBounds.ScreenBounds;

public class MainScreen extends JFrame {

	private JPanel contentPanel;
	private ParamsView sendMessageView;
	private MessageView messageView;
	
	public MainScreen() {
		
		ScreenBounds sb = new ScreenBounds(ViewConstants.SCREEN_WIDTH, ViewConstants.SCREEN_HEIGHT);
		setBounds(sb.getX(), sb.getY(), sb.getWidth(), sb.getHeight());
		setTitle("UDP Broker Client");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		contentPanel = new JPanel();
		contentPanel.setLayout(new BorderLayout());
		setContentPane(contentPanel);
		
		sendMessageView = new ParamsView();
		
		JPanel pNorth = new JPanel();
		pNorth.add(sendMessageView);
		contentPanel.add(pNorth, BorderLayout.NORTH);
		
		JPanel pMessageView = new JPanel();
		contentPanel.add(pMessageView, BorderLayout.SOUTH);
		
		messageView = new MessageView();
		pMessageView.add(messageView);
		
	}
	
	public MessageView getMessageView() {
		return this.messageView;
	}
	
	public JButton getBtSendMessage() {
		return this.sendMessageView.getBtSendMessage();
	}
	
	public JButton getBtCreateClients() {
		return this.sendMessageView.getBtCreateClients();
	}
	
	public MessageDataView getMessageSendView() {
		return this.sendMessageView.getMessageSendView();
	}
	
	public ClientsDataView getClientsDataView() {
		return this.sendMessageView.getClientsDataView();
	}
}
