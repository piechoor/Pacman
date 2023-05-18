import javax.swing.*;
import java.io.File;
public class App {

    public App() {
        JFrame frame = new JFrame("Pacman");
        setIcon(frame, "imgs/pacman_icon.png");
        Map map = new Map();
        frame.add(map);

        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        createUI(frame);
    }
    public static void main(String[] args) {

        App a = new App();
    }

    private static void createUI(JFrame frame) {
        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start the game!");

        panel.setBounds(100,300,200,200);
        panel.add(startButton);

        frame.add(panel);
        frame.setLayout(null);
    }

    private static void setIcon(JFrame frame, String path) {

        String absolutePath = new File(path).getAbsolutePath();
        ImageIcon icon = new ImageIcon(absolutePath);
        frame.setIconImage(icon.getImage());
    }
}
