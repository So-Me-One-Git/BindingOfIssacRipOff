package project;
import java.util.ArrayList;
import java.util.Random;
public class tester {
	static myMap map = new myMap();
	public static void main(String[] args) throws Exception {
		map.makeMap(50,50);
		System.out.println(map.printMap(map.dynamicMap));
	}
}
