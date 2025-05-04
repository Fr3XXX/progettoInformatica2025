package modelPackage;

public class CodaNegozio implements Runnable{

	public Cliente cliente;
	public long start;
	public boolean finito = true;
	
	public CodaNegozio(Cliente cliente) {
		this.cliente = cliente;
	}
	
	
	
	@Override
	public void run() {
		
		start = System.currentTimeMillis();
		while(finito) {
			
			if((System.currentTimeMillis() - start) > cliente.tempoAttesaMax) {
				//dalla view il cliente verrà rimosso qui in questo punto
				cliente.stufato = true;
			}
			
		}
			
	}

}
