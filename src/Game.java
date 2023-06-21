import game_map.Map;
import movers.Ghost;
import movers.Mover;
import movers.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

/**
 *  Class representing pacman game in sense of its mechanics.
 */
public class Game extends JFrame implements KeyListener{
    private final Map map;
    private Player player;
    private final List<Mover> movers;
    private volatile boolean gameFinished = false;

    public Game(Map game_map, JFrame game_frame){
        map = game_map;

        movers = map.getMovers();
        for (Mover mover : movers) { //extracts player from all movers
            if (mover instanceof Player)
                player = (Player) mover;
        }
        game_frame.addKeyListener(this);
        game_frame.requestFocus();
    }

    /**
     * Sets player's direction depending on the key pressed by user.
     * @param e the event to be processed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode) {
            case 38, 87 -> player.setDirection(Mover.Direction.NORTH);
            case 39, 68 -> player.setDirection(Mover.Direction.EAST);
            case 40, 83 -> player.setDirection(Mover.Direction.SOUTH);
            case 37, 65 -> player.setDirection(Mover.Direction.WEST);
            case 27 -> gameFinished = true;
        }
    }

    /**
     * Main game loop, runs threads, manages them and returns player's score after a ghost killed them.
     */
    public int play() {
            List<Thread> threads = new ArrayList<>();
            for (Mover mover : movers) {
                if (mover instanceof Ghost) {
                    Ghost ghost = (Ghost) mover;
                    ghost.setMap(map);
                }
                if (mover instanceof Player) {
                    Player player = (Player) mover;
                    player.setMap(map);
                }
            }
            while(!gameFinished || player.lives > 0) {
                for (Mover mover : movers) {
                    Thread thread = new Thread(mover);
                    thread.start();
                    threads.add(thread);
                }
                try {
                    for (Thread thread : threads) {
                        thread.join();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isCollision();
                if (player.lives == 0)
                    gameFinished = true;
                allFoodEaten();
            }
        return player.getScore();
    }

    /**
     * Checks for player collision with ghosts
     */
    private void isCollision() {
        for (Mover mover : movers) {
            if (mover instanceof Ghost) {
                Ghost ghost = (Ghost) mover;
                if (player.getTile()[0] == ghost.getTile()[0] && player.getTile()[1] == ghost.getTile()[1]) {
                    player.lives--;
                    map.setMapRebuild();
                    break;
                }
            }
        }
    }

    /**
     * Checks for all food eaten on map
     */
    private void allFoodEaten() {
        if (map.allFoodEaten())
            gameFinished = true;
    }

    public void keyTyped(KeyEvent e) {}  //declaration required by "KeyListener"
    public void keyReleased(KeyEvent e) {} //declaration required by "KeyListener"

}
