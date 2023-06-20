package movers;

import game_map.Map;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.geom.AffineTransform;

/**
 * Class represents object that can move on a map.
 */
public abstract class Mover implements Runnable{
    protected int iconW,iconH;  // dimensions of mover's icon
    protected int posX, posY; // mover's position in pixels

    public enum Direction {  //possible objects directions
        NORTH, WEST, SOUTH, EAST;
    }
    protected Direction dir;  // mover's direction
    protected ImageIcon icon;  // mover's icon
    AffineTransform transform = new AffineTransform();  // icon transformation


    Mover() {} // default constructor
    Mover(int width, int height) {
        iconW = width;
        iconH = height;
    }

    /**
     * Paints mover on the screen
     * @param g2d graphics 2D
     * @param posX x position
     * @param posY y position
     */
    public synchronized void paint(Graphics2D g2d, int posX, int posY) {
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }

    /**
     * Sets mover's direction.
     * @param d direction
     */
    public synchronized void setDirection(Direction d) {dir = d;}

    /**
     * @return Mover's direction
     */
    public Direction getDirection() {return dir;}

    /**
     * @return the tile indexes on which a mover is currently on
     */
    public int[] getTile() {
        return new int[] {posX/iconW, posY/iconH};
    }

    /**
     * Returns object's position.
     * @return 2-elements array with x, y coordinates
     */
    public int[] getPosition() {
        return new int[] {posX, posY};
    }

    /**
     * Sets object position.
     * @param x x coordinate
     * @param y y coordinate
     */
    public synchronized void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }

    /**
     * Sets object position in pixels, but tiles ids are given.
     * @param tileX x-th tile horizontally
     * @param tileY y-th tile vertically
     */
    public synchronized void setTile(int tileX, int tileY) {
        posX = tileX*iconW;
        posY = tileY*iconH;
    }


    /**
     * Animates mover
     * @param hor horizontal movement identifier: 1=EAST, -1=WEST, 0=NONE
     * @param ver vertical movement identifier: 1=SOUTH, -1=NORTH, 0=NONE
     */
    public synchronized void animateWalk(int hor, int ver, Map map) {
        int[] pos = this.getPosition();
        if (hor!=0) {
            for (int i = 0; i < map.tileW; i++) {
                this.setPosition(pos[0] + (i * hor), pos[1]);
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.repaint();
            }
        }
        else if (ver!=0) {
            for (int i = 0; i < map.tileH; i++) {
                this.setPosition(pos[0], pos[1] + (i * ver));
                try {
                    Thread.sleep(8);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                map.repaint();
            }
        }
    }

    /**
     * If the mover is on specified tile the method teleports mover to the other side of the map.
     * @return if the mover was teleported
     */
    public boolean teleport(int nextPosX, int nextPosY) {
        if (nextPosY == 14) {
            if (nextPosX == 0 & dir == Direction.WEST) {
                this.setTile(27, 14);
                this.dir = Direction.WEST;
                return true;
            }
            else if (nextPosX == 27 & dir == Direction.EAST) {
                this.setTile(0, 14);
                this.dir = Direction.EAST;
                return true;
            }
        }
        return false;
    }


    /**
     * Sets mover icon to the image from the given path.
     * @param path relative path to file
     */
    protected synchronized void setIcon(String path) {
        String absolutePath = new File(path).getAbsolutePath();
        icon = new ImageIcon(absolutePath);

        Image image = icon.getImage();  //scaling image
        Image tmp_image = image.getScaledInstance(23, 21,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(tmp_image);
    }

    @Override
    public void run() {
        move();
    }
    public abstract void move();
}
