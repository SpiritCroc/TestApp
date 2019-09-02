package de.spiritcroc.test;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by tobias on 10/4/17.
 */

public class TestActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstantceState) {
        super.onCreate(savedInstantceState);
        Fragment f = getFragmentManager().findFragmentByTag("BLABLA");
        if (f == null) {
            f = new TestPreferenceFragment();
        }
        getFragmentManager().beginTransaction().replace(android.R.id.content, f, "BLABLA").commit();
    }
}
