package modelPackage;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

import viewPackage.GamePanel;

public class GameObject {

	GamePanel gamePanel;
	BufferedImage texture;
	int scale = 3;
	int x, y, size_x, size_y;
	boolean visible = true;
	
	
	public GameObject(GamePanel gamePanel) {
		super();
		this.gamePanel = gamePanel;
		gamePanel.addGameObject(this);
	}
	
	public GameObject(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
		super();
		this.gamePanel = gamePanel;
		gamePanel.addGameObject(this);
		
		this.x = x;
		this.y = y;
		
		this.size_x = size_x;
		this.size_y = size_y;
		
		try {
			texture = ImageIO.read(getClass().getResourceAsStream(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void update() {
	}
	
	public void draw(Graphics2D g2) {
		if (texture != null && visible == true) {
		g2.drawImage(texture, x, y, size_x * scale, size_y * scale, null);
		}
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public void delete() {
		gamePanel.removeGameObject(this);
		/*	GamePanel gamePanel;
	BufferedImage texture;
	int x, y, size_x, size_y;
	boolean visible = true;*/
		
		gamePanel = null;
		texture = null;
		
		System.gc();
	}
	
	

	public GamePanel getGamePanel() {
		return gamePanel;
	}

	public void setGamePanel(GamePanel gamePanel) {
		this.gamePanel = gamePanel;
	}

	public BufferedImage getTexture() {
		return texture;
	}

	public void setTexture(BufferedImage texture) {
		this.texture = texture;
	}

	public int getScale() {
		return scale;
	}

	public void setScale(int scale) {
		this.scale = scale;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return size_x;
	}

	public void setWidth(int size_x) {
		this.size_x = size_x;
	}

	public int getHeight() {
		return size_y;
	}

	public void setHeight(int size_y) {
		this.size_y = size_y;
	}

	public boolean isVisible() {
		return visible;
	}
}
