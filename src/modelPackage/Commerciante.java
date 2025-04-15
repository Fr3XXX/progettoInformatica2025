package modelPackage;

public class Commerciante implements Runnable{
	
	public final int SOGLIA = 5;
	public Negozio negozio;
	public int numeroProdottiAcquisto;
	
	public Commerciante(Negozio negozio) {
		this.negozio = negozio;
		this.numeroProdottiAcquisto = 10;
	}
	
	@Override
	public void run() {
		while(true) {
			
			if(negozio.prodottiMagazzino.size() < SOGLIA) {
				if(negozio.prezzoAcquisto*numeroProdottiAcquisto < User.patrimonioUtente) {
					negozio.controller.acquistaProdottiMagazzino(numeroProdottiAcquisto);
					User.patrimonioUtente-=negozio.prezzoAcquisto*numeroProdottiAcquisto;
				}
			}
			
		}
		
	}

}
