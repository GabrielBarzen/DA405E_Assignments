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

        while (true){
            int n = scanner.nextInt();
            if (n == 0) break;

            Point[] points = new Point[n];
            for (int i = 0; i < points.length; i++) {
                Point p = new Point();
                p.x = scanner.nextInt();
                p.y = scanner.nextInt();
                points[i] = p;
            }

            if (solve(points)) {
                System.out.println("Yes");
            } else {
                System.out.println("No");
            }
        }
    }

    private boolean solve(Point[] points) {
        boolean turnLeft = leftTurn(points[0],points[1],points[2]);

        for (int i = 1; i < points.length; i++) {
            if (turnLeft != leftTurn(points[i],points[((i+1)%points.length)],points[((i+2)%points.length)])) {
                return true;
            }
        }
        return false;
    }

    private boolean leftTurn(Point p1, Point p2, Point p3) {
        return ((p2.x - p1.x) * (p3.y - p1.y)) - ((p2.y - p1.y) * (p3.x - p1.x)) > 0;
    }


    class Point implements Comparable<Point>{
        double x;
        double y;

        public Point(){}
        @Override
        public int compareTo(Point point) {
            return Double.compare(this.x, point.x);
        }
        @Override
        public String toString() {
            return "(" + x + "," + y + ")";
        }
    }
}
