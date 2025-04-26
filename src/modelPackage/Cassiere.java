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
				
				negozio.servito.release();//pronto per servire un cliente
				
				negozio.servito2.acquire();//aspetta che il cliente scelga il prodotto
				
				if(negozio.controller.cercaProdotto(indexVendita, vendita)) {
					System.out.println("Prodotto venduto con successo");
				}
				else {
					System.out.println("Il prodotto richiesto non è disponibile");
				}
				
				negozio.servito.release();
				
				negozio.servito2.acquire();
				
			} catch (InterruptedException e) {
				
				e.printStackTrace(); 
			}
			
			
			
			
		}
		
	}
}
