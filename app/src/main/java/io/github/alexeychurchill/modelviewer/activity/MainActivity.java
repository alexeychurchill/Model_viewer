package io.github.alexeychurchill.modelviewer.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.File;
import java.util.Arrays;

import io.github.alexeychurchill.modelviewer.R;
import io.github.alexeychurchill.modelviewer.filebrowser.OpenFileActivity;
import io.github.alexeychurchill.modelviewer.view.View3D;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Model3d;
import io.github.alexeychurchill.modelviewer.graphics.shapes.Vertex;

public class MainActivity extends AppCompatActivity {
    private static final int RQ_CALL_OPEN_FILE = 1;
    private double mMoveDelta = 0.5;
    private double mRotateDelta = 5.0;
    private double mScaleDelta = 0.125;

    private Model3d mModel = new Model3d();
    private View3D mViewport;
    private TextView tvTotalFaces;
    private TextView tvVisibleFaces;
    private ScrollView svHelp;

    private boolean bfcEnabled = false; //Back-face culling
    private boolean hmEnabled = false; //Help mode

    //Lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewport = ((View3D) findViewById(R.id.v3dView));
        tvTotalFaces = ((TextView) findViewById(R.id.tvFacesTotal));
        tvVisibleFaces = ((TextView) findViewById(R.id.tvFacesVisible));
        svHelp = ((ScrollView) findViewById(R.id.svHelp));
        //Build demo model
        buildModel();
        if (mViewport != null) {
            mViewport.setModel(mModel);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        bfcEnabled = sp.getBoolean(AppPrefsActivity.PREF_BFC_ENABLED, bfcEnabled);
        hmEnabled = sp.getBoolean(AppPrefsActivity.PREF_HM_ENABLED, hmEnabled);
        svHelp.setVisibility((hmEnabled) ? View.VISIBLE : View.GONE);
        updateView();
    }

    //Menu routine
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuItem miSettings = menu.add(0, 1, 0, R.string.menu_text_settings);
        miSettings.setIntent(new Intent(this, AppPrefsActivity.class));
        return true;
    }

    //Other activities result handler
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RQ_CALL_OPEN_FILE) {
                if (data == null) {
                    return;
                }
                String filename = data.getStringExtra(OpenFileActivity.EXTRA_FILENAME);
                openFile(filename);
                return;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Buttons handlers
    public void btnOpenModelOnClick(View view) {
        callOpenFile();
    }

    private void callOpenFile() {
        Intent openFileIntent = new Intent(this, OpenFileActivity.class);
        startActivityForResult(openFileIntent, RQ_CALL_OPEN_FILE);
    }

    private void openFile(String filename) {
        if (filename == null) {
            return;
        }
        mModel.loadFromFile(new File(filename));
        Toast.makeText(this,
                "Vertices: " + mModel.getVertices().size() + " Faces: " + mModel.getFaces().size(),
                Toast.LENGTH_SHORT)
                .show();
        updateView();
    }

    public void btnLeftOnClick(View view) {
        mModel.move(-mMoveDelta, 0.0, 0.0);
        updateView();
    }

    public void btnRightOnClick(View view) {
        mModel.move(mMoveDelta, 0.0, 0.0);
        updateView();
    }

    public void btnUpOnClick(View view) {
        mModel.move(0.0, mMoveDelta, 0.0);
        updateView();
    }

    public void btnDownOnClick(View view) {
        mModel.move(0.0, -mMoveDelta, 0.0);
        updateView();
    }

    public void btnFarOnClick(View view) {
        mModel.move(0.0, 0.0, mMoveDelta * 4);
        updateView();
    }

    public void btnNearOnClick(View view) {
        mModel.move(0.0, 0.0, -mMoveDelta * 4);
        updateView();
    }

    public void btnScalePlusOnClick(View view) {
        mModel.scale(1.0 + mScaleDelta);
        updateView();
    }

    public void btnScaleMinusOnClick(View view) {
        mModel.scale(1.0 - mScaleDelta);
        updateView();
    }

    public void btnXRotationPlusOnClick(View view) {
        mModel.rotateByX(mRotateDelta);
        updateView();
    }

    public void btnXRotationMinusOnClick(View view) {
        mModel.rotateByX(-mRotateDelta);
        updateView();
    }

    public void btnYRotationPlusOnClick(View view) {
        mModel.rotateByY(mRotateDelta);
        updateView();
    }

    public void btnYRotationMinusOnClick(View view) {
        mModel.rotateByY(-mRotateDelta);
        updateView();
    }

    public void btnZRotationPlusOnClick(View view) {
        mModel.rotateByZ(mRotateDelta);
        updateView();
    }

    public void btnZRotationMinusOnClick(View view) {
        mModel.rotateByZ(-mRotateDelta);
        updateView();
    }

    public void btnResetTransformsOnClick(View view) {
        mModel.resetTransforms();
        updateView();
    }

    public void v3dOnClick(View view) {
        //Faces indication
        tvTotalFaces.setText(String.valueOf(mViewport.getRenderer().getLastTotalFaces()));
        tvVisibleFaces.setText(String.valueOf(mViewport.getRenderer().getLastVisibleFaces()));
    }

    private void updateView() {
        if (mViewport == null) {
            return;
        }
        mViewport.getRenderer().setBackfaceCullingEnabled(bfcEnabled);
        mViewport.invalidate();
    }

    private void buildModel() {
        mModel.getVertices().addAll(
                Arrays.asList(new Vertex(1.0, 1.0, -1.0), //0
                        new Vertex(-1.0, 1.0, -1.0), //1
                        new Vertex(-1.0, -1.0, -1.0), //2
                        new Vertex(1.0, -1.0, -1.0), //3
                        new Vertex(1.0, 1.0, 1.0), //4
                        new Vertex(-1.0, 1.0, 1.0), //5
                        new Vertex(-1.0, -1.0, 1.0), //6
                        new Vertex(1.0, -1.0, 1.0)) //7
        );
        mModel.getFaces().addAll(
                Arrays.asList(
                        Arrays.asList(0, 1, 2, 3), //Front/rear //+
                        Arrays.asList(7, 6, 5, 4), //+
                        Arrays.asList(4, 5, 1, 0), //Top/bottom //+
                        Arrays.asList(2, 6, 7, 3), //+
                        Arrays.asList(1, 5, 6, 2), //Left/right //+
                        Arrays.asList(0, 3, 7, 4)  //+
                )
        );
    }
}
