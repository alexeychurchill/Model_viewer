package io.github.alexeychurchill.modelviewer.graphics;

import java.util.LinkedList;
import java.util.List;

/**
 * 2D Polygon
 */

public class Polygon2d {
    private List<Point> pointList = new LinkedList<>();

    public Polygon2d() {
    }

    public Polygon2d(List<Point> pointList) {
        this.pointList.addAll(pointList);
    }

    public List<Point> getPointList() {
        return pointList;
    }
}
