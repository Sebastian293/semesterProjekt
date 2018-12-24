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
    private int counter;

    private VolleyToChuck(Context context) {
        requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        Log.d("Request", requestQueue.toString());
    }

    public void addToRequestQueue(Request request) {
        if (requestQueue != null) {
            requestQueue.add(request);
        }
        ;
        Log.d("Add", "request added");
    }


    public static synchronized VolleyToChuck getInstance(Context context) {
        if (volleyInstance == null) {
            volleyInstance = new VolleyToChuck(context);
        }
        return volleyInstance;
    }

    public void cancelAllRequests(final Object tag) {
        if (requestQueue != null) {
            if (tag != null)
                requestQueue.cancelAll(new RequestQueue.RequestFilter() {
                    @Override
                    public boolean apply(Request<?> request) {
                        if (tag.equals(request.getTag())) {
                            return true;
                        }
                        return false;
                    }
                });
        } else {
            requestQueue.cancelAll(VolleyToChuck.TAG);
        }
    }

}
