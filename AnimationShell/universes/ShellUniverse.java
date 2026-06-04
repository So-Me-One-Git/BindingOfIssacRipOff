import java.util.ArrayList;

public class ShellUniverse implements Universe {

	private boolean complete = false;	
	private Background map = null;
	private DisplayableSprite player1 = null;
	private ArrayList<DisplayableSprite> sprites = new ArrayList<DisplayableSprite>();
	private ArrayList<Background> backgrounds = new ArrayList<Background>();
	private ArrayList<DisplayableSprite> disposalList = new ArrayList<DisplayableSprite>();

	public ShellUniverse () {
		map = new Map(50,50);
		player1 = ((Map)map).getPlayer();
		ArrayList<DisplayableSprite> barriers = ((Map)map).getBarriers();
		ArrayList<DisplayableSprite> enemies = ((Map)map).getEnemies();
		barriers.addAll(((Map)map).getBarriers());
		enemies.addAll(((Map)map).getEnemies());
		backgrounds.add(map);
		sprites.addAll(barriers);
		sprites.addAll(enemies);
		sprites.add(player1);
	}

	public double getScale() {
		return 1;
	}

	public double getXCenter() {
		return player1.getCenterX();
	}

	public double getYCenter() {
		return player1.getCenterY();
	}

	public void setXCenter(double xCenter) {
	}

	public void setYCenter(double yCenter) {
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		complete = true;
	}

	public ArrayList<Background> getBackgrounds() {
		return backgrounds;
	}	

	public ArrayList<DisplayableSprite> getSprites() {
		return sprites;
	}

	public boolean centerOnPlayer() {
		return false;
	}		

	public void update(Animation animation, long actual_delta_time) {

		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
			sprite.update(this, actual_delta_time);
    	} 
		
		disposeSprites();
		
	}
	
    protected void disposeSprites() {
        
    	//collect a list of sprites to dispose
    	//this is done in a temporary list to avoid a concurrent modification exception
		for (int i = 0; i < sprites.size(); i++) {
			DisplayableSprite sprite = sprites.get(i);
    		if (sprite.getDispose() == true) {
    			disposalList.add(sprite);
    		}
    	}
		
		//go through the list of sprites to dispose
		//note that the sprites are being removed from the original list
		for (int i = 0; i < disposalList.size(); i++) {
			DisplayableSprite sprite = disposalList.get(i);
			sprites.remove(sprite);
    	}
		
		//clear disposal list if necessary
    	if (disposalList.size() > 0) {
    		disposalList.clear();
    	}
    }


	public String toString() {
		return "ShellUniverse";
	}

}
