package modelPackage;

import java.util.ArrayList;

import controlPackage.*;

public class User {

	public static double patrimonioUtente;
	public ArrayList<Negozio> negoziPosseduti = new ArrayList<Negozio>();
	public ControllerUtente userController;
	public int[] prezziNegozi = { 50000, 100000, 175000};
	
	public User() {
		
		patrimonioUtente = 150000;
		userController = new ControllerUtente(this);
		
	}
	
}
