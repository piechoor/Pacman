import javax.swing.*;
import java.io.File;
import java.awt.event.*;
public class App {

    private static JFrame frame;
    private static JPanel panel;
    private static Map map;
    private static boolean gameStarted = false;
    private static final int WINDOW_WIDTH = 650, WINDOW_HEIGHT = 650;

    App() {
        frame = new JFrame("Pacman");
        panel = new JPanel();
        frame.add(panel);

        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setIcon("imgs/pacman_icon.png");
    }
    public static void main(String[] args) {

        App a = new App();
        a.openStartScreen();
        waitForStart();
        createMap();
    }

    public static void openStartScreen() {
        JButton startButton = new JButton("Start the game!");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                panel.setVisible(false);
                gameStarted = true;
            }
        });

        panel.setBounds(100,300,200,200);
        panel.add(startButton);
        frame.setVisible(true);

    }

    private static void createMap() {
        map = new Map(WINDOW_WIDTH, WINDOW_HEIGHT);
        frame.add(map);
        SwingUtilities.updateComponentTreeUI(frame);
    }
    private static void setIcon(String path) {

        String absolutePath = new File(path).getAbsolutePath();
        ImageIcon icon = new ImageIcon(absolutePath);
        frame.setIconImage(icon.getImage());
    }

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
