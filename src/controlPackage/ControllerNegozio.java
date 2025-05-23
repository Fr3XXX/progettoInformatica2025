package controlPackage;

import java.awt.Color;
import java.util.Random;

import javax.swing.SwingUtilities;

import modelPackage.*;
import viewPackage.*;

public class ControllerNegozio {

	public Negozio negozio;
	public ViewNegozio view;
	
	private boolean cassaLibera = false;
	
	public ControllerNegozio(Negozio negozio, ViewNegozio view) {
		this.negozio = negozio;
		this.view = view;
	}
	
	public void acquistaProdottiMagazzino(int nProdotti) {
			
		if(User.patrimonioUtente >= nProdotti*negozio.getPrezzoAcquisto()) {			
			if((negozio.getDimensioneMagazzino() - negozio.prodottiMagazzino.size()) >= nProdotti) {
				for(int i=0; i<nProdotti; i++) {
					User.patrimonioUtente-=negozio.getPrezzoAcquisto();	
					Prodotto prodottoComprare = negozio.prodottiEsistenti[(int)( Math.random()*49)];
					negozio.prodottiMagazzino.add(prodottoComprare);
					if(view.getTransactionLabel().getText().equals("")) {
						view.getTransactionLabel().setText("<html> </html>");
					}
					String stampaTransazione = view.getTransactionLabel().getText().replace("</html>",("<br>Prodotto con nome " + prodottoComprare.nome + " è stato acquistato per " + negozio.getPrezzoAcquisto() + "$</html>"));

					view.getTransactionLabel().setText(stampaTransazione);

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
			User.patrimonioUtente+=negozio.getPrezzoVendita();
			if(view.getTransactionLabel().getText().equals("")) {
				view.getTransactionLabel().setText("<html> </html>");
			}
			String stampaTransazione = view.getTransactionLabel().getText().replace("</html>",("<br>Prodotto con nome " + prodottoVendere.nome + " è stato venduto per " + negozio.getPrezzoAcquisto() + "$</html>"));
			view.getTransactionLabel().setText(stampaTransazione);
		}
	}
	
	//metodo per aumentare la dimensione del magazzino
	public void upgradeDimensioneMagazzino() {
		
		if(negozio.getPrezzoUpgradeMagazzino()>User.patrimonioUtente) {
			System.out.println("Non hai abbastanza soldi per questo upgrade");
		}
		else {
			negozio.livelliNegozio.replace("Magazzino", (negozio.livelliNegozio.get("Magazzino") + 1));
			User.patrimonioUtente-=negozio.getPrezzoUpgradeMagazzino();
			if(negozio.getDimensioneMagazzino() % 2 == 0) {
				int nuovoValore = (int) (negozio.getDimensioneMagazzino()*1.5);
				negozio.setDimensioneMagazzino(nuovoValore);
			}
			else {
				negozio.setDimensioneMagazzino(negozio.getDimensioneMagazzino()*2);//se il numero è dispari moltiplico per 2 per evitare che vengano numeri con la virgola
			}
			
			System.out.println("Dimensione magazzino aumentata a " + negozio.getDimensioneScaffali());
			this.aggiornaLivelloTotale();
			int nuovoValore = (int) (negozio.getPrezzoUpgradeMagazzino()*1.5);
			negozio.setPrezzoUpgradeMagazzino(nuovoValore);
			this.view.getUpgrades()[0] = "Lvl " + this.negozio.livelliNegozio.get("Magazzino") + ", Dimensione Magazzino - " + negozio.getPrezzoUpgradeMagazzino();
			this.view.refreshGrafica();
		}
		
	}
		
	//metodo per aumentare la dimensione degli scaffali
	public void upgradeDimensioneScaffali() {
		
		if(negozio.getPrezzoUpgradeScaffali()>User.patrimonioUtente) {
			System.out.println("Non hai abbastanza soldi per questo upgrade");
		}
		else {
			User.patrimonioUtente-=negozio.getPrezzoUpgradeScaffali();
			negozio.livelliNegozio.replace("Scaffali", (negozio.livelliNegozio.get("Scaffali") + 1));
			if(negozio.getDimensioneScaffali() % 2 == 0) {
				int nuovoValore = (int) (negozio.getDimensioneScaffali()*1.5);
				negozio.setDimensioneScaffali(nuovoValore);			}
			else {
				negozio.setDimensioneScaffali(negozio.getDimensioneScaffali()*2);//se il numero è dispari moltiplico per 2 per evitare che vengano numeri con la virgola
			}
			System.out.println("Dimensione scaffali aumentata a " + negozio.getDimensioneScaffali());
			int nuovoValore = (int) (negozio.getPrezzoUpgradeScaffali()*1.5);
			negozio.setPrezzoUpgradeScaffali(nuovoValore);
			this.view.getUpgrades()[1] = "Lvl " + this.negozio.livelliNegozio.get("Scaffali") + ", Dimensione Scaffali - " + negozio.getPrezzoUpgradeScaffali();
			this.view.refreshGrafica();
		}
		
	}
	
	public double getPrezzoUpgradeScaffali() {
		return negozio.getPrezzoUpgradeScaffali();
	}
	
	public double getPrezzoUpgradeMagazzino() {
		return negozio.getPrezzoUpgradeMagazzino();
	}
	
	//aggiorno i valori dell'HashMap dei livelli
	public void aggiornaLivelloTotale() {
		
		if((negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali")) % 2 == 0) {
			negozio.livelliNegozio.replace("Totale", (negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali")) / 2);
		}
		else {
			negozio.livelliNegozio.replace("Totale", (negozio.livelliNegozio.get("Magazzino") + negozio.livelliNegozio.get("Scaffali") - 1) / 2);
		}
	}

	public String sceltaProdotto(int indexDomanda) {
		
		    int nrRichiesta = (int)(Math.random()*49);
		    String richiesta;
		    if(indexDomanda == 0) {
		        richiesta = negozio.prodottiEsistenti[nrRichiesta].nome;
		    }
		    else {
		        richiesta = negozio.prodottiEsistenti[nrRichiesta].specifica;
		    }
		    
		    if(negozio.getCassiere() != null) {
			    negozio.getCassiere().vendita = richiesta;
			    negozio.getCassiere().indexVendita = indexDomanda;
		    }
		    return richiesta;
	}
	
	public void acquistaDipendente(int i) {

		if(negozio.getPrezzoDipendenti()>User.patrimonioUtente) {
			System.out.println("Non puoi permetterti di acquistare il dipendente");
		}
		else {

			switch (i) {
			
				case 0:
					negozio.setMagazziniere(new Magazziniere(negozio, negozio.getGamePanel()));
					negozio.dipendentiAcquistati[0] = true;
					User.patrimonioUtente-=negozio.getPrezzoDipendenti();
					System.out.println("Assunto magazziniere");
					break;

				case 1:
					negozio.setCassiere(new Cassiere(negozio, negozio.getGamePanel()));
					negozio.dipendentiAcquistati[1] = true;
					User.patrimonioUtente-=negozio.getPrezzoDipendenti();
					System.out.println("Assunto cassiere");
					break;
				
				case 2:
					negozio.setCommerciante(new Commerciante(negozio, negozio.getGamePanel()));
					negozio.dipendentiAcquistati[2] = true;
					User.patrimonioUtente-=negozio.getPrezzoDipendenti();
					System.out.println("Assunto commerciante");
					break;
					
				default:
					System.out.println("Errore nell'assunzione del dipendente");
			}
		}
		
	}
	
	public void cambiaStatoCassa(boolean libera) {
	    this.cassaLibera = libera;
	    view.setCassaBackgroundColor(libera ? Color.GREEN : Color.RED);
	    view.aggiornaValori();
	}
	
	public boolean isAcquistato(int i) {
		return negozio.dipendentiAcquistati[i];
	}
	public String getPrezzoDipendenti() {
		return ((Double)negozio.getPrezzoDipendenti()).toString();
	}
	
	public void setNumeroAcquistoCommerciante(int numero) {
		negozio.getCommerciante().numeroProdottiAcquisto = numero;
	}
	
	public void spostaProdottiMagazzinoScaffale(){
			if(negozio.prodottiMagazzino.size() > 0 && negozio.prodottiScaffale.size() < negozio.getDimensioneScaffali()) {
				negozio.prodottiScaffale.add(negozio.prodottiMagazzino.get(negozio.prodottiMagazzino.size() - 1));
				negozio.prodottiMagazzino.remove(negozio.prodottiMagazzino.size() - 1);
			}	
	}
	
	public boolean cercaProdotto(int indexVendita, String richiesta, int i) {
		
		switch(indexVendita) {
			case 0: 
					if(negozio.prodottiScaffale.get(i).nome.equals(richiesta)) {
						return true;
					}
				break;
			
			case 1:
					if(negozio.prodottiScaffale.get(i).specifica.equals(richiesta)) {
						return true;
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
			}
			
			break;
		
		case 1:
			
			for(Prodotto prodotto : negozio.prodottiScaffale) {
				if(prodotto.specifica.equals(richiesta)) {
					return prodotto;
				}
			}
			
			break;
		
		default:
			System.out.println("ERRORE");
		}
		return null; 
	}
	
	public boolean decideSeComprare(double prezzoAcquisto, double prezzoVendita) {

		Random random = new Random();
		
        double ricarico = (prezzoVendita - prezzoAcquisto) / prezzoAcquisto;

        if (ricarico <= 0) { 
        	return true;
        }

        // La probabilità di rifiuto è proporzionale al ricarico, massimo 70%
        double probabilitaRifiuto = Math.min(ricarico, 0.7);

        return random.nextDouble() >= probabilitaRifiuto;
    }
	
	public void stampaTestoCliente(String testo) {
		SwingUtilities.invokeLater(() -> {
	        if(view.getLabelSuperioreClienti() != null) {
	            view.getLabelSuperioreClienti().setText(testo);
	        }
	    });
	}
	
	public boolean isServendo() {
		for(int i=0; i < negozio.clienti.length; i++) {
			if(negozio.clienti[i].servendo) {
				return true;
			}
		}
		return false;
	}
	
	public int tipoNegozio() {
		
		if(this.negozio instanceof Libreria) {
			return 0;
		}
		
		if(this.negozio instanceof GameStop) {
			return 1;
		}
		
		if(this.negozio instanceof Vestiti) {
			return 2;
		}
		
		if(this.negozio instanceof Elettronica) {
			return 3;
		}
		
		if(this.negozio instanceof Concessionario) {
			return 4;
		}
		
		if(this.negozio instanceof Gioielleria) {
			return 5;
		}
		
		return -1; //errore
	
	}
	public boolean isCassaLibera() {
	    return cassaLibera;
	}

}
