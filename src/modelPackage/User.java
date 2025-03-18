package modelPackage;

import java.util.ArrayList;

import controlPackage.*;

public class User {

	public static double patrimonioUtente;
	public ArrayList<Negozio> negoziPosseduti = new ArrayList<Negozio>();
	public ControllerUtente userController;
	
	public User() {
		
		patrimonioUtente = 50000;
		userController = new ControllerUtente(this);
		
	}
	
}
