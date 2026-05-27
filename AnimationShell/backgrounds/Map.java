import java.util.ArrayList;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import project.myMap;

public class Map implements Universe{
    final static int TILE_HEIGHT_PIXEL = 64;// TO BE DETERMINED
    final static int TILE_WIDTH_PIXEL = 64;// TO BE DETERMINED
    private int map[][];
    static myMap mapMaker = new myMap();
    public Map() {
    	
    }
    
    
    
    
	@Override
	public double getScale() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getXCenter() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public double getYCenter() {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public void setXCenter(double xCenter) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setYCenter(double yCenter) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public boolean isComplete() {
		// TODO Auto-generated method stub
		return false;
	}
	@Override
	public void setComplete(boolean complete) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public ArrayList<DisplayableSprite> getSprites() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public ArrayList<Background> getBackgrounds() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public void update(Animation animation, long actual_delta_time) {
		// TODO Auto-generated method stub
		
	}
    
}
