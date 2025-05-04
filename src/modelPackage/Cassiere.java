package modelPackage;

import viewPackage.GamePanel;

public class Cassiere extends GameObject{

	Negozio negozio;
	public int indexVendita;
	public int i;
	public String vendita;
	public int checkpoint = 0;

	
	public Cassiere(Negozio negozio, GamePanel gamePanel) {
		super(gamePanel);
		this.negozio = negozio;
		this.i = 0;
	}
	
	//Dipendente che vende i prodotti
	@Override
	public void update() {

		if(checkpoint==0) {
			negozio.setServito(true);//pronto per servire un cliente
			checkpoint++;
			i=0;
		}
		
		if(checkpoint==1) {
			if(negozio.isServito2()) {
				checkpoint++;
			}
		}
		
		if(checkpoint==2) {
			
			if(i<=negozio.prodottiScaffale.size()) {
				if(negozio.getController().cercaProdotto(indexVendita, vendita, i)) {
					negozio.setTrovato(true);
					checkpoint=0;
				}
				
			}
		}	
	}
}
