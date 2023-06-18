package movers;

import game_map.Map;

import java.awt.*;
import java.awt.geom.AffineTransform;
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

    public Ghost(int width, int height, Player player) {
        iconW = width;
        iconH = height;
        this.player = player;
        //this.map = map;
        dir = Direction.NORTH;
        this.Target = player.getTile();
    }
    public void setMap(Map map) {
        this.map = map;
    }
    protected abstract void updateTarget();
    protected abstract void updateScaredModeTarget();
    protected void setTarget(int[] target) {
        this.Target = target;
    }
    protected void setScaredTarget(int[] ScaredTarget) {
        this.Target = ScaredTarget;
    }
    public synchronized void move() {
        if (teleport(this.getTile()[0], this.getTile()[1]))
            return;
        int[] currentTile = getTile();
        if (isIntersection(currentTile)) {
            Direction newDirection = chooseDirection(currentTile);
            setDirection(newDirection);
        }
        moveDirection();

    }
    private synchronized boolean isIntersection(int[] tile) {
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
    private synchronized boolean isValidTile(int row, int column) {
        return row >= 0 && row < MAP_HEIGHT && column < MAP_WIDTH  && column >= 0 && map.getMap()[row][column] != 1;
    }

    private synchronized Direction chooseDirection(int[] tile) {
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

    private synchronized int[] getTileInDirection(int[] tile, Direction direction) {
        int[] nextTile = tile.clone();
        switch (direction) {
            case NORTH -> {
                nextTile[1] -= 1;
                break;
            }
            case SOUTH -> {
                nextTile[1] += 1;
                break;
            }
            case WEST -> {
                nextTile[0] -= 1;
                break;
            }
            case EAST -> {
                nextTile[0] += 1;
                break;
            }
        }
        return nextTile;
    }

    protected synchronized int getDistance(int[] tile1, int[] tile2) {
        int dx = tile1[0] - tile2[0];
        int dy = tile1[1] - tile2[1];
        return dx * dx + dy * dy;
    }

    private synchronized Direction getOppositeDirection(Direction current) {
        switch (current) {
            case NORTH -> {
                return Direction.SOUTH;
            }
            case SOUTH -> {
                return Direction.NORTH;
            }
            case WEST -> {
                return Direction.EAST;
            }
            case EAST -> {
                return Direction.WEST;
            }
            default -> {
                return current;
            }
        }
    }

    private synchronized void moveDirection() {
        int[] position = getTile();

        switch (getDirection()) {
            case NORTH -> {
                /*position[1] -= iconH;*/
                animateWalk(0, -1, map);
                this.setTile(position[0], position[1] - 1);
                break;
            }
            case SOUTH -> {
                /*position[1] += iconH;*/
                animateWalk(0, 1, map);
                this.setTile(position[0], position[1] + 1);
                break;
            }
            case WEST -> {
                /*position[0] -= iconW;*/
                animateWalk(-1, 0, map);
                this.setTile(position[0] - 1, position[1]);
                break;
            }
            case EAST -> {
                /*position[0] += iconW;*/
                animateWalk(1, 0, map);
                this.setTile(position[0] + 1, position[1]);
                break;
            }
        }
        //setPosition(position[0], position[1]);
    }

    protected synchronized int[] getTileInFrontOfPlayer(int[] playerTile, Direction playerDir, int dist) {
        int[] targetTile = playerTile.clone();
        switch (playerDir) {
            case NORTH -> {
                targetTile[1] -= dist;
                break;
            }
            case SOUTH -> {
                targetTile[1] += dist;
                break;
            }
            case WEST -> {
                targetTile[0] -= dist;
                break;
            }
            case EAST -> {
                targetTile[0] += dist;
                break;
            }
        }
        return targetTile;
    }

    /**
     * Paints ghost on the screen with proper rotation.
     * @param g2d graphics 2D
     * @param posX x position
     * @param posY y position
    @Override
    public void paint(Graphics2D g2d, int posX, int posY) {

        double angle = switch (dir) {  //sets correct angle for the chosen direction
            case NORTH -> -Math.PI/2;
            case WEST -> Math.PI;
            case SOUTH -> Math.PI/2;
            case EAST -> 0.0;
        };

        // transforms icon on the map
        transform = new AffineTransform();
        transform.rotate(angle, posX+(double) icon.getIconWidth()/2, posY+(double) icon.getIconHeight()/2);
        transform.translate(posX, posY);
        g2d.drawImage(icon.getImage(), transform, null);
    }*/
}
