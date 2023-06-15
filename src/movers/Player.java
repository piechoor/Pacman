package movers;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;

/**
 * Class represents pacman - player's character
 */
public class Player extends Mover {
    public Player(int width, int height) {
        iconW = width;
        iconH = height;
        dir = Direction.WEST;
        setTile(14,23);
        setIcon("imgs/pacman_icon.png");
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

    public void changeIcon(String state) {
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
}
