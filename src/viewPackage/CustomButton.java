package viewPackage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomButton extends JButton {

    // Costruttore per il bottone
    public CustomButton(String text) {
        super(text); // Passa il testo al costruttore di JButton
        setFocusPainted(false);  // Rimuove l'effetto di focus del bottone predefinito
        setContentAreaFilled(false);  // Rende trasparente l'area del contenuto
        setBorderPainted(false); // Rimuove il bordo predefinito
        setForeground(Color.WHITE); // Imposta il colore del testo
    }

    // Sovrascrive il metodo per disegnare il bottone
    @Override
    protected void paintComponent(Graphics g) {
        // Aggiungi effetti di "hover"
        if (getModel().isPressed()) {
            g.setColor(new Color(0, 123, 255)); // Colore blu se premuto
        } else if (getModel().isRollover()) {
            g.setColor(new Color(0, 150, 255)); // Colore blu più chiaro al passaggio del mouse
        } else {
            g.setColor(new Color(0, 123, 255)); // Colore di sfondo normale
        }
        
        // Disegna un rettangolo con angoli arrotondati
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);

        // Disegna il testo sopra il bottone
        super.paintComponent(g); // Assicura che il testo venga disegnato correttamente sopra il bottone
    }

    public static void main(String[] args) {
        // Crea una finestra
        JFrame frame = new JFrame("Bottone Personalizzato");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(null);  // Usa il layout null per la posizione assoluta dei componenti

        // Crea un bottone personalizzato
        CustomButton button = new CustomButton("Cliccami!");
        button.setFont(new Font("Arial", Font.BOLD, 40));
        button.setBounds(100, 100, 200, 50); // Imposta la posizione e la dimensione del bottone
        frame.add(button);

        // Aggiungi un ActionListener al bottone
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Bottone cliccato!");
            }
        });

        // Rendi visibile la finestra
        frame.setVisible(true);
    }
}
