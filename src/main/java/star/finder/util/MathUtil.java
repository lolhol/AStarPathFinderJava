package star.finder.util;

public class MathUtil {
    public static double getDistance(int[] pos1, int[] pos2) {
        return (double) (Math.sqrt((pos1[0] - pos2[0]) * (pos1[0] - pos2[0]) + (pos1[1] - pos2[1]) * (pos1[1] - pos2[1])));
    }

    public static double getDistance(Node pos1, Node pos2) {
        return (double) (Math.sqrt((pos1.x - pos2.x) * (pos1.x - pos2.x) + (pos1.y - pos2.y) * (pos1.y - pos2.y)));
    }
}
