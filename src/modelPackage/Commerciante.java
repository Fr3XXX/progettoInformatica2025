package modelPackage;

public class Commerciante implements Runnable{
	
	public final int SOGLIA = 5;
	public Negozio negozio;
	public int numeroProdottiAcquisto;
	
	public Commerciante(Negozio negozio) {
		this.negozio = negozio;
		this.numeroProdottiAcquisto = 10;
	}
	
	//Dipendente che compra i prodotti per il negozio quando i prodotti in magazzino vanno sotto una certa soglia
	@Override
	public void run() {
		while(true) {
			
			if(negozio.prodottiMagazzino.size() < SOGLIA) {
				try {
					negozio.dipendenti.acquire();
					if(negozio.prezzoAcquisto*numeroProdottiAcquisto < User.patrimonioUtente) {
						negozio.controller.acquistaProdottiMagazzino(numeroProdottiAcquisto);
						User.patrimonioUtente-=negozio.prezzoAcquisto*numeroProdottiAcquisto;
					}
					negozio.dipendenti.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}

}
