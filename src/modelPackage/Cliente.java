package modelPackage;

import viewPackage.GamePanel;

public class Cliente extends GameObject{

	public Negozio negozio;
	public long tempoAttesaMax;
	public double maxSoldiSpendibili;
	public String domandaCliente;
	public int indexDomanda;
	public String richiesta="";
	public boolean venduto;
	public boolean stufato; //se il cliente si stufa di stare in fila diventa true
	public boolean servendo = false;
	public int checkpoint = 0; //serve per tenere traccia del punto in cui il cliente è arrivato
	public String rispostaCliente;
	public Thread tempoInFila;
	public CodaNegozio coda;
	public String[][] domande = new String[2][10];//domande[0] sono la richiesta specifica del libro, domande[1] sono richieste di libri in base alla specifica
	public String[][] risposte = new String[2][5];//risposte[0] positive, risposte[1] negative.
	public String[][] esclamazioni = new String[2][3];//esclamazioni[0] per prezzo alto, esclamazioni[1] per troppo tempo in fila
	
	
	public Cliente(Negozio negozio, GamePanel gamePanel) {
		
		super(gamePanel);
		this.negozio = negozio;
		
		this.domande[0][0] = "Buongiorno, potrei sapere se avete disponibile una copia di ";
		this.domande[0][1] = "Salve, percaso avete ";
		this.domande[0][2] = "Ciao! Per caso avete una copia di ";
		this.domande[0][3] = "Buongiorno, vorrei acquistare una copia di ";
		this.domande[0][4] = "Salve, sto cercando un prodotto, sapete se avete ";
		this.domande[0][5] = "Buongiorno, vorrei aquistare un prodotto. Mi pare si chiamasse ";
		this.domande[0][6] = "Salve, ho sentito parlare molto bene di ";
		this.domande[0][7] = "Ciao! Finalmente ho deciso di acquistare ";
		this.domande[0][8] = "Buongiorno, vorrei acquistare una copia del prodotto ";
		this.domande[0][9] = "Salve, sto cercando un prodotto chiamato ";
		
		this.domande[1][0] = "Buongiorno, potrei sapere se avete disponibile un prodotto ";
		this.domande[1][1] = "Salve, percaso avete un prodotto ";
		this.domande[1][2] = "Ciao! Per caso avete un prodotto ";
		this.domande[1][3] = "Buongiorno, vorrei acquistare un prodotto ";
		this.domande[1][4] = "Salve, sto cercando un prodotto, ne avete uno ";
		this.domande[1][5] = "Buongiorno, vorrei aquistare un prodotto. Sto cercando qualcosa ";
		this.domande[1][6] = "Salve, visto che ne parlano tutti bene, vorrei un prodotto ";
		this.domande[1][7] = "Ciao! Finalmente ho deciso di acquistare un prodotto ";
		this.domande[1][8] = "Buongiorno, vorrei acquistare una copia di un prodotto ";
		this.domande[1][9] = "Salve, sto cercando un prodotto ";
		
		
		this.risposte[0][0] = "Grazie mille!";
		this.risposte[0][1] = "Grazie, buona giornata";
		this.risposte[0][2] = "Perfetto! Grazie e arrivederci";
		this.risposte[0][3] = "La ringrazio, arrivederci";
		this.risposte[0][4] = "Grazie mille! Non vedevo l'ora di avercelo tra le mani!";
		
		this.risposte[1][0] = "Ah che peccato... Sarà per la prossima volta";
		this.risposte[1][1] = "Va be non fa niente, tornerò un altro giorno. Arrivederci";
		this.risposte[1][2] = "Ma dai! Sono sicuro che se vado da un'altra parte troverò quello che cerco... scandaloso!";
		this.risposte[1][3] = "Non ha alcun senso, tutti dovrebbero avere questo prodotto!";
		this.risposte[1][4] = "Grazie lo stesso, buona giornata";
		
		
		this.esclamazioni[0][0] = "Ma quanto ci vuole? Non si muove mai questa fila!";
		this.esclamazioni[0][1] = "Mamma mia ma è infinita questa coda!";
		this.esclamazioni[0][2] = "Che stress, non posso aspettare tutto il giorno!";
		
		this.esclamazioni[1][0] = "Ma è un furto! Con questi prezzi ci compro una casa!";
		this.esclamazioni[1][1] = "Cosa?! Questo costa più del mio stipendio!";
		this.esclamazioni[1][2] = "Stanno scherzando, vero? A questo prezzo dovrebbe essere d'oro!";
		

		
	}
	
	//rendere gameobject, cambiare semafori con degli if
	@Override
	public void update() {
			
		if(this.negozio.isAperto() == true) {
			if(this.checkpoint==0) {
				stufato = false;
				tempoAttesaMax = (long) ((Math.random()*89 + 1)*1000); //tempo di attesa che va da 1 a 90
				maxSoldiSpendibili = this.negozio.getPrezzoAcquisto() + Math.random()*(this.negozio.getPrezzoAcquisto()/2 - 2) + 2;
				indexDomanda = (int) (Math.random());
				domandaCliente = this.domande[indexDomanda][(int) (Math.random()*9)];
				this.checkpoint++;
			}
			
			if(this.negozio.getCodaNegozio() < this.negozio.getMaxCoda()) {
				if(checkpoint==1) {
					this.negozio.setCodaNegozio(this.negozio.getCodaNegozio() + 1);
					checkpoint++;
				}
				if(!negozio.getController().isServendo() || this.servendo) {
					
						//qui può andare avanti solo se può essere servito, quindi diventa il primo cliente della fila
						//qui bisogna abilitare il bottone per servire(btnServire.setEnabled(true));
						//in modo tale che si possa andare avanti
						if(checkpoint==2) {
							coda = new CodaNegozio(this);
							tempoInFila = new Thread(coda);
							tempoInFila.start();
							checkpoint++;
						}
						
						if(negozio.isServito()) { // quando verrà premuto il bottone per servire, ci sarà il anche servito.release(), 
							//che permetterà di far andare avanti qui
							coda.finito = false;
							if(!stufato) {
								if(checkpoint==3) {
									negozio.getController().sceltaProdotto(indexDomanda, richiesta);
									//qui verrà stampata la domanda a schermo nella view tramite un metodo che prenderà come parametro
									//domandaCliente
									negozio.setServito2(true);//il cliente ha scelto il prodotto
								}
								
								if(negozio.isTrovato()) {
									//qui aspetta che il venditore clicchi e trovi il prodotto da vendere
									//nel punto in cui verrà scelto andrà fatto il servito.release() per far andare avanti il cliente
											
											

									/*ANCORA DA CAPIRE PERCHE' SERVE LA VIEW PER CAPIRSI MEGLIO*/
									for(int i=0; i<negozio.prodottiScaffale.size(); i++) { //qui verrà controllato se è stato dato il prodotto corretto al cliente in base alla sua richiesta
										if(negozio.getController().cercaProdotto(indexDomanda, richiesta, i)) {
											venduto = true;
										}
									}
									//e il cliente deciderà in base a ciò e in base al prezzo se acquistare il prodotto
									//la richiesta del cliente sarà nel controller, recuperata dai listener
									if(venduto) {
										if(negozio.getController().decideSeComprare(negozio.getPrezzoAcquisto(), negozio.getPrezzoVendita()) ) {
											rispostaCliente = risposte[0][(int)( Math.random()*4)];
											negozio.getController().vendiProdotto(negozio.getController().cercaProdottoVendere(indexDomanda, richiesta));
										}
										else {
											rispostaCliente = esclamazioni[1][(int) (Math.random()*2)];
										}
									}
									else {
										rispostaCliente = risposte[1][(int) (Math.random()*4)];
									}
											
									//stampa risposta nella view con metodo che prende come parametro rispostaCliente
											
									this.servendo=false;
									negozio.setServito2(false);
									negozio.setServito(false);
									negozio.setTrovato(false);//il cliente esce dal negozio dando possibilità al cliente successivo di diventare il primo della fila
									this.checkpoint=0;		
								}
									
							}
							else {
								rispostaCliente = esclamazioni[0][(int) (Math.random()*2)];
							}
						}
				}	
			}
		}
	}	
	
}
