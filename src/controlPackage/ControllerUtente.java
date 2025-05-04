package controlPackage;

import modelPackage.*;

public class ControllerUtente {

	public User utente;
	
	public ControllerUtente(User utente) {
		this.utente = utente;
	}
	

	public boolean acquistaNegozio(double prezzo, Negozio negozioAcquistare) {
		
		if(User.patrimonioUtente >= prezzo) {
			utente.negoziPosseduti.add(negozioAcquistare);
			User.patrimonioUtente-=prezzo;
			return true;
		}
		else {
			System.out.println("Non hai abbastanza soldi per comprare questo negozio");
			return false;
		}
		
	}

}


