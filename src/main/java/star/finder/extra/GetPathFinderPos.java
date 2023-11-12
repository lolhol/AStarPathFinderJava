package star.finder.extra;

import star.finder.util.MathUtil;
import star.finder.util.Node;

import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;

public class GetPathFinderPos {
    private static class OpenNode implements Comparable<OpenNode> {
        public double cost;
        public int x, y;

        public OpenNode(int[] position, double totalCost) {
            this.cost = totalCost;
            this.x = position[0];
            this.y = position[1];
        }

        @Override
        public int compareTo(OpenNode other) {
            return Double.compare(this.cost, other.cost);
        }
    }

    public static int[] getNextPos(List<List<Boolean>> curMap, int[] curPos, HashSet<int[]> remove) {
        PriorityQueue<OpenNode> possibleNodes = new PriorityQueue<>();

        for (int x = 0; x < curMap.size(); x++) {
            for (int y = 0; y < curMap.get(x).size(); y++) {
                if (curPos[0] == x && curPos[1] == y) continue;

                if (!curMap.get(x).get(y)) continue;
                if (!remove.contains(new int[]{x, y})) continue;

                OpenNode curPossible = new OpenNode(new int[] {x, y}, 0);
                if (hasObstructionBetweenPoints((int) curPos[0], (int) curPos[1], curPossible.x, curPossible.y, curMap)) curPossible.cost += MathUtil.getDistance(new int[]{(int) curPos[0], (int) curPos[1]}, new int[]{x, y});
                curPossible.cost += MathUtil.getDistance(new int[]{(int) curPos[0], (int) curPos[1]}, new int[]{x, y});

                possibleNodes.add(curPossible);
            }
        }

        OpenNode best = possibleNodes.poll();
        assert best != null;
        return new int[] {best.x, best.y};
    }

    public static boolean hasObstructionBetweenPoints(int x1, int y1, int x2, int y2, List<List<Boolean>> map) {
        int dx = Math.abs(x2 - x1);
        int dy = Math.abs(y2 - y1);
        int sx = (x1 < x2) ? 1 : -1;
        int sy = (y1 < y2) ? 1 : -1;

        int err = dx - dy;

        while (true) {
            // Check for an obstruction at the current position
            if (isObstructed(x1, y1, map)) {
                return true;  // Obstruction found
            }

            if (x1 == x2 && y1 == y2) {
                break;  // Reached the destination without obstruction
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

        return false;  // No obstruction found
    }

    private static boolean isObstructed(int x, int y, List<List<Boolean>> map) {
        if (x >= 0 && x < map.size() && y >= 0 && y < map.get(0).size()) {
            return map.get(x).get(y);
        }

        return true;
    }
}
