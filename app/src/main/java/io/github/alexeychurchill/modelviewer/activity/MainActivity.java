package io.github.alexeychurchill.modelviewer.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Arrays;

import io.github.alexeychurchill.modelviewer.R;
import io.github.alexeychurchill.modelviewer.View3D;
import io.github.alexeychurchill.modelviewer.graphics.Model3d;
import io.github.alexeychurchill.modelviewer.graphics.Vertex;

public class MainActivity extends AppCompatActivity {
    private double moveDelta = 0.25;
    private double rotateDelta = 5.0;
    private double scaleDelta = 0.25;
    private Model3d mCube = new Model3d();
    private View3D mView3D;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mView3D = ((View3D) findViewById(R.id.v3dView));
        //Build model
        buildModel();
        mView3D.setModel(mCube);
        mView3D.invalidate();
    }

    //Buttons handlers
    public void btnLeftOnClick(View view) {
        mCube.move(-moveDelta, 0.0, 0.0);
        mView3D.invalidate();
    }

    public void btnRightOnClick(View view) {
        mCube.move(moveDelta, 0.0, 0.0);
        mView3D.invalidate();
    }

    public void btnUpOnClick(View view) {
        mCube.move(0.0, moveDelta, 0.0);
        mView3D.invalidate();
    }

    public void btnDownOnClick(View view) {
        mCube.move(0.0, -moveDelta, 0.0);
        mView3D.invalidate();
    }

    public void btnScalePlusOnClick(View view) {
        mCube.scale(1.0 + scaleDelta);
        mView3D.invalidate();
    }

    public void btnScaleMinusOnClick(View view) {
        mCube.scale(1.0 - scaleDelta);
        mView3D.invalidate();
    }

    public void btnXRotationPlusOnClick(View view) {
        mCube.rotateByX(rotateDelta);
        mView3D.invalidate();
    }

    public void btnXRotationMinusOnClick(View view) {
        mCube.rotateByX(-rotateDelta);
        mView3D.invalidate();
    }

    public void btnYRotationPlusOnClick(View view) {
        mCube.rotateByY(rotateDelta);
        mView3D.invalidate();
    }

    public void btnYRotationMinusOnClick(View view) {
        mCube.rotateByY(-rotateDelta);
        mView3D.invalidate();
    }

    public void btnZRotationPlusOnClick(View view) {
        mCube.rotateByZ(rotateDelta);
        mView3D.invalidate();
    }

    public void btnZRotationMinusOnClick(View view) {
        mCube.rotateByZ(-rotateDelta);
        mView3D.invalidate();
    }

    private void buildModel() {
        mCube.getVertices().addAll(
                Arrays.asList(new Vertex(1.0, 1.0, -1.0),
                        new Vertex(-1.0, 1.0, -1.0),
                        new Vertex(-1.0, -1.0, -1.0),
                        new Vertex(1.0, -1.0, -1.0),
                        new Vertex(1.0, 1.0, 1.0),
                        new Vertex(-1.0, 1.0, 1.0),
                        new Vertex(-1.0, -1.0, 1.0),
                        new Vertex(1.0, -1.0, 1.0))
        );
        mCube.getFaces().addAll(
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
