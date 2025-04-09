package viewPackage;

import java.awt.Color;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import modelPackage.*;

public class GamePanel extends JPanel implements Runnable{
	//screen settings
	//inserisce tutti i dati fonfdamentali per la finestra
	final int originalTitleSize=16;//tiyolo 16x16
	int a = Toolkit.getDefaultToolkit().getScreenSize().height;
	//serve per fare una scala perche se il computer ha una risoluzione maggiore allora il gioco sembrereppe piu piccolo
	final int scale=3;
		
	public final int tileSize = originalTitleSize * scale;
	//mappa dei tiles
	public final int maxScreenCol = 40;
	public final int maxScreenRow = 22;
	public final int screenWidth = tileSize * maxScreenCol;
	public final int screenHeight = tileSize * maxScreenRow;
	
	private List<GameObject> gameObjects = new ArrayList<>();
	//FPS
	int FPS=60;
	
	Thread gameThread;
	
	TileManager tileM = new TileManager(this);
	
	
	public GamePanel() {
		this.setPreferredSize(new Dimension(screenWidth,screenHeight));
		
		this.setBackground(Color.black);
		this.setDoubleBuffered(true);
		
		this.setFocusable(true);
		
	}
	public void startGameThread() {
	    // Inizializza input e thread separato
	    InputManager.initialize(this);

	    // Istanzia oggetti
	    Gioielleria gioielleria = new Gioielleria(this, "/casa.png", 10, 10, 64, 64);

	    // Avvio del thread principale del gioco
	    gameThread = new Thread(this);
	    gameThread.start();
	}

	@Override
	public void run() {
		//su tutti i metodi
		double drawInterval=1000000000/FPS;// 0.01666 seconds
				
		//terzo metodo 
		double delta=0;
		long lastTime= System.nanoTime();
		long currentTime;
		//per fps
		long timer=0;
		int drawCount =0;
		
		while(gameThread != null ) {
			//delta o accumulator method
			currentTime=System.nanoTime();
			
			delta += (currentTime - lastTime)/drawInterval;
			timer += (currentTime - lastTime) ;
			lastTime = currentTime;
			
			//questo fa si che passi il tempo desiderato
			if(delta >= 1) {
				update();
				repaint();
				delta--;
				drawCount++;
			}
			if(timer >= 1000000000) {
				System.out.println("FPS: "+drawCount);
				drawCount=0;
				timer=0;
			}
		}
		
	}
	public void update() {
		
		for (GameObject gameObject : gameObjects) {
			gameObject.update();
		}
		
	}
public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		
		//draw method
		tileM.draw(g2);
		
		for (GameObject gameObject : gameObjects) {
			gameObject.draw(g2);
		}
		
		
		//chiamata dalla classe Player
		//player.draw(g2);
		
		//safe some memory (buona pratica) 
		g2.dispose();
		
	}

	public void addGameObject(GameObject gameObject) {
		gameObjects.add(gameObject);
	}
	
	public void removeGameObject(GameObject gameObject) {
		gameObjects.remove(gameObject);
	}
}
