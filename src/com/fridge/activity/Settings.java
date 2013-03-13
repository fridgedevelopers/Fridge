
package com.fridge.activity;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.widget.Toast;

import com.fridge.R;
import com.fridge.util.ApplicationController;

public class Settings extends PreferenceActivity
{
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.activity_settings);
        preferences = ((ApplicationController) getApplication()).getPreferences();
        final ListPreference listPreference = (ListPreference) findPreference("recipes_count");
        listPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
        {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue)
            {
                
                return false;
            }
        });
        final CheckBoxPreference checkBoxPreference = (CheckBoxPreference) findPreference("update_data");
        checkBoxPreference
                .setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
                {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue)
                    {
                        Editor editor = preferences.edit();
                        
                        if(!preferences.getBoolean(preference.getKey(), false))
                        {
                            Toast.makeText(getBaseContext(), "Turning on download...", Toast.LENGTH_SHORT).show();
                            checkBoxPreference.setChecked(true);
                            editor.putBoolean(preference.getKey(), true);
                        }
                        else
                        {
                            Toast.makeText(getBaseContext(), "Turning off download...", Toast.LENGTH_SHORT).show();
                            checkBoxPreference.setChecked(false);
                            editor.putBoolean(preference.getKey(), false);
                        }

                        editor.commit();

                        return false;
                    }
                });
    }
}