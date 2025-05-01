package controlPackage;

import modelPackage.*;
import viewPackage.*;

public class ControllerNegozio {

	public Negozio negozio;
	
	public ControllerNegozio(Negozio negozio) {
		this.negozio = negozio;
	}
	
	public void acquistaNegozio(double prezzo) {
		
	}
	
	public void aperturaNegozio() {
		if(!negozio.aperto) {
			negozio.aperto=true;
		}
		else {
			negozio.aperto=false;
		}
	}
	
	public void acquistaProdottiMagazzino(int nProdotti) {
			
		if(User.patrimonioUtente >= nProdotti*negozio.prezzoAcquisto) {			
			if((50 - negozio.prodottiMagazzino.size()) >= nProdotti) {
				for(int i=0; i<nProdotti; i++) {
					User.patrimonioUtente-=negozio.prezzoAcquisto;			
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
	public void vendiProdotto(Prodotto prodottoVendere) {
		if(prodottoVendere != null) {
			negozio.prodottiScaffale.remove(negozio.prodottiScaffale.indexOf(prodottoVendere));
			User.patrimonioUtente+=negozio.prezzoVendita;
			System.out.println("prodotto con nome " + prodottoVendere.nome + " è stato venduto");
		}
	}
	
	//metodo per aumentare la dimensione del magazzino
	public void upgradeDimensioneMagazzino(double costoUpgrade) {
		
		if(costoUpgrade>User.patrimonioUtente) {
			System.out.println("Non hai abbastanza soldi per questo upgrade");
		}
		else {
			User.patrimonioUtente-=costoUpgrade;
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
	public void upgradeDimensioneScaffali(double costoUpgrade) {
		
		if(costoUpgrade>User.patrimonioUtente) {
			System.out.println("Non hai abbastanza soldi per questo upgrade");
		}
		else {
			User.patrimonioUtente-=costoUpgrade;
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

	public void sceltaProdotto(int indexDomanda, String richiesta) {
		
		int nrRichiesta = (int)Math.random()*49;
		if(indexDomanda == 0) {
			richiesta = negozio.prodottiEsistenti[nrRichiesta].nome;
		}
		else {
			richiesta = negozio.prodottiEsistenti[nrRichiesta].specifica;
		}
		
		negozio.cassiere.vendita = richiesta;
		negozio.cassiere.indexVendita = indexDomanda;
	}
	
	public void acquistaDipendente(int prezzo, String nome) {
		//nome è il nome del dipendente che deve corrispondere con la chiave dell'hashmap
		if(prezzo>User.patrimonioUtente) {
			System.out.println("Non puoi permetterti di acquistare il dipendente");
		}
		else {

			switch (nome) {
			
				case "Magazziniere":
					negozio.magazziniere = new Magazziniere(negozio);
					User.patrimonioUtente-=User.patrimonioUtente-prezzo;
					break;
			
				case "Cassiere":
					negozio.cassiere = new Cassiere(negozio, negozio.getGamePanel());
					User.patrimonioUtente-=User.patrimonioUtente-prezzo;
					break;
				
				case "Commerciante":
					negozio.commerciante = new Commerciante(negozio);
					User.patrimonioUtente-=User.patrimonioUtente-prezzo;
					break;
					
				default:
					System.out.println("Errore nell'assunzione del dipendente");
			}
		}
		
	}
	
	public void setNumeroAcquistoCommerciante(int numero) {
		negozio.commerciante.numeroProdottiAcquisto = numero;
	}
	/*
	public void spostaProdottiMagazzinoScaffale(){
		
		for(int i=0; negozio.prodottiScaffale.size() < negozio.dimensioneScaffali && negozio.prodottiMagazzino.size() != 0; i++) {
			//problema causato da te "pizzardi" ;-)
			negozio.prodottiScaffale.add(negozio.prodottiMagazzino.getLast());
			negozio.prodottiMagazzino.removeLast();
			
			Thread.sleep(1000);
		}
	}
<<<<<<< HEAD
	*/
	
	public boolean cercaProdotto(int indexVendita, String richiesta) {
		
		switch(indexVendita) {
			case 0: 
				
				for(Prodotto prodotto : negozio.prodottiScaffale) {
					if(prodotto.nome.equals(richiesta)) {
						return true;
					}
					Thread.sleep(250);
				}
				
				break;
			
			case 1:
				
				for(Prodotto prodotto : negozio.prodottiScaffale) {
					if(prodotto.specifica.equals(richiesta)) {
						return true;
					}
					Thread.sleep(250);
				}
				
				break;
			
			default:
				System.out.println("ERRORE");
		}
		
		return false;
	}
	
	public Prodotto cercaProdottoVendere(int indexVendita, String richiesta) {
		switch(indexVendita) {
		case 0: 
			
			for(Prodotto prodotto : negozio.prodottiScaffale) {
				if(prodotto.nome.equals(richiesta)) {
					return prodotto;
				}
				Thread.sleep(250);
			}
			
			break;
		
		case 1:
			
			for(Prodotto prodotto : negozio.prodottiScaffale) {
				if(prodotto.specifica.equals(richiesta)) {
					return prodotto;
				}
				Thread.sleep(250);
			}
			
			break;
		
		default:
			System.out.println("ERRORE");
		}
		return null; 
	}
	
	public boolean isServendo() {
		for(int i=0; i < negozio.clienti.length; i++) {
			if(negozio.clienti[i].servendo) {
				return true;
			}
		}
		return false;
	}

}
