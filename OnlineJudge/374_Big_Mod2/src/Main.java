import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                long b = Integer.parseInt(in.readLine());
                long e = Integer.parseInt(in.readLine());
                long m = Integer.parseInt(in.readLine());
                System.out.println(bigMod(b,e,m));

                in.readLine();
            } catch (NumberFormatException e) {
                break;
            }
        } while (true);
    }

    private static long bigMod(long b, long e, long m) {
        if (e==0) return 1;
        long result = bigMod(b,e/2,m);

        if (e%2==0){
            return ((result*result)%m);
        }

        return ((b*(result*result))%m);
    }
}










