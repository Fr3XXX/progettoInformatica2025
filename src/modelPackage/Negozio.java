package modelPackage;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Negozio {

	public int dimensioneScaffali; //numero massimo di prodotti da poter avere in vendita
	public int dimensioneMagazzino; //numero  massimo di prodotti da poter avere in magazzino
	public HashMap <String, Integer> livelliNegozio = new HashMap <String, Integer>(); //tiene tracca del livello del negozio
	public double prezzoVendita;
	public double prezzoAcquisto;
	public ArrayList <Prodotto> prodottiScaffale = new ArrayList <>(); //lista di prodotti in vendita
	public ArrayList <Prodotto> prodottiMagazzino = new ArrayList <>(); //lista di prodotti in magazzino
	public Prodotto[] prodottiEsistenti = new Prodotto[50];
  
	public Negozio() {
		
		//quando un negozio viene comprato vengono settate le dimensioni base
		this.dimensioneMagazzino = 50;
		this.dimensioneScaffali = 30;
		livelliNegozio.put("Totale", 1);
		livelliNegozio.put("Scaffali", 1);
		livelliNegozio.put("Magazzino", 1);
		
	}
	
	//all'interno delle classi dei singoli negozi verranno settati i prezzi di base
	public abstract void setPrezziBase();
	
	//all'interno del singolo negozio verranno inseriti i prodotto che esistono, poi quando si acquistano prodotti,
	//essi verranno scelti tra i prodotti esistenti in maniera casuale.
	public abstract void inserisciProdottiEsistenti();
		
}
