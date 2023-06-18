package game_map;

import movers.*;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages game map and displaying it in the app's frame.
 */
public class Map extends JComponent {

    private final int width, height;  //map dimensions
    private int tilesWidth, tilesHeight;  //max object dimensions
    private Graphics2D g2d;

    private final int[][] map =  // Represents game map [31x28]: 0=path; 1=wall; 2=outside, 5=pacman;
           {{ 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0 ,1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 1 },
            { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
            { 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1 },
            { 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1 },
            { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }};


    MapTile food, path, wall, out;  //objects displayed on map
    ImageIcon mapImage;
    AffineTransform mapTransform = new AffineTransform();
    volatile Player player;
    //list of ghosts
    volatile RedGhost redGhost;
    PinkGhost pinkGhost;
    BlueGhost blueGhost;
    OrangeGhost orangeGhost;
    List<Mover> movers;
    private boolean mapInitialized = false;
    public int tileW, tileH;

    /**
     * Initializes map with given dimensions
     * @param w map width
     * @param h map height
     */
    public Map(int w, int h) {
        width = w;
        height = h;
        tilesHeight = map.length;
        tilesWidth = map[0].length;
        tileW = width/tilesWidth;
        tileH = height/tilesHeight;

        player = new Player(tileW, tileH);
        redGhost = new RedGhost(tileW, tileH, player);
        pinkGhost = new PinkGhost(tileW, tileH, player);
        blueGhost = new BlueGhost(tileW, tileH, player, redGhost);
        orangeGhost = new OrangeGhost(tileW, tileH, player);
        movers = new ArrayList<Mover>();
        movers.add(player);
        movers.add(redGhost);
        movers.add(pinkGhost);
        movers.add(blueGhost);
        movers.add(orangeGhost);

        // prepares map theme
        mapImage = new ImageIcon(new File("imgs/pacman_map.png").getAbsolutePath());
        Image image = mapImage.getImage();  //scaling image
        Image tmp_image = image.getScaledInstance(635, 615,  java.awt.Image.SCALE_SMOOTH);
        mapImage = new ImageIcon(tmp_image);
        mapTransform.translate(0, 0);
        spawnFood();
    }

    /**
     * Prints map based on the current state of "map" array.
     */
    public void update() {
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                if (map[i][j] == 9)
                    food.paintTile(j * tileW, i * tileH, true);
            }
        }

        int[] playerPos = player.getPosition();
        player.paint(g2d, playerPos[0], playerPos[1]);
        int[] redGhostPos = redGhost.getPosition();
        redGhost.paint(g2d, redGhostPos[0], redGhostPos[1]);
        int[] pinkGhostPos = pinkGhost.getPosition();
        pinkGhost.paint(g2d, pinkGhostPos[0], pinkGhostPos[1]);
        int[] blueGhostPos = blueGhost.getPosition();
        blueGhost.paint(g2d, blueGhostPos[0], blueGhostPos[1]);
        int[] orangeGhostPos = orangeGhost.getPosition();
        orangeGhost.paint(g2d, orangeGhostPos[0], orangeGhostPos[1]);
    }

    /**
     * Places food in predefined positions of map and updates it.
     */
    private void spawnFood() {
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                if (i > 8 & i < 20 & j > 6 & j < 21)  //excluding middle part
                    continue;
                if (i < 15 & i > 13 & (j < 6 | j > 21))  //excluding side parts
                    continue;
                if (map[i][j] == 0) {  //places food on the available path
                    map[i][j] = 9;
                }
            }
        }
        mapInitialized = true;
    }

    /**
     * Overrides JComponent method. Shouldn't be called!
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {

        g2d = (Graphics2D) g;

        // Creating map objects with given colors and dimensions
        path = new MapTile(new Color(0, 0, 0, 100), tileW, tileH, g2d);
        wall = new MapTile(new Color(34, 57, 206), tileW, tileH, g2d);
        out = new MapTile(Color.black, tileW, tileH, g2d);
        food = new MapTile(new Color(253, 207, 165), (int) (0.2 * (float) tileW), (int) (0.2 * (float) tileH), g2d);

        // Setting background color and size
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);
        g2d.drawImage(mapImage.getImage(), mapTransform, null);  //display map theme

        update();
        g2d.dispose();  //releases surplus resources

    }

    /**
     * Checks if the tile of given coordinates is a tile that can be entered on.
     * @param tileX x tile coordinate
     * @param tileY y tile coordinate
     * @return bool if the tile is a walkable tile
     */
    public boolean isWalkable(int tileX, int tileY) {
        // Checks if the given tile is out of the map's boundaries
        if (tileX < 0 | tileX > tilesWidth-1)
            return false;
        if (tileY < 0 | tileY > tilesHeight-1)
            return false;

        if (map[tileY][tileX] == 0 | map[tileY][tileX] == 9)  //if path or food
            return true;

        return false;
    }

    /**
     * Checks if there's food on the given tile. If yes, deletes it.
     * @param tileX x coordinate of the tile
     * @param tileY y coordinate of the tile
     * @return if the food was eaten
     */
    public boolean eatFood(int tileX, int tileY) {
        if (map[tileY][tileX] == 9) {
            map[tileY][tileX] = 0;
            return true;
        }
        return false;
    }

    /**
     * Returns map's movers.
     * @return list of movers
     */
    public List<Mover> getMovers() {
        return movers;
    }

    public int[][] getMap() {
        return this.map;
    }
    public boolean allFoodEaten() {
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                if (map[i][j] == 9) {
                    return false;
                }
            }
        }
        return true;
    }

}
