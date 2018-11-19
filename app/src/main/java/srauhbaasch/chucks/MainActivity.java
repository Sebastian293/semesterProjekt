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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView categoryList = findViewById(R.id.category_list);
        String[] categories = {"developer", "movies", "food"};


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

                }
            }
        });
    }

    private static class DataContainer {
        public static String[] createData() {
            String[] data = new String[100];
            for (int i = 0; i < 100; i++) {
                data[i] = "Testdaten " + i;
            }

            return data;
        }
    }
}
