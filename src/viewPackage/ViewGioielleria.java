package viewPackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import modelPackage.GameObject;
import modelPackage.User;

public class ViewGioielleria extends GameObject {

    private boolean frameVisible = false;
    boolean open = false;
    GamePanel gamePanel;
    MyFrameNegozio frameGioielleria;
    JButton closeFrame;
    JButton pulsanteRosso;
    JLabel labelContatore;

    public ViewGioielleria(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
        super(gamePanel, path, x, y, size_x, size_y);
        this.gamePanel = gamePanel;
    }

    @Override
    public void update() {
        if (InputManager.isMouseOver(this) && InputManager.isMousePressed("left") && !open) {
            frameVisible = true;
            creaFrame();
            open = true;

            if (frameVisible) {
                frameGioielleria.requestFocusInWindow();
            }
        }
        super.update();
    }

    public void creaFrame() {
        frameGioielleria = new MyFrameNegozio();
        frameGioielleria.setSize(850, 650);
        frameGioielleria.setLayout(new BoxLayout(frameGioielleria.getContentPane(), BoxLayout.Y_AXIS));

        creaBarraSuperiore();
        creaPannelloBudget();
        creaPannelliContenuto();
        creaPannelloPulsanti();

        frameGioielleria.setLocationRelativeTo(null);
        frameGioielleria.setVisible(frameVisible);
    }

    private void creaBarraSuperiore() {
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Pulsante Upgrade
        JButton upgradeButton = new JButton("UPGRADE");
        stylePulsante(upgradeButton, new Color(255, 165, 0), new Dimension(150, 40));

        // Pulsante Acquista Dipendenti
        JButton hireButton = new JButton("ACQUISTA DIP.");
        stylePulsante(hireButton, new Color(75, 0, 130), new Dimension(150, 40));

        topPanel.add(upgradeButton);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(hireButton);
        topPanel.add(Box.createHorizontalGlue());

        // Pulsante Chiudi
        closeFrame = new JButton("CHIUDI");
        stylePulsante(closeFrame, new Color(220, 20, 60), new Dimension(150, 40));
        closeFrame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ViewGioielleria.this.open = false;
                frameGioielleria.dispose();
                SwingUtilities.invokeLater(() -> gamePanel.requestFocusInWindow());
            }
        });

        topPanel.add(closeFrame);
        frameGioielleria.add(topPanel);
    }

    private void creaPannelloBudget() {
        JPanel budgetPanel = new JPanel();
        budgetPanel.setLayout(new BoxLayout(budgetPanel, BoxLayout.X_AXIS));
        budgetPanel.setBorder(BorderFactory.createTitledBorder("Soldi / Budget"));
        budgetPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JLabel budgetLabel = new JLabel("Budget: " + User.patrimonioUtente + "$");
        budgetLabel.setFont(new Font("Arial", Font.BOLD, 18));
        budgetPanel.add(Box.createHorizontalGlue());
        budgetPanel.add(budgetLabel);
        budgetPanel.add(Box.createHorizontalGlue());

        frameGioielleria.add(Box.createVerticalStrut(15));
        frameGioielleria.add(budgetPanel);
        frameGioielleria.add(Box.createVerticalStrut(25));
    }

    private void creaPannelliContenuto() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JPanel inventoryPanel = new JPanel();
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Grafica Libri in Magazzino"));
        inventoryPanel.setPreferredSize(new Dimension(220, 420));
        contentPanel.add(inventoryPanel);
        contentPanel.add(Box.createHorizontalStrut(25));

        JPanel mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Grafica Libreria"));
        mainPanel.setPreferredSize(new Dimension(320, 420));
        contentPanel.add(mainPanel);
        contentPanel.add(Box.createHorizontalStrut(25));

        JPanel transactionPanel = new JPanel();
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Lista delle Transazioni"));
        transactionPanel.setPreferredSize(new Dimension(220, 420));
        contentPanel.add(transactionPanel);

        frameGioielleria.add(contentPanel);
        frameGioielleria.add(Box.createVerticalStrut(30));
    }

    private void creaPannelloPulsanti() {
        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.X_AXIS));
        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0)); // Spostamento a destra
        
        // Pannello per pulsante rosso e contatore
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        
        // Pulsante rosso più grande (50x50 invece di 40x40)
        pulsanteRosso = new JButton();
        pulsanteRosso.setName("pulsanteRosso");
        pulsanteRosso.setBackground(Color.RED);
        pulsanteRosso.setPreferredSize(new Dimension(50, 50)); // Dimensioni aumentate
        pulsanteRosso.setMaximumSize(new Dimension(50, 50));
        pulsanteRosso.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pulsanteRosso.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pulsanteRosso.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                pulsanteRosso.setBackground(new Color(255, 100, 100));
            }
            public void mouseExited(MouseEvent e) {
                pulsanteRosso.setBackground(Color.RED);
            }
        });

        // Label contatore più grande (40x40 invece di 30x30)
        labelContatore = new JLabel("0");
        labelContatore.setName("labelContatore");
        labelContatore.setOpaque(true);
        labelContatore.setBackground(Color.WHITE);
        labelContatore.setForeground(Color.BLACK);
        labelContatore.setFont(new Font("Arial", Font.BOLD, 18)); // Font più grande
        labelContatore.setHorizontalAlignment(JLabel.CENTER);
        labelContatore.setPreferredSize(new Dimension(40, 40)); // Dimensioni aumentate
        labelContatore.setMaximumSize(new Dimension(40, 40));
        labelContatore.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        leftPanel.add(pulsanteRosso);
        leftPanel.add(Box.createHorizontalStrut(10)); // Spaziatura aumentata
        leftPanel.add(labelContatore);
        
        // Pannello centrale per i pulsanti principali
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        JButton buyButton = new JButton("ACQUISTA LIBRI");
        stylePulsanteCentrato(buyButton, new Color(50, 150, 50));

        JButton priceButton = new JButton("IMPOSTA PREZZO");
        stylePulsanteCentrato(priceButton, new Color(70, 70, 200));

        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(buyButton);
        centerPanel.add(Box.createHorizontalStrut(50));
        centerPanel.add(priceButton);
        centerPanel.add(Box.createHorizontalGlue());

        // Aggiunta dei pannelli
        mainButtonPanel.add(leftPanel);
        mainButtonPanel.add(Box.createHorizontalGlue());
        mainButtonPanel.add(centerPanel);
        mainButtonPanel.add(Box.createHorizontalGlue());

        frameGioielleria.add(mainButtonPanel);
        frameGioielleria.add(Box.createVerticalStrut(20));
    }

    private void stylePulsante(JButton button, Color backgroundColor, Dimension size) {
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(size);
        button.setMaximumSize(size);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    }

    private void stylePulsanteCentrato(JButton button, Color backgroundColor) {
        button.setFont(new Font("Arial", Font.BOLD, 20));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createRaisedBevelBorder());
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(250, 60));
        button.setMaximumSize(new Dimension(250, 60));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.brighter());
            }
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor);
            }
        });
    }

    public JButton getPulsanteRosso() {
        return pulsanteRosso;
    }

    public JLabel getLabelContatore() {
        return labelContatore;
    }
}