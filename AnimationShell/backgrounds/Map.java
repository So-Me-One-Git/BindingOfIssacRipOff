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
	
	private Image image0001 = null;
	
	myMap mapMaker = new myMap();
	public Map() {
		mapMaker.makeMap(10, 10);
		this.map = mapMaker.getDynamicMap();
		
		try {
			image0001 = ImageIO.read(new File("res/0,0,0,0.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
	}
	private Image selectImage(int x, int y) {
		if (y < 0 || y >= map.length  || x < 0 || x >= map[0].length) {
			return null;			
		}
		if (map[y][x] == 0) {
			return null;
		}
		if(map[y][x] == 1) {
			try {
				return ImageIO.read(new File("res/0,0,0,0.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (map[y][x] == 2) {
			int up = 0;
			int down = 0;
			int left = 0;
			int right = 0;
			if( mapMaker.checkInBounds(y+1, x) && map[y+1][x] == 1 ) {
				down = 1;
			}
			if(mapMaker.checkInBounds(y-1, x) && map[y-1][x] == 1) {
				up = 1;
			}
			if( mapMaker.checkInBounds(y,x-1) && map[y][x-1] == 1 ) {
				left = 1;
			}
			if( mapMaker.checkInBounds(y,x+1) && map[y][x+1] == 1 ) {
				right = 1;
			}
			String wallSides =String.format("%d,%d,%d,%d",up,down,left,right);
			try {
				return  ImageIO.read(new File(String.format("res/%s.png", wallSides)));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
	public Tile getTile(int x, int y) {

		Tile newTile = new Tile (null, x, y, pixelWidth, pixelHeight, false);

		if (y < 0 || y >= map.length  || x < 0 || x >= map[0].length) {
			return newTile;			
		}
		int xSize = (x * pixelWidth );
		int ySize = (y * pixelHeight);
		newTile = new Tile (selectImage(x,y),xSize,ySize,pixelWidth,pixelHeight,false);
		return newTile;
	}
	public ArrayList<DisplayableSprite> getBarriers() {
		ArrayList<DisplayableSprite> walls = new ArrayList<DisplayableSprite>();
		for (int x = 0; x < map[0].length; x++) {
			for (int y = 0; y < map.length; y++) {
				if (map[y][x] == 2) {
					walls.add(new BarrierSprite(x * pixelWidth, y * pixelHeight, (x + 1) * pixelWidth, (y + 1) * pixelHeight, false));
				}
			}
			
		}
		return walls;

	}
	@Override
	public int getCol(double x) {
		int col = 0;
		col = (int) (x / pixelWidth);
		if (x < 0) {
			return col - 1;
		}
		else {
			return col;
		}
	}
	@Override
	public int getRow(double y) {
		int row = 0;
		row = (int) (y / pixelHeight);
		if (y < 0) {
			return row - 1;
		}
		else {
			return row;
		}
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