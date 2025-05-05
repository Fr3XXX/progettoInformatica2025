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
    
    public Cliente(Negozio negozio, GamePanel gamePanel) {
        super(gamePanel);
        this.negozio = negozio;
        
        // Inizializzazione domande, risposte ed esclamazioni...
        // (Mantieni il codice esistente qui)
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
                richiesta = negozio.getController().sceltaProdotto(indexDomanda);
                negozio.getController().stampaTestoCliente(domandaCliente + richiesta + "?");
                checkpoint++;
                negozio.setServito2(true);
            }
            
            if(checkpoint == 4 && negozio.isTrovato()) {
                processaRisposta();
                negozio.getController().cambiaStatoCassa(false); // Cassa libera
                resetCliente();
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