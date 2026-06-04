
public class PathfindingNode {
    public int x, y;
    public double g; // cost from start
    public double h; // heuristic to goal
    public double f; // g + h
    public PathfindingNode parent;

    public PathfindingNode(int x, int y) {
        this.x = x;
        this.y = y;
    }
}