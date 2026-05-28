import java.util.ArrayList;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Map implements Background{
    static int pixelHeight = 64;// TO BE DETERMINED
    static int pixelWidth = 64;// TO BE DETERMINED
    private int map[][];
    private Image displayTile;
    myMap mapMaker = new myMap();
    public Map() {
    	mapMaker.makeMap(10, 10);
    	this.map = mapMaker.getDynamicMap();
    }
	@Override
	public Tile getTile(int x, int y) {
		int up = 0;
		int down = 0;
		int left = 0;
		int right = 0;
		if(map[y+1][x] == 1) {
			down = 1;
		}
		if(map[y-1][x] == 1) {
			up = 1;
		}
		if(map[y][x-1] == 1) {
			left = 1;
		}
		if(map[y][x+1] == 1) {
			right = 1;
		}
		String wallSides =String.format("%d,%d,%d,%d",up,down,left,right);
		try {
			this.displayTile = ImageIO.read(new File(String.format("res/backgrounds/%s.png", wallSides)));
		} catch (IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
		int xSize = (x * pixelWidth );
        int ySize = (y * pixelHeight);
        Tile newTile = null;
        if (y < 0 || x < 0 || y > map.length - 1 || x > map[0].length - 1) {
            newTile = new Tile(null, xSize, ySize, pixelWidth, pixelHeight, false);
            return newTile;
        }
        if(map[y][x] == 0) {
        	newTile = new Tile (this.displayTile, x, y, pixelWidth, pixelHeight, false);
        }
        if(map[y][x] == 1) {
        	newTile = new Tile (this.displayTile, x, y, pixelWidth, pixelHeight, false);
        }
        

		return null;
	}
	@Override
	public int getCol(double x) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int getRow(double y) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getShiftX() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getShiftY() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setShiftX(double shiftX) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setShiftY(double shiftY) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void update(Universe universe, long actual_delta_time) {
		// TODO Auto-generated method stub
		
	}
    
    
}