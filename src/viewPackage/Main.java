package viewPackage;

import javax.swing.JFrame;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame window = new JFrame();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);//l'utente non puo modificare la grandezza del frame
		window.setTitle("2d game");
		
		GamePanel gamePanel = new GamePanel();
		
		window.add(gamePanel);
		
		//window.pack();
		
		window.setLocationRelativeTo(null);//la finestra apparira al centro dello schermo
		window.setUndecorated(true);
		window.setExtendedState(JFrame.MAXIMIZED_BOTH);
		window.setVisible(true);
		
		gamePanel.startGameThread();
	}

}
