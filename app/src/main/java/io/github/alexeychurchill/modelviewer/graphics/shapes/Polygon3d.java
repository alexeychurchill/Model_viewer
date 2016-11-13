package io.github.alexeychurchill.modelviewer.graphics.shapes;

import java.util.LinkedList;
import java.util.List;

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

    // TODO: 13.11.2016 Add normal vector calculation
}
