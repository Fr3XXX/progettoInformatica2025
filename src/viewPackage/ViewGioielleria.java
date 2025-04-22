package viewPackage;

import java.awt.Color;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.SwingUtilities;

import modelPackage.GameObject;

public class ViewGioielleria extends GameObject {

    private boolean frameVisible = false;
    boolean open = false;
    GamePanel gamePanel;
    MyFrameNegozio frameGioielleria;
    JButton closeFrame;
    
    public ViewGioielleria(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
        super(gamePanel, path, x, y, size_x, size_y);
        this.gamePanel = gamePanel;
        
    }

    @Override
    public void update() {
        if (InputManager.isMouseOver(this) && InputManager.isMousePressed("left") && !open) {
            frameVisible = true;
            createFrame();
            open = true; // evita doppie aperture causate da click multipli

            if (frameVisible) {
                frameGioielleria.requestFocusInWindow();//mette il focus sulla finestra della gioielleria
            }
        }
        
        //System.out.println(gamePanel.hasFocus());
        super.update();
    }

    public void createFrame() {
        frameGioielleria = new MyFrameNegozio();

        // Bottone di chiusura
        closeFrame = new JButton("Exit");
        closeFrame.setFont(new Font("Arial", Font.BOLD, 20));
        closeFrame.setForeground(Color.WHITE);
        closeFrame.setBackground(new Color(220, 20, 60)); // Rosso acceso
        closeFrame.setFocusPainted(false);
        closeFrame.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        closeFrame.setCursor(new Cursor(Cursor.HAND_CURSOR));

    
        closeFrame.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                closeFrame.setBackground(new Color(255, 69, 0)); // Rosso arancio al passaggio
            }

            public void mouseExited(MouseEvent e) {
                closeFrame.setBackground(new Color(220, 20, 60));
            }

            public void mouseClicked(MouseEvent e) {
                ViewGioielleria.this.open = false;
                frameGioielleria.dispose();

                // Qui restituiamo il focus al gamePanel dopo la chiusura
                SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
            }
        });

        closeFrame.setVisible(true);
        frameGioielleria.add(closeFrame);
        frameGioielleria.setLocationRelativeTo(null); // centra la finestra
        frameGioielleria.setVisible(frameVisible);
    }
}
