package br.cefetmg.vitor.broker_client.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import br.cefetmg.vitor.broker_client.controller.service.ClientListener;
import br.cefetmg.vitor.broker_client.controller.service.ClientSendMessage;
import br.cefetmg.vitor.broker_client.controller.service.SendMessageService;
import br.cefetmg.vitor.broker_client.view.MainScreen;
import br.cefetmg.vitor.udp_broker.Constants;
import br.cefetmg.vitor.udp_broker.core.impl.Credentials;
import br.cefetmg.vitor.udp_broker.models.Topic;
import br.cefetmg.vitor.udp_broker.models.message.Message;
import br.cefetmg.vitor.udp_broker.models.message.MessageHeader;
import br.cefetmg.vitor.udp_broker.models.message.MessageType;
import br.cefetmg.vitor.udp_broker.models.message.body.MessageBodyHello;
import br.cefetmg.vitor.udp_broker.models.message.body.MessageBodyPublish;

public class Controller {

	private MainScreen screen;
	private ClientListener clientListener;
	private SendMessageService sendMessageToBroker;
	private String id;
	private List<ClientSendMessage> clients;
	private long timeMessageSent;
	private boolean showTimeReceivedMessage;
	
	public Controller() throws IOException {

		screen = new MainScreen();
		screen.setVisible(true);

		sendMessageToBroker = new SendMessageService();

//		clientListener = new ClientListener(this, Constants.SERVER_PORT);
		clientListener = new ClientListener(this, Constants.CLIENT_PORT);
		Thread thread = new Thread(clientListener);
		thread.start();

		screen.getBtSendMessage().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					showTimeReceivedMessage = screen.isShowTimeReceivedMessage();
					timeMessageSent = sendMessage();
				} catch(IOException ex) {
					JOptionPane.showMessageDialog(null, ex.getMessage());
				}
			}
		});
		
		screen.getBtCreateClients().addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					screen.getBtCreateClients().setEnabled(false);
					screen.getBtSendMessage().setEnabled(false);
					createClients();
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
					
					screen.getBtCreateClients().setEnabled(true);
					screen.getBtSendMessage().setEnabled(true);
				}
			}
		});
	}
	
	public void setAddress(InetAddress address) {
		sendMessageToBroker.setAddress(address);
	}
	
	public void createClients() throws IOException {
		Message message = buildMessage();
		
		if (message != null) {
			int clientsAmount = 0;
			int time = 0;
			int maxAmountMessage = 0;
			String ipAddress = screen.getClientsDataView().getTextIpAddress().getText().trim();
			
			try {
				clientsAmount = Integer.parseInt(screen.getClientsDataView().getTextClientsAmount().getText().trim());
			} catch (NumberFormatException ex) {
			}
			try {
				time = Integer.parseInt(screen.getClientsDataView().getTextTime().getText().trim());
			} catch (NumberFormatException ex) {
			}
			try {
				maxAmountMessage = Integer.parseInt(screen.getClientsDataView().getTextMaximumMessageAmount().getText().trim());
			} catch (NumberFormatException ex) {
			}
			
			if (clientsAmount <= 0)
				throw new IOException("É necessário informar uma quantidade de clientes maior que 0");
			
			if (time <= 0)
				throw new IOException("É necessário informar um intervalor de tempo maior que 0");
			
			if (maxAmountMessage <= 0)
				throw new IOException("É necessário informar uma quantidade de mensagens maior que 0");
			
			if (ipAddress.equals(""))
				throw new IOException("É necessário informar endereco de IP para q os clientes enviem msg");
			
			timeMessageSent = System.currentTimeMillis();
			
			clients = new ArrayList<ClientSendMessage>();
			for (int i = 0; i < clientsAmount; i++) {
				ClientSendMessage client = new ClientSendMessage(this, message, ipAddress, time, maxAmountMessage);
				clients.add(client);
				
				Thread t = new Thread(client);
				t.start();
			}
		}
	}
	
	public void finishClient() {
		System.out.println("terminou 1");
		for (ClientSendMessage client : clients) {
			if (client.isActive())
				return;
		}
		System.out.println("terminou tudo");
		screen.getBtCreateClients().setEnabled(true);
		screen.getBtSendMessage().setEnabled(true);
	}
	
	public void updateSentMessage(ClientSendMessage client) {
		
		String txt = "Cliente " + clients.indexOf(client) 
			+ ": " + client.getCountSentMessage();
		
		
		screen.getMessageView().setMessage(txt);
	}
	
	public long sendMessage() throws IOException {

		Message message = buildMessage();
		
		if (message != null) {
			return sendMessageToBroker.sendMessage(message);
		}
		
		return 0;
	}
	
	public Message buildMessage() {
		MessageHeader messageHeader = new MessageHeader();
		messageHeader.setMessageType(screen.getMessageSendView().getMessageType());
		
		Message message = null;
		if (messageHeader.getMessageType() == MessageType.HELLO) {
			message = buildHelloMessage(messageHeader);
		} else if (messageHeader.getMessageType() == MessageType.PUBLISH) {
			message = buildPublishMessage(messageHeader);
		}
		
		return message;
	}

	public Message buildPublishMessage(MessageHeader messageHeader) {
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

	public void receivedMessage() {
		if (showTimeReceivedMessage) {
			long time = System.currentTimeMillis() - timeMessageSent;
			
			JOptionPane.showMessageDialog(null, "Tempo de resposta: " + time + "ms");
		}
	}
}
