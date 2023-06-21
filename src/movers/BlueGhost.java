package movers;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Class represents blue ghost(Inky)
 */
public class BlueGhost extends Ghost{
    RedGhost redGhost;
    public BlueGhost(int width, int height, Player player, RedGhost redGhost) {
        super(width, height, player);
        setIcon("imgs/ghost_blue.png");
        setTile(11, 14);
        dir = Direction.WEST;
        this.redGhost = redGhost;
    }

    /**
     * Function move for Inky
     */
    @Override
    public void move() {
        updateTarget();
        super.move();
    }

    /**
     * Update target for Inky
     */
    @Override
    protected void updateTarget() {
        if (inHouse) {
            setTarget(new int[]{13, 11});
            checkHome();
        }
        else {
            int[] playerTile = player.getTile();
            int[] redGhostTile = redGhost.getTile();
            playerTile = getTileInFrontOfPlayer(playerTile, player.getDirection(), 2);
            int[] targetTile = calculateBlueGhostTarget(playerTile, redGhostTile);
            setTarget(targetTile);
        }
    }

    /**
     * Update Inky's target for scared mode
     */
    @Override
    protected void updateScaredModeTarget() {
        setScaredTarget(new int[]{MAP_HEIGHT - 1, MAP_WIDTH - 1});
    }

    /**
     * Paints ghost on the screen with proper rotation.
     * @param g2d graphics 2D
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
        transform.rotate(0.0, posX+(double) icon.getIconWidth()/2, posY+(double) icon.getIconHeight()/2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }
    private int[] calculateBlueGhostTarget(int[] playerTile, int[] redGhostTile) {
        int[] targetTile = new int[2];
        targetTile[0] = 2 * playerTile[0] - redGhostTile[0];
        targetTile[1] = 2 * playerTile[1] - redGhostTile[1];
        return  targetTile;
    }

}

