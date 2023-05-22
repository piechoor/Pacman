package movers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.geom.AffineTransform;

/**
 * Class represents object that can move on a map.
 */
public class Mover {
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
    public void paint(Graphics2D g2d, int posX, int posY) {
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }

    /**
     * Sets mover's direction.
     * @param d direction
     */
    public void setDirection(Direction d) {dir = d;}

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
    public void setPosition(int x, int y) {
        posX = x;
        posY = y;
    }

    /**
     * Sets object position in pixels, but tiles ids are given.
     * @param tileX x-th tile horizontally
     * @param tileY y-th tile vertically
     */
    public void setTile(int tileX, int tileY) {
        posX = tileX*iconW;
        posY = tileY*iconH;
    }

    /**
     * Sets mover icon to the image from the given path.
     * @param path relative path to file
     */
    protected void setIcon(String path) {
        String absolutePath = new File(path).getAbsolutePath();
        icon = new ImageIcon(absolutePath);

        Image image = icon.getImage();  //scaling image
        Image tmp_image = image.getScaledInstance(23, 21,  java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(tmp_image);
    }
}
