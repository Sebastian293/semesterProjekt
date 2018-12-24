package srauhbaasch.chucks;

import android.content.Intent;
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
    private PlaceholderTask placeHolderTask;
    private String selectedCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent intent = getIntent();
        selectedCategory = intent.getStringExtra("SELECTED_CATEGORY");

        jokeFragment = (JokeFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);

        boolean createNewList = true;
        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getString("SELECTED_CATEGORY");
            createNewList = false;
        }

        jokeFragment.setSelectedCategory(selectedCategory);
        jokeFragment.setTAG(JokeActivity.TAG);
        jokeFragment.addRequests(createNewList);

        //startPlaceholderTask();
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyToChuck.getInstance(getApplicationContext()).cancelAllRequests(JokeActivity.TAG);
        cancelTask();
        hideProgressBar();
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
                VolleyToChuck.getInstance(getApplicationContext()).cancelAllRequests(JokeActivity.TAG);
                cancelTask();
                startPlaceholderTask();
                return true;
            case R.id.cancel:
                VolleyToChuck.getInstance(getApplicationContext()).cancelAllRequests(JokeActivity.TAG);
                cancelTask();
                hideProgressBar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void startPlaceholderTask() {
        Log.d(TAG, "Attempting to start AsyncTaskExample");

        placeHolderTask = new PlaceholderTask(getApplicationContext(), jokeFragment);

        Integer num = 100;
        Integer increment = 1;
        Integer sleep = 200;
        placeHolderTask.execute(num, increment, sleep);
        Log.d(TAG, "AsyncTask has been started");
    }

    private void cancelTask() {
        if (placeHolderTask != null) {
            placeHolderTask.cancel(true);
        }
    }

    private void hideProgressBar() {
        if (jokeFragment != null) {
            jokeFragment.getProgressBar().setVisibility(View.GONE);
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
