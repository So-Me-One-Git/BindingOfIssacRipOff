
import java.util.Random;

import project.OverlapException;
import project.Point;

import java.util.ArrayList;

public class myMap {

	private int emptyRoom = 1;
	private int border = 2;
	private int nothing = 0;
	private int enemy = 3;
	private int xPos = 0;
	private int yPos = 0;
	private double fillPercent = 0.35;
	private int startxPos;
	private int startyPos;
	private static final Random random = new Random();
	int[][] dynamicMap;

	public void setStartPos(int x, int y) {
		this.startxPos = x;
		this.startyPos = y;
	}
	public void dynamicMap(int x, int y) {
		this.dynamicMap = new int[y][x];
	}
	public void setxPos(int xPos) {
		this.xPos=xPos;
	}
	public void setyPos(int yPos) {
		this.yPos = yPos;
	}
	public int getxPos() {
		return this.xPos;
	}
	public int getyPos() {
		return this.yPos;
	}
	public int getStartxPos() {
		return this.startxPos;
	}
	public int getStartyPos() {
		return this.startyPos;
	}
	public int getMaxX(){
		return dynamicMap[0].length;
	}
	public int getMaxY() {
		return dynamicMap.length;
	}
	public int getElement(int x,int y) {
		return dynamicMap[y][x];
	}
	public void setElement(int x, int y, int value) {
		dynamicMap[y][x] = value;
	}
	public int[][] getDynamicMap() {
		return dynamicMap;
	}
	public void goUp() throws OverlapException {
		if(!checkOverlap("up")) {
			setElement(xPos,yPos-1,emptyRoom);
			yPos--;
		}else {
			throw new OverlapException("Overlap");
		}
	}

	public void goDown() throws OverlapException {
		if(!checkOverlap("down")) {
			setElement(xPos,yPos+1,emptyRoom);
			yPos++;
		}else {
			throw new OverlapException("Overlap");
		}
	}
	public void goRight() throws OverlapException {
		if(!checkOverlap("right")) {
			setElement(xPos+1,yPos,emptyRoom);
			xPos++;
		}else {
			throw new OverlapException("Overlap");
		}
	}
	public void goLeft() throws OverlapException {
		if(!checkOverlap("left")) {
			setElement(xPos-1,yPos,emptyRoom);
			xPos--;
		}else {
			throw new OverlapException("Overlap");
		}
	}
	public boolean checkOverlap(String direction){
		int futureX = xPos;
		int futureY = yPos;
		switch(direction) {
		case "up":
			futureY--;
			break;
		case "down":
			futureY++;
			break;
		case "left":
			futureX--;
			break;
		case "right":
			futureX++;
			break;
		}
		if(checkInBounds(futureY, futureX) && dynamicMap[futureY][futureX] == 0) {
			return false;// FALSE MEANS THAT THERE IS NO OVERLAP
		}else{
			return true;
		}
	}
	public boolean checkStuck() {
		if (checkOverlap("down") && checkOverlap("up") && checkOverlap("left") && checkOverlap("right")) {	
			return true;
		}else {
			return false;
		}

	}
	public boolean checkInBounds(int y, int x) {
		if(y >= 0 && y < dynamicMap.length && x >= 0 && x < dynamicMap[y].length) {
			return true;
		}else {
			return false;
		}
	}
	public String printMap(int[][] map) {
		StringBuilder mapString = new StringBuilder();
		for(int y = 0; y < getMaxY(); y++) {
			for(int x = 0; x < getMaxX(); x++) {
				mapString.append(map[y][x]).append(" ");
			}
			mapString.append("\n");
		} 
		return mapString.toString();
	}
	public String makeRandomRoom(int biasUp, int biasDown, int biasLeft, int biasRight) {
		ArrayList<String> directionBias = new ArrayList<>();
		for(int i = 0; i < biasUp; i ++) {
			directionBias.add("up");
		}
		for(int i = 0; i < biasDown; i ++) {
			directionBias.add("down");
		}
		for(int i = 0; i < biasLeft; i ++) {
			directionBias.add("left");
		}
		for(int i = 0; i < biasRight; i ++) {
			directionBias.add("right");
		} 
		String ranDirection = directionBias.get(random.nextInt(directionBias.size()));
		try {
			switch (ranDirection){
			case"up":
				goUp();
				break;
			case"down":
				goDown();
				break;
			case"left":
				goLeft();
				break;
			case"right":
				goRight();
				break;
			}
			return ranDirection;
		}catch(OverlapException e) {
			if(checkStuck() && percentFilled() != 1) {
				findRandomRoom();
				return makeRandomRoom(biasUp, biasDown, biasLeft, biasRight);
			}else {
				return"";
			}
		}
	}
	public boolean oneExist() {
	    for (int y = 0; y < getMaxY(); y++) {
	        for (int x = 0; x < getMaxX(); x++) {
	            if (getElement(x, y) == emptyRoom) 
	            	return true;
	        }
	    }
	    return false;
	}
	public boolean findStartPos() {
		while (true) {
		    int ranX = random.nextInt(getMaxX());
		    int ranY = random.nextInt(getMaxY());

		    if (getElement(ranX, ranY) == emptyRoom) {
		        setStartPos(ranX, ranY);
		        return true;
		    }
		}
	}

	public boolean findRandomRoom() {
		if(oneExist()) {
			int ranX = random.nextInt(getMaxX());
			int ranY = random.nextInt(getMaxY());
			int currentX = getxPos();
			int currentY = getyPos();
			if(percentFilled() == 1) {
				return false;
			}
			if (getElement(ranX, ranY) == emptyRoom) {
				setxPos(ranX);
				setyPos(ranY);
				if (checkStuck()) {
					setxPos(currentX);
					setyPos(currentY);
					return findRandomRoom();
				} else {
					return true;
				}
			} else {
				return findRandomRoom();
			}
		}
		return false;
	}
	public void makeMap(int x, int y) {
		makeMap(x,y,1);
	}
	public void makeMap(int x, int y, int size) {
			dynamicMap(x, y);
			setStartPos(x/2, y/2);
			makeFracture(size);
			makeNoise();
			connectAllClusters();
			if(percentFilled() < this.fillPercent) {
				makeMap(x,y,(int)(this.fillPercent/ percentFilled() * size));
			}else {
				dynamicMap = expandWithBorder(dynamicMap);
				makeBarrier();
				findStartPos();
				makeEnemy();
			}
	}
	public int[][] expandWithBorder(int[][] grid) {
	    int rows = grid.length;
	    int cols = grid[0].length;

	    int[][] expanded = new int[rows + 2][cols + 2];

	    for (int y = 0; y < rows; y++) {
	        for (int x = 0; x < cols; x++) {
	            expanded[y + 1][x + 1] = grid[y][x];
	        }
	    }

	    return expanded;
	}
	public void makeBarrier() {
			for(int y = 0; y < getMaxY(); y++) {
				for(int x = 0; x < getMaxX(); x++) {
						if(getElement(x,y) == nothing && (adjacentOne(x,y) != 0)) {
							setElement(x, y, border);
						}
					}
				}
	}
	public void makeEnemy() {
		for(int y = 0; y < getMaxY(); y++) {
			for(int x = 0; x < getMaxX(); x++) {
				if(adjacentOne(x,y) == 2 && getElement(x,y) == emptyRoom && random.nextDouble() < 0.2) {
					setElement(x,y, enemy);
				}
				if(adjacentOne(x,y) == 3 && getElement(x,y) == emptyRoom && random.nextDouble() < 0.4) {
					setElement(x,y, enemy);
				}
				if(adjacentOne(x,y) == 4 && getElement(x,y) == emptyRoom && random.nextDouble() < 0.6) {
					setElement(x,y, enemy);
				}
			}
		}
	}
	public int adjacentOne(int x, int y) {
		int amount = 0;
	    if (checkInBounds(x, y + 1) && getElement(x, y + 1) == emptyRoom) {
	        amount++;
	    }
	    if (checkInBounds(x, y - 1) && getElement(x, y - 1) == emptyRoom) {
	    	amount++;
	    }
	    if (checkInBounds(x + 1, y) && getElement(x + 1, y) == emptyRoom) {
	    	amount++;
	    }
	    if (checkInBounds(x - 1, y) && getElement(x - 1, y) == emptyRoom) {
	    	amount++;
	    }
	    return amount;
	}
	
	public float percentFilled() {
		int ones = 0;
		for(int y = 0; y < getMaxY(); y++) {
			for(int x = 0; x < getMaxX(); x++) {
				if(getElement(x,y) == emptyRoom) {
					ones++;
				}
			}
		}
		return (float) ones/(getMaxX() * getMaxY());
	}
	public void makeNoise() {
		for(int y = 0; y < getMaxY(); y++) {
			for(int x = 0; x < getMaxX(); x++) {
				if (getElement(x,y) == 1 && adjacentOne(x,y) != 0) {
					if (adjacentOne(x,y) == 3 && random.nextDouble() < 0.4){
						setElement(x,y,nothing);
					}
					if (adjacentOne(x,y) == 4 && random.nextDouble() < 0.8){
						setElement(x,y,nothing);
					}

				}
			}
		}
	}
	public void makeRandomBranch(){
		int leftWeight = 1;
		int rightWeight = 1;
		int upWeight = 1;
		int downWeight = 1;
		int currentDirection = random.nextInt(4);
		int diagonal = random.nextInt(2);
		int size = random.nextInt(5,15);
		switch(diagonal) {
		case 0:
			switch(currentDirection) {
			case 0:
				upWeight = 15;
				break;
			case 1:
				downWeight = 15;
				break;
			case 2:
				leftWeight = 15;
				break;
			case 3:
				rightWeight = 10;
				break;
			}
		case 2:
			switch(currentDirection) {
			case 0:
				upWeight = 15;
				rightWeight = 15;
				break;
			case 1:
				upWeight = 15;
				leftWeight = 15;
				break;
			case 2:
				downWeight = 15;
				rightWeight = 15;
				break;
			case 3:
				downWeight = 15;
				leftWeight = 15;
				break;
			}
		} for (int i = 0; i < size; i++) {
			makeRandomRoom(upWeight, downWeight, leftWeight, rightWeight);
		}

	}
	public void makeFracture(int size) {
		makeRandomBranch();
		for (int i = 0; i < size; i++) {
			findRandomRoom();
			makeRandomBranch();
		}
	}
	public void floodFill(int x, int y, boolean[][] visited, ArrayList<Point> cluster) {

	    if(x < 0 || x >= getMaxX() || y < 0 || y >= getMaxY()) {
	        return;
	    }

	    if(visited[y][x] || getElement(x, y) != emptyRoom) {
	        return;
	    }

	    visited[y][x] = true;
	    cluster.add(new Point(x, y));

	    floodFill(x + 1, y, visited, cluster);
	    floodFill(x - 1, y, visited, cluster);
	    floodFill(x, y + 1, visited, cluster);
	    floodFill(x, y - 1, visited, cluster);
	}
	public ArrayList<ArrayList<Point>> getClusters() {

	    boolean[][] visited = new boolean[getMaxY()][getMaxX()];

	    ArrayList<ArrayList<Point>> clusters = new ArrayList<>();

	    for(int y = 0; y < getMaxY(); y++) {
	        for(int x = 0; x < getMaxX(); x++) {

	            if(getElement(x, y) == emptyRoom && !visited[y][x]) {

	                ArrayList<Point> cluster = new ArrayList<>();

	                floodFill(x, y, visited, cluster);

	                clusters.add(cluster);
	            }
	        }
	    }

	    return clusters;
	}
	public void connectAllClusters() {

	    while(true) {

	        ArrayList<ArrayList<Point>> clusters = getClusters();

	        if(clusters.size() <= 1) {
	            return;
	        }

	        int bestDistance = Integer.MAX_VALUE;

	        Point bestA = null;
	        Point bestB = null;

	        for(int i = 0; i < clusters.size(); i++) {

	            for(int j = i + 1; j < clusters.size(); j++) {

	                for(Point a : clusters.get(i)) {

	                    for(Point b : clusters.get(j)) {

	                        int dist =
	                            Math.abs(a.x - b.x) +
	                            Math.abs(a.y - b.y);

	                        if(dist < bestDistance) {

	                            bestDistance = dist;

	                            bestA = a;
	                            bestB = b;
	                        }
	                    }
	                }
	            }
	        }

	        carvePath(bestA, bestB);
	    }
	}
	public void carvePath(Point a, Point b) {

	    int x = a.x;
	    int y = a.y;

	    while(x != b.x) {
	        setElement(x, y, emptyRoom);
	        x += Integer.signum(b.x - x);
	    }

	    while(y != b.y) {
	        setElement(x, y, emptyRoom);
	        y += Integer.signum(b.y - y);
	    }

	    setElement(x, y, emptyRoom);
	}
	

}
