import game_map.Map;
import movers.Mover;
import movers.Player;

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

    public Game(Map game_map, JFrame game_frame){
        map = game_map;

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

        switch (player.getDirection()) {
            case NORTH:
                if (map.isPath(pos[0], pos[1]-1))
                    pos[1] -= 1;
                break;
            case EAST:
                if (map.isPath(pos[0]+1, pos[1]))
                    pos[0] += 1;
                break;
            case SOUTH:
                if (map.isPath(pos[0], pos[1]+1))
                    pos[1] += 1;
                break;
            case WEST:
                if (map.isPath(pos[0]-1, pos[1]))
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
        while (true) {
            moveMovers();
            map.update();
            SwingUtilities.updateComponentTreeUI(frame);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void keyTyped(KeyEvent e) {}  //declaration required by "KeyListener"
    public void keyReleased(KeyEvent e) {} //declaration required by "KeyListener"

}
