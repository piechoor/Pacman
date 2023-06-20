import game_map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.File;
import java.awt.event.*;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main program's class managing overall app functionalities.
 */
public class App {

    private static JFrame frame;
    private static JPanel panel;
    private static Map map;
    private static Game game;
    private static String scoresFile = "scores.txt";
    private static String playername = "Guest";
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
        frame.setLocationRelativeTo(null);

        setIcon("imgs/pacman_icon.png");
    }

    /**
     * Program starts here:
     */
    public static void main(String[] args) {

        App app = new App();
        app.createMap();

        ScreenManager screen = new ScreenManager(frame, panel, map);

        gameStarted = screen.openStartScreen();
        waitForStart();
        game = new Game(map, frame);
        int gameScore = game.play();
        writeScore(gameScore, playername);
        screen.openEndScreen();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

}
