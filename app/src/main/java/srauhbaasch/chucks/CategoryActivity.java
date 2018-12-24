package srauhbaasch.chucks;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private static String TAG = "Category";
    private JokesFragment jokesFragment;
    private PlaceholderTask placeHolderTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoriesFragment categoryFragment = (CategoriesFragment) getSupportFragmentManager().findFragmentById(R.id.categoryFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            jokesFragment = (JokesFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);
            jokesFragment.setTAG(CategoryActivity.TAG);
            categoryFragment.setFragmentToUpdate(jokesFragment);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        VolleyToChuck.getInstance(getApplicationContext()).cancelAllRequests(CategoryActivity.TAG);
        cancelTask();
        hideProgressBar();
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
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
                VolleyToChuck.getInstance(getApplicationContext()).cancelAllRequests(CategoryActivity.TAG);
                cancelTask();
                startPlaceholderTask();
                return true;
            case R.id.cancel:
                VolleyToChuck.getInstance(getApplicationContext()).cancelAllRequests(CategoryActivity.TAG);
                cancelTask();
                hideProgressBar();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DataContainer {
        public static ArrayList<String> dataList = new ArrayList<>();
    }

    private void startPlaceholderTask() {
        Log.d(TAG, "Attemting to start AsyncTaskExample");
        placeHolderTask = new PlaceholderTask(getApplicationContext(), jokesFragment);

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

    private void hideProgressBar(){
        if(jokesFragment != null){
            jokesFragment.getProgressBar().setVisibility(View.GONE);
        }
    }
}
