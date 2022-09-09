import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
public class Main {
    static BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    static boolean even = true;
    public static void main(String[] args) throws IOException {
        String numCaseIn = in.readLine();
        int numCases = Integer.parseInt(numCaseIn);
        for (int i = 0; i < numCases; i++) {
            even = true;
            if (i > 0) {
                System.out.println();
            }
            in.readLine();
            evaluate(in.readLine());
            if (even) {
                System.out.println("YES");
            } else {
                System.out.println("NO");
            }
        }
    }
    private static int evaluate(String readLine) throws IOException {
        String[] input = readLine.split(" ");
        int[] quart = new int[input.length];
        for (int i = 0; i < input.length; i++) {
            quart[i] = Integer.parseInt(input[i]);
        }
        int wL = quart[0];
        int dL = quart[1];
        int wR = quart[2];
        int dR = quart[3];
        if (wL == 0) {
            wL = evaluate(in.readLine());
        }
        if (wR == 0) {
            wR = evaluate(in.readLine());
        }
        if (wL * dL == wR * dR) {
            return wL + wR;
        } else {
            even = false;
            return 0;
        }
    }
}