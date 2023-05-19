package game_map;

import movers.Player;

import javax.swing.*;
import java.awt.*;

/**
 * Manages game map and displaying it in the app's frame.
 */
public class Map extends JComponent {

    private final int width, height;  //map dimensions
    private int maxObjWidth, maxObjHeight;  //max object dimensions
    private Graphics2D g2d;

    private final int[][] map =  // Represents game map [30x28]: 0=void; 1=wall; 2=outside;
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


    MapTile food, blank, wall, out;  //objects displayed on map
    Player player;

    /**
     * Initializes map with given dimensions
     * @param w map width
     * @param h map height
     */
    public Map(int w, int h) {
        width = w;
        height = h;
    }

    /**
     * Prints map based on the current state of "map" array.
     */
    public void update() {
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                switch (map[j][i]) {
                    case 0 -> blank.paintTile(i * maxObjWidth, j * maxObjHeight, false);
                    case 1 -> wall.paintTile(i * maxObjWidth, j * maxObjHeight, false);
                    case 2 -> out.paintTile(i * maxObjWidth, j * maxObjHeight,false);
                    case 9 -> food.paintTile(i * maxObjWidth, j * maxObjHeight, true);
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
        for (int i = 0; i < map[0].length; i++) {
            for (int j = 0; j < map.length; j++) {
                if (j > 8 & j < 20 & i > 6 & i < 21)  //excluding middle part
                    continue;
                if (j < 15 & j > 13 & (i < 6 | i > 21))  //excluding side parts
                    continue;
                if (map[j][i] == 0) {  //places food on the available path
                    map[j][i] = 9;
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

        maxObjWidth = width / map[0].length;
        maxObjHeight = height / map.length;

        // Creating map objects with given colors and dimensions
        blank = new MapTile(new Color(0, 0, 0, 100), maxObjWidth, maxObjHeight, g2d);
        wall = new MapTile(new Color(34, 57, 206), maxObjWidth, maxObjHeight, g2d);
        out = new MapTile(Color.black, maxObjWidth, maxObjHeight, g2d);
        food = new MapTile(new Color(253, 207, 165), (int) (0.2*(float) maxObjWidth), (int) (0.2*(float) maxObjHeight), g2d);
        player = new Player();

        // Setting background color and size
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, width, height);

        spawnFood();
        g2d.dispose();  //releases surplus resources
    }
}
