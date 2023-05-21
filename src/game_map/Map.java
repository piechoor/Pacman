package game_map;

import movers.Mover;
import movers.Player;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages game map and displaying it in the app's frame.
 */
public class Map extends JComponent {

    private final int width, height;  //map dimensions
    private int maxObjWidth, maxObjHeight, tilesWidth, tilesHeight;  //max object dimensions
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
            { 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 2, 2, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
            { 0, 0, 0, 0, 0, 0, 0, 0 ,0 ,0 ,1, 2, 2, 2, 2, 2, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 },
            { 1, 1, 1, 1, 1, 1, 0, 1, 1, 0, 1, 2, 2, 2, 2, 2, 2, 1, 0, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
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
    Player player;
    //list of ghosts
    List<Mover> movers;

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

        player = new Player();
        //place to create ghosts
        movers = new ArrayList<Mover>();
        movers.add(player);
    }

    /**
     * Prints map based on the current state of "map" array.
     */
    public void update() {
        for (int i = 0; i < tilesHeight; i++) {
            for (int j = 0; j < tilesWidth; j++) {
                switch (map[i][j]) {
                    case 0 -> path.paintTile(j * maxObjWidth, i * maxObjHeight, false);
                    case 1 -> wall.paintTile(j * maxObjWidth, i * maxObjHeight, false);
                    case 2 -> out.paintTile(j * maxObjWidth, i * maxObjHeight,false);
                    case 9 -> food.paintTile(j * maxObjWidth, i * maxObjHeight, true);
                }
            }
        }

        int[] playerPos = player.getPosition();
        player.paint(g2d, playerPos[0]*maxObjWidth, playerPos[1]*maxObjHeight);
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
        update();
    }

    /**
     * Overrides JComponent method. Shouldn't be called!
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(Graphics g) {

        g2d = (Graphics2D) g;

        maxObjWidth = width / tilesWidth;
        maxObjHeight = height / tilesHeight;

        // Creating map objects with given colors and dimensions
        path = new MapTile(new Color(0, 0, 0, 100), maxObjWidth, maxObjHeight, g2d);
        wall = new MapTile(new Color(34, 57, 206), maxObjWidth, maxObjHeight, g2d);
        out = new MapTile(Color.black, maxObjWidth, maxObjHeight, g2d);
        food = new MapTile(new Color(253, 207, 165), (int) (0.2*(float) maxObjWidth), (int) (0.2*(float) maxObjHeight), g2d);


        // Setting background color and size
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);

        spawnFood();
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
     * Returns map's movers.
     * @return list of movers
     */
    public List<Mover> getMovers() {
        return movers;
    }

}
