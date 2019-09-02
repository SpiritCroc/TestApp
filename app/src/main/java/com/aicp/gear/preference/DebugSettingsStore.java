/*
 * Copyright (C) 2018 Android Ice Cold Project
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

package com.aicp.gear.preference;

import android.content.Context;
import android.util.Log;

public class DebugSettingsStore extends SharedPreferencesSettingsStore {

    private static final String TAG = DebugSettingsStore.class.getSimpleName();

    public DebugSettingsStore(Context context) {
        super(context);
    }

    public boolean getBoolean(String key, boolean defValue) {
        boolean result = super.getBoolean(key, defValue);
        Log.d(TAG, "getBoolean(" + key + ", " + defValue + ") = " + result);
        return result;
    }

    public float getFloat(String key, float defValue) {
        float result = super.getFloat(key, defValue);
        Log.d(TAG, "getFloat(" + key + ", " + defValue + ") = " + result);
        return result;
    }

    public int getInt(String key, int defValue) {
        int result = super.getInt(key, defValue);
        Log.d(TAG, "getFloat(" + key + ", " + defValue + ") = " + result);
        return result;
    }

    public long getLong(String key, long defValue) {
        long result = super.getLong(key, defValue);
        Log.d(TAG, "getLong(" + key + ", " + defValue + ") = " + result);
        return result;
    }

    public String getString(String key, String defValue) {
        String result = super.getString(key, defValue);
        Log.d(TAG, "getString(" + key + ", " + defValue + ") = " + result);
        return result;
    }

    public void putBoolean(String key, boolean value) {
        Log.d(TAG, "putBoolean(" + key + ", " + value + ")");
        super.putBoolean(key, value);
    }

    public void putFloat(String key, float value) {
        Log.d(TAG, "putFloat(" + key + ", " + value + ")");
        super.putFloat(key, value);
    }

    public void putInt(String key, int value) {
        Log.d(TAG, "putInt(" + key + ", " + value + ")");
        super.putInt(key, value);
    }

    public void putLong(String key, long value) {
        Log.d(TAG, "putLong(" + key + ", " + value + ")");
        super.putLong(key, value);
    }

    public void putString(String key, String value) {
        Log.d(TAG, "putString(" + key + ", " + value + ")");
        super.putString(key, value);
    }
}
