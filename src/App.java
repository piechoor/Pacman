import javax.swing.*;
import java.io.File;
public class App {

    App() {
        JFrame frame = new JFrame("Pacman");
        setIcon(frame, "imgs/pacman_icon.png");
        createUI(frame);
    }
    public static void main(String[] args) {
        new App();
    }

    private static void createUI(JFrame frame) {
        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start the game!");

        panel.setBounds(100,300,200,200);
        panel.add(startButton);

        frame.add(panel);
        frame.setSize(400,400);
        frame.setLayout(null);
        frame.setVisible(true);
    }

    private static void setIcon(JFrame frame, String path) {

        String absolutePath = new File(path).getAbsolutePath();
        ImageIcon icon = new ImageIcon(absolutePath);

        frame.setIconImage(icon.getImage());
    }
}
