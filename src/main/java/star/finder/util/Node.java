package star.finder.util;

import java.util.ArrayList;
import java.util.List;

public class Node implements Comparable<Node> {
    public int x, y;
    public Node parent;
    public double g;
    public double h;
    public double f;

    public Node(int x, int y, Node parent, double g, double h) {
        this.x = x;
        this.y = y;
        this.parent = parent;
        this.g = g;
        this.h = h;
        this.f = g + h;
    }

    @Override
    public int compareTo(Node other) {
        return Double.compare(this.f, other.f);
    }

    public Node[] getNodesAround(int addX, int addY) {
        Node[] nodes = new Node[addX * addY];

        for (int x = -addX; x < addX; ++x) {
            for (int y = -addY; y < addY; ++y) {
                if (this.y != y || this.x != x) nodes[x] = new Node(this.x + x, this.y + y, this, 0, 0);
            }
        }

        return nodes;
    }

    public boolean isOnGrid(boolean[][] obstacleGrid) {
        return this.y >= 0 && this.y < obstacleGrid[this.y].length && this.x >= 0 && this.x < obstacleGrid.length;
    }

    public boolean isOnGrid(boolean[][] obstacleGrid, int x, int y) {
        return y >= 0 && y < obstacleGrid.length && x >= 0 && x < obstacleGrid[y].length;
    }

    public List<Node> getNodesAround(int addX, int addY, boolean[][] grid) {
        List<Node> nodes = new ArrayList<>();

        for (int x = -addX; x < addX; ++x) {
            for (int y = -addY; y < addY; ++y) {
                if ((this.y != y || this.x != x) && isOnGrid(grid, this.x + x, this.y + y) && x > 0 && y > 0) nodes.add(new Node(this.x + x, this.y + y, this, 0, 0));
            }
        }

        return nodes;
    }

    public int getNonAirAround(int addX, int addY, boolean[][] grid) {
        int amNonAir = 0;

        for (Node node : getNodesAround(addX, addY, grid)) {
            if (!grid[node.y][node.x]) {
                amNonAir++;
            }
        }

        return amNonAir;
    }

    public List<Node> makeList() {
        List<Node> nodes = new ArrayList<>();

        Node node = this;
        while (node != null) {
            //System.out.println(node.x + "," + node.y);
            nodes.add(node);
            node = node.parent;
        }

        List<Node> nodesRev = new ArrayList<>();
        int m = nodes.size() - 1;
        while (m > -1) {
            nodesRev.add(nodes.get(m));
            m--;
        }

        return nodesRev;
    }
}
