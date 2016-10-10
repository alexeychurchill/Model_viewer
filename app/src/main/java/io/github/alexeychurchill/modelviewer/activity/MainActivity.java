package io.github.alexeychurchill.modelviewer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

import io.github.alexeychurchill.modelviewer.R;
import io.github.alexeychurchill.modelviewer.view.View3D;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Model3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Vertex;

public class MainActivity extends AppCompatActivity {
    private double mMoveDelta = 0.5;
    private double mRotateDelta = 5.0;
    private double mScaleDelta = 0.125;

    private Model3d mModel = new Model3d();
    private View3D mViewport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewport = ((View3D) findViewById(R.id.v3dView));
        //Build demo model
        buildModel();
        //...
        mViewport.setModel(mModel);
        mViewport.invalidate();
    }

    //Buttons handlers
    public void btnOpenModelOnClick(View view) {
    }

    public void btnLeftOnClick(View view) {
        mModel.move(-mMoveDelta, 0.0, 0.0);
        mViewport.invalidate();
    }

    public void btnRightOnClick(View view) {
        mModel.move(mMoveDelta, 0.0, 0.0);
        mViewport.invalidate();
    }

    public void btnUpOnClick(View view) {
        mModel.move(0.0, mMoveDelta, 0.0);
        mViewport.invalidate();
    }

    public void btnDownOnClick(View view) {
        mModel.move(0.0, -mMoveDelta, 0.0);
        mViewport.invalidate();
    }

    public void btnScalePlusOnClick(View view) {
        mModel.scale(1.0 + mScaleDelta);
        mViewport.invalidate();
    }

    public void btnScaleMinusOnClick(View view) {
        mModel.scale(1.0 - mScaleDelta);
        mViewport.invalidate();
    }

    public void btnXRotationPlusOnClick(View view) {
        mModel.rotateByX(mRotateDelta);
        mViewport.invalidate();
    }

    public void btnXRotationMinusOnClick(View view) {
        mModel.rotateByX(-mRotateDelta);
        mViewport.invalidate();
    }

    public void btnYRotationPlusOnClick(View view) {
        mModel.rotateByY(mRotateDelta);
        mViewport.invalidate();
    }

    public void btnYRotationMinusOnClick(View view) {
        mModel.rotateByY(-mRotateDelta);
        mViewport.invalidate();
    }

    public void btnZRotationPlusOnClick(View view) {
        mModel.rotateByZ(mRotateDelta);
        mViewport.invalidate();
    }

    public void btnZRotationMinusOnClick(View view) {
        mModel.rotateByZ(-mRotateDelta);
        mViewport.invalidate();
    }

    private void buildModel() {
        mModel.getVertices().addAll(
                Arrays.asList(new Vertex(1.0, 1.0, -1.0),
                        new Vertex(-1.0, 1.0, -1.0),
                        new Vertex(-1.0, -1.0, -1.0),
                        new Vertex(1.0, -1.0, -1.0),
                        new Vertex(1.0, 1.0, 1.0),
                        new Vertex(-1.0, 1.0, 1.0),
                        new Vertex(-1.0, -1.0, 1.0),
                        new Vertex(1.0, -1.0, 1.0))
        );
        mModel.getFaces().addAll(
                Arrays.asList(
                        Arrays.asList(0, 1, 2, 3), //Front/rear
                        Arrays.asList(4, 5, 6, 7),
                        Arrays.asList(0, 1, 5, 4), //Top/bottom
                        Arrays.asList(3, 2, 6, 7),
                        Arrays.asList(1, 2, 6, 5), //Left/right
                        Arrays.asList(0, 3, 7, 4)
                )
        );
    }
}
