import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Main {
    public static void main(String[] args) throws IOException {
        new Main();
    }

    Main() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            int n = Integer.parseInt(in.readLine()); //First input is the number of households, if 0 terminate.
            int[] a = new int[n]; //a is the array for storing each household's amount of stock or deficit of wine
            if (n <= 0) break;
            String[] aIn = in.readLine().split(" ");
            for (int i = 0; i < aIn.length; i++) {
                a[i] = Integer.parseInt(aIn[i]);
            }

            long result = 0;
            boolean done = false;

            while (!done) {

                int biggest = 0;
                for (int i = 0; i < a.length; i++) {
                    if (a[i]>a[biggest]) {
                        biggest = i;
                    }
                }

                int pos = biggest;
                int neg = biggest;
                while (pos < a.length || neg >= 0) {

                    if (neg == -1) neg = biggest;
                    if (pos >= a.length) pos = biggest;
                    if (a[pos] >= 0 && a[neg] >= 0) {
                        neg--;
                        pos++;
                        continue;
                    }

                    if (a[pos] <= a[neg] && pos != biggest ) {
                        if (a[biggest] >= (a[pos] * -1)){
                            a[biggest] += a[pos];
                            result += (a[pos] * -1) * (pos-biggest);
                            a[pos] = 0;
                        } else {
                            result += (long) (a[biggest]) * (pos-biggest);
                            a[pos] += a[biggest];
                            a[biggest] = 0;
                        }
                    } else if (neg != biggest) {
                        if (a[biggest] >= (a[neg] * -1)){
                            a[biggest] += a[neg];
                            result += (a[neg] * -1) * (biggest-neg);
                            a[neg] = 0;
                        } else {
                            result += (long) (a[biggest]) * (biggest-neg);
                            a[neg] += a[biggest];
                            a[biggest] = 0;
                        }
                    }

                    neg--;
                    pos++;
                    if (a[biggest]==0)break;
                }

                for (int i : a) {
                    if (i == 0) {
                        done = true;
                    } else {
                        done = false;
                        break;
                    }
                }

            }

            System.out.println(result);
        }
    }
}

