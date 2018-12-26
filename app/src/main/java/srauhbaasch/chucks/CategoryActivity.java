package srauhbaasch.chucks;

import android.content.Intent;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {
    public static String TAG = "CategoryActivity";
    private JokeFragment jokeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        CategoryFragment categoryFragment = (CategoryFragment) getSupportFragmentManager().findFragmentById(R.id.categoryFragment);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            jokeFragment = (JokeFragment) getSupportFragmentManager().findFragmentById(R.id.jokeFragment);
            //jokeFragment.setTAG(CategoryActivity.TAG);
            categoryFragment.setFragmentToUpdate(jokeFragment);
            categoryFragment.continueLoadData();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(jokeFragment != null) {
            jokeFragment.cancelTask(CategoryActivity.TAG);
            jokeFragment.hideProgressBar();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_options, menu);
        }else{
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_options_option, menu);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                jokeFragment.cancelTask(CategoryActivity.TAG);
                jokeFragment.addRequests(CategoryActivity.TAG, true);
                return true;

            case R.id.cancel:
                jokeFragment.cancelTask(CategoryActivity.TAG);
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

    public static class DataContainer {
        public static ArrayList<String> dataList = new ArrayList<>();
    }
}
