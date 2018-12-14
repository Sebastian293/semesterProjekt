package srauhbaasch.chucks;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    private JokesFragment jokesFragment;

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
                startPlaceholderTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
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

        PlaceholderTask placeHolderTask = new PlaceholderTask(jokesFragment);

        Integer num = 100;
        Integer increment = 1;
        Integer sleep = 200;
        placeHolderTask.execute(num, increment, sleep);
        Log.d(TAG, "AsyncTask has been started");
    }
}
