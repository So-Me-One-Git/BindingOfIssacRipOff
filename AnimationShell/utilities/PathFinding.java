import java.util.*;

public class PathFinding {

    private static final int[][] DIRS = {
        {1, 0}, {-1, 0}, {0, 1}, {0, -1}
    };

    public static List<PathfindingNode> findPath(int[][] grid, int startX, int startY, int goalX, int goalY) {

        PriorityQueue<PathfindingNode> open = new PriorityQueue<>(Comparator.comparingDouble(n -> n.f));
        HashSet<String> closed = new HashSet<>();

        PathfindingNode start = new PathfindingNode(startX, startY);
        start.g = 0;
        start.h = heuristic(startX, startY, goalX, goalY);
        start.f = start.h;

        open.add(start);

        while (!open.isEmpty()) {

        	PathfindingNode current = open.poll();

            if (current.x == goalX && current.y == goalY) {
                return reconstructPath(current);
            }

            closed.add(current.x + "," + current.y);

            for (int[] dir : DIRS) {
                int nx = current.x + dir[0];
                int ny = current.y + dir[1];

                if (!isValid(grid, nx, ny) || closed.contains(nx + "," + ny)) {
                    continue;
                }

                double tentativeG = current.g + 1;

                PathfindingNode neighbor = new PathfindingNode(nx, ny);
                neighbor.g = tentativeG;
                neighbor.h = heuristic(nx, ny, goalX, goalY);
                neighbor.f = neighbor.g + neighbor.h;
                neighbor.parent = current;

                open.add(neighbor);
            }
        }

        return new ArrayList<>(); // no path
    }

    private static boolean isValid(int[][] grid, int x, int y) {
        return x >= 0 && y >= 0 && x < grid.length && y < grid[0].length
                && grid[x][y] == 0;
    }

    private static double heuristic(int x, int y, int gx, int gy) {
        return Math.abs(x - gx) + Math.abs(y - gy); // Manhattan distance
    }

    private static List<PathfindingNode> reconstructPath(PathfindingNode end) {
        List<PathfindingNode> path = new ArrayList<>();

        PathfindingNode current = end;
        while (current != null) {
            path.add(current);
            current = current.parent;
        }

        Collections.reverse(path);
        return path;
    }
}
