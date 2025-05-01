package modelPackage;

import viewPackage.GamePanel;
public class Magazziniere extends GameObject{

	public final int SOGLIA = 5;
	
	Negozio negozio;
	public boolean finito = false;
	public int checkpoint = 0;
	private long lastActionTime = 0;
    private final long DELAY = 250;
	
	
	public Magazziniere(Negozio negozio, GamePanel gamePanel) {
		super(gamePanel);
		this.negozio = negozio;
	}
	
	
	//Dipendente che quando il numero di prodotti negli scaffali va al di sotto di una certa soglia, sposta i prodotti dal magazzino agli scaffali
	@Override
	public void update() {
		if(negozio.dipendenti2) {
			long now = System.currentTimeMillis();
			if(checkpoint==0) {
				if(negozio.prodottiScaffale.size() < SOGLIA) {
					checkpoint++;
				}
			}
			if(checkpoint==1) {
				negozio.dipendenti=false;
				if (now - lastActionTime >= DELAY && negozio.trovato==false && i< negozio.prodottiScaffale.size()) {
					if(negozio.prodottiMagazzino.size() > 0 && negozio.prodottiScaffale.size() < negozio.dimensioneScaffali) {
						negozio.controller.spostaProdottiMagazzinoScaffale();
					}
					else {
						finito = true;
						checkpoint = 0;
						negozio.dipendenti = true;
					}
					
		        }
					
			}
		
		}
		

	}

}
