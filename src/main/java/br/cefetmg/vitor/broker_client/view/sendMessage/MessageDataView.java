package br.cefetmg.vitor.broker_client.view.sendMessage;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;

import br.cefetmg.vitor.broker_client.ViewConstants;
import br.cefetmg.vitor.broker_client.view.InputView;
import br.cefetmg.vitor.udp_broker.models.message.MessageType;

public class MessageDataView extends JPanel {

	private JComboBox<MessageType> boxMessageType;
	private JTextField textToken;
	private JTextField textTopic;
	private JTextField textMessage;
	
	private JTextField textId;
	private JTextField textPassword;
	
	private InputView inputMessageType;
	private InputView inputToken;
	private InputView inputTopic;
	private InputView inputMessage;
	private InputView inputId;
	private InputView inputPassword;
	
	public MessageDataView() {
	
		setLayout(new BorderLayout());
		
		JPanel pCenter = new JPanel();
		add(pCenter, BorderLayout.CENTER);
		
		boxMessageType = new JComboBox<MessageType>();
		inputMessageType = new InputView("Tipo de mensagem", boxMessageType);
		pCenter.add(inputMessageType);

		textToken = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputToken = new InputView("Token", textToken);
		pCenter.add(inputToken);
		
		textTopic = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputTopic = new InputView("Tópico", textTopic);
		pCenter.add(inputTopic);
		
		textMessage = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_L);
		inputMessage = new InputView("Mensagem", textMessage);
		pCenter.add(inputMessage);
		
		textId = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputId = new InputView("ID", textId);
		pCenter.add(inputId);
		
		textPassword = new JTextField(ViewConstants.NUMBER_COLUMN_TEXT_M);
		inputPassword = new InputView("Senha", textPassword);
		pCenter.add(inputPassword);
		
		setMessageTypes();
		verifyVisibilityByMessageType();
		
		boxMessageType.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				verifyVisibilityByMessageType();
			}
		});
	}
	
	private void verifyVisibilityByMessageType() {
		MessageType selectedMessageType = getMessageType();
		
		setVisibleInput(selectedMessageType == MessageType.HELLO);
	}
	
	private void setVisibleInput(boolean hello) {
		inputId.setVisible(hello);
		inputPassword.setVisible(hello);
		
		inputToken.setVisible(!hello);
		inputTopic.setVisible(!hello);
		inputMessage.setVisible(!hello);
	}
	
	private void setMessageTypes() {
		
		boxMessageType.removeAllItems();
		for (MessageType messageType : MessageType.values()) {
			boxMessageType.addItem(messageType);
		}
		
	}
	
	public String getToken() {
		String token = textToken.getText();
		
		if (token != null)
			token = token.trim();
		
		return token;
	}
	
	public String getTopic() {
		String topic = textTopic.getText();
		
		if (topic != null)
			topic = topic.trim();
		
		return topic;
	}
	
	public String getId() {
		String id = textId.getText();
		
		if (id != null)
			id = id.trim();
		
		return id;
	}
	
	public String getPassword() {
		String password = textPassword.getText();
		
		if (password != null)
			password = password.trim();
		
		return password;
	}
	
	public MessageType getMessageType() {
		
		return boxMessageType.getItemAt(boxMessageType.getSelectedIndex());
	}
	
	public String getMessage() {
		String message = textMessage.getText();
		
		if (message != null)
			message = message.trim();
		
		return message;
	}
}
