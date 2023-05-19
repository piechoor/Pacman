package movers;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.awt.geom.AffineTransform;

/**
 * Class represents object that can move on a map.
 */
public class Mover {
    protected int tileX, tileY;

    public enum Direction {  //possible objects directions
        NORTH, WEST, SOUTH, EAST;
    }
    AffineTransform transform = new AffineTransform();
    protected Direction dir;
    protected ImageIcon icon;

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
     * Returns object's position.
     * @return 2-elements array with x, y coordinates
     */
    public int[] getPosition() {
        return new int[] {tileX, tileY};
    }

    /**
     * Sets object position.
     * @param x x coordinate
     * @param y y coordinate
     */
    public void setPosition(int x, int y) {
        tileX = x;
        tileY = y;
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
