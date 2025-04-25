package modelPackage;

public class Cassiere implements Runnable{

	Negozio negozio;
	public int indexVendita;
	public String vendita;
	
	public Cassiere(Negozio negozio) {
		this.negozio = negozio;
	}
	
	//Dipendente che vende i prodotti
	@Override
	public void run() {
		while(true) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
			negozio.servito.release();
			
			
		}
		
	}
}
