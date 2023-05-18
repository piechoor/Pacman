import javax.swing.*;
import java.io.File;
import java.awt.event.*;
public class App {

    static JFrame frame;
    static JPanel panel;
    public App() {
        frame = new JFrame("Pacman");
        panel = new JPanel();
        frame.add(panel);

        frame.setSize(400,400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        setIcon("imgs/pacman_icon.png");
    }
    public static void main(String[] args) {

        App a = new App();
        a.createUI();
    }

    public static void createUI() {
        JButton startButton = new JButton("Start the game!");
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                panel.setVisible(false);
                startGame();
            }
        });

        panel.setBounds(100,300,200,200);
        panel.add(startButton);

        frame.setVisible(true);

    }

    private static void startGame() {
        Map map = new Map();
        frame.add(map);
    }
    private static void setIcon(String path) {

        String absolutePath = new File(path).getAbsolutePath();
        ImageIcon icon = new ImageIcon(absolutePath);
        frame.setIconImage(icon.getImage());
    }
}
