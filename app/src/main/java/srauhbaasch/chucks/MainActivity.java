package srauhbaasch.chucks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    TextView mainPageJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonRefresh = findViewById(R.id.refresh_joke);
        Button butonOpenCategorys = findViewById(R.id.select_category);
        mainPageJoke = findViewById(R.id.mainPageJoke);

        final RequestQueue queue = Volley.newRequestQueue(this);
        final String url ="https://api.chucknorris.io/jokes/random";

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject result = new JSONObject(response);
                                    mainPageJoke.setText(result.getString("value"));
                                } catch (JSONException e) {
                                    mainPageJoke.setText(R.string.JsonErrorMessage);
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mainPageJoke.setText(R.string.volleyErrorMessage);
                    }
                });
                queue.add(stringRequest);
            }
        });

        butonOpenCategorys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openJokes = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(openJokes);
            }
        });

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject result = new JSONObject(response);
                            mainPageJoke.setText(result.getString("value"));
                        } catch (JSONException e) {
                            mainPageJoke.setText(R.string.JsonErrorMessage);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainPageJoke.setText(R.string.volleyErrorMessage);
            }
        });
        queue.add(stringRequest);
    }
}
