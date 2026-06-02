import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class SimpleSprite implements DisplayableSprite {

	private static final double HITBOX_CONSTANT  = -1;
	private static Image image;	
	private double centerX = 0;
	private double centerY = 0;
	private double width = 50;
	private double height = 50;
	private boolean dispose = false;	

	private final double VELOCITY = 200;

	public SimpleSprite(double centerX, double centerY, double height, double width) {
		this(centerX, centerY);
		
		this.height = height;
		this.width = width;
	}

	
	public SimpleSprite(double centerX, double centerY) {

		this.centerX = centerX;
		this.centerY = centerY;
		
		if (image == null) {
			try {
				image = ImageIO.read(new File("res/simple-sprite.png"));
			}
			catch (IOException e) {
				System.out.println(e.toString());
			}		
		}		
	}

	public Image getImage() {
		return image;
	}
	
	//DISPLAYABLE
	
	public boolean getVisible() {
		return true;
	}
	
	public double getMinX() {
		return centerX - (width / 2);
	}

	public double getMaxX() {
		return centerX + (width / 2);
	}

	public double getMinY() {
		return centerY - (height / 2);
	}

	public double getMaxY() {
		return centerY + (height / 2);
	}

	public double getHeight() {
		return height;
	}

	public double getWidth() {
		return width;
	}

	public double getCenterX() {
		return centerX;
	};

	public double getCenterY() {
		return centerY;
	};
	
	
	public boolean getDispose() {
		return dispose;
	}

	public void update(Universe universe, long actual_delta_time) {
		
		double velocityX = 0;
		double velocityY = 0;
		
		KeyboardInput keyboard = KeyboardInput.getKeyboard();
		
		//LEFT	
		if ((keyboard.keyDown(37)) && checkCollisionWithBarrier(universe.getSprites(), velocityX + HITBOX_CONSTANT, 0 ) == false) {
			velocityX = -VELOCITY;
		}
		//UP
		if ((keyboard.keyDown(38)) && checkCollisionWithBarrier(universe.getSprites(), velocityX, 0) == false) {
			velocityY = -VELOCITY;			
		}
		// RIGHT
		if ((keyboard.keyDown(39)) && checkCollisionWithBarrier(universe.getSprites(), velocityX, 0) == false) {
			velocityX += VELOCITY;
		}
		// DOWN
		if ((keyboard.keyDown(40)) && checkCollisionWithBarrier(universe.getSprites(), velocityX, 0) == false) {
			velocityY += VELOCITY;			
		}

		double deltaX = actual_delta_time * 0.001 * velocityX;
        this.centerX += deltaX;
		
		double deltaY = actual_delta_time * 0.001 * velocityY;
    	this.centerY += deltaY;

	}


	@Override
	public void setDispose(boolean dispose) {
		this.dispose = true;
	}
	private boolean checkCollisionWithBarrier(ArrayList<DisplayableSprite> sprites, double futureX, double futureY) {

		boolean colliding = false;

		for (DisplayableSprite sprite : sprites) {
			if (sprite instanceof BarrierSprite) {
				if (CollisionDetection.overlaps(this.getMinX() + futureX, this.getMinY() + futureY, 
						this.getMaxX()  + futureX, this.getMaxY() + futureY , 
						sprite.getMinX(),sprite.getMinY(), 
						sprite.getMaxX(), sprite.getMaxY())) {
					colliding = true;
					break;					
				}
			}
		}		
		return colliding;		
	}
}
