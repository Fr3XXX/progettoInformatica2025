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

		

		this.setLocationRelativeTo(null);// la finestra apparira al centro dello schermo
		this.setUndecorated(true);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setVisible(true);
		
		User utente = new User();
		ControllerUtente controllerUtente = new ControllerUtente(utente);
		
		
		ViewNegozio viewVestiti = new ViewNegozio(gamePanel, "/tiles/negozioVestiti.png", 10, 10, 64, 64);
		
		
		ViewNegozio viewLibreia= new ViewNegozio(gamePanel, "/tiles/negozioLibreria.png", 10, 200, 64, 64);
		
		
		ViewNegozio viewElettronica= new ViewNegozio(gamePanel, "/tiles/negozioElettronica.png", 10, 400, 64, 64);
		
		
		ViewNegozio viewGioielleria= new ViewNegozio(gamePanel, "/tiles/negozioGioielleria.png", 200, 10, 64, 64);
		
		
		ViewNegozio viewConcessionario= new ViewNegozio(gamePanel, "/tiles/negozioConcessionario.png", 200, 200, 64, 64);
		
		
		ViewNegozio viewGameStop= new ViewNegozio(gamePanel, "/tiles/negozioGameStop.png", 200, 400, 64, 64);
		
		
		//sono di prova per avere un negozio su cui fare la view, poi verrannno tolti e aggiunta condizione per comprarli
		controllerUtente.acquistaNegozio(0, new Gioielleria(gamePanel, viewGioielleria));
		Gioielleria gioielleria = (Gioielleria) utente.negoziPosseduti.get(0);
		
		
		//parte gioco
		gamePanel.startGameThread();
		
		
	}
}
