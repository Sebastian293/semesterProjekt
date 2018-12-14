package srauhbaasch.chucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class JokeActivity extends AppCompatActivity {

    private static final String TAG = "JokesActivity";
    private JokesFragment jokesFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent openJokes = getIntent();
        ArrayList<String> categoryContentList = openJokes.getStringArrayListExtra("DEVELOPER_ARRAY");
        CategoryActivity.DataContainer.dataList.addAll(categoryContentList);

        jokesFragment =(JokesFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);
        jokesFragment.updateAdapter();

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
                startPlaceholderTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    private void startPlaceholderTask() {
        Log.d(TAG, "Attemting to start AsyncTaskExample");

        PlaceholderTask placeHolderTask = new PlaceholderTask(jokesFragment);

        Integer num = 100;
        Integer increment = 1;
        Integer sleep = 200;
        placeHolderTask.execute(num, increment, sleep);
        Log.d(TAG, "AsyncTask has been started");
    }
}
