package controlPackage;

import modelPackage.*;

public class Controller {

	public Negozio negozio;
	
	public Controller(Negozio negozio) {
		this.negozio = negozio;
	}
	
	public void acquistaProdottiMagazzino(double patrimonio, int nProdotti) {
			
		if(patrimonio >= nProdotti*negozio.prezzoAcquisto) {			
			if((50 - negozio.prodottiMagazzino.size()) >= nProdotti) {
				for(int i=0; i<nProdotti; i++) {
					patrimonio-=negozio.prezzoAcquisto;			
							negozio.prodottiMagazzino.add(negozio.prodottiEsistenti[(int) Math.random()*49]);
				}	
			}
			else {
				System.out.println("Non c'è spazio in magazzino per comprare questi prodotti");
			}
		}
		else {
			System.out.println("Non hai abbastanza soldi per comprare tutti questi prodotti");
		}
			
	}
			
	//vendita e rimozione dallo scaffale di un prodotto
	public void vendiProdotto(double patrimonio, Prodotto prodotto) {
			
		boolean venduto=false;
		
		for(int i=0; i<negozio.prodottiScaffale.size() && venduto==false; i++) {	
			if(negozio.prodottiScaffale.equals(prodotto)) {
				negozio.prodottiScaffale.remove(i);
				patrimonio+=negozio.prezzoVendita;
				System.out.println("prodotto con nome " + prodotto.nome + " è stato venduto");
				venduto = true;
			}		
		}
		
		if(!venduto) {
			System.out.println("Il prodotto non è presente negli scaffali");
		}
		
	}
	
	//metodo per aumentare la dimensione del magazzino
	public void upgradeDimensioneMagazzino(double costoUpgrade, double patrimonio) {
		
		if(costoUpgrade>patrimonio) {
			System.out.println("Non hai abbastanza soldi per questo upgrade");
		}
		else {
			patrimonio-=costoUpgrade;
			if(negozio.dimensioneMagazzino % 2 == 0) {
				negozio.dimensioneMagazzino*=1.5;
			}
			else {
				negozio.dimensioneMagazzino*=2;//se il numero è dispari moltiplico per 2 per evitare che vengano numeri con la virgola
			}
			
			//aggiorno i valori dell'HashMap dei livelli
			negozio.livelliNegozio.replace("Magazzino", (negozio.livelliNegozio.get("Magazzino") + 1));
			
			if((negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali")) % 2 == 0) {
				negozio.livelliNegozio.replace("Totale", (negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali")) / 2);
			}
			else {
				negozio.livelliNegozio.replace("Totale", (negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali") - 1) / 2);
			}
		}
		
	}
		
	//metodo per aumentare la dimensione degli scaffali
	public void upgradeDimensioneScaffali(double costoUpgrade, double patrimonio) {
		
		if(costoUpgrade>patrimonio) {
			System.out.println("Non hai abbastanza soldi per questo upgrade");
		}
		else {
			patrimonio-=costoUpgrade;
			if(negozio.dimensioneScaffali % 2 == 0) {
				negozio.dimensioneScaffali*=1.5;			}
			else {
				negozio.dimensioneScaffali*=2;//se il numero è dispari moltiplico per 2 per evitare che vengano numeri con la virgola
			}
			
			//aggiorno i valori dell'HashMap dei livelli
			negozio.livelliNegozio.replace("Scaffali", (negozio.livelliNegozio.get("Scaffali") + 1));
			
			if((negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali")) % 2 == 0) {
				negozio.livelliNegozio.replace("Totale", (negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali")) / 2);
			}
			else {
				negozio.livelliNegozio.replace("Totale", (negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali") - 1) / 2);
			}
		}
		
	}
	
	
}
