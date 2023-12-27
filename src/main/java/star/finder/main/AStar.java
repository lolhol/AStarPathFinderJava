package star.finder.main;

import star.finder.util.MathUtil;
import star.finder.util.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

// A basic AStar module
public class AStar {
    // Possibly soon multithreading but idk

    private final int[][] DIRECTIONS = { { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 } };

    public Node run(Node start, Node end, boolean[][] obstacleGrid, int maxIter, boolean isMulti) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[] closedList = new boolean[obstacleGrid.length * obstacleGrid[0].length];
        int cur = 0;

        openList.add(start);
        System.out.println(end.x + " | " + end.y);

        while (!openList.isEmpty() && cur < maxIter) {
            Node currentNode = openList.poll();

            if (currentNode.x == end.x && currentNode.y == end.y) {
                return currentNode;
            }

            for (int[] direction : DIRECTIONS) {
                int newX = currentNode.x + direction[0];
                int newY = currentNode.y + direction[1];

                if (newY >= 0 && newY < obstacleGrid.length &&
                        newX >= 0 && newX < obstacleGrid[newY].length &&
                        obstacleGrid[newY][newX] &&
                        !closedList[newY * obstacleGrid[newY].length + newX]) {
                    double newH = MathUtil.getDistance(currentNode, end)
                            + currentNode.getNonAirAround(3, 3, obstacleGrid);
                    double newAddR = MathUtil.getDistance(currentNode, start);
                    Node neighbor = new Node(newX, newY, currentNode, newAddR, newH);
                    openList.add(neighbor);
                }
            }

            closedList[currentNode.x * obstacleGrid[0].length + currentNode.y] = true;
            cur++;
        }

        System.out.println(cur);
        return null; // No path found
    }

    public Node run(int[] pos1, int[] pos2, List<List<Integer>> nums, int maxIter) {
        boolean[][] booleanList = new boolean[nums.size()][nums.get(0).size()];

        for (int x = 0; x < nums.size(); ++x) {
            for (int y = 0; y < nums.get(x).size(); ++y) {
                int cur = nums.get(x).get(y);
                booleanList[y][x] = cur > 180;
            }
        }

        return run(new Node(pos1[0], pos1[1], null, 0, MathUtil.getDistance(pos1, pos2)),
                new Node(pos2[0], pos2[1], null, MathUtil.getDistance(pos1, pos2), 0), booleanList, maxIter, false);
    }

    public List<Node> run(int[] pos1, int[] pos2, List<List<Integer>> nums, boolean isList) {
        Node result = run(pos1, pos2, nums, 10000);
        if (result == null) {
            return new ArrayList<>();
        }

        if (isList) {
            return result.makeList();
        }

        return new ArrayList<>();
    }

    public List<Node> run(int[] pos1, int[] pos2, boolean[][] map) {
        Node result = this.run(new Node(pos1[0], pos1[1], null, 0, MathUtil.getDistance(pos1, pos2)),
                new Node(pos2[0], pos2[1], null, MathUtil.getDistance(pos1, pos2), 0), map, 50, false);

        return result == null ? new ArrayList<>() : result.makeList();
    }

    public List<Node> run(int[] pos1, int[] pos2, byte[] initMap, int mapSizeX, int mapSizeY) {
        boolean[][] gridMap = new boolean[mapSizeX][mapSizeY];

        for (int x = 0; x < mapSizeX; x++) {
            for (int y = 0; y < mapSizeY; y++) {
                int current =  (int) initMap[x * mapSizeX + y] & 0xFF;
                gridMap[x][y] = current > 180;
            }
        }

        return this.run(pos1, pos2, gridMap);
    }

    public List<Node> shortenList(List<Node> original, boolean[][] gridBoard) {
        List<Node> returnList = new ArrayList<>();
        int i = 0;
        int curCount = 0;
        Node curMain = original.get(curCount);
        while (i < original.size()) {
            Node curTMP = original.get(i);

            if (MathUtil.isObstacleBetweenBresenham(curMain, curTMP, gridBoard)) {
                returnList.add(original.get(i - 1));
                curMain = curTMP;
            }

            i++;
        }

        return returnList;
    }
}
