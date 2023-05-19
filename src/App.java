import game_map.Map;

import javax.swing.*;
import java.io.File;
import java.awt.event.*;

/**
 * Main program's class managing overall app functionalities.
 */
public class App {

    private static JFrame frame;
    private static JPanel panel;
    private static Map map;
    private static boolean gameStarted = false;
    private static final int WINDOW_WIDTH = 650, WINDOW_HEIGHT = 650;

    /**
     * A constructor prepares and configures basic app elements.
     */
    App() {
        frame = new JFrame("Pacman");
        panel = new JPanel();
        frame.add(panel);

        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setIcon("imgs/pacman_icon.png");
    }

    /**
     * Program starts here:
     */
    public static void main(String[] args) {

        App app = new App();
        app.openStartScreen();
        waitForStart();
        createMap();
    }

    /**
     * Creates a start screen with basic information and a start button.
     */
    public static void openStartScreen() {
        JButton startButton = new JButton("Start the game!");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                panel.setVisible(false);
                gameStarted = true;
            }
        });
        // The button is placed into a panel
        panel.setBounds(100,300,200,200);
        panel.add(startButton);
        frame.setVisible(true);

    }

    /**
     * Creates game map and assures that it's displayed in the app's frame.
     */
    private static void createMap() {
        map = new Map(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(map);
        SwingUtilities.updateComponentTreeUI(frame);
    }

    /**
     * Sets the program icon from a given path.
     * @param path relative image path
     */
    private static void setIcon(String path) {

        String absolutePath = new File(path).getAbsolutePath();
        ImageIcon icon = new ImageIcon(absolutePath);
        frame.setIconImage(icon.getImage());
    }

    /**
     * Stops the app completely until the start button is clicked.
     */
    public static void waitForStart() {
        while (!gameStarted) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
