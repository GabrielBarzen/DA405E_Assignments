public class Main {
    public static void main(String[] args) {
        int n = 100;
        int k = 100;
        System.out.println(waysToAdd(k,n)%100000);

    }


    //
    //  (n+(k-1))!/n!k!
    //

    //save the previous multiplications in order to exchange time spent for space spent
    static long[][] precompute = new long[101][101];
    private static long waysToAdd(int k, int n) {
        System.out.println("running pair  k, n : " +k + ", " +n );
        if (precompute[k][n] != 0) {
            System.out.println("already computed returning : " + precompute[k][n] );
            return precompute[k][n];
        }
        //If k = 0, the program has calculated all the k,n pairs down to 0
        if (k<=1){
            precompute[k][n] = 1;
            return precompute[k][n];
        }

        int numerator = (n+k-1);
        int denumerator = (k-1);

        for (int i = 1; i < k; i++) {
            long result = 0;
            long prevResult = waysToAdd(i,n);
            System.out.println("recursive ways to add : " + prevResult + ", for pair k, n : " + k + ", " + n);
            System.out.println("numerator : " + numerator);
            System.out.println("denumerator : " + denumerator);
            System.out.println(((result = (prevResult) * (numerator)/denumerator)) + " = " + prevResult + " * " +numerator+ " / "+ denumerator);
            System.out.println(((result ) + " = " + prevResult + " * " +(numerator/ denumerator)));
            precompute[k][n] =  result;
            System.out.println(precompute[k][n]);

            precompute[k][n] %= 1000000;
        }
        return precompute[k][n];
    }
}
