package modelPackage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Semaphore;

import controlPackage.*;

public abstract class Negozio {

	public int dimensioneScaffali; //numero massimo di prodotti da poter avere in vendita
	public int dimensioneMagazzino; //numero  massimo di prodotti da poter avere in magazzino
	public HashMap <String, Integer> livelliNegozio = new HashMap <String, Integer>(); //tiene tracca del livello del negozio
	public double prezzoVendita;
	public double prezzoAcquisto;
	public double prezzoNegozio;
	public int livelloNegozio;
	public boolean aperto;
	public int codaNegozio;
	public int maxCoda;
	public ControllerNegozio controller;
	public ArrayList <Prodotto> prodottiScaffale = new ArrayList <>(); //lista di prodotti in vendita
	public ArrayList <Prodotto> prodottiMagazzino = new ArrayList <>(); //lista di prodotti in magazzino
	public Prodotto[] prodottiEsistenti = new Prodotto[50];
	public Thread[] clienti = new Thread[8];
	public Semaphore servito = new Semaphore(0);//serve per gestire i clienti
	public Semaphore mutex = new Semaphore(1); //serve per gestire la mutua esclusione legata ai clienti
  
	public Negozio(int livelloNegozio) {
			
		this.codaNegozio = 0;
		this.maxCoda = 6;
		this.livelliNegozio.put("Totale", 1);
		this.livelliNegozio.put("Scaffali", 1);
		this.livelliNegozio.put("Magazzino", 1);
		this.aperto=false;
		controller= new ControllerNegozio(this);
		
		for(int i=0; i<clienti.length; i++) {
			clienti[i] = new Thread(new Cliente(this));
			
		}
			
		
		
		
	}
	
	//all'interno delle classi dei singoli negozi verranno settati i prezzi di base
	public abstract void setPrezziBase();
	
	//all'interno del singolo negozio verranno inseriti i prodotto che esistono, poi quando si acquistano prodotti,
	//essi verranno scelti tra i prodotti esistenti in maniera casuale.
	public abstract void inserisciProdottiEsistenti();
		
}
