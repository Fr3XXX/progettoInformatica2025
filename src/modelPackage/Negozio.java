package modelPackage;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

import controlPackage.*;
import viewPackage.*;

public abstract class Negozio extends GameObject{

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
	public Cliente[] clienti = new Cliente[8];
	public boolean servito = false;//serve per gestire i clienti
	public boolean servito2 = false;//serve per gestire l'interazione cliente-cassiere
	public boolean trovato = false; //serve per gestire l'interazione cliente-cassiere
	public boolean daVendere = false; //serve per gestire l'interazione cliente-cassiere
	public boolean dipendenti = false;//serve per far lavorare magazziniere e commerciante uno alla volta
	public Magazziniere magazziniere;
	public Cassiere cassiere;
	public Commerciante commerciante;
	
	
	public Negozio(int livelloNegozio, GamePanel gamePanel) {
		
		super(gamePanel);
		
		this.codaNegozio = 0;
		this.maxCoda = 6;
		this.livelliNegozio.put("Totale", 1);
		this.livelliNegozio.put("Scaffali", 1);
		this.livelliNegozio.put("Magazzino", 1);
		this.aperto=false;
		this.inserisciProdottiEsistenti();
		controller= new ControllerNegozio(this);
		
		
		for(int i=0; i<clienti.length; i++) {
			clienti[i] = new Cliente(this, gamePanel);
		}
		
	}
	
	public void setPrezziBase() {
		switch(livelloNegozio) {
		case 1:
			this.dimensioneMagazzino = 20;
			this.dimensioneScaffali = 10;
			this.prezzoNegozio = 50000;
			this.prezzoVendita = 45;
			this.prezzoAcquisto = 30;
			break;
		case 2:
			this.dimensioneMagazzino = 40;
			this.dimensioneScaffali = 20;
			this.prezzoNegozio = 100000;
			this.prezzoVendita = 75;
			this.prezzoAcquisto = 50;
			break;
		case 3:
			this.dimensioneMagazzino = 60;
			this.dimensioneScaffali = 30;
			this.prezzoNegozio = 175000;
			this.prezzoVendita = 135;
			this.prezzoAcquisto = 90;
			break;
		}
	}
	
	//all'interno del singolo negozio verranno inseriti i prodotto che esistono, poi quando si acquistano prodotti,
	//essi verranno scelti tra i prodotti esistenti in maniera casuale.
	public abstract void inserisciProdottiEsistenti();
		
}
