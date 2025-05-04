package viewPackage;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import modelPackage.*;
import controlPackage.*;

public class MyFrame extends JFrame {
    
    public JLabel patrimonioLabel;
    private User utente;
    
    public MyFrame(String titolo) {
        super(titolo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // Configura il layout principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        GamePanel gamePanel = new GamePanel(this);
        
        // Crea e configura il label del patrimonio
        patrimonioLabel = new JLabel("PATRIMONIO: " + User.patrimonioUtente + "$");
        patrimonioLabel.setFont(new Font("Arial", Font.BOLD, 24));
        patrimonioLabel.setForeground(Color.YELLOW);
        patrimonioLabel.setHorizontalAlignment(SwingConstants.CENTER);
        patrimonioLabel.setOpaque(true);
        patrimonioLabel.setBackground(new Color(0, 0, 0, 150));
        
        // Aggiungi i componenti
        mainPanel.add(patrimonioLabel, BorderLayout.NORTH);
        mainPanel.add(gamePanel, BorderLayout.CENTER);
        this.add(mainPanel);
        
        // Configurazione finestra
        this.setLocationRelativeTo(null);
        this.setUndecorated(true);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Inizializza utente e controller
        utente = new User();
        ControllerUtente controllerUtente = new ControllerUtente(utente);
        
        ViewNegozio viewVestiti = new ViewNegozio(gamePanel, "/tiles/negozioVestiti.png", 50, 50, 64, 64, this, controllerUtente, "Vestiti");
		
		
		ViewNegozio viewLibreria= new ViewNegozio(gamePanel, "/tiles/negozioLibreria.png", 1300, 50, 64, 64, this, controllerUtente, "Libreria");
		
		
		ViewNegozio viewElettronica= new ViewNegozio(gamePanel, "/tiles/negozioElettronica.png", 650, 500, 64, 64, this, controllerUtente, "Elettronica");
		
		
		ViewNegozio viewGioielleria= new ViewNegozio(gamePanel, "/tiles/negozioGioielleria.png", 650, 50, 64, 64, this, controllerUtente, "Gioielleria");
		
		
		ViewNegozio viewConcessionario= new ViewNegozio(gamePanel, "/tiles/negozioConcessionario.png", 1300, 500, 64, 64, this, controllerUtente, "Concessionario");
		
		
		ViewNegozio viewGameStop= new ViewNegozio(gamePanel, "/tiles/negozioGameStop.png", 50, 500, 64, 64, this, controllerUtente, "GameStop");
        
        gamePanel.startGameThread();
        
        this.setVisible(true);
    }
}