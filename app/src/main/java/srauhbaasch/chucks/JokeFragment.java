package srauhbaasch.chucks;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JokeFragment extends Fragment {
    private JokeAdapter jokesAdapter;
    //private Object TAG;

    private String selectedCategory;
    private ProgressBar progressBar;
    private PlaceholderTask placeHolderTask;
    private int counter = 0;
    private int maxElements;

    public JokeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_joke, container, false);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        maxElements = sharedPreferences.getInt(getString(R.string.max_entries), 100);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setMax(maxElements);

        ListView jokesListView = view.findViewById(R.id.jokeListView);

        jokesAdapter = new JokeAdapter(getActivity(), CategoryActivity.DataContainer.dataList);
        jokesListView.setAdapter(jokesAdapter);

        return view;
    }

    public void updateAdapter() {
        jokesAdapter.notifyDataSetChanged();
    }

    public ProgressBar getProgressBar() {
        return progressBar;
    }

    public void setSelectedCategory(String category) {
        this.selectedCategory = category;
    }

    private void startPlaceholderTask(String tag) {
        Log.d(tag, "Attempting to start AsyncTaskExample");
        placeHolderTask = new PlaceholderTask(getContext(), this);

        Integer num = PreferenceManager.getDefaultSharedPreferences(getContext()).getInt(getString(R.string.max_entries), 100);
        Integer start = counter;
        Integer increment = 1;
        Integer sleep = 200;
        placeHolderTask.execute(num, start, increment, sleep);
        Log.d(tag, "AsyncTask has been started");
    }

    private JsonObjectRequest createRequests(String tag) {
        String url = getString(R.string.default_url) + "?category=" + selectedCategory;


        if (selectedCategory == null) {
            selectedCategory = "";
        }
        Log.d("URL", url);
        final long startTime = System.currentTimeMillis();

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("Response", String.format("Time: %d", System.currentTimeMillis() - startTime));
                            String newName = PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.personal_name), null);
                            if(newName != null){
                                CategoryActivity.DataContainer.dataList.add(response.getString("value").replace("Chuck Norris", newName));
                            }else{
                                CategoryActivity.DataContainer.dataList.add(response.getString("value"));
                            }
                            updateAdapter();
                            Log.d("Response", String.format("Time: %d", System.currentTimeMillis() - startTime));
                        } catch (JSONException e) {
                            Log.d("Response.JSON.ERROR", e.getMessage());
                            e.printStackTrace();
                        }
                        counter++;
                        setProgressBar();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Response.Error", error.toString());
                        Log.d("Response", String.format("Time: %d", System.currentTimeMillis() - startTime));
                        error.printStackTrace();
                        counter++;
                        setProgressBar();
                        Toast.makeText(getContext(), "Request " + counter + " failed.", Toast.LENGTH_SHORT).show();
                    }
                });

        if (tag != null) {
            getRequest.setTag(tag);
        } else {
            getRequest.setTag(VolleyToChuck.TAG);
        }
        return getRequest;
    }

    private void setProgressBar() {
        progressBar.setProgress(counter);
        if (counter >= maxElements) {
            progressBar.setVisibility(View.GONE);
        }
    }

    public void addRequests(String tag, boolean createNewList) {
        boolean loadSyntheticData = PreferenceManager.getDefaultSharedPreferences(getContext()).getBoolean(getString(R.string.load_synthetic_data), false);
        cancelTask(tag);

        if(!InternetConnectionService.hasConnection(getContext())){
            Toast.makeText(getContext(), R.string.no_internet_message, Toast.LENGTH_LONG).show();
            return;
        }

        if (createNewList) {
            CategoryActivity.DataContainer.dataList.clear();
            updateAdapter();
        }

        counter = CategoryActivity.DataContainer.dataList.size();
        progressBar.setProgress(counter);

        if (counter >= maxElements) {
            return;
        }

        if (loadSyntheticData) {
            startPlaceholderTask(tag);
        } else {
            addJSONRequests(tag);
        }

        progressBar.setVisibility(View.VISIBLE);
    }

    private void addJSONRequests(String tag) {
        for (int i = counter; i < maxElements; i++) {
            VolleyToChuck.getInstance(getContext()).addToRequestQueue(createRequests(tag));
        }
    }

    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    public void cancelTask(String tag) {
        if (placeHolderTask != null) {
            placeHolderTask.cancel(true);
        }
        VolleyToChuck.getInstance(getContext()).cancelAllRequests(tag);
    }
}


