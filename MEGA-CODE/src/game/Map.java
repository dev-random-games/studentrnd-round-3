package game;

import java.awt.Color;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;

import mvc.RectSprite;
import mvc.Sprite;

/*
 * Representation of game map (landscape and towers) kept by both the server and both clients.
 */
public class Map extends Sprite{
	
	public int width;		//x and y position, number of tiles width-wise and height-wise
	public int height;
	float tileWidth, tileHeight;	//W/H for individual tiles on the map
	float mapWidth, mapHeight;		//Total width and height of the map
	
	public Tile[][] tiles;
	
	public int[][] map;
	public int[][] graph;
	
	public ArrayList<Tower> towers;

	public ArrayList<Monster> monster;

	
	Point start, end;

	
	public Map(int width, int height, float tileWidth, float tileHeight){
		this.width = width;
		this.height = height;
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		mapWidth = width * tileWidth;
		mapHeight = height * tileHeight;
		
		graph = new int[width][height];
		map = new int[width][height];
		
		tiles = new Tile[width][height];
		towers = new ArrayList<Tower>();
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tiles[x][y] = new Tile(x, y, tileWidth, tileHeight, false);
			}
		}
		
		start = new Point(0, 0);
		end = new Point(width - 1, height - 1);
		generatePath(start, end);
	}

	@Override
	public Rectangle getBoundingBox() {
		return new Rectangle(0, 0, (int) mapWidth, (int) mapHeight);
	}

	@Override
	public void draw() {
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tiles[x][y].draw();
			}
		}
	}
	
	public boolean addTower(int x, int y){
		if (tiles[x][y].tower == null){
			Tower tower = new Tower(x * tileWidth + 5, y * tileHeight + 5, tileWidth - 10, tileHeight - 10);
			towers.add(tower);
			tiles[x][y].tower = tower;
			generatePath(start, end);
			return true;
		} else {
			return false;
		}
	}
	
	/*
	 * Returns an integer representation of the map -- -1 means that monsters can't walk through a square.
	 */
	public int[][] getTraversableMap(){
		int[][] map = new int[width][height];
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				map[x][y] = (tiles[x][y].tower == null && !tiles[x][y].highGround) ? 0 : -1;	//Traversable if there is no tower on the tile and it is low ground.
			}
		}
		
		return map;
	}
	
	public ArrayList<Point> checkNode(Point p, ArrayList<Point> points, int count, Point start, Point end){
		if (p.x == end.x && p.y == end.y){
			return null;
		}else if(p.x < 0 || p.y < 0 || p.x >= width || p.y >= height || count >= graph[p.x][p.y] || map[p.x][p.y] == -1){
			return points;
		}else{
			graph[p.x][p.y] = count;
			points.add(new Point(p.x + 1, p.y));
			points.add(new Point(p.x - 1, p.y));
			points.add(new Point(p.x, p.y + 1));
			points.add(new Point(p.x, p.y - 1));
			return points;
		}
	}
	
	public void mapNode(Point p, Point start, Point end){
		int count = 0;
		ArrayList<Point> points = new ArrayList<Point>();
		ArrayList<Point> newPoints = new ArrayList<Point>();
		points.add(p);
		//boolean cont = true;
		while (true){
			count ++;
			for (Point point : points){
				newPoints = checkNode(point, newPoints, count, start, end);
				if (newPoints == null){
					return;
				}
			}
			points = newPoints;
			if (newPoints.size() == 0){
				break;
			}
			newPoints = new ArrayList<Point>();
		}
	}
	
	private Point smallestNeighbor(Point p){
		int max = width * height + 1;
		int l, r, u, d;
		l = r = u = d = max;
		if (p.x > 0 && p.x < width){
			l = graph[p.x - 1][p.y];
		}
		if (p.x >= 0 && p.x < width - 1){
			r = graph[p.x + 1][p.y];
		}
		if (p.y > 0 && p.y < height){
			u = graph[p.x][p.y - 1];
		}
		if (p.y >= 0 && p.y < height - 1){
			d = graph[p.x][p.y + 1];
		}
		if (l <= r && l <= u && l <= d){
			return new Point(p.x - 1, p.y);
		}else{
			if (r <= u && r <= d){
				return new Point(p.x + 1, p.y);
			}else{
				if (u <= d){
					return new Point(p.x, p.y - 1);
				}else{
					return new Point(p.x, p.y + 1);
				}
			}
		}
	}
	
	public void regraph(Point start, Point end){
		for (int x = 0; x < graph.length; x ++){
			for (int y = 0; y < graph[x].length; y ++){
				graph[x][y] = width * height + 1;
			}
		}
		mapNode(new Point(start.x, start.y), start, end);
	}
	
	public ArrayList<Point> generatePath(Point start, Point end){
		map = getTraversableMap();
		regraph(start, end);
		
		for (int x = 0; x < width; x++){
			for (int y = 0; y < height; y++){
				tiles[x][y].onPath = false;
			}
		}
		
		int count = width * height + 1;
		Point p = end;
		Point next;
		
		ArrayList<Point> path = new ArrayList<Point>();
		
		path.add(start);
		
		while (count > 1){
			next = this.smallestNeighbor(p);
			path.add(next);
			if (graph[next.x][next.y] >= count){
				break;
			}else{
				count = graph[next.x][next.y];
				tiles[next.x][next.y].onPath = true; 
			}
			p = next;
		}
		
		return path;
	}

}
