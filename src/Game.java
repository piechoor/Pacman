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
        int[] posT = player.getTile();

        if (map.eatFood(posT[0], posT[1]))
            score += 1;
        if (teleport(posT[0], posT[1], player.getDirection()))
            return true;
        map.repaint();

        switch (player.getDirection()) {
            case NORTH:
                if (map.isWalkable(posT[0], posT[1]-1)) {
                    animateWalk(0,-1);
                    player.setTile(posT[0], posT[1]-1);}
                break;
            case EAST:
                if (map.isWalkable(posT[0]+1, posT[1])) {
                    animateWalk(1,0);
                    player.setTile(posT[0]+1, posT[1]);}
                break;
            case SOUTH:
                if (map.isWalkable(posT[0], posT[1]+1)) {
                    animateWalk(0,1);
                    player.setTile(posT[0], posT[1]+1);}
                break;
            case WEST:
                if (map.isWalkable(posT[0]-1, posT[1])) {
                    animateWalk(-1,0);
                    player.setTile(posT[0]-1, posT[1]);}
                break;
            default:
                return false;
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

    /**
     * Animates player
     * @param hor horizontal movement identifier: 1=EAST, -1=WEST, 0=NONE
     * @param ver vertical movement identifier: 1=SOUTH, -1=NORTH, 0=NONE
     */
    private void animateWalk(int hor, int ver) {
        int[] pos = player.getPosition();

        if (hor!=0) {
            for (int i = 0; i < map.tileW; i++) {
                player.setPosition(pos[0] + (i * hor), pos[1]);
                if (i==0) player.changeIcon("close");
                if (i==(int) map.tileW/2) player.changeIcon("open");
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.repaint();
            }
        }
        else if (ver!=0) {
            for (int i = 0; i < map.tileH; i++) {
                player.setPosition(pos[0], pos[1] + (i * ver));
                if (i==0) player.changeIcon("close");
                if (i==(int) map.tileH/2) player.changeIcon("open");
                try {
                    Thread.sleep(7);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.repaint();
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
                player.setTile(27, 14);
                player.setDirection(Mover.Direction.WEST);
                return true;
            }
            else if (posX == 27 & dir == Mover.Direction.EAST) {
                player.setTile(0, 14);
                player.setDirection(Mover.Direction.EAST);
                return true;
            }
        }
        return false;
    }

    public void keyTyped(KeyEvent e) {}  //declaration required by "KeyListener"
    public void keyReleased(KeyEvent e) {} //declaration required by "KeyListener"

}
