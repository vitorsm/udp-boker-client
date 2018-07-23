package br.cefetmg.vitor.broker_client;

import java.io.IOException;
import javax.swing.JOptionPane;
import br.cefetmg.vitor.broker_client.controller.Controller;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) {
		
		try {
			Controller controller = new Controller();
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			System.exit(-1);
		}
		
	}
}
