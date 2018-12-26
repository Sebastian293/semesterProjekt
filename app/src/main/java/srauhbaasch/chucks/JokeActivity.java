package srauhbaasch.chucks;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

public class JokeActivity extends AppCompatActivity {
    private static final String TAG = "JokesActivity";
    private JokeFragment jokeFragment;
    private String selectedCategory;
    private boolean createNewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intent = getIntent();
        selectedCategory = intent.getStringExtra("SELECTED_CATEGORY");

        jokeFragment = (JokeFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);

        createNewList = true;
        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getString("SELECTED_CATEGORY");
            createNewList = false;
        }

        jokeFragment.setSelectedCategory(selectedCategory);
        //jokeFragment.setTAG(JokeActivity.TAG);

    }

    @Override
    protected void onStart() {
        super.onStart();
        jokeFragment.addRequests(JokeActivity.TAG, createNewList);
        createNewList = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        jokeFragment.cancelTask(JokeActivity.TAG);
        jokeFragment.hideProgressBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                jokeFragment.cancelTask(CategoryActivity.TAG);
                jokeFragment.addRequests(JokeActivity.TAG, true);
                return true;

            case R.id.cancel:
                jokeFragment.cancelTask(JokeActivity.TAG);
                jokeFragment.hideProgressBar();
                return true;

            case R.id.options:
                Intent intent = new Intent(this, OptionActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (selectedCategory != null) {
            savedInstanceState.putString("SELECTED_CATEGORY", selectedCategory);
        }
    }
}
