import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        new Main();
    }
    public Main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            try {
                String[] toValidate = in.readLine().split(" ");
                if (toValidate.length < 2) break;
                char[] unCipher = toValidate[0].toCharArray();
                char[] cipher = toValidate[1].toCharArray();
                boolean isSubSequence = false;
                if (!(cipher.length < unCipher.length)) {
                    int numRequired = unCipher.length;
                    int numFound = 0;
                    int start = 0;
                    for (int i = 0; i < unCipher.length; i++) {
                        for (int comparison = start; comparison < cipher.length; comparison++) {
                            if (unCipher[i] == cipher[comparison]) {
                                start = comparison + 1;
                                numFound++;
                                break;
                            }
                        }
                    }
                    isSubSequence = numRequired == numFound;
                }
                System.out.println(isSubSequence ? "Yes" : "No");
            } catch (Exception e) {break;}
        }
    }
}