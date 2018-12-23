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

import com.android.volley.Request;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class JokesFragment extends Fragment {
    private JokesAdapter jokesAdapter;
    private String selectedCategory;
    private ProgressBar progressBar;

    public JokesFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);

        progressBar = view.findViewById(R.id.progressBar);
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

    public JsonObjectRequest createRequests() {
        String url = getString(R.string.default_url) + "?category=" + selectedCategory;
        Log.d("URL", url);
        if (selectedCategory != null) {
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                Log.d("Response", response.getString("value"));
                                CategoryActivity.DataContainer.dataList.add(response.getString("value"));
                                updateAdapter();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                Log.d("Response.JSON.ERROR", e.getMessage());
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.d("Response.Error", error.getMessage());
                            error.printStackTrace();
                        }
                    });

            getRequest.setTag(VolleyToChuck.TAG);
            return getRequest;
        }
        return null;
    }

    public void addRequests(){
        VolleyToChuck.getInstance(getContext()).cancelAllRequests();
        CategoryActivity.DataContainer.dataList.clear();
        updateAdapter();
        for (int i = 0; i < 100; i++) {
            VolleyToChuck.getInstance(getContext()).addToRequestQueue(createRequests());
        }
    }
}


