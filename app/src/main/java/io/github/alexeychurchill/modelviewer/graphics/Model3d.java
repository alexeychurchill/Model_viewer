package io.github.alexeychurchill.modelviewer.graphics;

import java.util.LinkedList;
import java.util.List;

/**
 * 3D model class
 */

public class Model3d {
    private List<Vertex> vertices = new LinkedList<>();
    private List<List<Integer>> faces = new LinkedList<>();
    private double[][] transforms = MatrixUtils.getIdentityMatrix(4);

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<List<Integer>> getFaces() {
        return faces;
    }

    public List<Polygon3d> getPolygons() {
        List<Polygon3d> polygon3dList = new LinkedList<>();
        for (List<Integer> face : faces) {
            Polygon3d polygon3d = new Polygon3d();
            for (Integer vertexNumber : face) {
                polygon3d.getPointList().add(transform(vertices.get(vertexNumber)));
            }
            polygon3dList.add(polygon3d);
        }
        return polygon3dList;
    }

    public void move(double dx, double dy, double dz) {
        double[][] moveTransform = {
                {1.0, 0.0, 0.0,  dx},
                {0.0, 1.0, 0.0,  dy},
                {0.0, 0.0, 1.0,  dz},
                {0.0, 0.0, 0.0, 1.0}
        };
        transforms = MatrixUtils.multiply(moveTransform, transforms);
    }

    public void scale(double s) {
        double[][] scaleTransform = {
                {s,   0.0, 0.0, 0.0},
                {0.0,   s, 0.0, 0.0},
                {0.0, 0.0,   s, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        transforms = MatrixUtils.multiply(scaleTransform, transforms);
    }

    public void rotateByX(double alpha) {
        alpha = (Math.PI / 180.0) * alpha;
        double sin = Math.sin(alpha);
        double cos = Math.cos(alpha);
        double[][] rotateXTransform = {
                {1.0, 0.0, 0.0, 0.0},
                {0.0, cos, -sin, 0.0},
                {0.0, sin, cos, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        transforms = MatrixUtils.multiply(rotateXTransform, transforms);
    }

    public void rotateByY(double beta) {
        beta = (Math.PI / 180.0) * beta;
        double sin = Math.sin(beta);
        double cos = Math.cos(beta);
        double[][] rotateYTransform = {
                {cos, 0.0, sin, 0.0},
                {0.0, 1.0, 0.0, 0.0},
                {-sin, 0.0, cos, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        transforms = MatrixUtils.multiply(rotateYTransform, transforms);
    }

    public void rotateByZ(double phi) {
        phi = (Math.PI / 180.0) * phi;
        double sin = Math.sin(phi);
        double cos = Math.cos(phi);
        double[][] rotateZTransform = {
                {cos, -sin, 0.0, 0.0},
                {sin, cos, 0.0, 0.0},
                {0.0, 0.0, 1.0, 0.0},
                {0.0, 0.0, 0.0, 1.0}
        };
        transforms = MatrixUtils.multiply(rotateZTransform, transforms);
    }

    private Vertex transform(Vertex vertex) {
        double x = vertex.getX() * transforms[0][0] + vertex.getY() * transforms[0][1] + vertex.getZ() * transforms[0][2] + transforms[0][3];
        double y = vertex.getX() * transforms[1][0] + vertex.getY() * transforms[1][1] + vertex.getZ() * transforms[1][2] + transforms[1][3];
        double z = vertex.getX() * transforms[2][0] + vertex.getY() * transforms[2][1] + vertex.getZ() * transforms[2][2] + transforms[2][3];
        return new Vertex(x, y, z);
    }
}
