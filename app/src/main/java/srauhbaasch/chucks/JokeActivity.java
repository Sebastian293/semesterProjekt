package srauhbaasch.chucks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class JokeActivity extends AppCompatActivity{
    private static final String TAG = "JokesActivity";
    private JokesFragment jokesFragment;
    private PlaceholderTask placeHolderTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        jokesFragment =(JokesFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cancelPlaceholderTask();
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
    private void startPlaceholderTask() {
        Log.d(TAG, "Attempting to start AsyncTaskExample");

        placeHolderTask = new PlaceholderTask(getApplicationContext(), jokesFragment);

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
