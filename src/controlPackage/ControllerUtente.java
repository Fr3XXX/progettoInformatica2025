package controlPackage;

import modelPackage.*;

public class ControllerUtente {

	User utente;
	
	public ControllerUtente(User utente) {
		this.utente = utente;
	}
	

	public void acquistaNegozio(double prezzo, Negozio negozioAcquistare) {
		
		if(User.patrimonioUtente >= prezzo) {
			utente.negoziPosseduti.add(negozioAcquistare);
			User.patrimonioUtente-=prezzo;
		}
		else {
			System.out.println("Non hai abbastanza soldi per comprare questo negozio");
		}
		
	}

}


