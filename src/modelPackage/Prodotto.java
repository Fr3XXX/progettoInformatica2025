package modelPackage;

public class Prodotto {

	public String nome; //per una macchina rappresenta il modello, per un libro il titolo, etc...
	public String specifica; //es. genere, modello, ...
	
	public Prodotto(String nome, String specifica) {
		this.nome = nome;
		this.specifica = specifica;
	}
	
}
