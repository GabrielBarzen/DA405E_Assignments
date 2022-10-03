import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static long[][] map;
    static long[][] costs;
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        String strIn;

        StringBuilder sb = new StringBuilder();
        while ((strIn=in.readLine())!=null && strIn.length()!=0) {
            sb.append(strIn.trim());
            sb.append(" ");
        }
        sb.append(" |");



        StringTokenizer st = new StringTokenizer(sb.toString().trim());
        int y;
        int x;
        while(true){
            String startToken = st.nextToken();
            if (startToken.equals("|")) break;

            y = Integer.parseInt(startToken);
            x = Integer.parseInt(st.nextToken());

            map = new long[y][x];
            costs = new long[y][x];
            for (int i = 0; i < costs.length; i++) {
                Arrays.fill(costs[i],Long.MAX_VALUE);
            }

            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[0].length; j++) {
                    String token = st.nextToken();

                    map[i][j] = Integer.parseInt(token);


                }
            }


            init();
            minDist();
            long[] result = minPath();

            for (int i = 0; i < result.length; i++) {
                if (i==result.length-1) {
                    System.out.println(result[i]);
                    continue;
                }
                if (i==result.length-2) {
                    System.out.println(result[i] + 1);
                    continue;
                }
                System.out.print((result[i] + 1) + " ");
            }

        }
    }

    public static void init(){
        for (int i = 0; i < costs.length; i++) {
            costs[i][costs[0].length-1] = map[i][costs[0].length-1];
        }
    }
    public static void minDist() {
        for (int y = 0; y < costs.length; y++) {
            minDist(y,0);
        }

    }

    private static void printArrs() {
        System.out.println("Printing map");
        printMap();
        System.out.println("Printing costs");
        printCosts();
    }
    private static void printCosts() {
        for (int i = 0; i < costs.length; i++) {
            System.out.println(Arrays.toString(costs[i]));
        }
    }
    private static void printMap() {
        for (int i = 0; i < map.length; i++) {
            System.out.println(Arrays.toString(map[i]));
        }
    }

    public static long minDist(int y,int x) {
        if (costs[y][x] != Long.MAX_VALUE) {
            return costs[y][x];

        }

        long yp1 = minDist(((y+1)%(map.length)),x + 1 );
        long y0 = minDist(y,x + 1);
        long ym1 = minDist((((y-1)+map.length)%(map.length)),x + 1);

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

    private static long[]  minPath() {
        long[] minPath = new long[costs[0].length+1];
        int y = 0;
        long startPosCost = costs[y][0];
        for (int i = 1; i < costs.length; i++) {
            if (costs[i][0] < costs[y][0]) {
                y = i;
                startPosCost = costs[i][0];
                continue;
            }
            if (costs[i][0] < startPosCost) startPosCost = costs[i][0];
        }

        minPath[minPath.length-1] = startPosCost;

        minPath[0] = y;

        for (int x = 1; x < minPath.length-1; x++) {
            int[] yOpt = new int[3]; // Create array for previous best options
            yOpt[0] = ((y-1)+map.length)%(map.length);
            yOpt[1] = y;
            yOpt[2] = (y+1)%(map.length);

            sort(yOpt);

            long smallest = costs[yOpt[0]][x];
            y = yOpt[0];
            if (costs[yOpt[1]][x] < smallest) {
                smallest = costs[yOpt[1]][x];
                y = yOpt[1];
            }
            if (costs[yOpt[2]][x] < smallest) {
                smallest = costs[yOpt[2]][x];
                y = yOpt[2];
            }


            minPath[x] = y;

        }


        return minPath;
    }



}
