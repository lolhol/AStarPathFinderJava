package star.finder.main;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import star.finder.util.Node;

import java.util.Arrays;
import java.util.List;

class AStarTest {
    private String[] map;
    private AStar star = new AStar();

    @BeforeEach
    public void beforeEach() {
        map = new String[] {
                "####################",
                "#                  #",
                "#               #  #",
                "###          #    ##",
                "###               ##",
                "###       ##      ##",
                "###      ####     ##",
                "###       ##       #",
                "###                #",
                "#                  #",
                "####################"
        };
    }


    @Test
    void testFinder() {
        boolean[][] pMap = parseMap();

        List<Node> out = star.run(new int[]{1, 1}, new int[] {15, 5}, pMap);
        printMapWithPath(out, map, new int[]{1, 1}, new int[] {15, 5});
    }

    private void printMapWithPath(List<Node> path, String[] map, int[] start, int[] end) {
        String[] newMap = map.clone();
        for (Node n : path) {
            newMap[n.y] = replaceCharAt(n.x, newMap[n.y], '*');
        }

        newMap[start[1]] = replaceCharAt(start[0], newMap[start[1]], 'S');
        newMap[end[1]] = replaceCharAt(end[0], newMap[end[1]], 'E');

        for (String i : newMap) {
            System.out.println(i);
        }
    }

    private String replaceCharAt(int pos, String origin, char newChar) {
        char[] charArray = origin.toCharArray();
        charArray[pos] = newChar;
        return new String(charArray);
    }

    private boolean[][] parseMap() {
        boolean[][] gridMap = new boolean[map.length][];

        for (int i = 0; i < map.length; i++) {
            boolean[] mapTMP = new boolean[map[i].length()];
            for (int j = 0; j < map[i].length(); j++) {
                mapTMP[j] = map[i].charAt(j) != '#';
            }

            gridMap[i] = mapTMP;
        }

        return gridMap;
    }
}
