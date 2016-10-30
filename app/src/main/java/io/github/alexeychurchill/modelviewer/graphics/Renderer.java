package io.github.alexeychurchill.modelviewer.graphics;

import android.util.Log;

import java.util.LinkedList;
import java.util.List;

import io.github.alexeychurchill.modelviewer.graphics.shapes.Model3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Point;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Polygon2d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Polygon3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Vertex;

/**
 * Renderer class
 * Projects 3d points to 2d points, 3d polys to 2d polys
 */

public class Renderer {
    public static final double DEFAULT_C = -500.0;
    private double c = DEFAULT_C;

    public double getC() {
        return c;
    }

    public void setC(double c) {
        this.c = c;
    }

    public Point project(Vertex vertex) {
        double coefficient = 1.0 - (vertex.getZ() / c);
        return new Point(vertex.getX() / coefficient, vertex.getY() / coefficient);
    }

    public List<Point> projectPoints(List<Vertex> points3d) {
        List<Point> points2d = new LinkedList<>();
        for (Vertex vertex : points3d) {
            points2d.add(project(vertex));
        }
        return points2d;
    }

    public Polygon2d projectPolygon(Polygon3d polygon3d) {
        return new Polygon2d(projectPoints(polygon3d.getPointList()));
    }

    private boolean polygonVisible(Polygon3d polygon3d) {
        double xv = -polygon3d.getPointList().get(0).getX();
        double yv = -polygon3d.getPointList().get(0).getY();
        double zv = c-polygon3d.getPointList().get(0).getZ();

        double xa = polygon3d.getPointList().get(1).getX() - polygon3d.getPointList().get(0).getX();
        double ya = polygon3d.getPointList().get(1).getY() - polygon3d.getPointList().get(0).getY();
        double za = polygon3d.getPointList().get(1).getZ() - polygon3d.getPointList().get(0).getZ();

        double xb = polygon3d.getPointList().get(2).getX() - polygon3d.getPointList().get(0).getX();
        double yb = polygon3d.getPointList().get(2).getY() - polygon3d.getPointList().get(0).getY();
        double zb = polygon3d.getPointList().get(2).getZ() - polygon3d.getPointList().get(0).getZ();

        double xn = ya * zb - yb * za;
        double yn = za * xb - zb * xa;
        double zn = xa * yb - xb * ya;

        return (xv * xn + yv * yn + zv * zn) < 0.0;
    }

    public List<Polygon2d> projectModel(Model3d model3d) {
        List<Polygon2d> polygons2d = new LinkedList<>();
        for (Polygon3d polygon3d : model3d.getPolygons()) {
            if (polygonVisible(polygon3d)) {
                polygons2d.add(projectPolygon(polygon3d));
            }
        }
        Log.d("zzz", "Visible polygons: " + polygons2d.size());
        return polygons2d;
    }
}
