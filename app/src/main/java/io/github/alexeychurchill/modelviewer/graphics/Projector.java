package io.github.alexeychurchill.modelviewer.graphics;

import java.util.LinkedList;
import java.util.List;

import io.github.alexeychurchill.modelviewer.graphics.shapes.Model3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Point;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Polygon2d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Polygon3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Vertex;

/**
 * Projector class
 * Projects 3d points to 2d points, 3d polys to 2d polys
 */

public class Projector {
    public static final double DEFAULT_C = 25.0;
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

    public List<Polygon2d> projectModel(Model3d model3d) {
        List<Polygon2d> polygons2d = new LinkedList<>();
        for (Polygon3d polygon3d : model3d.getPolygons()) {
            polygons2d.add(projectPolygon(polygon3d));
        }
        return polygons2d;
    }
}
