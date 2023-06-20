import game_map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScreenManager {

    static JFrame frame = null;
    static JPanel panel = null;
    static Map map = null;

    private static String scoresFile = "scores.txt";
    private static String playername = "Guest";
    private static boolean gameStarted = false;
    private static final int WINDOW_WIDTH = 650, WINDOW_HEIGHT = 650;
    ScreenManager(JFrame frame, JPanel panel, Map map) {
        this.frame = frame;
        this.panel = panel;
        this.map = map;
    }


    /**
     * Creates a start screen with basic information and a start button.
     */
    public static boolean openStartScreen() {
        JLabel nameInfo = new JLabel("Enter your name:");
        nameInfo.setForeground(Color.WHITE);
        JButton startButton = new JButton("Start the game!");
        JTextField nameField = new JTextField("guest", 8);
        ImageIcon logo = new ImageIcon(new File("imgs/pacman_logo.png").getAbsolutePath());

        Image image = logo.getImage();
        Image tmp_image = image.getScaledInstance(600, 400, java.awt.Image.SCALE_SMOOTH);
        logo = new ImageIcon(tmp_image);
        JLabel logoLabel = new JLabel(logo);
        logoLabel.setBounds((WINDOW_WIDTH-600) / 2, 0, 600, 400);
        startButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                playername = nameField.getText();
                panel.setVisible(false);
                frame.remove(panel);
                frame.remove(logoLabel);
                frame.setLayout(new BorderLayout());
                gameStarted = true;
            }
        });
        // The button is placed into a panel
        panel.setBounds((WINDOW_WIDTH-300)/2,(WINDOW_HEIGHT)/2,300,100);
        panel.add(nameInfo);
        panel.add(nameField);
        panel.add(startButton);
        panel.setBackground(Color.BLACK);

        frame.getContentPane().setBackground(Color.BLACK);
        frame.getContentPane().setLayout(null);
        frame.getContentPane().add(logoLabel);
        frame.setVisible(true);

        if (gameStarted)
            return true;
        return false;
    }



    public static void openEndScreen() {
        frame.remove(map);
        frame.repaint();

        ImageIcon gameOver = new ImageIcon(new File("imgs/game_over.png").getAbsolutePath());
        Image image = gameOver.getImage();
        Image tmp_image = image.getScaledInstance(400, 200, java.awt.Image.SCALE_SMOOTH);
        gameOver = new ImageIcon(tmp_image);
        JLabel label = new JLabel(gameOver);
        label.setBounds((WINDOW_WIDTH - 400) / 2, 0, 400, 200);

        int scoresW = WINDOW_WIDTH/4, scoresH = WINDOW_HEIGHT/2;
        JTextArea scoresList = new JTextArea();
        scoresList.setEditable(false);
        scoresList.setFont(new Font("ITC Avant Garde Gothic", Font.BOLD, 20));
        scoresList.setBounds((WINDOW_WIDTH-scoresW)/2, 3*(WINDOW_HEIGHT-scoresH)/4, scoresW, scoresH);
        scoresList.setBackground(new Color(0, 0, 0, 0));
        scoresList.setForeground(Color.WHITE);

        List<String> strings = readScores();
        scoresList.append("Highest scores: \n\n");
        for (String str : strings) {
            scoresList.append(str + "\n");
        }

        frame.setLayout(null);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.getContentPane().add(label);
        frame.getContentPane().add(scoresList);
        frame.setVisible(true);
        frame.repaint();
    }


    private static List<String> readScores() {
        List<String> scores = new ArrayList<>();
        try {
            Path path = Paths.get(scoresFile);
            List<String> lines = Files.readAllLines(path);

            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] elements = line.split(" ");
                scores.add((i + 1) + ". " + elements[1] + "- " + elements[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return scores;
    }

}
