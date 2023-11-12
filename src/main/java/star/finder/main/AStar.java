package star.finder.main;

import star.finder.util.MathUtil;
import star.finder.util.Node;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

// A basic AStar module
public class AStar {
    private final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
    public Node run(Node start, Node end, boolean[][] obstacleGrid, int maxIter) {
        PriorityQueue<Node> openList = new PriorityQueue<>();
        boolean[] isInOpenList = new boolean[obstacleGrid.length * obstacleGrid[0].length];
        boolean[] closedList = new boolean[obstacleGrid.length * obstacleGrid[0].length];
        int cur = 0;

        openList.add(start);

        while (!openList.isEmpty() && cur < maxIter) {
            Node currentNode = openList.poll();
            isInOpenList[currentNode.x * obstacleGrid[0].length + currentNode.y] = false;

            if (currentNode.x == end.x && currentNode.y == end.y) {
                //System.out.println(cur);
                return currentNode;
            }

            for (int[] direction : DIRECTIONS) {
                int newX = currentNode.x + direction[0];
                int newY = currentNode.y + direction[1];

                if (newX >= 0 && newX < obstacleGrid.length &&
                        newY >= 0 && newY < obstacleGrid[0].length &&
                        obstacleGrid[newX][newY] &&
                        !closedList[newX * obstacleGrid[0].length + newY]) {

                    double newH = MathUtil.getDistance(currentNode, end) + currentNode.getNonAirAround(3, 3, obstacleGrid);
                    //if (!isInOpenList[newX * obstacleGrid[0].length + newY] || newH < currentNode.h) {
                    double newAddR = MathUtil.getDistance(currentNode, start);
                    Node neighbor = new Node(newX, newY, currentNode, newAddR, newH);
                    openList.add(neighbor);
                    isInOpenList[newX * obstacleGrid[0].length + newY] = true;
                }
            }

            closedList[currentNode.x * obstacleGrid[0].length + currentNode.y] = true;
            cur++;
        }

        return null; // No path found
    }

    public Node run(int[] pos1, int[] pos2, List<List<Integer>> nums) {
        boolean[][] booleanList = new boolean[nums.size()][nums.get(0).size()];

        for (int x = 0; x < nums.size(); ++x) {
            for (int y = 0; y < nums.get(x).size(); ++y) {
                int cur = nums.get(x).get(y);
                booleanList[y][x] = cur > 180;
            }
        }

        return run(new Node(pos1[0], pos1[1], null, 0, MathUtil.getDistance(pos1, pos2)), new Node(pos2[0], pos2[1], null, MathUtil.getDistance(pos1, pos2), 0), booleanList, 10000);
    }

    public List<Node> run(int[] pos1, int[] pos2, List<List<Integer>> nums, boolean isList) {
        Node result = run(pos1, pos2, nums);
        if (result == null) {
            return new ArrayList<>();
        }

        if (isList) {
            return result.makeList();
        }

        return new ArrayList<>();
    }
}
