package modelPackage;

import java.util.ArrayList;

import java.util.HashMap;

import controlPackage.*;
import viewPackage.*;

public abstract class Negozio extends GameObject{

	private int dimensioneScaffali; //numero massimo di prodotti da poter avere in vendita
	private int dimensioneMagazzino; //numero  massimo di prodotti da poter avere in magazzino
	public HashMap <String, Integer> livelliNegozio = new HashMap <String, Integer>(); //tiene tracca del livello del negozio
	private double prezzoVendita;
	private double prezzoAcquisto;
	private double prezzoNegozio;
	private double prezzoUpgradeMagazzino;
	private double prezzoUpgradeScaffali;
	private double prezzoDipendenti;
	private int livelloNegozio;
	private int codaNegozio;
	private Cliente clienteCorrente;
	private int maxCoda;
	private ControllerNegozio controller;
	public ArrayList <Prodotto> prodottiScaffale = new ArrayList <>(); //lista di prodotti in vendita
	public ArrayList <Prodotto> prodottiMagazzino = new ArrayList <>(); //lista di prodotti in magazzino
	public Prodotto[] prodottiEsistenti = new Prodotto[50];
	public Cliente[] clienti = new Cliente[8];
	private boolean servito = false;//serve per gestire i clienti
	private boolean servito2 = false;//serve per gestire l'interazione cliente-cassiere
	private boolean trovato = false; //serve per gestire l'interazione cliente-cassiere
	private boolean daVendere = false; //serve per gestire l'interazione cliente-cassiere
	private boolean dipendenti = true;//serve per far lavorare magazziniere e commerciante uno alla volta
	private boolean dipendenti2 = true; //serve per far lavorare magazziniere e commerciante uno alla volta
	private Magazziniere magazziniere;
	private Cassiere cassiere;
	private Commerciante commerciante;
	public boolean[] dipendentiAcquistati = new boolean[3];
	private ViewNegozio view;
	
	
	public Negozio(int livelloNegozio, GamePanel gamePanel, ViewNegozio view) {
		
		super(gamePanel);
		controller= new ControllerNegozio(this, view);
		this.view = view;
		this.codaNegozio = 0;
		this.maxCoda = 6;
		this.livelloNegozio = livelloNegozio;
		this.livelliNegozio.put("Totale", 1);
		this.livelliNegozio.put("Scaffali", 1);
		this.livelliNegozio.put("Magazzino", 1);
		for(int i=0; i<dipendentiAcquistati.length; i++) {
			dipendentiAcquistati[i] = false;
		}
		this.inserisciProdottiEsistenti();
		this.setPrezziBase();
		view.addController(controller);
		
		
		
		for(int i=0; i<clienti.length; i++) {
			clienti[i] = new Cliente(this, gamePanel);
		}
		
	}
	
	public int getDimensioneScaffali() {
		return dimensioneScaffali;
	}

	public void setDimensioneScaffali(int dimensioneScaffali) {
		this.dimensioneScaffali = dimensioneScaffali;
	}

	public int getDimensioneMagazzino() {
		return dimensioneMagazzino;
	}

	public void setDimensioneMagazzino(int dimensioneMagazzino) {
		this.dimensioneMagazzino = dimensioneMagazzino;
	}

	public double getPrezzoVendita() {
		return prezzoVendita;
	}

	public void setPrezzoVendita(double prezzoVendita) {
		this.prezzoVendita = prezzoVendita;
	}

	public double getPrezzoAcquisto() {
		return prezzoAcquisto;
	}

	public void setPrezzoAcquisto(double prezzoAcquisto) {
		this.prezzoAcquisto = prezzoAcquisto;
	}

	public double getPrezzoNegozio() {
		return prezzoNegozio;
	}

	public void setPrezzoNegozio(double prezzoNegozio) {
		this.prezzoNegozio = prezzoNegozio;
	}

	public double getPrezzoUpgradeMagazzino() {
		return prezzoUpgradeMagazzino;
	}

	public void setPrezzoUpgradeMagazzino(double prezzoUpgradeMagazzino) {
		this.prezzoUpgradeMagazzino = prezzoUpgradeMagazzino;
	}

	public double getPrezzoUpgradeScaffali() {
		return prezzoUpgradeScaffali;
	}

	public void setPrezzoUpgradeScaffali(double prezzoUpgradeScaffali) {
		this.prezzoUpgradeScaffali = prezzoUpgradeScaffali;
	}

	public double getPrezzoDipendenti() {
		return prezzoDipendenti;
	}

	public void setPrezzoDipendenti(double prezzoDipendenti) {
		this.prezzoDipendenti = prezzoDipendenti;
	}

	public int getLivelloNegozio() {
		return livelloNegozio;
	}

	public void setLivelloNegozio(int livelloNegozio) {
		this.livelloNegozio = livelloNegozio;
	}

	public int getCodaNegozio() {
		return codaNegozio;
	}

	public void setCodaNegozio(int codaNegozio) {
		this.codaNegozio = codaNegozio;
	}

	public int getMaxCoda() {
		return maxCoda;
	}

	public void setMaxCoda(int maxCoda) {
		this.maxCoda = maxCoda;
	}

	public ControllerNegozio getController() {
		return controller;
	}

	public void setController(ControllerNegozio controller) {
		this.controller = controller;
	}

	public boolean isServito() {
		return servito;
	}

	public void setServito(boolean servito) {
		this.servito = servito;
	}

	public boolean isServito2() {
		return servito2;
	}

	public void setServito2(boolean servito2) {
		this.servito2 = servito2;
	}

	public boolean isTrovato() {
		return trovato;
	}

	public void setTrovato(boolean trovato) {
		this.trovato = trovato;
	}

	public boolean isDaVendere() {
		return daVendere;
	}

	public void setDaVendere(boolean daVendere) {
		this.daVendere = daVendere;
	}

	public boolean isDipendenti() {
		return dipendenti;
	}

	public void setDipendenti(boolean dipendenti) {
		this.dipendenti = dipendenti;
	}

	public boolean isDipendenti2() {
		return dipendenti2;
	}

	public void setDipendenti2(boolean dipendenti2) {
		this.dipendenti2 = dipendenti2;
	}

	public Magazziniere getMagazziniere() {
		return magazziniere;
	}

	public void setMagazziniere(Magazziniere magazziniere) {
		this.magazziniere = magazziniere;
	}

	public Cassiere getCassiere() {
		return cassiere;
	}

	public void setCassiere(Cassiere cassiere) {
		this.cassiere = cassiere;
	}

	public Commerciante getCommerciante() {
		return commerciante;
	}

	public void setCommerciante(Commerciante commerciante) {
		this.commerciante = commerciante;
	}

	public ViewNegozio getView() {
		return view;
	}

	public Cliente getClienteCorrente() {
		return clienteCorrente;
	}

	public void setClienteCorrente(Cliente clienteCorrente) {
		this.clienteCorrente = clienteCorrente;
	}

	public void setView(ViewNegozio view) {
		this.view = view;
	}

	public void setPrezziBase() {
		//imposto i valori base del negozio in base al livello di esso
		switch(livelloNegozio) {
		case 1:
			this.dimensioneMagazzino = 20;
			this.dimensioneScaffali = 10;
			this.prezzoNegozio = 50000;
			this.prezzoVendita = 45;
			this.prezzoAcquisto = 30;
			this.prezzoUpgradeMagazzino = 5000;
			this.prezzoUpgradeScaffali = 5000;
			this.prezzoDipendenti = 10000;
			break;
		case 2:
			this.dimensioneMagazzino = 40;
			this.dimensioneScaffali = 20;
			this.prezzoNegozio = 100000;
			this.prezzoVendita = 75;
			this.prezzoAcquisto = 50;
			this.prezzoUpgradeMagazzino = 10000;
			this.prezzoUpgradeScaffali = 10000;
			this.prezzoDipendenti = 20000;
			break;
		case 3:
			this.dimensioneMagazzino = 60;
			this.dimensioneScaffali = 30;
			this.prezzoNegozio = 175000;
			this.prezzoVendita = 135;
			this.prezzoAcquisto = 90;
			this.prezzoUpgradeMagazzino = 17500;
			this.prezzoUpgradeScaffali = 17500;
			this.prezzoDipendenti = 25000;
			break;
		default:
			System.out.println("Errore nell'assegnazione dei valori");
		}
	}
	
	//all'interno del singolo negozio verranno inseriti i prodotto che esistono, poi quando si acquistano prodotti,
	//essi verranno scelti tra i prodotti esistenti in maniera casuale.
	public abstract void inserisciProdottiEsistenti();
		
}
