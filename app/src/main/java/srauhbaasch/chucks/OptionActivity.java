package srauhbaasch.chucks;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class OptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean loadSyntheticData = sharedPreferences.getBoolean(getString(R.string.load_synthetic_data), false);
        int maxEntries = sharedPreferences.getInt(getString(R.string.max_entries), 100);
        String personalName = sharedPreferences.getString(getString(R.string.personal_name), null);

        final SharedPreferences.Editor sharedPreferencesEditor = sharedPreferences.edit();

        final EditText nameEditText = findViewById(R.id.choose_name_edit_text);
        nameEditText.setText(personalName);

        SeekBar seekBar = findViewById(R.id.seekBar);
        seekBar.setProgress(maxEntries);

        Button saveButton = findViewById(R.id.save_name_button);

        Switch syntheticDataSwitch = findViewById(R.id.switch_load_real_jokes);
        syntheticDataSwitch.setChecked(loadSyntheticData);

        final TextView numberOfJokesToLoad = findViewById(R.id.number_of_jokes_to_load);
        numberOfJokesToLoad.setText(String.valueOf(maxEntries));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int progress_value;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                progress_value = progress;
                numberOfJokesToLoad.setText(String.valueOf(progress_value));
                sharedPreferencesEditor.putInt(getString(R.string.max_entries), progress_value);
                sharedPreferencesEditor.apply();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                numberOfJokesToLoad.setText(String.valueOf(progress_value));
                sharedPreferencesEditor.putInt(getString(R.string.max_entries), progress_value);
                sharedPreferencesEditor.apply();
            }
        });

        syntheticDataSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferencesEditor.putBoolean(getString(R.string.load_synthetic_data), isChecked);
                sharedPreferencesEditor.apply();
            }
        });

        nameEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    sharedPreferencesEditor.putString(getString(R.string.personal_name), nameEditText.getText().toString());
                    sharedPreferencesEditor.apply();
                    Log.d("SaveName", nameEditText.getText().toString());
                }
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pattern correctEntry = Pattern.compile("((\\S+)( \\S+)*)");
                Pattern noInput = Pattern.compile("(\\s)*");

                Log.d("REGEX", String.valueOf(correctEntry.matcher(nameEditText.getText().toString()).matches()));
                Log.d("REGEX", String.valueOf(noInput.matcher(nameEditText.getText().toString()).matches()) + "\n");
                if (correctEntry.matcher(nameEditText.getText().toString()).matches()) {
                    sharedPreferencesEditor.putString(getString(R.string.personal_name), nameEditText.getText().toString());
                    sharedPreferencesEditor.apply();
                    Log.d("SaveName", nameEditText.getText().toString());

                    Toast.makeText(getApplicationContext(), "Name set to default Chuck Norris.", Toast.LENGTH_LONG).show();
                    nameEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);

                } else if(noInput.matcher(nameEditText.getText().toString()).matches()){
                    sharedPreferencesEditor.putString(getString(R.string.personal_name), null);
                    sharedPreferencesEditor.apply();

                    Log.d("SaveName", "Null");
                    Toast.makeText(getApplicationContext(), "Name set to default Chuck Norris.", Toast.LENGTH_LONG).show();

                    nameEditText.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }else{
                    Toast.makeText(getApplicationContext(), "Wrong entry. Try 'firstname lastname'.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
