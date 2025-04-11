package modelPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import controlPackage.ControllerNegozio;
import viewPackage.GamePanel;

public class Concessionario extends Negozio{

	
	
public Concessionario(GamePanel gamePanel, int dimensioneScaffali, int dimensioneMagazzino,
			HashMap<String, Integer> livelliNegozio, double prezzoVendita, double prezzoAcquisto, double prezzoNegozio,
			int livelloNegozio, boolean aperto, int codaNegozio, int maxCoda, ControllerNegozio controller,
			ArrayList<Prodotto> prodottiScaffale, ArrayList<Prodotto> prodottiMagazzino, Prodotto[] prodottiEsistenti,
			Thread[] clienti, Semaphore servito, Semaphore mutex) {
		super(gamePanel, dimensioneScaffali, dimensioneMagazzino, livelliNegozio, prezzoVendita, prezzoAcquisto,
				prezzoNegozio, livelloNegozio, aperto, codaNegozio, maxCoda, controller, prodottiScaffale,
				prodottiMagazzino, prodottiEsistenti, clienti, servito, mutex);
		
		
	}

//	public Concessionario() {
//		super(3);
//		// TODO Auto-generated constructor stub
//	}

	@Override
	public void inserisciProdottiEsistenti() {
		// TODO Auto-generated method stub
		
	}

}
