package br.cefetmg.vitor.broker_client.controller.service;

import java.io.IOException;

import br.cefetmg.vitor.broker_client.controller.Controller;
import br.cefetmg.vitor.udp_broker.models.message.Message;

public class ClientSendMessage implements Runnable {

	private int maxAmountMessage;
	private int time;
	private Message message;
	
	private long lastTimeSendMessage;
	private int countSentMessage;
	
	private Controller controller;
	private SendMessageService sendMessageService;
	
	private boolean active;
	public ClientSendMessage(Controller controller, Message message, String ipAddress, int time, int maxAmountMessage) throws IOException {
		this.controller = controller;
		this.message = message;
		this.time = time;
		this.maxAmountMessage = maxAmountMessage;
		
		this.sendMessageService = new SendMessageService(ipAddress);
	}

	public boolean isActive() {
		return active;
	}
	
	public int getCountSentMessage() {
		return this.countSentMessage;
	}
	
	@Override
	public void run() {
		active = true;
		
		while(countSentMessage < maxAmountMessage) {
			long currentTime = System.currentTimeMillis();
			
			if (currentTime - lastTimeSendMessage >= time) {
				lastTimeSendMessage = currentTime;
				countSentMessage++;
				
				try {
					this.sendMessageService.sendMessage(message);
					controller.updateSentMessage(this);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		active = false;
		
		controller.finishClient();
	}
}
