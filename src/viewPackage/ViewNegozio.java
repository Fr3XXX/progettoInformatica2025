package viewPackage;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import modelPackage.GameObject;
import modelPackage.User;
import controlPackage.*;

public class ViewNegozio extends GameObject {

    ControllerNegozio controller;
    
    private boolean frameVisible = false;
    boolean open = false;
    boolean upgradeCreato = false;
    GamePanel gamePanel;
    MyFrameNegozio frameGioielleria;
    JButton closeFrame;
    JButton pulsanteRosso;
    JLabel labelContatore;
    
    // Array di upgrade disponibili
    public String[] upgrades = {
        "Lvl 1, Dimensione Magazzino ",
        "Lvl 1, Dimensione Scaffali ",
    };
    
    // Variabili per i frame
    private JFrame upgradeFrame;
    private JFrame dipendentiFrame;
    
    private JPanel topPanel;
    private JButton upgradeButton;
    private JButton bottoneDipendenti;
    private JPanel budgetPanel;
    private JLabel budgetLabel;
    private JPanel contentPanel;
    private JPanel inventoryPanel;
    private JPanel mainPanel;
    private JPanel transactionPanel;
    private JPanel mainButtonPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JButton buyButton;
    private JButton priceButton;
    private JPanel upgradeMainPanel;
    private JButton upgradeChiudiButton;
    private JPanel dipendentiMainPanel;
    private JButton dipendentiChiudiButton;

    public ViewNegozio(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y) {
        super(gamePanel, path, x, y, size_x, size_y);
        this.gamePanel = gamePanel;
    }

    public void addController(ControllerNegozio controller) {
        this.controller = controller;
        upgrades[0] += "- " + controller.getPrezzoUpgradeMagazzino();
        upgrades[1] += "- " + controller.getPrezzoUpgradeScaffali();
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
        else if(open){
            
        	this.aggiornaValori();
            
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
        topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.X_AXIS));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Pulsante Upgrade con listener per aprire il frame piccolo
        upgradeButton = new JButton("UPGRADE");
        stylePulsante(upgradeButton, new Color(255, 165, 0), new Dimension(150, 40));
        upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriFrameUpgrade();
            }
        });

        // Pulsante Acquista Dipendenti
        bottoneDipendenti = new JButton("DIPENDENTI");
        stylePulsante(bottoneDipendenti, new Color(75, 0, 130), new Dimension(150, 40));
        bottoneDipendenti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriFrameDipendenti();
            }
        });

        topPanel.add(upgradeButton);
        topPanel.add(Box.createHorizontalStrut(15));
        topPanel.add(bottoneDipendenti);
        topPanel.add(Box.createHorizontalGlue());

        // Pulsante Chiudi
        closeFrame = new JButton("CHIUDI");
        stylePulsante(closeFrame, new Color(220, 20, 60), new Dimension(150, 40));
        closeFrame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ViewNegozio.this.open = false;
                frameGioielleria.dispose();
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        gamePanel.requestFocusInWindow();
                    }
                });
            }
        });

        topPanel.add(closeFrame);
        frameGioielleria.add(topPanel);
    }

    private void apriFrameUpgrade() {
        this.upgradeCreato = true;
        upgradeFrame = new JFrame("Upgrade Disponibili");
        upgradeFrame.setSize(350, 250);
        upgradeFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        upgradeFrame.setLayout(new BoxLayout(upgradeFrame.getContentPane(), BoxLayout.Y_AXIS));
        
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) (screenSize.getWidth() - upgradeFrame.getWidth());
        int y = (int) (screenSize.getHeight() - upgradeFrame.getHeight());
        upgradeFrame.setLocation(x, y);
        upgradeMainPanel = new JPanel();
        upgradeMainPanel.setLayout(new BoxLayout(upgradeMainPanel, BoxLayout.Y_AXIS));
        upgradeMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    
        for (String upgrade : upgrades) {
            JPanel upgradePanel = new JPanel();
            upgradePanel.setLayout(new BoxLayout(upgradePanel, BoxLayout.X_AXIS));
            upgradePanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            
            JLabel upgradeLabel = new JLabel(upgrade);
            upgradeLabel.setFont(new Font("Arial", Font.BOLD, 14));
            
            JButton acquistaButton = new JButton("Acquista");
            acquistaButton.setFont(new Font("Arial", Font.PLAIN, 12));
            acquistaButton.setPreferredSize(new Dimension(100, 25));
            acquistaButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(upgrade == upgrades[0]) {
                        controller.upgradeDimensioneMagazzino();
                    }
                    else {
                        controller.upgradeDimensioneScaffali();
                    }
                }
            });
            
            upgradePanel.add(upgradeLabel);
            upgradePanel.add(Box.createHorizontalGlue());
            upgradePanel.add(acquistaButton);
            
            upgradeMainPanel.add(upgradePanel);
            if (!upgrade.equals(upgrades[upgrades.length - 1])) {
                upgradeMainPanel.add(Box.createVerticalStrut(5));
            }
        }
        
        upgradeChiudiButton = new JButton("Chiudi");
        upgradeChiudiButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                upgradeFrame.dispose();
            }
        });
        upgradeChiudiButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
        
        upgradeMainPanel.add(Box.createVerticalStrut(15));
        upgradeMainPanel.add(upgradeChiudiButton);
        
        upgradeFrame.add(upgradeMainPanel);
        upgradeFrame.setVisible(true);
    }

    private void apriFrameDipendenti() {
        if (dipendentiFrame == null) {
            dipendentiFrame = new JFrame("Acquista Dipendenti");
            dipendentiFrame.setSize(350, 300);
            dipendentiFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            dipendentiFrame.setLayout(new BoxLayout(dipendentiFrame.getContentPane(), BoxLayout.Y_AXIS));
            
            Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
            int x = (int) (screenSize.getWidth() - dipendentiFrame.getWidth());
            int y = (int) (screenSize.getHeight() - dipendentiFrame.getHeight());
            dipendentiFrame.setLocation(x, y);
            
            dipendentiMainPanel = new JPanel();
            dipendentiMainPanel.setLayout(new BoxLayout(dipendentiMainPanel, BoxLayout.Y_AXIS));
            dipendentiMainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            for (int i = 0; i < 3; i++) {
                final int index = i;
                JPanel dipPanel = new JPanel();
                dipPanel.setLayout(new BoxLayout(dipPanel, BoxLayout.X_AXIS));
                dipPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                JLabel dipLabel = new JLabel("");
                dipLabel.setFont(new Font("Arial", Font.BOLD, 14));
                
                switch(i) {
                    case 0:
                        dipLabel.setText("Magazziniere - " + controller.getPrezzoDipendenti());
                        break;
                    case 1:
                        dipLabel.setText("Cassiere - " + controller.getPrezzoDipendenti());
                        break;
                    case 2:
                        dipLabel.setText("Commerciante - " + controller.getPrezzoDipendenti());
                        break;
                    default:
                        System.out.println("ERRORE");
                }
                
                JButton acquistaButton = new JButton();
                if(controller.isAcquistato(i)) {
                    acquistaButton.setText("Già posseduto");
                    acquistaButton.setEnabled(false);
                }
                else {
                    acquistaButton.setText("Acquista");
                }
                acquistaButton.setFont(new Font("Arial", Font.PLAIN, 12));
                acquistaButton.setPreferredSize(new Dimension(100, 25));
                acquistaButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.acquistaDipendente(index);
                    }
                });
                
                dipPanel.add(dipLabel);
                dipPanel.add(Box.createHorizontalGlue());
                dipPanel.add(acquistaButton);
                
                dipendentiMainPanel.add(dipPanel);
                if (i < 5) {
                    dipendentiMainPanel.add(Box.createVerticalStrut(5));
                }
            }
            
            dipendentiChiudiButton = new JButton("Chiudi");
            dipendentiChiudiButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dipendentiFrame.dispose();
                }
            });
            dipendentiChiudiButton.setAlignmentX(JPanel.CENTER_ALIGNMENT);
            
            dipendentiMainPanel.add(Box.createVerticalStrut(15));
            dipendentiMainPanel.add(dipendentiChiudiButton);
            
            dipendentiFrame.add(dipendentiMainPanel);
        }
        
        dipendentiFrame.setVisible(true);
    }

    private void creaPannelloBudget() {
        budgetPanel = new JPanel();
        budgetPanel.setLayout(new BoxLayout(budgetPanel, BoxLayout.X_AXIS));
        budgetPanel.setBorder(BorderFactory.createTitledBorder("Soldi / Budget"));
        budgetPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        budgetLabel = new JLabel("Budget: " + User.patrimonioUtente + "$");
        budgetLabel.setFont(new Font("Arial", Font.BOLD, 18));
        budgetPanel.add(Box.createHorizontalGlue());
        budgetPanel.add(budgetLabel);
        budgetPanel.add(Box.createHorizontalGlue());

        frameGioielleria.add(Box.createVerticalStrut(15));
        frameGioielleria.add(budgetPanel);
        frameGioielleria.add(Box.createVerticalStrut(25));
    }

    private void creaPannelliContenuto() {
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.X_AXIS));
        contentPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        inventoryPanel = new JPanel();
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Grafica Libri in Magazzino"));
        inventoryPanel.setPreferredSize(new Dimension(220, 420));
        contentPanel.add(inventoryPanel);
        contentPanel.add(Box.createHorizontalStrut(25));

        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Grafica Libreria"));
        mainPanel.setPreferredSize(new Dimension(320, 420));
        contentPanel.add(mainPanel);
        contentPanel.add(Box.createHorizontalStrut(25));

        transactionPanel = new JPanel();
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Lista delle Transazioni"));
        transactionPanel.setPreferredSize(new Dimension(220, 420));
        contentPanel.add(transactionPanel);

        frameGioielleria.add(contentPanel);
        frameGioielleria.add(Box.createVerticalStrut(30));
    }

    private void creaPannelloPulsanti() {
        mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.X_AXIS));
        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        
        pulsanteRosso = new JButton();
        pulsanteRosso.setName("pulsanteRosso");
        pulsanteRosso.setBackground(Color.RED);
        pulsanteRosso.setPreferredSize(new Dimension(50, 50));
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

        labelContatore = new JLabel("0");
        labelContatore.setName("labelContatore");
        labelContatore.setOpaque(true);
        labelContatore.setBackground(Color.WHITE);
        labelContatore.setForeground(Color.BLACK);
        labelContatore.setFont(new Font("Arial", Font.BOLD, 18));
        labelContatore.setHorizontalAlignment(JLabel.CENTER);
        labelContatore.setPreferredSize(new Dimension(40, 40));
        labelContatore.setMaximumSize(new Dimension(40, 40));
        labelContatore.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        leftPanel.add(pulsanteRosso);
        leftPanel.add(Box.createHorizontalStrut(10));
        leftPanel.add(labelContatore);
        
        centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.X_AXIS));
        centerPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        buyButton = new JButton("ACQUISTA LIBRI");
        stylePulsanteCentrato(buyButton, new Color(50, 150, 50));

        priceButton = new JButton("IMPOSTA PREZZO");
        stylePulsanteCentrato(priceButton, new Color(70, 70, 200));

        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(buyButton);
        centerPanel.add(Box.createHorizontalStrut(50));
        centerPanel.add(priceButton);
        centerPanel.add(Box.createHorizontalGlue());

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
    
    private void aggiornaValori() {
        pulsanteRosso.setBackground(Color.RED);
    }
    
    
}