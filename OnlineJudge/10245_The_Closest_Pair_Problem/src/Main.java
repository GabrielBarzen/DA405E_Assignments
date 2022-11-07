import java.util.Locale;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        new Main();
    }

    Main(){
        Scanner scanner = new Scanner(System.in);
        int n;
        while ((n = scanner.nextInt()) != 0) {
            double[][] points = new double[n][2];
            for (int i = 0; i < n; i++) {
                points[i][0] = scanner.nextDouble();
                points[i][1] = scanner.nextDouble();
            }
            if (n == 1) {
                System.out.println("INFINITY");
                continue;
            }

            double shortest = Double.MAX_VALUE;
            for (int p1i = 0; p1i < points.length; p1i++) {
                for (int p2i = p1i+1; p2i < points.length; p2i++) {
                    double distance = distance(points[p1i][0],points[p1i][1],points[p2i][0],points[p2i][1]);
                    if ( distance < shortest) shortest = distance;
                }
            }

            System.out.println(shortest < 10000.0000 ? String.format(Locale.US,"%.4f", shortest) : "INFINITY");
        }
    }


    private static double distance (double p1x,double p1y, double p2x, double p2y ) {
        return Math.sqrt((Math.pow((p1x-p2x),2) + Math.pow((p1y-p2y),2)));
    }
}