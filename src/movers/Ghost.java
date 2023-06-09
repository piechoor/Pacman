package movers;

import game_map.Map;

import java.util.ArrayList;
import java.util.List;

/**
 * Class represents ghosts - computer's character
 */
public abstract class Ghost extends Mover {
    protected static final int MAP_WIDTH = 28;
    protected static final int MAP_HEIGHT = 31;
    protected Player player;
    protected Map map;
    protected int[] Target;

    protected boolean inHouse = true;

    /**
     * Constructor for ghost class
     * @param width Icon width
     * @param height Icon height
     * @param player Reference to player
     */
    public Ghost(int width, int height, Player player) {
        iconW = width;
        iconH = height;
        this.player = player;
        //this.map = map;
        dir = Direction.NORTH;
        this.Target = player.getTile();
    }

    /**
     * Function to set map to ghost
     * @param map Game map
     */
    public void setMap(Map map) {
        this.map = map;
    }

    /**
     * Abstract function to update ghost target
     */
    protected abstract void updateTarget();
    /**
     * Abstract function to update ghost target for scared mode(not done)
     */
    protected abstract void updateScaredModeTarget();

    /**
     * Sets ghost target
     * @param target Target coordinates
     */
    protected void setTarget(int[] target) {
        this.Target = target;
    }

    /**
     * Sets ghost target for scared mode(not done)
     * @param ScaredTarget Target coordinates for scared mode
     */
    protected void setScaredTarget(int[] ScaredTarget) {
        this.Target = ScaredTarget;
    }

    /**
     * Main function that represents ghost movement algorithm
     */
    public void move() {
        if (teleport(this.getTile()[0], this.getTile()[1]))
            return;
        int[] currentTile = getTile();
        if (isIntersection(currentTile)) {
            Direction newDirection = chooseDirection(currentTile);
            setDirection(newDirection);
        }
        moveDirection();

    }

    /**
     * Checks if ghost at the intersection
     * @param tile Ghost tile
     * @return True if intersection
     */
    private boolean isIntersection(int[] tile) {
        int upTile = tile[1] - 1;
        int downTile = tile[1] + 1;
        int leftTile = tile[0] - 1;
        int rightTile = tile[0] + 1;
        int validDirections = 0;
        if (isValidTile(upTile, tile[0]))
            validDirections++;
        if (isValidTile(downTile, tile[0]))
            validDirections++;
        if (isValidTile(tile[1], leftTile))
            validDirections++;
        if (isValidTile(tile[1], rightTile))
            validDirections++;
        if (validDirections == 2) {
            if ((isValidTile(tile[1] - 1, tile[0]) && isValidTile(tile[1] + 1, tile[0])) ||
                    (isValidTile(tile[1], tile[0] - 1) && isValidTile(tile[1], tile[0] + 1))) {
                return false;
            }
            else
                return true;
        }
        return validDirections >= 3;
    }

    /**
     * Checks for valid tile to move
     * @param row Map row
     * @param column Map column
     * @return True if valid
     */
    private boolean isValidTile(int row, int column) {
        return row >= 0 && row < MAP_HEIGHT && column < MAP_WIDTH  && column >= 0 && (map.getMap()[row][column] != 1 || (inHouse && map.getMap()[row][column] == 3));
    }

    /**
     * Algorithm to set the best direction for ghost
     * @param tile Ghost tile
     * @return Best direction for ghost
     */
    private Direction chooseDirection(int[] tile) {
        List<Direction> directions = new ArrayList<>();
        Direction currentDirection = getDirection();
        Direction oppositeDirection = getOppositeDirection(currentDirection);
        if (isValidTile(tile[1] - 1, tile[0]) && oppositeDirection != Direction.NORTH) {
            directions.add(Direction.NORTH);
        }
        if (isValidTile(tile[1] + 1, tile[0]) && oppositeDirection != Direction.SOUTH) {
            directions.add(Direction.SOUTH);
        }
        if (isValidTile(tile[1], tile[0] - 1) && oppositeDirection != Direction.WEST) {
            directions.add(Direction.WEST);
        }
        if (isValidTile(tile[1], tile[0] + 1) && oppositeDirection != Direction.EAST) {
            directions.add(Direction.EAST);
        }
        if (directions.isEmpty()) {
            return oppositeDirection;
        }
        Direction best = directions.get(0);
        int minDistance = getDistance(Target, getTileInDirection(tile, best));

        for (Direction direction : directions) {
            int[] nextTile = getTileInDirection(tile, direction);
            int distance = getDistance(Target, nextTile);
            if (distance < minDistance) {
                minDistance = distance;
                best = direction;
            }
            else if (distance == minDistance) {
                if (direction == Direction.NORTH) {
                    best = Direction.NORTH;
                }
                else if (direction == Direction.WEST && best != Direction.NORTH) {
                    best = Direction.WEST;
                }
                else if (direction == Direction.SOUTH && best != Direction.WEST && best != Direction.NORTH) {
                    best = Direction.SOUTH;
                }
                else if (direction == Direction.EAST && best == Direction.EAST) {
                    best = Direction.EAST;
                }
            }
        }
        return best;
    }

    /**
     * Function to get tile in direction
     * @param tile Current ghost tile
     * @param direction Current direction
     * @return Next tile coordinates
     */
    private int[] getTileInDirection(int[] tile, Direction direction) {
        int[] nextTile = tile.clone();
        switch (direction) {
            case NORTH -> nextTile[1] -= 1;
            case SOUTH -> nextTile[1] += 1;
            case WEST -> nextTile[0] -= 1;
            case EAST -> nextTile[0] += 1;
        }
        return nextTile;
    }

    /**
     * Get distance between two tiles
     * @param tile1 First tile
     * @param tile2 Second tile
     * @return Distance
     */
    protected int getDistance(int[] tile1, int[] tile2) {
        int dx = tile1[0] - tile2[0];
        int dy = tile1[1] - tile2[1];
        return dx * dx + dy * dy;
    }

    /**
     * Gets opposite direction for the ghost (ghost cannot move opposite direction)
     * @param current Current direction
     * @return Opposite direction
     */
    private Direction getOppositeDirection(Direction current) {
        switch (current) {
            case NORTH:
                return Direction.SOUTH;
            case SOUTH:
                return Direction.NORTH;
            case WEST:
                return Direction.EAST;
            case EAST:
                return Direction.WEST;
            default:
                return current;
        }
    }

    /**
     * Function to move ghost
     */
    private void moveDirection() {
        int[] position = getTile();

        switch (getDirection()) {
            case NORTH -> {
                animateWalk(0, -1, map);
                this.setTile(position[0], position[1] - 1);
            }
            case SOUTH -> {
                animateWalk(0, 1, map);
                this.setTile(position[0], position[1] + 1);
            }
            case WEST -> {
                animateWalk(-1, 0, map);
                this.setTile(position[0] - 1, position[1]);
            }
            case EAST -> {
                animateWalk(1, 0, map);
                this.setTile(position[0] + 1, position[1]);
            }
        }
    }

    /**
     * Gets tile in front of player
     * @param playerTile Current player tile
     * @param playerDir Current player direction
     * @param dist Distance in front of player
     * @return Tile coordinates
     */
    protected int[] getTileInFrontOfPlayer(int[] playerTile, Direction playerDir, int dist) {
        int[] targetTile = playerTile.clone();
        switch (playerDir) {
            case NORTH -> targetTile[1] -= dist;
            case SOUTH -> targetTile[1] += dist;
            case WEST -> targetTile[0] -= dist;
            case EAST -> targetTile[0] += dist;
        }
        return targetTile;
    }

    /**
     * Function to check if ghost outside the house
     */
    protected void checkHome() {
        if (this.getTile()[0] == 13 && this.getTile()[1] == 11)
            this.inHouse = false;
    }

    /**
     * Sets inHouse to true
     */
    public void setHouse() {
        this.inHouse = true;
    }
}
