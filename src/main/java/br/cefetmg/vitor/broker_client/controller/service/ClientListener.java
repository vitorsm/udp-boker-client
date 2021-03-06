package br.cefetmg.vitor.broker_client.controller.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.Charset;

import javax.swing.JOptionPane;

import br.cefetmg.vitor.broker_client.controller.Controller;
import br.cefetmg.vitor.udp_broker.Constants;
import br.cefetmg.vitor.udp_broker.models.message.Message;
import br.cefetmg.vitor.udp_broker.models.message.MessageType;
import br.cefetmg.vitor.udp_broker.models.message.body.MessageBodyPublish;
import br.cefetmg.vitor.udp_broker.utils.MessageUtils;

public class ClientListener implements Runnable {
	
	private DatagramSocket socket;
	private boolean running;
	private byte[] buffer;
	
	private Controller controller;
	
	public ClientListener(Controller controller, int port) throws SocketException {
		this.controller = controller;
		
		socket = new DatagramSocket(port);
		running = true;
		buffer = new byte[Constants.MESSAGE_LENGTH];
	}
	
	
	@Override
	public void run() {
		while (running) {
			try {
				DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
				
				socket.receive(packet);
				controller.receivedMessage();
				
//				JOptionPane.showMessageDialog(null, "Mensagem recebida:");
//				//teste
//				if (packet.getData() != null) {
//					String msg = new String(packet.getData(), Charset.forName("utf-8"));
//					JOptionPane.showMessageDialog(null, msg);
//				}
				
				proccessMessage(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void proccessMessage(DatagramPacket packet) {
		
		Message message = MessageUtils.convertToMessage(packet.getData());
		
		if (message.getMessageHeader().getMessageType() == MessageType.DATA) {
			proccessData(message, packet);
		} else if (message.getMessageHeader().getMessageType() == MessageType.UPDATE_PARAM) {
			proccessUpdateParam(message, packet);
		}
		
	}
	
	private void proccessData(Message message, DatagramPacket packet) {
		message.convertMessageBodyToPublishMessageBody();
		MessageBodyPublish messageBody = (MessageBodyPublish) message.getMessageBody();
		System.out.println("content: " + messageBody.getMessageContent());
		System.out.println("topic: " + messageBody.getTopic().getValue());
		controller.setMessageData(messageBody, packet.getAddress().getHostAddress());
	}
	
	private void proccessUpdateParam(Message message, DatagramPacket packet) {
		
		InetAddress address = packet.getAddress();
		controller.setAddress(address);
		controller.setMessageHelloResponse(address.toString());
		
	}
	
}
