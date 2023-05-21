import game_map.Map;
import movers.Mover;
import movers.Player;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.nio.file.FileAlreadyExistsException;
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
        movePlayer();
        //move ghosts
    }

    /**
     * Moves player according to its direction. If the tile chosen to
     * be moved on isn't a path method changes nothing.
     * @return true if the player was moved, false otherwise
     */
    private boolean movePlayer() {
        int[] pos = player.getPosition();

        if (map.eatFood(pos[0], pos[1]))
            score += 1;
        if (teleport(pos[0], pos[1], player.getDirection()))
            return true;
        SwingUtilities.updateComponentTreeUI(frame);

        switch (player.getDirection()) {
            case NORTH:
                if (map.isWalkable(pos[0], pos[1]-1))
                    pos[1] -= 1;
                break;
            case EAST:
                if (map.isWalkable(pos[0]+1, pos[1]))
                    pos[0] += 1;
                break;
            case SOUTH:
                if (map.isWalkable(pos[0], pos[1]+1))
                    pos[1] += 1;
                break;
            case WEST:
                if (map.isWalkable(pos[0]-1, pos[1]))
                    pos[0] -= 1;
                break;
            default:
                return false;
        }
        player.setPosition(pos[0], pos[1]);
        return true;
    }

    /**
     * Game loop
     */
    public void play() {
        while (!gameFinished) {

            moveMovers();
            map.update();
            SwingUtilities.updateComponentTreeUI(frame);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * If the player is on specified tile the method teleports player to the other side of the map.
     * @param posX player's x coordinate
     * @param posY player's y coordinate
     * @param dir player's direction
     * @return if the player was teleported
     */
    private boolean teleport(int posX, int posY, Mover.Direction dir) {
        if (posY == 14) {
            if (posX == 0 & dir == Mover.Direction.WEST) {
                player.setPosition(27, 14);
                player.setDirection(Mover.Direction.WEST);
                return true;
            }
            else if (posX == 27 & dir == Mover.Direction.EAST) {
                player.setPosition(0, 14);
                player.setDirection(Mover.Direction.EAST);
                return true;
            }
        }
        return false;
    }

    /**
     * @return Player's game score
     */
    public int getScore() {
        return score;
    }

    public void keyTyped(KeyEvent e) {}  //declaration required by "KeyListener"
    public void keyReleased(KeyEvent e) {} //declaration required by "KeyListener"

}
