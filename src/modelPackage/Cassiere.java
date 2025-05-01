package modelPackage;

import viewPackage.GamePanel;

public class Cassiere extends GameObject{

	Negozio negozio;
	public int indexVendita;
	public String vendita;
	public int checkpoint = 0;
	
	public Cassiere(Negozio negozio, GamePanel gamePanel) {
		super(gamePanel);
		this.negozio = negozio;
	}
	
	//Dipendente che vende i prodotti
	@Override
	public void update() {
		while(true) {
			
			
				Thread.sleep(1000);
				negozio.servito = true;//pronto per servire un cliente
				
				if(negozio.servito2) {//aspetta che il cliente scelga il prodotto		
					if(negozio.controller.cercaProdotto(indexVendita, vendita)) {
						negozio.daVendere = true;
					}
					else {
						negozio.daVendere = false;
					}
					negozio.trovato=true;
				
				}
			
		}
		
	}
}
