package modelPackage;

import viewPackage.*;

public class Gioielleria extends Negozio{

	ViewGioielleria viewGioielleria;
	

	public Gioielleria(GamePanel gamePanel) {
		super(3, gamePanel);
		this.viewGioielleria = new ViewGioielleria(gamePanel, "/casa.png", 10, 10, 64, 64);
		
	}


	@Override
	public void inserisciProdottiEsistenti() {
		// TODO Auto-generated method stub
		
	}

}
