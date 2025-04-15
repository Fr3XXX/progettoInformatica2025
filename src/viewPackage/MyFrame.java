package viewPackage;

import javax.swing.JFrame;
import modelPackage.*;
import controlPackage.*;


public class MyFrame extends JFrame{
	
	
	public MyFrame(String titolo) {
		super(titolo);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);// l'utente non puo modificare la grandezza del frame
		


		GamePanel gamePanel = new GamePanel(this);

		 this.add(gamePanel);

		// window.pack();

		this.setLocationRelativeTo(null);// la finestra apparira al centro dello schermo
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
		User utente = new User();
		ControllerUtente controllerUtente = new ControllerUtente(utente);
		
		//sono di prova per avere un negozio su cui fare la view, poi verrannno tolti e aggiunta condizione per comprarli
		controllerUtente.acquistaNegozio(0, new Gioielleria(gamePanel));
		Gioielleria gioielleria = (Gioielleria) utente.negoziPosseduti.get(0);
		
		
		//parte gioco
		gamePanel.startGameThread();
		
	}
}
