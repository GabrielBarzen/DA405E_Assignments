//10300
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        int numTests = Integer.parseInt(in.readLine());

        for (int i = 0; i < numTests; i++) {

            int caseTotal = 0;
            int numFarmers = Integer.parseInt(in.readLine());

            for (int j = 0; j < numFarmers; j++) {
                String[] triple = in.readLine().split(" ");

                int farmAreal = Integer.parseInt(triple[0]);
                int animalAmount = Integer.parseInt(triple[1]);
                int envParam = Integer.parseInt(triple[2]);

                caseTotal += farmAreal * envParam;

            }
            System.out.println(caseTotal);
        }
    }
}
