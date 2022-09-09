import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        do {
            try {
                int b = Integer.parseInt(in.readLine());
                int e = Integer.parseInt(in.readLine());
                int m = Integer.parseInt(in.readLine());
                BigInteger base = BigInteger.valueOf(b);
                System.out.println(base.modPow(BigInteger.valueOf(e), BigInteger.valueOf(m)));
                in.readLine();
            } catch (NumberFormatException e) {
                break;
            }
        } while (true);
    }
}










