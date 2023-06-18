import game_map.Map;
import movers.Ghost;
import movers.Mover;
import movers.Player;
import movers.RedGhost;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;


public class Game extends JFrame implements KeyListener{
    private Map map;
    private Player player;
    private List<Mover> movers;
    private JFrame frame;
    private int score;
    private boolean gameFinished = false;

    public Game(Map game_map, JFrame game_frame){
        map = game_map;
        score = 0;

        movers = map.getMovers();
        for (Mover mover : movers) { //extracts player from all movers
            if (mover instanceof Player)
                player = (Player) mover;
        }
        frame = game_frame;
        frame.addKeyListener(this);
        frame.requestFocus();
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
    public void moveMovers() {
        player.movePlayer(map);
        //move ghosts
        moveGhosts();
        map.repaint();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private boolean moveGhosts() {
        for (Mover mover : movers) {
            if (mover instanceof Ghost) {
                Ghost ghost = (Ghost) mover;
                int[] posT = ghost.getTile();
                if (ghost.teleport(posT[0], posT[1]))
                    return true;
                ghost.move();
            }
        }
        return true;
    }

    /**
     * Game loop
     */
    public int play() {
        while (!gameFinished) {
            moveMovers();
        }
        return score;
    }

    public void keyTyped(KeyEvent e) {}  //declaration required by "KeyListener"
    public void keyReleased(KeyEvent e) {} //declaration required by "KeyListener"

}
