package game_map;

import movers.Mover;
import movers.Player;
import movers.Ghost;

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

    private final int[][] map =  // Represents game map [30x28]: 0=path; 1=wall; 2=outside;
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
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 3, 3, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
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
    Player player;
    //list of ghosts
    Ghost ghost_red;
    Ghost ghost_blue;
    Ghost ghost_orange;
    Ghost ghost_pink;
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

        player = new Player(tileW, tileH); //TODO change
        ghost_red = new Ghost(tileW, tileH, "red");
        ghost_orange = new Ghost(tileW, tileH, "orange");
        ghost_blue = new Ghost(tileW, tileH, "blue");
        ghost_pink = new Ghost(tileW, tileH, "pink");
        movers = new ArrayList<Mover>();
        movers.add(player);
        movers.add(ghost_red);
        movers.add(ghost_orange);
        movers.add(ghost_blue);
        movers.add(ghost_pink);

        // prepares map theme
        mapImage = new ImageIcon(new File("imgs/pacman_map.png").getAbsolutePath());
        Image image = mapImage.getImage();  //scaling image
        Image tmp_image = image.getScaledInstance(635, 615,  java.awt.Image.SCALE_SMOOTH);
        mapImage = new ImageIcon(tmp_image);
        mapTransform.translate(0, 0);
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

        int[] ghost_redPos = ghost_red.getPosition();
        ghost_red.paint(g2d, ghost_redPos[0], ghost_redPos[1]);
        int[] ghost_orangePos = ghost_orange.getPosition();
        ghost_orange.paint(g2d, ghost_orangePos[0], ghost_orangePos[1]);
        int[] ghost_bluePos = ghost_blue.getPosition();
        ghost_blue.paint(g2d, ghost_bluePos[0], ghost_bluePos[1]);
        int[] ghost_pinkPos = ghost_pink.getPosition();
        ghost_pink.paint(g2d, ghost_pinkPos[0], ghost_pinkPos[1]);
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

        if (!mapInitialized)  //TODO change location
            spawnFood();

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

}
