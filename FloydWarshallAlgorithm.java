import java.util.Arrays;

public class FloydWarshallAlgorithm {

    public static final double INF = Double.POSITIVE_INFINITY;

    public static double[][] floydWarshall(double[][] W) {
        int n = W.length;
        double[][][] D = new double[n][n][n];
        D[0] = W;
        for (int k = 0; k < n - 1; k++) {
            int km = k + 1;
            D[km] = new double[n][n];
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    D[km][i][j] = Math.min(D[km - 1][i][j], D[km - 1][i][k] + D[km - 1][k][j]);
                }
            }
            System.out.println("\nD" + (km));
            printMatrix(D[km]);
        }
        return D[n - 1];
    }

    public static double[][] floydWarshallRecursive(double[][] W, int k) {
        int n = W.length;
        double[][] Dk = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                Dk[i][j] = Math.min(W[i][j], W[i][k] + W[k][j]);
            }
        }
        System.out.println("\nD" + (k + 1));
        printMatrix(Dk);
        if (k == n - 1) {
            return Dk;
        }
        return floydWarshallRecursive(Dk, k + 1);
    }

    public static double[][] constructParentMatrix(double[][] W) {
        int n = W.length;
        double[][] P = new double[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (i != j && W[i][j] != INF) {
                    P[i][j] = i;
                } else {
                    P[i][j] = -1; // Using -1 to indicate "none"
                }
            }
        }
        return P;
    }

    public static void printMatrix(double[][] matrix) {
        for (double[] row : matrix) {
            for (double val : row) {
                if (val == INF) {
                    System.out.print("INF ");
                } else {
                    System.out.printf("%.1f ", val);
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        double[][] W = {
            {0, 3, 8, INF, -4},
            {INF, 0, INF, 1, 7},
            {INF, 4, 0, INF, INF},
            {2, INF, -5, 0, INF},
            {INF, INF, INF, 6, 0}
        };

        System.out.println("Simple Floyd-Warshall");
        double[][] finalMatrix = floydWarshall(W);
        System.out.println("\nFinal");
        printMatrix(finalMatrix);

        System.out.println("\nRecursive Floyd-Warshall");
        double[][] finalRecursive = floydWarshallRecursive(W, 0);
        System.out.println("\nFinal");
        printMatrix(finalRecursive);

        System.out.println("\nParent Matrix");
        double[][] parentMatrix = constructParentMatrix(W);
        printMatrix(parentMatrix);
    }
}
