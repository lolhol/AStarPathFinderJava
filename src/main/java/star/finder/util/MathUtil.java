package star.finder.util;

import java.util.List;

public class MathUtil {
    public static double getDistance(int[] pos1, int[] pos2) {
        return (double) (Math.sqrt((pos1[0] - pos2[0]) * (pos1[0] - pos2[0]) + (pos1[1] - pos2[1]) * (pos1[1] - pos2[1])));
    }

    public static double getDistance(Node pos1, Node pos2) {
        return (double) (Math.sqrt((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.y - pos2.y) * (pos1.y - pos2.y)));
    }

    public static boolean isObstacleBetweenBresenham(Node pos1, Node pos2, boolean[][] grid) {
        int x1 = pos1.x;
        int y1 = pos1.y;
        int x2 = pos2.x;
        int y2 = pos2.y;

        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);

        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (x1 != x2 || y1 != y2) {
            if (x1 >= 0 && x1 < grid.length && y1 >= 0 && y1 < grid[0].length && grid[x1][y1]) {
                return true;
            }

            int e2 = 2 * err;
            if (e2 > -dy) {
                err -= dy;
                x1 += sx;
            }
            if (e2 < dx) {
                err += dx;
                y1 += sy;
            }
        }

        return false;
    }

}
