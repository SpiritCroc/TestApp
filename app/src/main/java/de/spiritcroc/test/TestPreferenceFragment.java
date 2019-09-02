package de.spiritcroc.test;

import android.os.Bundle;

import com.aicp.extras.preference.AicpPreferenceFragment;

public class TestPreferenceFragment extends AicpPreferenceFragment {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.test);
    }
}
