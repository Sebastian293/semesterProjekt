package srauhbaasch.chucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonRefresh = findViewById(R.id.refresh_joke);
        Button butonOpenCategorys = findViewById(R.id.select_category);

        butonOpenCategorys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openJokes = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(openJokes);
            }
        });
    }
}
