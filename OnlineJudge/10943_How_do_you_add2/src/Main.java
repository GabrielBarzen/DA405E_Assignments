public class Main {
    public static void main(String[] args) {
        int n = 100;
        int k = 100;
        waysToAdd(k,n);

    }

    //
    //  (n+(k-1))!/n!k!
    //

    //save the previous multiplications in order to exchange time spent for space spent
    static long[][] precompute = new long[101][101];
    private static long waysToAdd(int k, int n) {
        if (k <= 1) return precompute[k][n] = 1;
        if (precompute[k][n] != 0 ) return precompute[n][n];
        int numerator = (n+k-1);
        int denumerator = (k-1);
        precompute[k][n] = (waysToAdd(k-1,n) * numerator/denumerator);
        System.out.println(precompute[k][n]);
        return precompute[k][n];
    }
}
