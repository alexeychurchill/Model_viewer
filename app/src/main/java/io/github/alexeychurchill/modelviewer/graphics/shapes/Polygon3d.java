package io.github.alexeychurchill.modelviewer.graphics.shapes;

import java.util.LinkedList;
import java.util.List;

import io.github.alexeychurchill.modelviewer.graphics.math.Vector;

/**
 * 3D Polygon class
 */

public class Polygon3d {
    private List<Vertex> pointList = new LinkedList<>();

    public Polygon3d() {
    }

    public Polygon3d(List<Vertex> pointList) {
        this.pointList.addAll(pointList);
    }

    public List<Vertex> getPointList() {
        return pointList;
    }

    public Vector normalVector() {
        double ax = getPointList().get(1).getX() - getPointList().get(0).getX();
        double ay = getPointList().get(1).getY() - getPointList().get(0).getY();
        double az = getPointList().get(1).getZ() - getPointList().get(0).getZ();

        double bx = getPointList().get(2).getX() - getPointList().get(0).getX();
        double by = getPointList().get(2).getY() - getPointList().get(0).getY();
        double bz = getPointList().get(2).getZ() - getPointList().get(0).getZ();

        Vector a = new Vector(ax, ay, az);
        Vector b = new Vector(bx, by, bz);

        return a.crossProduct(b);
    }
}
