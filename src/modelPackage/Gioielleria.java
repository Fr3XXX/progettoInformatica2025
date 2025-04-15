package modelPackage;

import viewPackage.*;

public class Gioielleria extends Negozio{

	ViewGioielleria viewGioielleria;
	

	public Gioielleria(GamePanel gamePanel, ViewGioielleria viewGioielleria) {
		super(3, gamePanel);
		this.viewGioielleria = viewGioielleria;
		
	}


	@Override
	public void inserisciProdottiEsistenti() {
		// TODO Auto-generated method stub
		
	}

}
