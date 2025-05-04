package modelPackage;

import viewPackage.GamePanel;
public class Magazziniere extends GameObject{

	public final int SOGLIA = 5;
	
	Negozio negozio;
	public boolean finito = false;
	public int checkpoint = 0;
	private long lastActionTime = 0;
    private final long DELAY = 2000;
	
	
	public Magazziniere(Negozio negozio, GamePanel gamePanel) {
		super(gamePanel);
		this.negozio = negozio;
	}
	
	
	//Dipendente che quando il numero di prodotti negli scaffali va al di sotto di una certa soglia, sposta i prodotti dal magazzino agli scaffali
	@Override
	public void update() {
		
		long now = System.currentTimeMillis();
		if (now - lastActionTime >= DELAY && !negozio.isTrovato()) {
			if(negozio.isDipendenti2()) {


				if(checkpoint==0) {
					if(negozio.prodottiScaffale.size() < SOGLIA) {
						checkpoint++;
					}
				}
				if(checkpoint==1) {
					negozio.setDipendenti(false);
	 
						lastActionTime = now;
						if(negozio.prodottiMagazzino.size() > 0 && negozio.prodottiScaffale.size() < negozio.getDimensioneScaffali()) {
							negozio.getController().spostaProdottiMagazzinoScaffale();
						}
						else {
							finito = true;
							checkpoint = 0;
							negozio.setDipendenti(true);
						}
						
			        
						
				}
			
			}
		}
	}

}
