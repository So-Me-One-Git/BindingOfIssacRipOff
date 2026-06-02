import java.util.ArrayList;
import java.util.HashMap;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

public class Map implements Background{
	private int pixelHeight = 32;// TO BE DETERMINED
	private int pixelWidth = 32;// TO BE DETERMINED
	private int map[][];
	private int startxPos;
	private int startyPos;
	private HashMap<String, Image> images = new HashMap<>();
	
	myMap mapMaker = new myMap();
	public Map(int x, int y) {
		mapMaker.makeMap(x, y);
		this.map = mapMaker.getDynamicMap();
		System.out.println(mapMaker.printMap(map));
		startxPos = mapMaker.getStartxPos() * pixelWidth;
		startyPos = mapMaker.getStartyPos() * pixelHeight;
		try {
			images.put("0000", ImageIO.read(new File("res/0,0,0,0.png")));
			images.put("0001", ImageIO.read(new File("res/0,0,0,1.png")));
			images.put("0010", ImageIO.read(new File("res/0,0,1,0.png")));
			images.put("0100", ImageIO.read(new File("res/0,1,0,0.png")));
			images.put("1000", ImageIO.read(new File("res/1,0,0,0.png")));
			images.put("0011", ImageIO.read(new File("res/0,0,1,1.png")));
			images.put("0110", ImageIO.read(new File("res/0,1,1,0.png")));
			images.put("1010", ImageIO.read(new File("res/1,0,1,0.png")));
			images.put("0101", ImageIO.read(new File("res/0,1,0,1.png")));
			images.put("1100", ImageIO.read(new File("res/1,1,0,0.png")));
			images.put("1001", ImageIO.read(new File("res/1,0,0,1.png")));
			images.put("1110", ImageIO.read(new File("res/1,1,1,0.png")));
			images.put("0111", ImageIO.read(new File("res/0,1,1,1.png")));
			images.put("1011", ImageIO.read(new File("res/1,0,1,1.png")));
			images.put("1101", ImageIO.read(new File("res/1,1,0,1.png")));
			images.put("1111", ImageIO.read(new File("res/1,1,1,1.png")));
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
			return images.get("0000");
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
			String wallSides =String.format("%d%d%d%d",up,down,left,right);
			return images.get(wallSides);
		}
		return null;
	}
	public Tile getTile(int x, int y) {
		int xSize = (x * pixelWidth );
		int ySize = (y * pixelHeight);
		Tile newTile = new Tile (selectImage(x,y), xSize, ySize, pixelWidth, pixelHeight, false);

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
	public double getPixelWidth() {
		return this.pixelWidth;
	}
	public double getPixelHeight() {
		return this.pixelHeight;
	}
	public double getStartxPos() {
		return this.startxPos;
	}
	public double getStartyPos() {
		return this.startyPos;
	}
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