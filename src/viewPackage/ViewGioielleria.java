package viewPackage;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modelPackage.GameObject;



public class ViewGioielleria extends GameObject {
    private JPanel frameGioielleria;
    private boolean frameVisible = false;
    GamePanel gamePanel;
    
    public ViewGioielleria(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
        super(gamePanel, path, x, y, size_x, size_y);
        this.gamePanel = gamePanel;
        createPanel();
    }
    
    public void createPanel() {

    }
    
    @Override
    public void update() {
        if(InputManager.isMouseOver(this) && InputManager.isMousePressed("left")) {
        	frameVisible = !frameVisible;
            getFrameGioielleria().setVisible(frameVisible);
            
            if(frameVisible) {
            	getFrameGioielleria().requestFocusInWindow();
            }
        }
        super.update();
    }

	public JPanel getFrameGioielleria() {
		return frameGioielleria;
	}

	public void setFrameGioielleria(JPanel frameGioielleria) {
		this.frameGioielleria = frameGioielleria;
	}

	
}
