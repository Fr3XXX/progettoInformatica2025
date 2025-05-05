package modelPackage;

import javax.swing.SwingUtilities;
import viewPackage.GamePanel;

public class Cliente extends GameObject {

    public Negozio negozio;
    public long tempoAttesaMax;
    public double maxSoldiSpendibili;
    public String domandaCliente;
    public int indexDomanda;
    public String richiesta="";
    public boolean venduto;
    public boolean stufato;
    public boolean servendo = false;
    public int checkpoint = 0;
    public String rispostaCliente;
    public Thread tempoInFila;
    public CodaNegozio coda;
    public String[][] domande = new String[2][10];
    public String[][] risposte = new String[2][5];
    public String[][] esclamazioni = new String[2][3];
    private long lastActionTime = 0;
    private final long DELAY = 1500;
    private long lastActionTime2 = 0;
    private final long DELAY2 = 10000;
    private final long DELAY3 = 4000;
    private long lastActionTime3 = 0;
    
    public Cliente(Negozio negozio, GamePanel gamePanel) {
        super(gamePanel);
        this.negozio = negozio;
        
     
        this.domande[0][0] = "Avete qualcosa che potrebbe interessarmi ";
        this.domande[0][1] = "Sto cercando un prodotto particolare ";
        this.domande[0][2] = "Mi chiedevo se tenete ";
        this.domande[0][3] = "C'è qualcosa in negozio che assomigli a ";
        this.domande[0][4] = "Ho sentito che qui vendete ";
        this.domande[0][5] = "Mi serve urgentemente ";
        this.domande[0][6] = "Vorrei sapere se è disponibile ";
        this.domande[0][7] = "Potrei vedere ";
        this.domande[0][8] = "È possibile acquistare ";
        this.domande[0][9] = "C'è disponibilità per ";

        
        this.domande[1][0] = "Avete qualcosa per uso quotidiano come ";
        this.domande[1][1] = "Cerco un prodotto stagionale, tipo ";
        this.domande[1][2] = "Vorrei qualcosa di tecnologico, come ";
        this.domande[1][3] = "Ci sono articoli ecologici, tipo ";
        this.domande[1][4] = "Sto cercando qualcosa per la casa, come ";
        this.domande[1][5] = "Mi servirebbe qualcosa per il tempo libero, tipo ";
        this.domande[1][6] = "Avete qualcosa adatto ai bambini, come ";
        this.domande[1][7] = "Sto cercando un regalo, magari qualcosa come ";
        this.domande[1][8] = "Mi interessa qualcosa di economico, come ";
        this.domande[1][9] = "Ci sono novità nel reparto, tipo ";

        
        this.risposte[0][0] = "Perfetto, è proprio quello che cercavo.";
        this.risposte[0][1] = "Ottimo, lo prendo subito.";
        this.risposte[0][2] = "Finalmente qualcuno che capisce!";
        this.risposte[0][3] = "Grazie, ottimo servizio.";
        this.risposte[0][4] = "Sono molto soddisfatto dell’acquisto.";

        
        this.risposte[1][0] = "No, non è quello che volevo.";
        this.risposte[1][1] = "Non fa per me.";
        this.risposte[1][2] = "Mi aspettavo qualcosa di diverso.";
        this.risposte[1][3] = "Non è come lo immaginavo.";
        this.risposte[1][4] = "Lasciamo perdere, grazie comunque.";

        
        this.esclamazioni[0][0] = "Peccato...";
        this.esclamazioni[0][1] = "Eh, va bene così.";
        this.esclamazioni[0][2] = "Magari la prossima volta.";

        
        this.esclamazioni[1][0] = "Che delusione!";
        this.esclamazioni[1][1] = "Mi avete fatto perdere tempo!";
        this.esclamazioni[1][2] = "Non tornerò più qui!";


        
        
    }
    
    @Override
    public void update() {
        long now = System.currentTimeMillis();
        if(now - lastActionTime >= DELAY) {
            lastActionTime = now;
            
            if(this.checkpoint == 0) {
                if(now - lastActionTime2 >= DELAY2) {
                    lastActionTime2 = now;
                    stufato = false;
                    tempoAttesaMax = (long) ((Math.random()*89 + 1)*1000);
                    maxSoldiSpendibili = this.negozio.getPrezzoAcquisto() + Math.random()*(this.negozio.getPrezzoAcquisto()/2 - 2) + 2;
                    indexDomanda = (int) (Math.random());
                    domandaCliente = this.domande[indexDomanda][(int) (Math.random()*9)];
                    System.out.println(domandaCliente);
                    System.out.println("Entrato in check 0");
                    this.checkpoint++;
                }
            }
            
            if(checkpoint == 1 && negozio.getCodaNegozio() < negozio.getMaxCoda()) {
                this.negozio.setCodaNegozio(this.negozio.getCodaNegozio() + 1);
                coda = new CodaNegozio(this);
                tempoInFila = new Thread(coda);
                tempoInFila.start();
                checkpoint++;
            }
            
            if(checkpoint == 2 && !negozio.getController().isCassaLibera()) {
            	if(!negozio.dipendentiAcquistati[1]) {
            	System.out.println("Entrato in check 2");
                negozio.setClienteCorrente(this);
                negozio.getController().cambiaStatoCassa(true);
                checkpoint++;}
            	else {
            		if(negozio.isServito()) {
            			System.out.println("Entrato in check 2");
                        negozio.setClienteCorrente(this);
                        negozio.getController().cambiaStatoCassa(true);
                        checkpoint++;
            		}
            	}
            }
            
            if(checkpoint == 3) {
            	System.out.println("Entrato in check 3");
                richiesta = negozio.getController().sceltaProdotto(indexDomanda);
                System.out.println(domandaCliente);
                negozio.getController().stampaTestoCliente(domandaCliente + richiesta + "?");
                checkpoint++;
                negozio.setServito2(true);
            }
            
            if(checkpoint == 4 && negozio.isTrovato()) {
            	System.out.println("Entrato in check 4");
                processaRisposta();
                negozio.getController().cambiaStatoCassa(false); // Cassa libera
                long now2 = System.currentTimeMillis();
                if(now2 - lastActionTime3 >= DELAY3) {
                	lastActionTime3 = now2;
                    resetCliente();
                }

            }
        }
    }

    private void processaRisposta() {
        for(int i=0; i<negozio.prodottiScaffale.size(); i++) {
            if(negozio.getController().cercaProdotto(indexDomanda, richiesta, i)) {
                venduto = true;
            }
        }
        
        if(venduto==negozio.isDaVendere()) {
            if(venduto) {
                if(negozio.getController().decideSeComprare(negozio.getPrezzoAcquisto(), negozio.getPrezzoVendita())) {
                    rispostaCliente = risposte[0][(int)( Math.random()*4)];
                    negozio.getController().vendiProdotto(negozio.getController().cercaProdottoVendere(indexDomanda, richiesta));
                } else {
                    rispostaCliente = esclamazioni[1][(int) (Math.random()*2)];
                }
            } else {
                rispostaCliente = risposte[1][(int)(Math.random()*4)];
            }
        } else {
            rispostaCliente = "HAI DATO LA RISPOSTA SBAGLIATA AD UN CLIENTE";
        }
        
        negozio.getController().stampaTestoCliente(rispostaCliente);
    }

    private void resetCliente() {
        this.negozio.setCodaNegozio(this.negozio.getCodaNegozio() - 1);
        negozio.setServito2(false);
        negozio.setServito(false);
        negozio.setTrovato(false);
        this.checkpoint = 0;
        this.servendo = false;
    }
}