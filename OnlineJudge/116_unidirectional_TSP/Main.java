import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static int[][] map;
    static int[][] costs;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String strIn;

        StringBuilder sb = new StringBuilder();
        while ((strIn=in.readLine())!=null && strIn.length()!=0) {
            if (strIn.split(" ").length == 2) sb.append("# ");
            sb.append(strIn);
            sb.append(" ");
        }
        sb.append("|");

        StringTokenizer st = new StringTokenizer(sb.toString());
        int y;
        int x;
        while(true){
            String startToken = st.nextToken();
            if (startToken.equals("|")) break;

            y = Integer.parseInt(st.nextToken());
            x = Integer.parseInt(st.nextToken());

            map = new int[y][x];
            costs = new int[y][x];

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {

                    map[i][j] = Integer.parseInt(st.nextToken());

                }
            }


            init();
            for (int i = 0; i < y; i++) {
                if (map[i][x-1] != 0) {
                    minDist(i, x - 1);
                }
            }


            int[] result = minPath();

            for (int i = 0; i < result.length; i++) {
                if (i==result.length-1) {
                    System.out.println();
                    System.out.println(result[i]);
                    continue;
                }
                System.out.print((result[i] + 1) + " ");
            }

        }
    }

    private static int[]  minPath() {
        //Find start path
        int y = 0;
        int startPosCost = Integer.MAX_VALUE;
        for (int i = 1; i < costs.length; i++) {
            if (costs[i][costs[0].length-1] < costs[y][costs[0].length-1]) {
                y = i;
                startPosCost = costs[i][costs[0].length-1];
                continue;
            }
            if (costs[i][costs[0].length-1] < startPosCost) startPosCost = costs[i][costs[0].length-1];
        }



        int[] minPath = new int[map[0].length+1];
        minPath[minPath.length-2] = y;
        minPath[minPath.length-1] = startPosCost;


        for (int x = minPath.length-3; x >= 0; x--) {
            int smallestCost = costs[y][costs[0].length-1];

            int[] yOpt = new int[3]; // Create array for previous best options
            yOpt[0] = ((y-1)+map.length)%(map.length);
            yOpt[1] = y;
            yOpt[2] = (y+1)%(map.length);

            sort(yOpt);

            for (int i = 0; i < yOpt.length; i++) {
                if (costs[yOpt[i]][x] < smallestCost) {
                    y = yOpt[i];
                    smallestCost = costs[y][x];
                }
            }
            minPath[x] = y;
        }
        return minPath;
    }

    private static void sort(int[] in) {
        //Basic selection sort
        for (int i = 0; i < in.length; i++) {
            for (int j = i; j < in.length; j++) {
                if (in[j] < in[i]) {
                    int temp = in[i];
                    in[i] = in[j];
                    in[j] = temp;
                }
            }
        }
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

        if (y0 <= yp1 && y0 <= ym1) {

            costs[y][x] = y0+map[y][x];
            return costs[y][x];
        }
        if (yp1 <= y0 && yp1 <= ym1) {

            costs[y][x] = yp1+map[y][x];
            return costs[y][x];
        }


        costs[y][x] = ym1+map[y][x];
        return costs[y][x];


    }
}
