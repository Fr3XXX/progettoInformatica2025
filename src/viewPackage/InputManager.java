package viewPackage;

import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import modelPackage.GameObject;

public class InputManager implements KeyListener, MouseListener, MouseMotionListener, MouseWheelListener, Runnable {

    private static final Set<Integer> keysPressed = new HashSet<>();
    private static final Set<Integer> mouseButtonsPressed = new HashSet<>();
    private static int mouseX, mouseY;
    private static int mouseWheelRotation = 0;

    private static Thread inputThread;
    private static boolean running = false;

    private InputManager() {}

    public static void initialize(JPanel panel) {
        panel.setFocusable(true);
        InputManager manager = new InputManager();

        panel.addKeyListener(manager);
        panel.addMouseListener(manager);
        panel.addMouseMotionListener(manager);
        panel.addMouseWheelListener(manager);

        // Start thread
        startThread();
    }

    private static void startThread() {
        if (inputThread == null || !inputThread.isAlive()) {
            running = true;
            inputThread = new Thread(new InputManager());
            inputThread.start();
        }
    }

    public static void stopThread() {
        running = false;
    }

    @Override
    public void run() {
        // Qui puoi aggiungere logica futura tipo keyJustPressed ecc.
        while (running) {
            try {
                Thread.sleep(5); // leggero delay per evitare uso eccessivo CPU
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Esempio: reset temporaneo dello scroll (simula "justScrolled")
            mouseWheelRotation = 0;
        }
    }

    // --- Input API (tastiera, mouse) ---
    public static boolean isKeyPressed(char keyChar) {
        return keysPressed.contains((int) Character.toUpperCase(keyChar)) || keysPressed.contains((int) Character.toLowerCase(keyChar));
    }

    public static boolean isMousePressed(String button) {
        return switch (button.toLowerCase()) {
            case "left" -> mouseButtonsPressed.contains(MouseEvent.BUTTON1);
            case "right" -> mouseButtonsPressed.contains(MouseEvent.BUTTON3);
            case "middle" -> mouseButtonsPressed.contains(MouseEvent.BUTTON2);
            default -> false;
        };
    }

    public static boolean isMouseOver(GameObject obj) {
        int x = obj.getX();
        int y = obj.getY();
        int width = obj.getWidth() * obj.getScale();
        int height = obj.getHeight() * obj.getScale();
        Rectangle bounds = new Rectangle(x, y, width, height);
        return bounds.contains(new Point(mouseX, mouseY));
    }

    public static int getMouseX() {
        return mouseX;
    }

    public static int getMouseY() {
        return mouseY;
    }

    public static int getMouseWheelRotation() {
        return mouseWheelRotation;
    }

    // --- Eventi tastiera ---
    @Override
    public void keyPressed(KeyEvent e) {
        keysPressed.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keysPressed.remove(e.getKeyCode());
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    // --- Eventi mouse ---
    @Override
    public void mousePressed(MouseEvent e) {
        mouseButtonsPressed.add(e.getButton());
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        mouseButtonsPressed.remove(e.getButton());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        mouseWheelRotation = e.getWheelRotation();
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
}
