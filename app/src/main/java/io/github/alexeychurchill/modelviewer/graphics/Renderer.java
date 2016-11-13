package io.github.alexeychurchill.modelviewer.graphics;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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
    //3D properties
    public static final double DEFAULT_C = -500.0;
    private double c = DEFAULT_C;
    private int lastVisibleFaces = 0;
    private int lastTotalFaces = 0;
    private boolean backfaceCullingEnabled = false;
    //Rendering
    private ScreenConverter screenConverter = new ScreenConverter();
    private Paint renderPaint = new Paint();

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

        // TODO: 13.11.2016 Normal vector calculation: here -> Polygon3D
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
        // FIXME: 13.11.2016 Implement callback, which can tell how many total faces and visible faces
        List<Polygon2d> polygons2d = new LinkedList<>();
        List<Polygon3d> modelPolygons = model3d.getPolygons();
        lastTotalFaces = modelPolygons.size();

        if (backfaceCullingEnabled) {
            int removed = performBackfaceCulling(modelPolygons);
        }
        for (Polygon3d polygon3d : modelPolygons) {
            polygons2d.add(projectPolygon(polygon3d));
        }

        lastVisibleFaces = polygons2d.size();
        return polygons2d;
    }

    public int getLastVisibleFaces() {
        return lastVisibleFaces;
    }

    public int getLastTotalFaces() {
        return lastTotalFaces;
    }

    public boolean isBackfaceCullingEnabled() {
        return backfaceCullingEnabled;
    }

    public void setBackfaceCullingEnabled(boolean backfaceCullingEnabled) {
        this.backfaceCullingEnabled = backfaceCullingEnabled;
    }

    public Bitmap renderToBitmap(Model3d model, int width, int height, int backgroundColor) {
        Bitmap renderedBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas renderCanvas = new Canvas(renderedBitmap);

        screenConverter.setScreenCenterX(width / 2);
        screenConverter.setScreenCenterY(height / 2);
        renderPaint.setColor(Color.BLUE);
        renderPaint.setStyle(Paint.Style.STROKE);

        renderCanvas.drawColor(backgroundColor);
        renderModelToCanvas(model, renderCanvas);

        return renderedBitmap;
    }

    private void renderModelToCanvas(Model3d model, Canvas renderCanvas) {
        if ((model == null) || (renderCanvas == null)) {
            return;
        }
        List<Polygon2d> polygons2d = projectModel(model);
        Path polygonPath = new Path();
        for (Polygon2d polygon : polygons2d) {
            polygonPath = getPathFromPolygon(polygonPath, polygon);
            renderCanvas.drawPath(polygonPath, renderPaint);
        }
    }

    private Path getPathFromPolygon(Path pathToReuse, Polygon2d polygon) {
        Path path = pathToReuse;
        if (path == null) {
            path = new Path();
        }

        int xScreen = screenConverter.toScreenX(polygon.getPointList().get(0).getX());
        int yScreen = screenConverter.toScreenY(polygon.getPointList().get(0).getY());

        path.reset();
        path.moveTo(xScreen, yScreen);

        for (int i = 1; i < polygon.getPointList().size(); i++) {
            xScreen = screenConverter.toScreenX(polygon.getPointList().get(i).getX());
            yScreen = screenConverter.toScreenY(polygon.getPointList().get(i).getY());
            path.lineTo(xScreen, yScreen);
        }

        path.close();

        return path;
    }

    /*
    * This performs backface culling
    * */
    private int performBackfaceCulling(List<Polygon3d> polygons) {
        int removedCount = 0;
        for (int polygonNumber = 0; polygonNumber < polygons.size(); polygonNumber++) {
            if (!polygonVisible(polygons.get(polygonNumber))) {
                polygons.remove(polygonNumber);
                removedCount++;
                polygonNumber--;
            }
        }
        return removedCount;
    }
}
