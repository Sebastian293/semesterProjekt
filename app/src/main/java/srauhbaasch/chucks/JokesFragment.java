package srauhbaasch.chucks;


import android.os.Bundle;
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

public class JokesFragment extends Fragment {
    private JokesAdapter jokesAdapter;
    private Object TAG;
    private String selectedCategory;
    private ProgressBar progressBar;
    private int counter;

    public JokesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);

        progressBar = view.findViewById(R.id.progressBar);
        progressBar.setMax(100);

        ListView jokesListView = view.findViewById(R.id.jokeListView);

        jokesAdapter = new JokesAdapter(getActivity(), CategoryActivity.DataContainer.dataList);
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

    public void setTAG(Object tag) {
        this.TAG = tag;
    }

    public JsonObjectRequest createRequests() {
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
                            CategoryActivity.DataContainer.dataList.add(response.getString("value"));
                            Log.d("Response", String.format("Time: %d", System.currentTimeMillis() - startTime));
                        } catch (JSONException e) {
                            Log.d("Response.JSON.ERROR", e.getMessage());
                            e.printStackTrace();
                        }
                        counter++;
                        updateAdapter();
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

        if (TAG != null) {
            getRequest.setTag(TAG);
        } else {
            getRequest.setTag(VolleyToChuck.TAG);
        }
        return getRequest;
    }

    private void setProgressBar() {
        progressBar.setProgress(counter);
        if (counter >= 100) {
            progressBar.setVisibility(View.GONE);
        }
    }

    private boolean checkIfDataContainerIsFull(){

        return false;
    }

    public void addRequests(boolean createNewList) {
        if (createNewList) {
            VolleyToChuck.getInstance(getContext()).cancelAllRequests(TAG);
            CategoryActivity.DataContainer.dataList.clear();
        }

        counter = CategoryActivity.DataContainer.dataList.size();
        progressBar.setProgress(counter);

        Log.d("Add", "Add with coubter = " + counter);
        if(counter >= 100){
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        updateAdapter();

        Log.d("Cancel", "starte request");
        for (int i = counter; i < 100; i++) {
            VolleyToChuck.getInstance(getContext()).addToRequestQueue(createRequests());
        }
    }
}


