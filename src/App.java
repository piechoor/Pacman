import game_map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Main program's class managing overall app functionalities.
 */
public class App {

    private static JFrame frame;
    private static JPanel panel;
    private static Map map;
    private static Game game;
    private static final String scoresFile = "scores.txt";
    private static String playername = "Guest";
    private static boolean gameStarted = false;
    private static final int WINDOW_WIDTH = 650, WINDOW_HEIGHT = 650;

    /**
     * A constructor prepares and configures basic app elements.
     */
    public static void initApp() {
        frame = new JFrame("Pacman");
        panel = new JPanel();
        frame.add(panel);

        frame.setSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        setIcon();
    }

    /**
     * Program starts here:
     */
    public static void main(String[] args) {

        initApp();
        openStartScreen();

        waitForStart();
        createMap();
        game = new Game(map, frame);
        int gameScore = game.play();
        writeScore(gameScore, playername);
        openEndScreen();
        try {
            Thread.sleep(30000);
       } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates a start screen with basic information and a start button.
     */
    private static void openStartScreen() {
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

        startButton.addActionListener(e -> {
            playername = nameField.getText();
            panel.setVisible(false);
            frame.remove(panel);
            frame.remove(logoLabel);
            frame.setLayout(new BorderLayout());
            gameStarted = true;
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

    }

    private static void openEndScreen() {
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
     */
    private static void setIcon() {

        String absolutePath = new File("imgs/pacman_icon.png").getAbsolutePath();
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

    private static void writeScore(int score, String player) {

        String playerScore = score + " " + player;

        try {
            Path file = Paths.get(scoresFile);

            List<String> lines = Files.readAllLines(file);
            lines.add(playerScore);

            List<String> sortedLines = lines.stream()
                    .sorted(Comparator.comparingInt(line -> -Integer.parseInt(line.split(" ")[0])))
                    .limit(10)
                    .toList();
            Files.write(file, sortedLines);

        } catch (IOException e) {
            System.err.println("Error reading/writing the file: " + e.getMessage());
        }
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
