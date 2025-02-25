package mainPakage;

import java.util.ArrayList;

public abstract class Negozio {

	public int dimensioneScaffali; //numero massimo di prodotti da poter avere in vendita
	public int dimensioneMagazzino; //numero  massimo di prodotti da poter avere in magazzino
	public double prezzoVendita;
	public double prezzoAcquisto;
	public ArrayList <Prodotto> prodottiScaffale = new ArrayList <>(); //lista di prodotti in vendita
	public ArrayList <Prodotto> prodottiMagazzino = new ArrayList <>(); //lista di prodotti in magazzino
	
	public Negozio() {
		
		//quando un negozio viene comprato vengono settate le dimensioni base
		this.dimensioneMagazzino = 50;
		this.dimensioneScaffali = 30;
		
	}
	
	//all'interno delle classi dei singoli negozi verranno settati i prezzi di base
	public abstract void setPrezziBase();
	
	//all'interno delle singole classi verranno gestiti acquisti e vendite in base ai propri prezzi
	public abstract void acquistaProdottiMagazzino(double patrimonio);
	public abstract void vendiProdotto(double patrimonio, Prodotto prodotto);
	
	//metodo per aumentare la dimensione del magazzino
	public void upgradeDimensioneMagazzino(double costoUpgrade, double patrimonio) {
		patrimonio-=costoUpgrade;
		if(dimensioneMagazzino % 2 == 0) {
			dimensioneMagazzino*=1.5;
		}
		else {
			dimensioneMagazzino*=2;//se il numero è dispari moltiplico per 2 per evitare che vengano numeri con la virgola
		}

	}
	
	//metodo per aumentare la dimensione degli scaffali
	public void upgradeDimensioneScaffali(double costoUpgrade, double patrimonio) {
		patrimonio-=costoUpgrade;
		if(dimensioneScaffali % 2 == 0) {
			dimensioneScaffali*=1.5;
		}
		else {
			dimensioneScaffali*=2;//se il numero è dispari moltiplico per 2 per evitare che vengano numeri con la virgola
		}
	}
	
}
