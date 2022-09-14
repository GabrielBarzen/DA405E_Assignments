import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main();
    }
    public Main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        int n = 0;
        while ((n = Integer.parseInt(in.readLine())) != 0) {
            long[] bottle = new long[n];
            String[] aIn = in.readLine().split(" ");
            for (int i = 0; i < aIn.length; i++) {
                bottle[i] = Long.parseLong(aIn[i]);
            }
            long result = 0;
            for (int i = 0; i < bottle.length-1; i++) {
                result += absolute(bottle[i]);
                bottle[i+1] += bottle[i];
            }
            System.out.println(result);
        }
    }
    private long absolute(long bottles) {
        return bottles < 0 ? bottles*-1 : bottles;
    }
}
