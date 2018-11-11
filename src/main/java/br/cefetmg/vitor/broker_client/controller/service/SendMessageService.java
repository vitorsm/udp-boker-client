package br.cefetmg.vitor.broker_client.controller.service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import br.cefetmg.vitor.udp_broker.Constants;
import br.cefetmg.vitor.udp_broker.models.message.Message;

public class SendMessageService {
	private InetAddress serverAddress;
	private int serverPort;
	
	public SendMessageService() throws IOException {
		serverPort = Constants.SERVER_PORT;
		serverAddress = InetAddress.getByName("255.255.255.255");
	}
	
	public SendMessageService(String ipAddress) throws IOException {
		serverPort = Constants.SERVER_PORT;
		serverAddress = InetAddress.getByName(ipAddress);
	}
	
	public void setAddress(InetAddress serverAddress) {
		this.serverAddress = serverAddress;
	}
	
	public long sendMessage(Message message) throws IOException {
		
		byte[] messageBytes = message.getBytes();
		
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(messageBytes, messageBytes.length, serverAddress, serverPort);
		
		socket.send(packet);
		
		return System.currentTimeMillis();
	}
	
}
