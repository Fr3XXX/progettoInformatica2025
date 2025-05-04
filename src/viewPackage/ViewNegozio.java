
package viewPackage;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.JFrame;
import modelPackage.GameObject;
import modelPackage.User;
import controlPackage.*;

public class ViewNegozio extends GameObject {

    ControllerNegozio controller;
    
    private int tipoNegozio = -1;
    private boolean frameVisible = false;
    private boolean open = false;
    private boolean controllerAdded = false;
    private GamePanel gamePanel;
    private MyFrameNegozio frameGioielleria;
    private JButton closeFrame;
    private JButton pulsanteRosso;
    private Color cassaBackgroundColor = Color.RED;
    private JLabel labelContatore;
    
    // Array di upgrade disponibili
    private  String[] upgrades = {
        "Lvl 1, Dimensione Magazzino ",
        "Lvl 1, Dimensione Scaffali ",
    };
    
    // Variabili per i frame
    private JFrame upgradeFrame;
    private JFrame dipendentiFrame;
    private JFrame magazzinoFrame;
    
 // Variabili per la finestra degli scaffali
    private JFrame scaffaliFrame;
    private JButton spostaButton;
    private JPanel spostaContainer;
    private JLabel numeroProdottiLabelScaffali;
    private JLabel quantitaLabelScaffali;
    private JPanel quantitaPanelScaffali;
    private JPanel mainPanelScaffali;
    private JLabel prodottiLabelScaffali;
    private JButton decrementButtonScaffali;
    private JButton incrementButtonScaffali;
    private JLabel quantityLabelScaffali;
    private int currentQuantityScaffali = 1;  // Valore corrente della quantità
    private JPanel totalePanelScaffali;
    private JPanel prezzoPanelScaffali;
    private JPanel containerPanelScaffali;
    private JLabel prezzoLabelScaffali;
    private boolean finestraScaffaliCreata = false;
    
    
    
    // Variabili per la finestra del magazzino
    private JPanel acquistaContainer;
    private JButton acquistaButtonMagazzino;
    private JLabel numeroProdottiLabelMagazzino;
    private JLabel quantitaLabelMagazzino;
    private JPanel quantitaPanelMagazzino;
    private JPanel mainPanelMagazzino;
    private JLabel prodottiLabelMagazzino;
    private JButton decrementButton;
    private JButton incrementButton;
    private JLabel quantityLabel;
    private int currentQuantity = 1;  // Valore corrente della quantità
    private JPanel totalePanelMagazzino;
    private JPanel prezzoPanel;
    private JPanel containerPanel;
    private JLabel prezzoLabel;
    private boolean finestraMagazzinoCreata = false;
    
    //variabili per la finestra dei dipendenti
    private JPanel dipendentiMainPanel;
    private JLabel[] dipLabel = new JLabel[3];
    private JPanel[] dipPanel = new JPanel[3];
    private JButton[] acquistaButton = new JButton[3];
    private boolean finestraDipendentiCreata = false;
    
    //variabili per la finestra di upgrade
    private JButton upgradeChiudiButton;
    private JButton[] acquistaButtonUpgrade = new JButton[getUpgrades().length];
    private JPanel[] upgradePanel = new JPanel[getUpgrades().length];
    private JLabel[] upgradeLabel = new JLabel[getUpgrades().length];
    private boolean finestraUpgradeCreata = false;
    
    //variabili per la grafica del magazzino e dello scaffale
    private JPanel inventoryPanel;
    private ArrayList<JPanel> celleScaffale;
    private ArrayList<JPanel> celleMagazzino;
    private double width; //dimensioni delle celle
    private double height;
    private int lastScaffaleCount = -1;
    private int lastMagazzinoCount = -1;
    
    // Altre variabili di interfaccia
    private JPanel topPanel;
    private JPanel upgradeMainPanel;
    private JButton upgradeButton;
    private JButton bottoneDipendenti;
    private JPanel budgetPanel;
    private JLabel budgetLabel;
    private JPanel contentPanel;
    private JPanel mainPanel;
    private JPanel transactionPanel;
    private JPanel mainButtonPanel;
    private JPanel leftPanel;
    private JPanel centerPanel;
    private JButton acquistaButtonProdotti;
    private JButton priceButton;
    
    private MyFrame citta;

    public ViewNegozio(GamePanel gamePanel, String path, int x, int y, int size_x, int size_y, MyFrame citta) {
        super(gamePanel, path, x, y, size_x, size_y);
        this.gamePanel = gamePanel;
        this.citta = citta;
    }

    public void addController(ControllerNegozio controller) {
        this.controller = controller;
        getUpgrades()[0] += "- " + controller.getPrezzoUpgradeMagazzino();
        getUpgrades()[1] += "- " + controller.getPrezzoUpgradeScaffali();
        tipoNegozio = controller.tipoNegozio();
        controllerAdded = true;
    }
    
    @Override
    public void update() {
        if (InputManager.isMouseOver(this) && InputManager.isMousePressed("left") && !open) {
            frameVisible = true;
            creaFrame();
            open = true;
            citta.setVisible(false);
            if (frameVisible) {
                frameGioielleria.requestFocusInWindow();
            }
        }
        else if(!open) {
        	citta.patrimonioLabel.setText("PATRIMONIO: " + User.patrimonioUtente + "$");
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

        upgradeButton = new JButton("UPGRADE");
        stylePulsante(upgradeButton, new Color(255, 165, 0), new Dimension(150, 40));
        upgradeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriFrameUpgrade();
            }
        });

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

        closeFrame = new JButton("CHIUDI");
        stylePulsante(closeFrame, new Color(220, 20, 60), new Dimension(150, 40));
        closeFrame.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                ViewNegozio.this.open = false;
                citta.setVisible(true);
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
    	finestraUpgradeCreata = true;
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
        int i = 0;
        for (String upgrade : getUpgrades()) {
        	final int index = i;  // Creo una copia final di i
            upgradePanel[i] = new JPanel();
            upgradePanel[i].setLayout(new BoxLayout(upgradePanel[i], BoxLayout.X_AXIS));
            upgradePanel[i].setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
            
            upgradeLabel[i] = new JLabel(upgrade);
            upgradeLabel[i].setFont(new Font("Arial", Font.BOLD, 14));
            
            acquistaButtonUpgrade[i] = new JButton("Acquista");
            acquistaButtonUpgrade[i].setFont(new Font("Arial", Font.PLAIN, 12));
            acquistaButtonUpgrade[i].setPreferredSize(new Dimension(100, 25));
            acquistaButtonUpgrade[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(index==0) {
                    	System.out.println("entrato in upgrade magazzino");
                        controller.upgradeDimensioneMagazzino();
                    }
                    else {
                    	System.out.println("entrato in upgrade scaffali");
                        controller.upgradeDimensioneScaffali();
                    }
                }
            });
            
            upgradePanel[i].add(upgradeLabel[i]);
            upgradePanel[i].add(Box.createHorizontalGlue());
            upgradePanel[i].add(acquistaButtonUpgrade[i]);
            
            upgradeMainPanel.add(upgradePanel[i]);
            if (!upgrade.equals(getUpgrades()[getUpgrades().length - 1])) {
                upgradeMainPanel.add(Box.createVerticalStrut(5));
            }
            i++;
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
     
    		finestraDipendentiCreata = true;
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
                dipPanel[i] = new JPanel();
                dipPanel[i].setLayout(new BoxLayout(dipPanel[i], BoxLayout.X_AXIS));
                dipPanel[i].setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
                dipLabel[i] = new JLabel("");
                dipLabel[i].setFont(new Font("Arial", Font.BOLD, 14));
                
                switch(i) {
                    case 0:
                        dipLabel[i].setText("Magazziniere - " + controller.getPrezzoDipendenti());
                        break;
                    case 1:
                        dipLabel[i].setText("Cassiere - " + controller.getPrezzoDipendenti());
                        break;
                    case 2:
                        dipLabel[i].setText("Commerciante - " + controller.getPrezzoDipendenti());
                        break;
                    default:
                        System.out.println("ERRORE");
                }
                
                acquistaButton[i] = new JButton();
                if(controller.isAcquistato(i)) {
                    acquistaButton[i].setText("Già posseduto");
                    acquistaButton[i].setEnabled(false);
                }
                else {
                    acquistaButton[i].setText("Acquista");
                }
                acquistaButton[i].setFont(new Font("Arial", Font.PLAIN, 12));
                acquistaButton[i].setPreferredSize(new Dimension(100, 25));
                acquistaButton[i].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        controller.acquistaDipendente(index);
                    }
                });
                
                dipPanel[i].add(dipLabel[i]);
                dipPanel[i].add(Box.createHorizontalGlue());
                dipPanel[i].add(acquistaButton[i]);
                
                dipendentiMainPanel.add(dipPanel[i]);
                if (i < 5) {
                    dipendentiMainPanel.add(Box.createVerticalStrut(5));
                }
            }
            
            JButton dipendentiChiudiButton = new JButton("Chiudi");
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
        
        
            dipendentiFrame.setVisible(true);
    }

    private void creaPannelloBudget() {
        budgetPanel = new JPanel();
        budgetPanel.setLayout(new BoxLayout(budgetPanel, BoxLayout.X_AXIS));
        budgetPanel.setBorder(BorderFactory.createTitledBorder("Patrimonio"));
        budgetPanel.setAlignmentX(JPanel.CENTER_ALIGNMENT);

        budgetLabel = new JLabel("Patrimonio: " + User.patrimonioUtente + "$");
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

        //grafica magazzino
        inventoryPanel = new JPanel();
        inventoryPanel.setBorder(BorderFactory.createTitledBorder("Magazzino"));
        inventoryPanel.setPreferredSize(new Dimension(220, 420));
        contentPanel.add(inventoryPanel);
        contentPanel.add(Box.createHorizontalStrut(25));

        //grafica scaffale
        mainPanel = new JPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Grafica Libreria"));
        mainPanel.setPreferredSize(new Dimension(320, 420));
        contentPanel.add(mainPanel);
        contentPanel.add(Box.createHorizontalStrut(25));

        //grafica transazioni
        transactionPanel = new JPanel();
        transactionPanel.setBorder(BorderFactory.createTitledBorder("Transazioni"));
        transactionPanel.setPreferredSize(new Dimension(220, 420));
        contentPanel.add(transactionPanel);

        frameGioielleria.add(contentPanel);
        frameGioielleria.add(Box.createVerticalStrut(30));
    }

    private ArrayList<JPanel> dividiAdattivo(JPanel pannello, int n) {
        
    	
        pannello.removeAll();
        
        
        int bestRows = calcolaRighe(n); 
        int bestCols = (int) Math.ceil((double) n / bestRows);
        pannello.setLayout(new GridLayout(bestRows, bestCols, 2, 2));
        
        
        ArrayList<JPanel> celle = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            JPanel cella = new JPanel(new BorderLayout());
            cella.setOpaque(true);
            celle.add(cella);
            pannello.add(cella);
        }
        
        
        int totalCells = bestRows * bestCols;
        for (int i = n; i < totalCells; i++) {
            JPanel empty = new JPanel();
            empty.setOpaque(false);
            pannello.add(empty);
        }
        
        
        pannello.revalidate();
        pannello.repaint();
        
        return celle;
    }
    
    private int calcolaRighe(int n) {
    	
        int width = inventoryPanel.getPreferredSize().width;
        int height = inventoryPanel.getPreferredSize().height;
        
        double bestRatioDiff = 1;
        int bestRows = 1;
        
        for (int rows = 1; rows <= n; rows++) {
            int cols = (int) Math.ceil((double) n / rows);
            double cellWidth = (double) width / cols;
            double cellHeight = (double) height / rows;
            
            double ratio = cellWidth / cellHeight;
            double ratioDiff = Math.abs(1.0 - ratio);
            
            if (cols * rows >= n && ratioDiff < bestRatioDiff) {
                this.width = cellWidth;
                this.height = cellHeight;
                bestRatioDiff = ratioDiff;
                bestRows = rows;
            }
        }
        return bestRows;
    }
    
    public void aggiungiImmagineAllaCella(JPanel cella, String percorsoFile) {
    	
        cella.removeAll();
        cella.setBackground(frameGioielleria.getBackground());
        
        try {
        	cella.setBackground(Color.GRAY);
            ImageIcon icona = new ImageIcon(getClass().getResource(percorsoFile));
            if (icona.getIconWidth() <= 0) {
                System.out.println("Immagine non trovata: " + percorsoFile);
               
            }
            else {
            	Image imgRidimensionata = icona.getImage().getScaledInstance(
                        (int)this.width, (int)this.height, Image.SCALE_SMOOTH); 

                    JLabel etichetta = new JLabel(new ImageIcon(imgRidimensionata));
                    etichetta.setHorizontalAlignment(JLabel.CENTER);
                    etichetta.setVerticalAlignment(JLabel.CENTER);

                    cella.add(etichetta);
                    cella.revalidate();
                    cella.repaint();
            }
            
        } catch (Exception e) {
        	cella.setBackground(frameGioielleria.getBackground());
            System.err.println("Errore caricamento immagine: " + e.getMessage());
            cella.add(new JLabel("X"));
        }
    }
    
    private void creaPannelloPulsanti() {
        mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new BoxLayout(mainButtonPanel, BoxLayout.X_AXIS));
        mainButtonPanel.setBorder(BorderFactory.createEmptyBorder(0, 50, 0, 0));
        
        leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.X_AXIS));
        
        pulsanteRosso = new JButton();
        pulsanteRosso.setName("pulsanteRosso");
        pulsanteRosso.setBackground(this.cassaBackgroundColor);
        pulsanteRosso.setPreferredSize(new Dimension(50, 50));
        pulsanteRosso.setMaximumSize(new Dimension(50, 50));
        pulsanteRosso.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        pulsanteRosso.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        pulsanteRosso.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //apri frame cassa
            }
        });

        labelContatore = new JLabel(((Integer)controller.negozio.getCodaNegozio()).toString());
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

        acquistaButtonProdotti = new JButton("ACQUISTA LIBRI");
        stylePulsanteCentrato(acquistaButtonProdotti, new Color(50, 150, 50));
        acquistaButtonProdotti.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                apriFinestraMagazzino();
            }
        });

        priceButton = new JButton("IMPOSTA PREZZO");
        stylePulsanteCentrato(priceButton, new Color(70, 70, 200));
        priceButton.addActionListener(new ActionListener() {
        	 @Override
             public void actionPerformed(ActionEvent e) {
                 apriFinestraScaffali();
             }
        	
        });

        centerPanel.add(Box.createHorizontalGlue());
        centerPanel.add(acquistaButtonProdotti);
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

    private void apriFinestraScaffali() {
    	
    	finestraScaffaliCreata = true;
        scaffaliFrame = new JFrame("GESTIONE SCAFFALI");
        scaffaliFrame.setSize(350, 250); // Aumentata l'altezza per la nuova label
        scaffaliFrame.setLayout(new BoxLayout(scaffaliFrame.getContentPane(), BoxLayout.Y_AXIS));
        scaffaliFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        scaffaliFrame.setLocationRelativeTo(frameGioielleria);

        mainPanelScaffali = new JPanel();
        mainPanelScaffali.setLayout(new BoxLayout(mainPanelScaffali, BoxLayout.Y_AXIS));
        mainPanelScaffali.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titolo "PRODOTTI"
        prodottiLabelScaffali = new JLabel("GESTIONE SCAFFALI");
        prodottiLabelScaffali.setFont(new Font("Arial", Font.BOLD, 16));
        prodottiLabelScaffali.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mainPanelScaffali.add(prodottiLabelScaffali);
        mainPanelScaffali.add(Box.createVerticalStrut(10));

        // Pannello per il totale
        totalePanelScaffali = new JPanel();
        totalePanelScaffali.setLayout(new BoxLayout(totalePanelScaffali, BoxLayout.X_AXIS));
        numeroProdottiLabelScaffali = new JLabel("");
        if(controllerAdded) {
            numeroProdottiLabelScaffali.setText("TOTALE: " + ((Integer)controller.negozio.prodottiScaffale.size()).toString() + "/" + ((Integer)controller.negozio.getDimensioneScaffali()).toString());
        }
        else {
            numeroProdottiLabelScaffali.setText("TOTALE: Non definito");
        }
        numeroProdottiLabelScaffali.setFont(new Font("Arial", Font.PLAIN, 12));
        totalePanelScaffali.add(numeroProdottiLabelScaffali);
        totalePanelScaffali.add(Box.createHorizontalGlue());
        
        mainPanelScaffali.add(totalePanelScaffali);
        mainPanelScaffali.add(Box.createVerticalStrut(5));

        // Pannello prezzo d'acquisto
        prezzoPanelScaffali = new JPanel();
        prezzoPanelScaffali.setLayout(new BoxLayout(prezzoPanelScaffali, BoxLayout.X_AXIS));
        prezzoLabelScaffali = new JLabel("");
        if(controllerAdded) {
        	prezzoLabelScaffali.setText("Prezzo d'acquisto: " + controller.negozio.getPrezzoAcquisto() + "$");
        }
        else {
        	prezzoLabelScaffali.setText("Prezzo d'acquisto: Non definito");
        }
        prezzoLabelScaffali.setFont(new Font("Arial", Font.PLAIN, 12));
        prezzoPanelScaffali.add(prezzoLabelScaffali);
        prezzoPanelScaffali.add(Box.createHorizontalGlue());
        
        mainPanelScaffali.add(prezzoPanelScaffali);
        mainPanelScaffali.add(Box.createVerticalStrut(15));

        // Pannello quantità
        containerPanelScaffali = new JPanel();
        containerPanelScaffali.setLayout(new BoxLayout(containerPanelScaffali, BoxLayout.X_AXIS));
        containerPanelScaffali.add(Box.createHorizontalGlue());
        
        quantitaPanelScaffali = new JPanel();
        quantitaPanelScaffali.setLayout(new BoxLayout(quantitaPanelScaffali, BoxLayout.X_AXIS));
        
        quantitaLabelScaffali = new JLabel("Prezzo di vendita:");
        quantitaLabelScaffali.setFont(new Font("Arial", Font.PLAIN, 12));
        quantitaPanelScaffali.add(quantitaLabelScaffali);
        quantitaPanelScaffali.add(Box.createHorizontalStrut(5));
        
        // Pulsante decremento
        decrementButtonScaffali = new JButton("-1");
        decrementButtonScaffali.setFont(new Font("Arial", Font.BOLD, 12));
        decrementButtonScaffali.setPreferredSize(new Dimension(50, 25));
        decrementButtonScaffali.addActionListener(e -> {
            if(currentQuantity > 1) {
                currentQuantity--;
                quantityLabel.setText(String.valueOf(currentQuantity));
            }
        });
        quantitaPanelScaffali.add(decrementButtonScaffali);
        quantitaPanelScaffali.add(Box.createHorizontalStrut(5));
        
        // Label per la quantità
        quantityLabelScaffali = new JLabel(((Integer)currentQuantityScaffali).toString());
        quantityLabelScaffali.setFont(new Font("Arial", Font.BOLD, 14));
        quantityLabelScaffali.setHorizontalAlignment(JLabel.CENTER);
        quantityLabelScaffali.setPreferredSize(new Dimension(50, 25));
        quantitaPanelScaffali.add(quantityLabelScaffali);
        quantitaPanelScaffali.add(Box.createHorizontalStrut(5));
        
        // Pulsante incremento
        incrementButtonScaffali = new JButton("+1");
        incrementButtonScaffali.setFont(new Font("Arial", Font.BOLD, 12));
        incrementButtonScaffali.setPreferredSize(new Dimension(50, 25));
        incrementButtonScaffali.addActionListener(e -> {
            currentQuantityScaffali++;
            quantityLabelScaffali.setText(String.valueOf(currentQuantityScaffali));
        });
        quantitaPanelScaffali.add(incrementButtonScaffali);
        
        containerPanelScaffali.add(quantitaPanelScaffali);
        containerPanelScaffali.add(Box.createHorizontalGlue());
        
        mainPanelScaffali.add(containerPanelScaffali);
        mainPanelScaffali.add(Box.createVerticalStrut(15));

        // Pulsante acquista (centrato)
        spostaContainer = new JPanel();
        spostaContainer.setLayout(new BoxLayout(spostaContainer, BoxLayout.X_AXIS));
        spostaContainer.add(Box.createHorizontalGlue());
        
        spostaButton = new JButton("SPOSTA");
        spostaButton.setFont(new Font("Arial", Font.BOLD, 14));
        spostaButton.setPreferredSize(new Dimension(120, 30));
        spostaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.spostaProdottiMagazzinoScaffale();
                } catch (NumberFormatException ex) {
                    System.out.println("Spostamento fallito");
                }
            }
        });
        
        spostaContainer.add(spostaButton);
        spostaContainer.add(Box.createHorizontalGlue());
        
        mainPanelScaffali.add(spostaContainer);
        mainPanelScaffali.add(Box.createVerticalStrut(10));

        scaffaliFrame.add(mainPanelScaffali);
        scaffaliFrame.setVisible(true);
    }
    
    private void apriFinestraMagazzino() {
        finestraMagazzinoCreata = true;
        magazzinoFrame = new JFrame("GESTIONE MAGAZZINO");
        magazzinoFrame.setSize(350, 250); // Aumentata l'altezza per la nuova label
        magazzinoFrame.setLayout(new BoxLayout(magazzinoFrame.getContentPane(), BoxLayout.Y_AXIS));
        magazzinoFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        magazzinoFrame.setLocationRelativeTo(frameGioielleria);

        mainPanelMagazzino = new JPanel();
        mainPanelMagazzino.setLayout(new BoxLayout(mainPanelMagazzino, BoxLayout.Y_AXIS));
        mainPanelMagazzino.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Titolo "PRODOTTI"
        prodottiLabelMagazzino = new JLabel("GESTIONE MAGAZZINO");
        prodottiLabelMagazzino.setFont(new Font("Arial", Font.BOLD, 16));
        prodottiLabelMagazzino.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        mainPanelMagazzino.add(prodottiLabelMagazzino);
        mainPanelMagazzino.add(Box.createVerticalStrut(10));

        // Pannello per il totale
        totalePanelMagazzino = new JPanel();
        totalePanelMagazzino.setLayout(new BoxLayout(totalePanelMagazzino, BoxLayout.X_AXIS));
        numeroProdottiLabelMagazzino = new JLabel("");
        if(controllerAdded) {
            numeroProdottiLabelMagazzino.setText("TOTALE: " + ((Integer)controller.negozio.prodottiMagazzino.size()).toString() + "/" + ((Integer)controller.negozio.getDimensioneMagazzino()).toString());
        }
        else {
            numeroProdottiLabelMagazzino.setText("TOTALE: Non definito");
        }
        numeroProdottiLabelMagazzino.setFont(new Font("Arial", Font.PLAIN, 12));
        totalePanelMagazzino.add(numeroProdottiLabelMagazzino);
        totalePanelMagazzino.add(Box.createHorizontalGlue());
        
        mainPanelMagazzino.add(totalePanelMagazzino);
        mainPanelMagazzino.add(Box.createVerticalStrut(5));

        // Pannello prezzo d'acquisto
        prezzoPanel = new JPanel();
        prezzoPanel.setLayout(new BoxLayout(prezzoPanel, BoxLayout.X_AXIS));
        prezzoLabel = new JLabel("");
        if(controllerAdded) {
        	prezzoLabel.setText("Prezzo d'acquisto: " + controller.negozio.getPrezzoAcquisto() + "$");
        }
        else {
        	prezzoLabel.setText("Prezzo d'acquisto: Non definito");
        }
        prezzoLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        prezzoPanel.add(prezzoLabel);
        prezzoPanel.add(Box.createHorizontalGlue());
        
        mainPanelMagazzino.add(prezzoPanel);
        mainPanelMagazzino.add(Box.createVerticalStrut(15));

        // Pannello quantità
        containerPanel = new JPanel();
        containerPanel.setLayout(new BoxLayout(containerPanel, BoxLayout.X_AXIS));
        containerPanel.add(Box.createHorizontalGlue());
        
        quantitaPanelMagazzino = new JPanel();
        quantitaPanelMagazzino.setLayout(new BoxLayout(quantitaPanelMagazzino, BoxLayout.X_AXIS));
        
        quantitaLabelMagazzino = new JLabel("Quantità:");
        quantitaLabelMagazzino.setFont(new Font("Arial", Font.PLAIN, 12));
        quantitaPanelMagazzino.add(quantitaLabelMagazzino);
        quantitaPanelMagazzino.add(Box.createHorizontalStrut(5));
        
        // Pulsante decremento
        decrementButton = new JButton("-1");
        decrementButton.setFont(new Font("Arial", Font.BOLD, 12));
        decrementButton.setPreferredSize(new Dimension(50, 25));
        decrementButton.addActionListener(e -> {
            if(currentQuantity > 1) {
                currentQuantity--;
                quantityLabel.setText(String.valueOf(currentQuantity));
            }
        });
        quantitaPanelMagazzino.add(decrementButton);
        quantitaPanelMagazzino.add(Box.createHorizontalStrut(5));
        
        // Label per la quantità
        quantityLabel = new JLabel(((Integer)currentQuantity).toString());
        quantityLabel.setFont(new Font("Arial", Font.BOLD, 14));
        quantityLabel.setHorizontalAlignment(JLabel.CENTER);
        quantityLabel.setPreferredSize(new Dimension(50, 25));
        quantitaPanelMagazzino.add(quantityLabel);
        quantitaPanelMagazzino.add(Box.createHorizontalStrut(5));
        
        // Pulsante incremento
        incrementButton = new JButton("+1");
        incrementButton.setFont(new Font("Arial", Font.BOLD, 12));
        incrementButton.setPreferredSize(new Dimension(50, 25));
        incrementButton.addActionListener(e -> {
            currentQuantity++;
            quantityLabel.setText(String.valueOf(currentQuantity));
        });
        quantitaPanelMagazzino.add(incrementButton);
        
        containerPanel.add(quantitaPanelMagazzino);
        containerPanel.add(Box.createHorizontalGlue());
        
        mainPanelMagazzino.add(containerPanel);
        mainPanelMagazzino.add(Box.createVerticalStrut(15));

        // Pulsante acquista (centrato)
        acquistaContainer = new JPanel();
        acquistaContainer.setLayout(new BoxLayout(acquistaContainer, BoxLayout.X_AXIS));
        acquistaContainer.add(Box.createHorizontalGlue());
        
        acquistaButtonMagazzino = new JButton("ACQUISTA");
        acquistaButtonMagazzino.setFont(new Font("Arial", Font.BOLD, 14));
        acquistaButtonMagazzino.setPreferredSize(new Dimension(120, 30));
        acquistaButtonMagazzino.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    aggiornaNumeroProdotti();
                } catch (NumberFormatException ex) {
                    System.out.println("Inserire un numero valido");
                }
            }
        });
        
        acquistaContainer.add(acquistaButtonMagazzino);
        acquistaContainer.add(Box.createHorizontalGlue());
        
        mainPanelMagazzino.add(acquistaContainer);
        mainPanelMagazzino.add(Box.createVerticalStrut(10));

        magazzinoFrame.add(mainPanelMagazzino);
        magazzinoFrame.setVisible(true);
    }

    private void aggiornaNumeroProdotti() {
    	if (numeroProdottiLabelMagazzino != null) {
            controller.acquistaProdottiMagazzino(currentQuantity);
            numeroProdottiLabelMagazzino.setText("TOTALE: " + ((Integer)controller.negozio.prodottiMagazzino.size()).toString() + "/" + ((Integer)controller.negozio.getDimensioneMagazzino()).toString());
        }
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
    	
        //aggiorno valori "cassa"
    	pulsanteRosso.setBackground(this.cassaBackgroundColor);
    	if(pulsanteRosso.getBackground() == Color.GREEN) {
    		pulsanteRosso.setEnabled(true);
    	}
    	else {
    		pulsanteRosso.setEnabled(false);
    	}
    	
    	if(controllerAdded) {
    		labelContatore.setText(((Integer)controller.negozio.getCodaNegozio()).toString());
        	if(((Integer)controller.negozio.getCodaNegozio()) == controller.negozio.getMaxCoda()) {
        		labelContatore.setForeground(Color.RED);
        	}
        	else{
        		labelContatore.setForeground(Color.BLACK);
        	}
        	
    	}
    	
    	
    	//aggiorno grafica scaffali
    	if(controllerAdded) {
	        int currentSize = controller.negozio.getDimensioneScaffali();
	        if(controller.negozio.prodottiScaffale.size() != lastScaffaleCount || 
	           (celleScaffale != null && celleScaffale.size() != currentSize)) {
	            
	            lastScaffaleCount = controller.negozio.prodottiScaffale.size();
	            
	            mainPanel.removeAll();
	            celleScaffale = dividiAdattivo(mainPanel, currentSize);
	            
	            String img = getPercorsoImmagine();
	            if(img != null) {
	                int productCount = controller.negozio.prodottiScaffale.size();
	                for(int i = 0; i < productCount && i < celleScaffale.size(); i++) {
	                    this.aggiungiImmagineAllaCella(celleScaffale.get(controller.negozio.getDimensioneScaffali() - 1 - i), img);
	                }
	            }

	        }
    	}
    	
    	 //aggiorno grafica magazzino
    	 if(controllerAdded) {
    	        int currentSize = controller.negozio.getDimensioneMagazzino();
    	        if(controller.negozio.prodottiMagazzino.size() != lastMagazzinoCount || 
    	           (celleMagazzino != null && celleMagazzino.size() != currentSize)) {
    	            
    	            lastMagazzinoCount = controller.negozio.prodottiMagazzino.size();
    	            
    	            inventoryPanel.removeAll();
    	            celleMagazzino = dividiAdattivo(inventoryPanel, currentSize);
    	            
    	            String img = getPercorsoImmagine();
    	            if(img != null) {
    	                int productCount = controller.negozio.prodottiMagazzino.size();
    	                for(int i = 0; i < productCount && i < celleMagazzino.size(); i++) {
    	                    this.aggiungiImmagineAllaCella(celleMagazzino.get(controller.negozio.getDimensioneMagazzino() - 1 - i), img);
    	                }
    	            }

    	        }
    	 }
    	//aggiorno valore patrimonio
    	budgetLabel.setText("Patrimonio: " + User.patrimonioUtente + "$");
    	
    	
    	//aggiorno finestra upgrade
    	if(finestraUpgradeCreata) {
        		this.getUpgrades()[0] = "Lvl" + this.controller.negozio.livelliNegozio.get("Magazzino") + ", Dimensione Magazzino - " + controller.negozio.getPrezzoUpgradeMagazzino();
        		this.upgradeLabel[0].setText(getUpgrades()[0]);
        		
            	this.getUpgrades()[1] = "Lvl" + this.controller.negozio.livelliNegozio.get("Scaffali") + ", Dimensione Scaffali - " + controller.negozio.getPrezzoUpgradeScaffali();
            	this.upgradeLabel[1].setText(getUpgrades()[1]);
    	}
    	
    	//aggiorno finestra dipendenti
    	if(finestraDipendentiCreata) {
    		
    		for(int i=0; i<3; i++) {
            	if(acquistaButton[i] != null) {
            		if(controller.isAcquistato(i)) {
        				acquistaButton[i].setText("Già posseduto");
                        acquistaButton[i].setEnabled(false);
                    }
                    else {
                        acquistaButton[i].setText("Acquista");
                    }
            	}
           	}
    	}
    	
    	
    	//aggiorno finestra magazzino
    	if(finestraMagazzinoCreata) {
    		numeroProdottiLabelMagazzino.setText("TOTALE: " + ((Integer)controller.negozio.prodottiMagazzino.size()).toString() + "/" + ((Integer)controller.negozio.getDimensioneMagazzino()).toString());
    	}
    	
    	
    	//aggiorno finestra scaffali
    	if(finestraScaffaliCreata) {
    		numeroProdottiLabelScaffali.setText("TOTALE: " + ((Integer)controller.negozio.prodottiScaffale.size()).toString() + "/" + ((Integer)controller.negozio.getDimensioneScaffali()).toString());
    	}

    	
    }

	public String[] getUpgrades() {
		return upgrades;
	}

	public void setUpgrades(String[] upgrades) {
		this.upgrades = upgrades;
	}
    
	public String getPercorsoImmagine() {
		
		//Non servono i break perchè ci sono i return
		switch(tipoNegozio) {
        case 0:
       	 	return "/tiles/imgLibreria.png";
       	 case 1:
        	return "/tiles/imgGameStop.png";
       	 case 2:
        	return "/tiles/imgVestiti.png";
       	 case 3:
        	return "/tiles/imgElettronica.png";
       	 case 4:
        	return "/tiles/imgConcessionario.png";
       	 case 5:
        	return "/tiles/imgGioielleria.png";
       	 default:
       	 	System.out.println("Errore nella definizione del tipo del negozio");
       	 	return null;
        }
	}
	
	public void refreshGrafica() {
		SwingUtilities.invokeLater(() -> {
	        lastScaffaleCount = -1;
	        lastMagazzinoCount = -1;
	        celleScaffale = null;
	        celleMagazzino = null;
	        aggiornaValori();
	    });
	}
}