package viewPackage;

import modelPackage.GameObject;

public class Gioielleria extends GameObject {

	public Gioielleria(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
		super(gamePanel, path, x, y, size_x, size_y);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void update() {
		//logica gioielleria
		if(InputManager.isMouseOver(this) && InputManager.isMousePressed("left")) {
			this.delete();
		}
		super.update();
	}
}
