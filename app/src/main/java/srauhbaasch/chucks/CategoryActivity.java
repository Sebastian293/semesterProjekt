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
    private ListView jokesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoriesFragment categoryFragment = (CategoriesFragment)getSupportFragmentManager().findFragmentById(R.id.categoryFragment);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Fragment jokesFragment = getSupportFragmentManager().findFragmentById(R.id.jokeFragment);
            categoryFragment.setFragmentToUpdate(jokesFragment);
        }


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
                startAddPlaceholderContentTask();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public static class DataContainer {
        public static ArrayList<String> dataList = new ArrayList<>();

        public static ArrayList<String> createData() {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                data.add("Testdaten " + i);
            }
            return data;
        }

        public static void addContent(String content){
            if(content != null) {
                dataList.add(content);
            }
        }
    }

    public void startAddPlaceholderContentTask(){
        Log.d(TAG, "Attempting to start AddPlaceholderContentTask");
        AddPlaceholderContentTask addPlaceholderContentTask = new AddPlaceholderContentTask(getApplicationContext());

        Integer num = 100;
        Integer increment = 2;
        Integer sleep = 200;

        addPlaceholderContentTask.execute(num, increment, sleep);
        Log.d(TAG, "AddPlaceholderContentTask has been startde");
    }
}
