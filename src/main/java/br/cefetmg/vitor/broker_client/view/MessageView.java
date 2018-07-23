package br.cefetmg.vitor.broker_client.view;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import br.cefetmg.vitor.broker_client.ViewConstants;

public class MessageView extends JPanel {

	private JTextArea textMessage;
	private final static String wrapLine = "\r\n\r\n\r\n";
	
	public MessageView() {
	
		JPanel pMessage = new JPanel();
		add(pMessage);
		
		textMessage = new JTextArea(ViewConstants.NUMBER_ROW_VIEW_MESSAGE, ViewConstants.NUMBER_COLUMN_VIEW_MESSAGE);
		textMessage.setEditable(false);
		
		JScrollPane scroll = new JScrollPane(textMessage);
		pMessage.add(scroll);
	}
	
	public void setMessage(String message) {
		
		String msg = textMessage.getText();
		
		if (msg.equals(""))
			textMessage.setText(message);
		else
			textMessage.setText(msg + wrapLine + message);
		
	}
}
