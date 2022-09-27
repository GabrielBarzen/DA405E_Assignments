import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
    static int[][] map;
    static int[][] costs;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String strIN = "";
        while ((strIN = in.readLine()) != null){
            String[] strPair = strIN.split(" ");
            int y = Integer.parseInt(strPair[0]);
            int x = Integer.parseInt(strPair[1]);
            map = new int[y][x];
            costs = new int[map.length][map[0].length];
            for (int i = 0; i < y; i++) {
                String[] currLine = in.readLine().split(" ");
                for (int i1 = 0; i1 < x; i1++) {
                    map[i][i1] = Integer.parseInt(currLine[i1]);
                }
            }
            printArrs();
            System.out.println("init arrs");
            init();
            System.out.println("init done");
            printArrs();
            System.out.println("running all mindist");

            for (int i = 0; i < y; i++) {
                minDist(i, x-1);
            }
            minPath();
            System.out.println("mindist done");
            printArrs();
        }
    }

    private static void minPath() {
    }

    public static void init(){
        for (int i = 0; i < map.length; i++) {
            costs[i][0] = map[i][0];
        }
    }

    public static int minDist(int y,int x) {

        if (costs[y][x] != 0) return costs[y][x];
        if (map[y][x] == 0) return Integer.MAX_VALUE;

        int yp1 = minDist(((y+1)%(map.length)),x-1 );
        int y0 = minDist(y,x-1);
        int ym1 = minDist((((y-1)+map.length)%(map.length)),x-1);

        if (yp1 <= y0 && yp1 <= ym1) {

            costs[y][x] = yp1+map[y][x];
            return costs[y][x];
        }
        if (y0 <= yp1 && y0 <= ym1) {

            costs[y][x] = y0+map[y][x];
            return costs[y][x];
        }
        if (ym1 <= y0 && ym1 <= yp1) {

            costs[y][x] = ym1+map[y][x];
            return costs[y][x];
        }

        return -1; //TODO
    }

    public static void printArrs(){
        for (int[] ints : map) {
            System.out.println(Arrays.toString(ints));
        }
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(costs[i]));
        }
    }
}
