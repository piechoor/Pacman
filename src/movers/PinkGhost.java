package movers;

import java.awt.*;
import java.awt.geom.AffineTransform;

public class PinkGhost extends Ghost {
    public PinkGhost(int width, int height, Player player) {
        super(width, height, player);
        setIcon("imgs/ghost_pink.png");
        setTile(15, 14);
        dir = Direction.WEST;
    }

    @Override
    public void move() {
        updateTarget();
        super.move();
    }

    @Override
    protected void updateTarget() {
        int[] playerTile = player.getTile();
        int[] targetTile = getTileInFrontOfPlayer(playerTile, player.getDirection(), 4);
        setTarget(targetTile);
    }

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

        /*double angle = switch (dir) {  //sets correct angle for the chosen direction
            case NORTH -> -Math.PI/2;
            case WEST -> Math.PI;
            case SOUTH -> Math.PI/2;
            case EAST -> 0.0;
        };  */      // transforms icon on the map
        transform = new AffineTransform();
        transform.rotate(0.0, posX + (double) icon.getIconWidth() / 2, posY + (double) icon.getIconHeight() / 2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }

}

