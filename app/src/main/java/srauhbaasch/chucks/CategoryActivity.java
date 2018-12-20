package srauhbaasch.chucks;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity implements PlaceholderTask.PlaceholderTaskListener{
    private static String TAG = "MainActivity";
    private JokesFragment jokesFragment;
    private PlaceholderTask placeHolderTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoriesFragment categoryFragment = (CategoriesFragment)getSupportFragmentManager().findFragmentById(R.id.categoryFragment);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            jokesFragment =(JokesFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);
            categoryFragment.setFragmentToUpdate(jokesFragment);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelPlaceholderTask();
    }

    public boolean onPrepareOptionsMenu(Menu menu){
        return getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.refresh:
                cancelPlaceholderTask();
                startPlaceholderTask();
                return true;
            case R.id.cancel:
                cancelPlaceholderTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void doAction(int progress) {
        jokesFragment.setProgressBar(progress);
        jokesFragment.updateAdapter();
    }

    @Override
    public void setUp() {
        jokesFragment.setProgressBar(0);
        jokesFragment.showProgressBar();
    }

    @Override
    public void cleanUp(boolean result) {
        jokesFragment.hideProgressBar();
        if(result){
            Toast.makeText(getApplicationContext(), R.string.download_success, Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(getApplicationContext(), R.string.download_error, Toast.LENGTH_SHORT).show();
        }
    }

    public static class DataContainer {
        public static ArrayList<String> dataList = new ArrayList<>();
        public static void addContent(String content){
            if(content != null) {
                dataList.add(content);
            }
        }
    }

    private void startPlaceholderTask() {
        Log.d(TAG, "Attemting to start AsyncTaskExample");
        placeHolderTask = new PlaceholderTask(this);

        Integer num = 100;
        Integer increment = 1;
        Integer sleep = 200;
        placeHolderTask.execute(num, increment, sleep);
        Log.d(TAG, "AsyncTask has been started");
    }
    private void cancelPlaceholderTask(){
        if(placeHolderTask != null){
            placeHolderTask.cancel(true);
        }
    }
}
