package mainPakage;

public class Libreria extends Negozio{

	Libro[] libri = new Libro[100]; //archivio di tutti i vari titoli esistenti
	
	public Libreria() {
		super();
		this.setPrezziBase();
	}
	
	
	@Override
	public void setPrezziBase() {
		prezzoAcquisto=12;
		prezzoVendita=20;
	}

	@Override
	public void acquistaProdottiMagazzino(double patrimonio) {
		
		
	}

	@Override
	public void vendiProdotto(double patrimonio, Prodotto prodotto) {
		
		
	}

}
