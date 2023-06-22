package movers;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Class represents pink ghost(Pinky)
 */
public class PinkGhost extends Ghost implements Runnable{
    public PinkGhost(int width, int height, Player player) {
        super(width, height, player);
        setIcon("imgs/ghost_pink.png");
        setTile(15, 14);
        dir = Direction.WEST;
    }

    /**
     * Function move for Pinky
     */
    @Override
    public void move() {
        updateTarget();
        super.move();
    }

    /**
     * Update target for Pinky
     */
    @Override
    protected void updateTarget() {
        if (inHouse) {
            setTarget(new int[]{13, 11});
            checkHome();
        }
        else {
            int[] playerTile = player.getTile();
            int[] targetTile = getTileInFrontOfPlayer(playerTile, player.getDirection(), 4);
            setTarget(targetTile);
        }
    }

    /**
     * Updates Pinky's target for scared mode
     */
    @Override
    protected void updateScaredModeTarget() {
        setScaredTarget(new int[]{0, 0});
    }

    /**
     * Paints ghost on the screen with proper rotation.
     *
     * @param g2d  graphics 2D
     * @param posX x position
     * @param posY y position
     */
    @Override
    public void paint(Graphics2D g2d, int posX, int posY) {

        transform = new AffineTransform();
        transform.rotate(0.0, posX + (double) icon.getIconWidth() / 2, posY + (double) icon.getIconHeight() / 2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }
    @Override
    public void run() {
        move();
    }
}

