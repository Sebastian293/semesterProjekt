package srauhbaasch.chucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Arrays;

public class JokeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);

        Intent openJokes = getIntent();
        ArrayList<String> categoryContentList = openJokes.getStringArrayListExtra("DEVELOPER_ARRAY");
        ListView jokeLV = findViewById(R.id.jokeListView);



        ArrayAdapter<String> jokeAdapter = new ArrayAdapter<>(this,R.layout.joke_item,R.id.jokeTextView, categoryContentList);
        jokeLV.setAdapter(jokeAdapter);

    }
}
