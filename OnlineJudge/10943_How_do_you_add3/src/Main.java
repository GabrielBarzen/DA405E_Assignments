import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    static int[][] precalculate = new int[101][101];

    public static void main(String[] args) throws IOException {
        //read input
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String[] pair = new String[2];
        //while input is not two 0 then run calculation
        while (!((pair = in.readLine().trim().split(" "))[0].equals("0") && pair[1].equals("0"))) {
            int n = Integer.parseInt(pair[0]);
            int k = Integer.parseInt(pair[1]);
            int res = waysToAdd(n, k);
            System.out.println(res%1000000);
        }
    }

    private static int waysToAdd(int n, int k) {
        //If already calculated return value
        if (precalculate[n][k] != 0){
            return precalculate[n][k];
        }
        if (k <= 1) return k;
        if (n == 1) return k;
        //if not calculated then calculate and assign it in array
        //For all i=0...n add calculate(i,k-1) to current array.
        //Then mod 1000000
        for (int i = 0; i <= n; i++) {
            precalculate[n][k] += waysToAdd(i, k-1)%1000000;
        }
        return precalculate[n][k];
    }
}