package modelPackage;

import viewPackage.GamePanel;
public class Commerciante extends GameObject{
	
	public final int SOGLIA = 5;
	public Negozio negozio;
	public int numeroProdottiAcquisto;
	public boolean finito = false;
	public int checkpoint = 0;
	
	public Commerciante(Negozio negozio, GamePanel gamePanel) {
		super(gamePanel);
		this.negozio = negozio;
		this.numeroProdottiAcquisto = 10;
	}
	
	//Dipendente che compra i prodotti per il negozio quando i prodotti in magazzino vanno sotto una certa soglia
	@Override
	public void update() {
		if(negozio.dipendenti) {
			if(checkpoint==0) {
				if(negozio.prodottiMagazzino.size() < SOGLIA) {
					checkpoint++;
				}
			}
			if(checkpoint==1) {
				negozio.dipendenti2 = false;
				if(negozio.prezzoAcquisto*numeroProdottiAcquisto < User.patrimonioUtente) {
					negozio.controller.acquistaProdottiMagazzino(numeroProdottiAcquisto);
					User.patrimonioUtente-=negozio.prezzoAcquisto*numeroProdottiAcquisto;
				}
				negozio.dipendenti2 = true;
			}
		
		}
			
	}

}
