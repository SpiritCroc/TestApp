/*
 * Copyright (C) 2019 Android Ice Cold Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.aicp.extras.preference;

import android.app.Fragment;
import android.support.v14.preference.PreferenceFragment;
import android.support.v7.preference.Preference;

public abstract class AicpPreferenceFragment extends PreferenceFragment {

    private static final String DIALOG_FRAGMENT_TAG =
            "com.aicp.gear.preference.AicpPreferenceFragment.DIALOG";

    @Override
    public void onDisplayPreferenceDialog(Preference preference) {
        // check if dialog is already showing
        Fragment existing = getFragmentManager().findFragmentByTag(DIALOG_FRAGMENT_TAG);
        android.util.Log.e("SCSCSC", "FOUND " + existing);
        if (existing != null) {
            existing.setTargetFragment(this, 0);
            return;
        }
        if (preference instanceof ColorListPreference) {
            ColorListPreferenceDialogFragment dialogFragment =
                    ColorListPreferenceDialogFragment.newInstance(preference.getKey());
            dialogFragment.setTargetFragment(this, 0);
            dialogFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else if (preference instanceof ColorMatrixListPreference) {
                ColorMatrixListPreferenceDialogFragment dialogFragment =
                        ColorMatrixListPreferenceDialogFragment.newInstance(preference.getKey());
                dialogFragment.setTargetFragment(this, 0);
                dialogFragment.show(getFragmentManager(), DIALOG_FRAGMENT_TAG);
        } else {
            super.onDisplayPreferenceDialog(preference);
        }
    }

}
