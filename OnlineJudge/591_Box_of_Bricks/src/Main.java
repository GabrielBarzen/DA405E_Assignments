import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int setNr = 0;
        while (true) {
            int n = Integer.parseInt(in.readLine());
            if (n <= 0) break;
            setNr++;

            String[] input = in.readLine().split(" ");
            int[] stacks = new int[input.length];
            for (int i = 0; i < input.length; i++) {
                stacks[i] = Integer.parseInt(input[i]);
            }

            int totalBlocks = 0;
            for (int i = 0; i < stacks.length; i++) {
                totalBlocks += stacks[i];
            }
            int avgHeight = totalBlocks/n;

            int numMoves = 0;
            for (int i = 0; i < stacks.length; i++) {
                if (stacks[i] > avgHeight) {
                    numMoves += stacks[i] - avgHeight;
                }
            }

            System.out.println("Set #" + setNr);
            System.out.println("The minimum number of moves is "+ numMoves +".");
            System.out.println();
        }
    }
}
