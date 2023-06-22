package movers;

import game_map.Map;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

/**
 * Class represents pacman - player's character
 */
public class Player extends Mover implements Runnable{
    protected Map map;
    public int lives = 3;
    private int score = 0;
    public Player(int width, int height) {
        iconW = width;
        iconH = height;
        dir = Direction.WEST;
        setTile(14,23);
        setIcon("imgs/pacman_icon.png");
    }

    /**
     * Sets map on which player is
     * @param map game's map
     */
    public void setMap(Map map) {
        this.map = map;
    }
    /**
     * Paints player on the screen with proper rotation.
     * @param g2d graphics 2D
     * @param posX x position
     * @param posY y position
     */
    @Override
    public void paint(Graphics2D g2d, int posX, int posY) {

        double angle = switch (dir) {  //sets correct angle for the chosen direction
            case NORTH -> -Math.PI/2;
            case WEST -> Math.PI;
            case SOUTH -> Math.PI/2;
            case EAST -> 0.0;
        };

        // scaling image (animation purpose only)
        Image tmp_icon = icon.getImage().getScaledInstance(23, 21,  java.awt.Image.SCALE_SMOOTH);;  //scaling image
        icon = new ImageIcon(tmp_icon);

        // transforms icon on the map
        transform = new AffineTransform();
        transform.rotate(angle, posX+(double) icon.getIconWidth()/2, posY+(double) icon.getIconHeight()/2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }


    /**
     * Moves player according to its direction. If the tile chosen to
     * be moved on isn't a path method changes nothing.
     * @return true if the player was moved, false otherwise
     */
    @Override
    public void move() {
        //System.out.print("");
        int[] posT = this.getTile();
        if (map.eatFood(posT[0], posT[1]))
            score += 1;
        if (this.teleport(posT[0], posT[1]))
            return;

        switch (this.getDirection()) {
            case NORTH:
                if (map.isWalkable(posT[0], posT[1]-1)) {
                    this.animateWalk(0,-1, map);
                    this.setTile(posT[0], posT[1]-1);}
                break;
            case EAST:
                if (map.isWalkable(posT[0]+1, posT[1])) {
                    this.animateWalk(1,0, map);
                    this.setTile(posT[0]+1, posT[1]);}
                break;
            case SOUTH:
                if (map.isWalkable(posT[0], posT[1]+1)) {
                    this.animateWalk(0,1, map);
                    this.setTile(posT[0], posT[1]+1);}
                break;
            case WEST:
                if (map.isWalkable(posT[0]-1, posT[1])) {
                    this.animateWalk(-1,0, map);
                    this.setTile(posT[0]-1, posT[1]);}
                break;
        }
    }


    /**
     * Animates player
     * @param hor horizontal movement identifier: 1=EAST, -1=WEST, 0=NONE
     * @param ver vertical movement identifier: 1=SOUTH, -1=NORTH, 0=NONE
     */
    @Override
    public void animateWalk(int hor, int ver, Map map) {
        int[] pos = this.getPosition();
        if (hor!=0) {
            for (int i = 0; i < map.tileW; i++) {
                this.setPosition(pos[0] + (i * hor), pos[1]);
                if (i==0) this.changeIcon("close");
                if (i==(int) map.tileW/2) this.changeIcon("open");
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
                this.setPosition(pos[0], pos[1] + (i * ver));
                if (i==0) this.changeIcon("close");
                if (i==(int) map.tileH/2) this.changeIcon("open");
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
     * Changes player's icon depending on a state given. Used for pacman animation.
     * @param state either "close" or "open"
     */
    private void changeIcon(String state) {
        String absolutePath = "";

        if (state == "open")
            absolutePath = new File("imgs/pacman_icon.png").getAbsolutePath();
        else if (state == "close")
            absolutePath = new File("imgs/pacman_icon2.png").getAbsolutePath();

        icon = new ImageIcon(absolutePath);

        Image image = icon.getImage();  //scaling image
        Image tmp_image = image.getScaledInstance(23, 21,  java.awt.Image.SCALE_SMOOTH);
        this.icon = new ImageIcon(tmp_image);
    }

    /**
     * @return player score
     */
    public int getScore() {
        return score;
    }

    @Override
    public void run() {
        move();
    }
}
