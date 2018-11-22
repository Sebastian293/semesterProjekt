package srauhbaasch.chucks;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private CategoryAdapter jokesAdapter;
    private ArrayList<String> jokesContentList;
    private ListView jokesListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView categoryList = findViewById(R.id.category_list);
        final String[] categories = {"developer", "movies", "food"};




        ArrayList<String> categoryNames = new ArrayList<>(Arrays.asList(categories));
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, R.layout.category_item, R.id.textView, categoryNames);

        categoryList.setAdapter(categoryAdapter);


        categoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent openJokes = new Intent(getApplicationContext(), JokeActivity.class);
                    openJokes.putExtra("DEVELOPER_ARRAY", DataContainer.createData());
                    startActivity(openJokes);
                }else{
                    if(jokesContentList == null) {
                        jokesContentList = DataContainer.createData();
                    }else{
                        jokesContentList.add("Example");
                    }
                    if(jokesListView == null){
                        jokesListView = findViewById(R.id.jokeListView);
                    }
                    if (jokesAdapter == null) {
                        jokesAdapter = new CategoryAdapter(getApplicationContext(), jokesContentList);
                        jokesListView.setAdapter(jokesAdapter);
                    }


                    jokesAdapter.updateData(jokesContentList);


                }
            }
        });
    }

    private static class DataContainer {
        public static ArrayList<String> createData() {
            ArrayList<String> data = new ArrayList<>();
            for (int i = 0; i < 100; i++) {
                data.add("Testdaten " + i);
            }

            return data;
        }
    }
}
