package srauhbaasch.chucks;

import android.content.Intent;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";
    private TextView mainPageJoke;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonRefresh = findViewById(R.id.refresh_joke);
        Button butonOpenCategorys = findViewById(R.id.select_category);
        mainPageJoke = findViewById(R.id.mainPageJoke);

        //final RequestQueue queue = Volley.newRequestQueue(this);
        //final String url = "https://api.chucknorris.io/jokes/random";

        /*buttonRefresh.setOnClickListener(new View.OnClickListener() {
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
        });*/

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*
                StringRequest stringRequest = new StringRequest(Request.Method.GET, "https://api.chucknorris.io/jokes/random",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject result = new JSONObject(response);
                                    mainPageJoke.setText(result.getString("value"));
                                } catch (JSONException e) {
                                    mainPageJoke.setText(R.string.json_error_message);
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mainPageJoke.setText(R.string.volley_error_message);
                    }
                });
                VolleyToChuck.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                */
                getNewJoke();
            }
        });

        butonOpenCategorys.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openJokes = new Intent(getApplicationContext(), CategoryActivity.class);
                startActivity(openJokes);
            }
        });
        /*
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject result = new JSONObject(response);
                            mainPageJoke.setText(result.getString("value"));
                        } catch (JSONException e) {
                            mainPageJoke.setText(R.string.json_error_message);
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mainPageJoke.setText(R.string.volley_error_message);
            }
        });
        queue.add(stringRequest);
        */

        if(savedInstanceState == null) {
            getNewJoke();
        }else{
            mainPageJoke.setText(savedInstanceState.getString("CURRENT_JOKE"));
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_without_cancel, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.refresh:
                getNewJoke();
                return true;

            case R.id.options:
                Intent intent = new Intent(this, OptionActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void getNewJoke() {
        if(!InternetConnectionService.hasConnection(getApplicationContext())){
            Toast.makeText(getApplicationContext(), R.string.no_internet_message, Toast.LENGTH_LONG).show();
            mainPageJoke.setText(R.string.volley_error_message);
            return;
        }

        String url = "https://api.chucknorris.io/jokes/random";
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            String newName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString(getString(R.string.personal_name), null);
                            if (newName != null) {
                                mainPageJoke.setText(response.getString("value").replace("Chuck Norris", newName));
                            } else {
                                mainPageJoke.setText(response.getString("value"));
                            }
                        } catch (JSONException e) {
                            mainPageJoke.setText(R.string.json_error_message);
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response.Error", error.toString());
                        error.printStackTrace();
                        mainPageJoke.setText(R.string.volley_error_message);
                    }
                });


        getRequest.setTag(VolleyToChuck.TAG);
        VolleyToChuck.getInstance(getApplicationContext()).addToRequestQueue(getRequest);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("CURRENT_JOKE", mainPageJoke.getText().toString());
    }
}

