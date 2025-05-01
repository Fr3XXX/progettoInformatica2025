package modelPackage;

import viewPackage.GamePanel;

public class Cassiere extends GameObject{

	Negozio negozio;
	public int indexVendita;
	public int i;
	public String vendita;
	private long lastActionTime = 0;
    private final long DELAY = 500;
	
	public Cassiere(Negozio negozio, GamePanel gamePanel) {
		super(gamePanel);
		this.negozio = negozio;
		this.i = 0;
	}
	
	//Dipendente che vende i prodotti
	@Override
	public void update() {
		long now = System.currentTimeMillis();

        // Altrimenti non fa nulla questo frame

		negozio.servito = true;//pronto per servire un cliente
				
		if(negozio.servito2) {//aspetta che il cliente scelga il prodotto	
			
			if(i>=negozio.prodottiScaffale.size()) {
				negozio.trovato=true;
			}
			
		}
			
	}
}
