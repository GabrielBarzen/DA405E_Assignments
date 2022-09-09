import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        String input = in.readLine();
        while (true) {
            input = in.readLine();
            if (input.equals("___________")) break;
            char result = decode(input);
            System.out.print(result);
        }
    }

    private static char decode(String input) {
        //A = 01000.001
        String cleaned;
        cleaned = input.replace(".","");
        char[] chars = cleaned.toCharArray();

        int cCode = 0;
        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == 'o') {
                int val = 1;
                for (int j = 0; j < 8-i; j++) {
                    val *= 2 ;
                }
                cCode += val;
            }
        }

        return (char) cCode;
    }
}