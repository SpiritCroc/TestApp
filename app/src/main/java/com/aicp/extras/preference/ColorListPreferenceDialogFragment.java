/*
 * Copyright (C) 2015 The Android Open Source Project
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

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v14.preference.ListPreferenceDialogFragment;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;

import de.spiritcroc.test.R;

public class ColorListPreferenceDialogFragment extends ListPreferenceDialogFragment {

    private static final String SAVE_STATE_INDEX = "ColorListPreferenceDialogFragment.index";
    private static final String SAVE_STATE_ENTRIES = "ColorListPreferenceDialogFragment.entries";
    private static final String SAVE_STATE_ENTRY_VALUES =
            "ColorListPreferenceDialogFragment.entryValues";
    private static final String SAVE_STATE_ENTRY_PREVIEWS =
            "ColorListPreferenceDialogFragment.entryPreviews";

    private int mClickedDialogEntryIndex;
    private CharSequence[] mEntries;
    private CharSequence[] mEntryValues;
    private CharSequence[] mEntryPreviews;

    public static ColorListPreferenceDialogFragment newInstance(String key) {
        final ColorListPreferenceDialogFragment fragment = new ColorListPreferenceDialogFragment();
        final Bundle b = new Bundle(1);
        b.putString(ARG_KEY, key);
        fragment.setArguments(b);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            final ColorListPreference preference = (ColorListPreference) getPreference();

            if (preference.getEntryPreviews() == null) {
                throw new IllegalStateException(
                        "ColorListPreference requires an entryPreviews array.");
            }

            mClickedDialogEntryIndex = preference.findIndexOfValue(preference.getValue());
            mEntries = preference.getEntries();
            mEntryValues = preference.getEntryValues();
            mEntryPreviews = preference.getEntryPreviews();
        } else {
            mClickedDialogEntryIndex = savedInstanceState.getInt(SAVE_STATE_INDEX, 0);
            mEntries = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRIES);
            mEntryValues = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRY_VALUES);
            mEntryPreviews = savedInstanceState.getCharSequenceArray(SAVE_STATE_ENTRY_PREVIEWS);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt(SAVE_STATE_INDEX, mClickedDialogEntryIndex);
        outState.putCharSequenceArray(SAVE_STATE_ENTRIES, mEntries);
        outState.putCharSequenceArray(SAVE_STATE_ENTRY_VALUES, mEntryValues);
        outState.putCharSequenceArray(SAVE_STATE_ENTRY_PREVIEWS, mEntryPreviews);
    }

    private boolean isDarkTheme() {
        TypedValue tv = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.colorBackground, tv, true);
        int bgColor = tv.data;
        getContext().getTheme().resolveAttribute(android.R.attr.colorForeground, tv, true);
        int fgColor = tv.data;
        return Color.luminance(fgColor) > Color.luminance(bgColor);
    }

    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder) {
        super.onPrepareDialogBuilder(builder);
        builder.setAdapter(new ArrayAdapter<CharSequence>(getActivity(),
                R.layout.preference_color_list_item, mEntries) {
            protected View getInflatedView(ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getActivity()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                return inflater.inflate(R.layout.preference_color_list_item, parent, false);
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ItemHolder holder;
                if (convertView == null) {
                    convertView = getInflatedView(parent);
                    holder = new ItemHolder();
                    holder.textView = convertView.findViewById(android.R.id.text1);
                    convertView.setTag(holder);
                } else {
                    holder = (ItemHolder) convertView.getTag();
                }
                Spannable text = new SpannableString(getItem(position));
                int color = Integer.decode(mEntryPreviews[position].toString());
                if (color == 0x1000000) {
                    // Magic value for black/white depending on theme
                    if (isDarkTheme()) {
                        color = 0xffffffff;
                    } else {
                        color = 0xff000000;
                    }
                } else if (color == 0x1000001) {
                    // Magic value for default accent
                    // For best readability: same as black/white
                    // (can't think of a good way to get accent from without our overlays, but
                    // including substratum overlays, which we would need here for accent color
                    // picker)
                    if (isDarkTheme()) {
                        color = 0xffffffff;
                    } else {
                        color = 0xff000000;
                    }
                } else {
                    // Add alpha channel
                    color |= 0xff000000;
                }
                text.setSpan(new ForegroundColorSpan(color), 0, text.length(),
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                holder.textView.setText(text);
                holder.textView.setCheckMarkTintList(ColorStateList.valueOf(0xff00ff00));
                holder.textView.setCheckMarkTintMode(PorterDuff.Mode.SRC_ATOP);
                return convertView;
            }
        }, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mClickedDialogEntryIndex = which;

                /*
                 * Clicking on an item simulates the positive button
                 * click, and dismisses the dialog.
                 */
                ColorListPreferenceDialogFragment.this.onClick(dialog,
                        DialogInterface.BUTTON_POSITIVE);
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onDialogClosed(boolean positiveResult) {
        final ColorListPreference preference = (ColorListPreference) getPreference();
        if (positiveResult && mClickedDialogEntryIndex >= 0) {
            String value = mEntryValues[mClickedDialogEntryIndex].toString();
            if (preference.callChangeListener(value)) {
                preference.setValue(value);
            }
        }
    }

    private static class ItemHolder {
        CheckedTextView textView;
    }
}
