package viewPackage;

import java.awt.Color;

import javax.swing.JFrame;

public class MyFrameNegozio extends JFrame{
	
	public MyFrameNegozio() {
		super("");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setResizable(false);// l'utente non puo modificare la grandezza del frame
		// window.pack();

		this.setLocationRelativeTo(null);// la finestra apparira al centro dello schermo
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setBackground(Color.RED);
		this.setVisible(false);
	}
}		
