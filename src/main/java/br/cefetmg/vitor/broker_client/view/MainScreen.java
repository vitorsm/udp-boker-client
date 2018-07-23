package br.cefetmg.vitor.broker_client.view;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import br.cefetmg.vitor.broker_client.ViewConstants;
import br.com.intcode.graphsupport.screenBounds.ScreenBounds;

public class MainScreen extends JFrame {

	private JPanel contentPanel;
	private MessageSendView messageSendView;
	private MessageView messageView;
	private JButton btSendMessage;
	
	public MainScreen() {
		
		ScreenBounds sb = new ScreenBounds(ViewConstants.SCREEN_WIDTH, ViewConstants.SCREEN_HEIGHT);
		setBounds(sb.getX(), sb.getY(), sb.getWidth(), sb.getHeight());
		setTitle("UDP Broker Client");
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
		contentPanel = new JPanel();
		setContentPane(contentPanel);
		
		contentPanel.setLayout(new BorderLayout());
		
		JPanel pInput = new JPanel();
		contentPanel.add(pInput, BorderLayout.NORTH);
		
		messageSendView = new MessageSendView();
		pInput.add(messageSendView);
		
		JPanel pBtSendMessage = new JPanel();
		contentPanel.add(pBtSendMessage, BorderLayout.CENTER);
		
		btSendMessage = new JButton("Enviar");
		pBtSendMessage.add(btSendMessage);
		
		
		JPanel pMessageView = new JPanel();
		contentPanel.add(pMessageView, BorderLayout.SOUTH);
		
		messageView = new MessageView();
		pMessageView.add(messageView);
		
	}
	
	public MessageView getMessageView() {
		return this.messageView;
	}
	
	public JButton getBtSendMessage() {
		return this.btSendMessage;
	}
	
	public MessageSendView getMessageSendView() {
		return this.messageSendView;
	}
}
