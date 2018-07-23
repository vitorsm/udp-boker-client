package br.cefetmg.vitor.broker_client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JOptionPane;

import br.cefetmg.vitor.broker_client.controller.service.ClientListener;
import br.cefetmg.vitor.broker_client.controller.service.SendMessageToBroker;
import br.cefetmg.vitor.broker_client.view.MainScreen;
import br.cefetmg.vitor.udp_broker.core.impl.Credentials;
import br.cefetmg.vitor.udp_broker.core.impl.SendUdpMessage;
import br.cefetmg.vitor.udp_broker.models.Topic;
import br.cefetmg.vitor.udp_broker.models.message.Message;
import br.cefetmg.vitor.udp_broker.models.message.MessageHeader;
import br.cefetmg.vitor.udp_broker.models.message.MessageType;
import br.cefetmg.vitor.udp_broker.models.message.body.MessageBodyHello;
import br.cefetmg.vitor.udp_broker.models.message.body.MessageBodyPublish;

public class Controller {

	private MainScreen screen;
	private ClientListener clientListener;
	private SendMessageToBroker sendMessageToBroker;
	private String id;
	
	public Controller() throws IOException {

		screen = new MainScreen();
		screen.setVisible(true);

		sendMessageToBroker = new SendMessageToBroker();

		clientListener = new ClientListener(this);
		Thread thread = new Thread(clientListener);
		thread.start();

		screen.getBtSendMessage().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					sendMessage();
				} catch(IOException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
	}
	
	public void setAddress(InetAddress address) {
		sendMessageToBroker.setAddress(address);
	}
	
	public void sendMessage() throws IOException {

		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setMessageType(screen.getMessageSendView().getMessageType());
		
		Message message = null;
		if (messageHeader.getMessageType() == MessageType.HELLO) {
			message = buildHelloMessage(messageHeader);
		} else if (messageHeader.getMessageType() == MessageType.PUBLISH) {
			message = buildDataMessage(messageHeader);
		}
		
		if (message != null) {
			sendMessageToBroker.sendMessage(message);
		}
	}

	public Message buildDataMessage(MessageHeader messageHeader) {
		messageHeader.setAccessToken(screen.getMessageSendView().getToken());

		Topic topic = new Topic();
		topic.setValue(screen.getMessageSendView().getTopic());

		String messageContent = screen.getMessageSendView().getMessage();

		MessageBodyPublish messageBody = new MessageBodyPublish();
		messageBody.setTopic(topic);
		messageBody.setMessageContent(messageContent);

		Message message = new Message();
		message.setMessageHeader(messageHeader);
		message.setMessageBody(messageBody);
		
		return message;
	}

	public Message buildHelloMessage(MessageHeader messageHeader) {
		messageHeader.setAccessToken("");
		this.id = screen.getMessageSendView().getId();
		
		Credentials credentials = new Credentials();
		credentials.setId(this.id);
		credentials.setPassword(screen.getMessageSendView().getPassword());

		MessageBodyHello messageBody = new MessageBodyHello();
		messageBody.setCredentials(credentials);

		Message message = new Message();
		message.setMessageHeader(messageHeader);
		message.setMessageBody(messageBody);
		
		return message;
	}

	public void setMessageData(MessageBodyPublish messageBody, String address) {

		String msgText = buildStringByMessageData(messageBody, address);
		screen.getMessageView().setMessage(msgText);

	}
	
	public void setMessageHelloResponse(String address) {
		
		String msgText = buildStringByMessageHello(address);
		screen.getMessageView().setMessage(msgText);
		
	}
	
	private String buildStringByMessageData(MessageBodyPublish messageBody, String address) {

		String strReturn = "";

		strReturn += "Tipo: Mensagem de dados\r\n";
		strReturn += "Topico: " + messageBody.getTopic().getValue() + "\r\n";
		strReturn += "Endereço origem: " + address + "\r\n";
		strReturn += messageBody.getMessageContent() + "\r\n";

		return strReturn;
	}
	
	private String buildStringByMessageHello(String address) {
		
		String strReturn = "";
		
		strReturn += "Tipo: Mensagem de resposta hello\r\n";
		strReturn += "Endereço origem: " + address + "\r\n";
		
		return strReturn;
	}
	
}
