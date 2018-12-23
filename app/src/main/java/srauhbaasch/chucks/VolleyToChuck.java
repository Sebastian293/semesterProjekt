package srauhbaasch.chucks;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class VolleyToChuck {
    public static final String TAG = "request";
    private static VolleyToChuck volleyInstance;
    private RequestQueue requestQueue;

    ////////////////////////////////////////////
/*
    public static StringRequest addRequest(String url) {
        return new StringRequest(Request.Method.GET, "https://api.chucknorris.io/jokes/random?category=" + url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject result = new JSONObject(response);
                            CategoryActivity.DataContainer.dataList.add(result.getString("value"));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("oh my god", error.getMessage());
            }
        });
    }
*/
    /////////////////////////////////////////

    private VolleyToChuck(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Log.d("Request", requestQueue.toString());
    }

    public void addToRequestQueue(Request request) {
        if (requestQueue != null) {
            requestQueue.add(request);
        }
    }


    public static synchronized VolleyToChuck getInstance(Context context) {
        if (volleyInstance == null) {
            volleyInstance = new VolleyToChuck(context);
        }
        return volleyInstance;
    }

    public void cancelAllRequests() {
        if (requestQueue != null) {
            Log.d("Request", requestQueue.toString());
            requestQueue.cancelAll(VolleyToChuck.TAG);
        }
    }
}
