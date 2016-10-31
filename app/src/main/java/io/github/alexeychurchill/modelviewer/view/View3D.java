package io.github.alexeychurchill.modelviewer.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import java.util.List;

import io.github.alexeychurchill.modelviewer.graphics.Renderer;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Model3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Polygon2d;
import io.github.alexeychurchill.modelviewer.graphics.ScreenConverter;

/**
 * 3D View
 */

public class View3D extends View {
    private Paint mPaint = new Paint();
    private ScreenConverter mConverter = new ScreenConverter();
    private Renderer projector = new Renderer();
    private Model3d model;

    public View3D(Context context) {
        super(context);
        init();
    }

    public View3D(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint.setStrokeWidth(1.0f);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.BLACK);
    }

    public Renderer getRenderer() {
        return projector;
    }

    public void setProjector(Renderer projector) {
        this.projector = projector;
    }

    public Model3d getModel() {
        return model;
    }

    public void setModel(Model3d model) {
        this.model = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mConverter.setScreenCenterX(canvas.getWidth() / 2);
        mConverter.setScreenCenterY(canvas.getHeight() / 2);
        canvas.drawColor(Color.WHITE);
        renderModel(canvas, model);
    }

    private void renderModel(Canvas destinationCanvas, Model3d model) {
        if (destinationCanvas == null) {
            return;
        }
        if (model == null) {
            return;
        }
        if (projector == null) {
            return;
        }
        List<Polygon2d> polygon2dList = projector.projectModel(model);
        for (Polygon2d polygon2d : polygon2dList) {
            drawPolygon(destinationCanvas, polygon2d);
        }
    }

    private void drawPolygon(Canvas destinationCanvas, Polygon2d polygon2d) {
        if (destinationCanvas == null) {
            return;
        }
        if (polygon2d == null) {
            return;
        }

        if (polygon2d.getPointList().size() == 0) {
            return;
        }

        int xScreen = mConverter.toScreenX(polygon2d.getPointList().get(0).getX());
        int yScreen = mConverter.toScreenY(polygon2d.getPointList().get(0).getY());;

        Path polygonPath = new Path();
        polygonPath.moveTo(xScreen, yScreen);

        for (int i = 1; i < polygon2d.getPointList().size(); i++) {
            xScreen = mConverter.toScreenX(polygon2d.getPointList().get(i).getX());
            yScreen = mConverter.toScreenY(polygon2d.getPointList().get(i).getY());
            polygonPath.lineTo(xScreen, yScreen);
        }

        polygonPath.close();

        mPaint.setColor(Color.BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        destinationCanvas.drawPath(polygonPath, mPaint);
    }
}
