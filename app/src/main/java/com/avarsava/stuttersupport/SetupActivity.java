package com.avarsava.stuttersupport;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

/**
 * @author Alexis Varsava <av11sl@brocku.ca>
 * @version 1.1
 * @since   1.1
 *
 * First time the app is launched, user is sent to this screen
 * to fill out a form with starting preferences.
 */

public class SetupActivity extends PreferenceActivity {
    /**
     * Resource ID of settings file to expand into layout.
     */
    protected int prefsFile = 0;

    /**
     * Database helper for tracking Setup completedness
     */
    private SetupDbHelper dbHelper;

    /**
     * Creates a settings screen based on the preferences file carried in the Extras. Uses
     * an automatic function of Android to generate the settings screen.
     *
     * @param savedInstanceState Bundle containing the extras, including the preferences file.
     */
    @Override
    public void onCreate(Bundle savedInstanceState){
        dbHelper = new SetupDbHelper(this, "setup.db", "SETUP");
        super.onCreate(savedInstanceState);

        prefsFile = R.xml.setup_prefs;

        // Load the preferences from an XML resource
        addPreferencesFromResource(prefsFile);
        
        showDialog(this, "Welcome!", "Please set your initial settings before beginning" +
                " to use this app.");
    }

    /**
     * Expands preferences from XML into a settings screen layout, then sets back and restore
     * buttons to listen for click activity.
     *
     * Since all the settings are just SharedPreferences, don't need to worry about actually
     * 'submitting' anything.
     *
     * @param preferencesResId Resoure ID of the preferences file to expand
     */
    @Override
    public void addPreferencesFromResource(int preferencesResId){
        super.addPreferencesFromResource(preferencesResId);

        final Activity thisActivity = this;
        Preference submitButton = findPreference("submitButton");
        submitButton.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
                dbHelper.setDone();
                finish();
                return true;
            }
        });
    }

    /**
     * Shows a dialog with a message, and 'OK' and 'Cancel' buttons. If the user presses 'OK',
     * restores the default settings for all activities in the app.
     *
     * based on https://stackoverflow.com/questions/8227820/alert-dialog-two-buttons
     * @param activity This activity
     * @param title Title for the dialog
     * @param message Message to display on dialog
     */
    public void showDialog(Activity activity, String title, CharSequence message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        if (title != null) builder.setTitle(title);

        builder.setMessage(message);
        builder.setPositiveButton(getString(R.string.OK_button), null);
        builder.show();
    }
}