package com.aicp.gear.preference;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.preference.PreferenceDataStore;
import android.view.View;
import android.widget.Switch;

import de.spiritcroc.test.R;

public class ColorBlendDialog extends DialogFragment {

    // Saved instance keys
    private static final String KEY_COLOR_START_KEY =
            ColorBlendDialog.class.getName() + ".color_start_key";
    private static final String KEY_COLOR_END_KEY =
            ColorBlendDialog.class.getName() + ".color_end_key";
    private static final String KEY_BLEND_REVERSE_KEY =
            ColorBlendDialog.class.getName() + ".blend_reverse_key";
    private static final String KEY_COLOR_START =
            ColorBlendDialog.class.getName() + ".color_start";
    private static final String KEY_COLOR_END =
            ColorBlendDialog.class.getName() + ".color_end";
    private static final String KEY_BLEND_REVERSE =
            ColorBlendDialog.class.getName() + ".blend_reverse";
    private static final String KEY_COLOR_START_DEFAULT =
            ColorBlendDialog.class.getName() + ".color_start_default";
    private static final String KEY_COLOR_END_DEFAULT =
            ColorBlendDialog.class.getName() + ".color_end_default";
    private static final String KEY_BLEND_REVERSE_DEFAULT =
            ColorBlendDialog.class.getName() + ".blend_reverse_default";

    private View mColorStartView;
    private View mColorEndView;
    private Switch mBlendReverseView;

    private String mColorStartKey;
    private String mColorEndKey;
    private String mBlendReverseKey;

    private int mColorStart;
    private int mColorEnd;
    private boolean mColorReverse;

    private int mColorStartDefault;
    private int mColorEndDefault;
    private boolean mColorReverseDefault;

    public ColorBlendDialog() {}

    public ColorBlendDialog newInstance(PreferenceDataStore preferenceStore, String colorStartKey,
                                        String colorEndKey, String blendReverseKey,
                                        int colorStartDefault, int colorEndDefault,
                                        boolean blendReverseDefault) {
        Bundle args = new Bundle();
        ColorBlendDialog colorBlendDialog = new ColorBlendDialog();
        colorBlendDialog.setArguments(args);
        return colorBlendDialog;
    }

    /*
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Activity activity = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        final View view = activity.getLayoutInflater()
                .inflate(R.layout.color_blend_preference_dialog, null);

        mColorStartView = view.findViewById(R.id.color_start);
        mColorEndView = view.findViewById(R.id.color_end);
        mBlendReverseView = view.findViewById(R.id.blend_reverse);

        if (savedInstanceState == null) {
            loadInitialValues();
        }
    }
    */

    private void loadInitialValues() {

    }

    private void restoreValues(Bundle savedInstanceState) {
    }
}
