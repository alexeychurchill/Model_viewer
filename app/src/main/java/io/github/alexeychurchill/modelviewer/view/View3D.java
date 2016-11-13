package io.github.alexeychurchill.modelviewer.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;

import io.github.alexeychurchill.modelviewer.graphics.Renderer;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Model3d;

/**
 * 3D View
 */

public class View3D extends View {
    private Renderer renderer = new Renderer();
    private Model3d model;

    public View3D(Context context) {
        super(context);
    }

    public View3D(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
    }

    public Model3d getModel() {
        return model;
    }

    public void setModel(Model3d model) {
        this.model = model;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        canvas.drawColor(Color.WHITE);
        Bitmap renderedBitmap = renderer.renderToBitmap(model, canvas.getWidth(), canvas.getHeight(), Color.WHITE);
        canvas.drawBitmap(renderedBitmap, 0.0f, 0.0f, null);
    }
}
