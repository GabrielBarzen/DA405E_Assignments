import java.io.BufferedInputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        new Main();
    }
    Main() {
        Scanner scanner = new Scanner(new BufferedInputStream(System.in));
        int numCase = scanner.nextInt();
        for (int i = 0; i < numCase; i++) {
            int numPoints = scanner.nextInt();
            Point[] points = new Point[numPoints];
            for (int j = 0; j < numPoints; j++) {
                Point p = new Point();
                p.x = scanner.nextInt();
                p.y = scanner.nextInt();
                points[j] = p;
            }

            Arrays.sort(points);

            Point[] peaks = pointSort(points);

            for (Point peak : peaks) {
                System.out.println("k: " + peak.k);
                System.out.println("m: " + peak.m);
            }

            double len = 0;
            for (int j = 0; j < peaks.length-1; j++) {
                double x1 = peaks[j].x;
                double y1 = peaks[j].y;

                double x2 = peaks[j].x;
                double y2 = peaks[j+1].y;

                double x3 = (y2-peaks[j].m)/peaks[j].k;
                double y3 = peaks[j+1].y;

                len += Math.sqrt(Math.pow(y1-y2,2) + Math.pow(x3-x2,2));
            }
            System.out.printf("%.2f%n", len);
        }
    }

    private Point[] pointSort(Point[] points) {
        List<Point> sortedPoints = new ArrayList<>();
        Point tallest = points[0];
        tallest.k = (points[0].y - points[0+1].y)/(points[0].x - points[0+1].x);
        tallest.m = points[0].y-(points[0].k*points[0].x);
        int startPos = 0;



        for (int i = 1; i < points.length; i++) {
            if(points[i].y >= tallest.y) {

                points[i].k = ((points[i].y - points[i+1].y)/(points[i].x - points[i+1].x));
                points[i].m = points[i].y-(points[i].k*points[i].x);
                tallest = points[i];
                startPos = i;
            }
        }
        sortedPoints.add(tallest);

        //Sätt högsta punkt till punkten efter
        tallest = points[startPos+1];

        for (int i = startPos+1; i < points.length; i++) {
            Point yCompP = new Point();
            for (int j = i+1; j < points.length; j++) {
                if (points[j].y >= tallest.y) {
                    tallest = points[j];
                    if (j+1 < points.length) {
                        yCompP = points[j + 1];
                    } else {
                        yCompP = null;
                    }
                    i = j;
                }
            }
            if (yCompP != null) {
                tallest.k = (((tallest.y - yCompP.y )/(tallest.x - yCompP.x)));
            }
            tallest.m = tallest.y-(tallest.k*tallest.x);
            sortedPoints.add(tallest);
            tallest = yCompP;
        }
        return sortedPoints.toArray(new Point[0]);
    }

    class Point implements Comparable<Point>{
        double x;
        double y;
        double k = 0.0;
        double m;

        @Override
        public int compareTo(Point point) {
            return Double.compare(this.x, point.x);
        }

        @Override
        public String toString() {
            return "(" + x + "," + y + "), k: " + k + ", m: " + m;
        }
    }
}
