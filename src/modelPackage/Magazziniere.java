package modelPackage;

public class Magazziniere implements Runnable{

	public final int SOGLIA = 5;
	
	Negozio negozio;
	
	public Magazziniere(Negozio negozio) {
		this.negozio = negozio;
	}
	
	
	@Override
	public void run() {
		while(true) {
			
			if(negozio.prodottiScaffale.size() < SOGLIA) {
					
			}
			
		}
		
	}

}
