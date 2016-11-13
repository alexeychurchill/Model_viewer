package io.github.alexeychurchill.modelviewer.graphics.math;

/**
 * Vector class
 * Implements 3-dimensional vector operations and storage
 */

public class Vector {
    private final double x;
    private final double y;
    private final double z;

    public Vector(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public Vector add(Vector vector) {
        return new Vector(x + vector.getX(), y + vector.getY(), z + vector.getZ());
    }

    public Vector crossProduct(Vector vector) {
        double x = this.y * vector.getZ() - vector.getY() * this.z;
        double y = this.z * vector.getX() - vector.getZ() * this.x;
        double z = this.x * vector.getY() - vector.getX() * this.y;
        return new Vector(x, y, z);
    }

    public double dotProduct(Vector vector) {
        return x * vector.getX() + y * vector.getY() + z * vector.getZ();
    }
}
