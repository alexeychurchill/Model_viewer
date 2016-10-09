package io.github.alexeychurchill.modelviewer.graphics;

import java.lang.reflect.Array;

/**
 * Matrix utils class.
 * Allows to multiply matrices, get E-matrix;
 */

public class MatrixUtils {
    public static double[][] getIdentityMatrix(int size) {
        double[][] identityMatrix = new double[size][size];
        for (int i = 0; i < size; i++) {
            identityMatrix[i][i] = 1.0;
        }
        return identityMatrix;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        if (a == null || b == null) {
            return null;
        }
        if (a.length < 1 || b.length < 1) {
            throw new IndexOutOfBoundsException("Empty matrix");
        }
        if (a[0].length != b.length) {
            throw new IndexOutOfBoundsException("Incompatible matrices");
        }
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < result.length; i++) {
            for (int j = 0; j < result[i].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }
}
