package viewPackage;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import modelPackage.GameObject;



public class ViewGioielleria extends GameObject {
    private JPanel panelGioielleria;
    private boolean panelVisible = false;
    GamePanel gamePanel;
    
    public ViewGioielleria(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
        super(gamePanel, path, x, y, size_x, size_y);
        this.gamePanel = gamePanel;
        createPanel();
    }
    
    public void createPanel() {
        setPanelGioielleria(new JPanel());
//        panelGioielleria.setBounds(0, 0, gamePanel.getWidth(), gamePanel.getHeight());
        getPanelGioielleria().setSize(300,300);
        getPanelGioielleria().setBackground(new Color(0, 0, 255, 200)); // Blu con trasparenza
        getPanelGioielleria().setLayout(null);
        getPanelGioielleria().setVisible(true);
        
        JLabel label = new JLabel("Benvenuto nella Gioielleria!");
        label.setBounds(100, 50, 300, 30);
        label.setForeground(Color.WHITE);
        getPanelGioielleria().add(label);
        
//        gamePanel.add(panelGioielleria);
//        getPanelGioielleria().setFocusable(true);
    }
    
    @Override
    public void update() {
        if(InputManager.isMouseOver(this) && InputManager.isMousePressed("left")) {
            panelVisible = !panelVisible;
            getPanelGioielleria().setVisible(panelVisible);
            
            if(panelVisible) {
                getPanelGioielleria().requestFocusInWindow();
            }
        }
        super.update();
    }

	public JPanel getPanelGioielleria() {
		return panelGioielleria;
	}

	public void setPanelGioielleria(JPanel panelGioielleria) {
		this.panelGioielleria = panelGioielleria;
	}
}
