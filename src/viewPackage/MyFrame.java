package viewPackage;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeListener;
import modelPackage.*;
import controlPackage.*;

public class MyFrame extends JFrame {
    
    private JLabel patrimonioLabel;
    private User utente;
    
    public MyFrame(String titolo) {
        super(titolo);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        
        // Configura il layout principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        
        GamePanel gamePanel = new GamePanel(this);
        
        // Crea e configura il label del patrimonio
        patrimonioLabel = new JLabel("PATRIMONIO TOT: 0$");
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
        this.setVisible(true);
        
        // Inizializza utente e controller
        utente = new User();
        ControllerUtente controllerUtente = new ControllerUtente(utente);
        
//        // Configura listener per aggiornamenti
//        utente.addPropertyChangeListener(e -> {
//            if("patrimonioUtente".equals(e.getPropertyName())) {
//                SwingUtilities.invokeLater(() -> {
//                    patrimonioLabel.setText("PATRIMONIO TOT: " + e.getNewValue() + "$");
//                });
//            }
//        });
        
        // Esempio negozio
        ViewNegozio viewVestiti = new ViewNegozio(gamePanel, "/tiles/negozioVestiti.png", 50, 50, 64, 64);
		
		
		ViewNegozio viewLibreia= new ViewNegozio(gamePanel, "/tiles/negozioLibreria.png", 1300, 50, 64, 64);
		
		
		ViewNegozio viewElettronica= new ViewNegozio(gamePanel, "/tiles/negozioElettronica.png", 650, 500, 64, 64);
		
		
		ViewNegozio viewGioielleria= new ViewNegozio(gamePanel, "/tiles/negozioGioielleria.png", 650, 50, 64, 64);
		
		
		ViewNegozio viewConcessionario= new ViewNegozio(gamePanel, "/tiles/negozioConcessionario.png", 1300, 500, 64, 64);
		
		
		ViewNegozio viewGameStop= new ViewNegozio(gamePanel, "/tiles/negozioGameStop.png", 50, 500, 64, 64);
		
        controllerUtente.acquistaNegozio(0, new Gioielleria(gamePanel, viewGioielleria));
        
        gamePanel.startGameThread();
    }
}