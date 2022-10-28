import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String[] strings = reader.readLine().split(" ");
        do {
            int n = Integer.parseInt(strings[0]);
            int k = Integer.parseInt(strings[1]);
            if (k == 1) {
                System.out.println("1");
            } else {
                int[] array = new int[k + 1];
                array[0] = n + 1;
                if (k > 2) {
                    for (int i = 1; i < k; i++) {
                        array[i] = array[i - 1] + (i) * array[0];
                    }
                    System.out.println(array[k - 1] % 1000000);
                } else {
                    System.out.println(array[0]);
                }

            }
            strings = reader.readLine().split(" ");
        } while (!(strings[0].equals("0") || strings[1].equals("0")));

    }
}
