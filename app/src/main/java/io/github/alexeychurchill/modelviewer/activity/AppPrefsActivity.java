package io.github.alexeychurchill.modelviewer.activity;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import io.github.alexeychurchill.modelviewer.R;

public class AppPrefsActivity extends AppCompatActivity {
    public static final String PREF_BFC_ENABLED = "io.github.alexeychurchill.modelviever.PREF_BFC_ENABLED";
    public static final String PREF_HM_ENABLED = "io.github.alexeychurchill.modelviever.PREF_HM_ENABLED";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_prefs);
        //Bfc
        final SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(AppPrefsActivity.this);
        Switch bfcSwitch = ((Switch) findViewById(R.id.swBackfaceCulling));
        if (bfcSwitch != null) {
            bfcSwitch.setChecked(sp.getBoolean(PREF_BFC_ENABLED, false));
            bfcSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sp.edit()
                            .putBoolean(AppPrefsActivity.PREF_BFC_ENABLED, isChecked)
                            .apply();
                }
            });
        }
        //Help mode
        Switch hmSwitch = ((Switch) findViewById(R.id.swHelpMode));
        if (hmSwitch != null) {
            hmSwitch.setChecked(sp.getBoolean(PREF_HM_ENABLED, false));
            hmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    sp.edit()
                            .putBoolean(AppPrefsActivity.PREF_HM_ENABLED, isChecked)
                            .apply();
                }
            });
        }
    }
}
