package viewPackage;

import java.awt.Graphics2D;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.imageio.ImageIO;

public class TileManager {
	Tile[] tile;
	GamePanel gp;
	//per la mappa
	int mapTileNum[][];
	

	
	public TileManager(GamePanel gp) {
		this.gp=gp;
		//quanti tile diversi implementiamo
		tile=new Tile[6];
		
		//map
		mapTileNum=new int[gp.maxScreenCol][gp.maxScreenRow];
		

		
		getTileImage();
		loadMap();
	}
	
	public void getTileImage() {
		//in caso non trova l'immagine va avanti lo stesso
		try {
			
			tile[0]=new Tile();
			tile[0].image = ImageIO.read(getClass().getResourceAsStream("/tiles/002.png"));
					
			tile[1]=new Tile();
			tile[1].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road1.png"));
			
			tile[2]=new Tile();
			tile[2].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road2.png"));
			
			tile[3]=new Tile();
			tile[3].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road3.png"));
			
			tile[4]=new Tile();
			tile[4].image = ImageIO.read(getClass().getResourceAsStream("/tiles/road4.png"));
			
			tile[5]=new Tile();
			tile[5].image = ImageIO.read(getClass().getResourceAsStream("/tiles/016.png"));
			
			
		}catch(IOException e) {
			e.printStackTrace();			
		}
	}
	
	
	
	public void loadMap() {
		try {
			InputStream is = getClass().getResourceAsStream("/maps/testMap.txt");
			//si importa il text file
			BufferedReader br=new BufferedReader(new InputStreamReader(is));
			
			int col=0;
			int row=0;
			
			while (col < gp.maxScreenCol && row<gp.maxScreenRow) {
				//questo legge tutta la linea del file 
				String line = br.readLine();
				while(col<gp.maxScreenCol) {
					//conta che ci sono gli spazzi tra i numeri cosi da prendere un numero dopo l'altro
					String numbers[]= line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					//riempiamo
					mapTileNum[col][row] = num;
					col++;
					
				}
				if(col==gp.maxScreenCol) {
					col=0;
					row++;
				}
			}
			br.close();
		}catch(Exception e){
			
		}
		
	}
	public void draw(Graphics2D g2) {
		//metodo non efficente per implementare tile
		/*
		g2.drawImage(tile[0].image, 0, 0, gp.tileSize, gp.tileSize,null);
		g2.drawImage(tile[1].image, 48, 0, gp.tileSize, gp.tileSize,null);
		g2.drawImage(tile[2].image, 96, 0, gp.tileSize, gp.tileSize,null);
	    */
		int col=0;
		int row=0;
		int x=0;
		int y=0;
		
		
		while(col<gp.maxScreenCol && row<gp.maxScreenRow) {
			int tileNum = mapTileNum[col][row]; 
			g2.drawImage(tile[tileNum].image, x, y,gp.tileSize,gp.tileSize,null);
			col++;
			x += gp.tileSize;
			
			if(col == gp.maxScreenCol) {
				col=0;
				x=0;
				row++;
				y += gp.tileSize;
			}
		 
		
		
		}
		//
		
	}
}
