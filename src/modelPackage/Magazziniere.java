package modelPackage;

public class Magazziniere implements Runnable{

	public final int SOGLIA = 5;
	
	Negozio negozio;
	
	public Magazziniere(Negozio negozio) {
		this.negozio = negozio;
	}
	
	
	//Dipendente che quando il numero di prodotti negli scaffali va al di sotto di una certa soglia, sposta i prodotti dal magazzino agli scaffali
	@Override
	public void run() {
		
		while(true) {
			if(negozio.prodottiScaffale.size() < SOGLIA) {
				
					try {
						negozio.dipendenti.acquire();
						negozio.controller.spostaProdottiMagazzinoScaffale();
						negozio.dipendenti.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					
			}
		}

	}

}
