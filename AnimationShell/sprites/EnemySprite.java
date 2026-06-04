import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;

public class EnemySprite implements DisplayableSprite {
	
    private static Image image;
    private double centerX;
    private double centerY;
    private double width;
    private double height;
    private boolean dispose = false;
    private final double VELOCITY = 200;
    private DisplayableSprite player;
    private int[][] map;
    private List<PathfindingNode> path;
    private int pathIndex = 0;
    private long lastRepath = 0;
    private int pixelWidth;
    private int pixelHeight;


    public EnemySprite(double centerX, double centerY, double height, double width,  DisplayableSprite player, int[][] map, int pixelWidth, int pixelHeight) {

        this.centerX = centerX;
        this.centerY = centerY;
        this.height = height;
        this.width = width;
        this.player = player;
        this.map = map;
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        if (image == null) {
            try {
                image = ImageIO.read(new File("res/enemy-sprite.png"));
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }
    }

    public EnemySprite(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }


    public void update(Universe universe, long actual_delta_time) {

        if (player == null || map == null) return;

        double xPP = player.getCenterX();
        double yPP = player.getCenterY();

        int startX = (int)(centerX / pixelWidth);
        int startY = (int)(centerY / pixelHeight);

        int goalX = (int)(xPP / pixelWidth);
        int goalY = (int)(yPP / pixelHeight);

        // distance in tile space
        int dxTiles = Math.abs(goalX - startX);
        int dyTiles = Math.abs(goalY - startY);

        boolean inRange = (dxTiles <= 3 && dyTiles <= 3);

        // ONLY pathfind if player is within 3 tiles
        if (inRange &&
            (path == null ||
             pathIndex >= path.size() ||
             System.currentTimeMillis() - lastRepath > 500)) {

            path = PathFinding.findPath(map, startX, startY, goalX, goalY);

            pathIndex = 0;
            lastRepath = System.currentTimeMillis();

            if (path == null || path.isEmpty()) return;
        }

        // if no valid path, do nothing
        if (path == null || pathIndex >= path.size()) return;

        PathfindingNode target = path.get(pathIndex);

        double dx = target.x - startX;
        double dy = target.y - startY;

        double velocityX = 0;
        double velocityY = 0;

        if (dx > 0) velocityX = VELOCITY;
        else if (dx < 0) velocityX = -VELOCITY;

        if (dy > 0) velocityY = VELOCITY;
        else if (dy < 0) velocityY = -VELOCITY;

        double deltaX = actual_delta_time * 0.001 * velocityX;
        double deltaY = actual_delta_time * 0.001 * velocityY;

        // movement with collision
        if (!checkCollisionWithBarrier(universe.getSprites(), deltaX, 0)) {
            centerX += deltaX;
        }

        if (!checkCollisionWithBarrier(universe.getSprites(), 0, deltaY)) {
            centerY += deltaY;
        }

        // advance path when close to node
        if (Math.abs(startX - target.x) < 1 &&
            Math.abs(startY - target.y) < 1) {
            pathIndex++;
        }
    }


    private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double futureX,double futureY) {

        for (DisplayableSprite sprite : sprites) {
            if (sprite instanceof BarrierSprite) {
                if (CollisionDetection.overlaps(
                        this.getMinX() + futureX,
                        this.getMinY() + futureY,
                        this.getMaxX() + futureX,
                        this.getMaxY() + futureY,
                        sprite.getMinX(),
                        sprite.getMinY(),
                        sprite.getMaxX(),
                        sprite.getMaxY())) {

                    return true;
                }
            }
        }
        return false;
    }


    public Image getImage() { return image; }

    public boolean getVisible() { return true; }

    public double getMinX() { 
    	return centerX - width / 2; 
    }

    public double getMaxX() { return centerX + width / 2; }

    public double getMinY() { return centerY - height / 2; }

    public double getMaxY() { return centerY + height / 2; }

    public double getCenterX() { return centerX; }

    public double getCenterY() { return centerY; }

    public double getWidth() { return width; }

    public double getHeight() { return height; }

    public boolean getDispose() { return dispose; }

    public void setDispose(boolean dispose) {
        this.dispose = true;
    }
}