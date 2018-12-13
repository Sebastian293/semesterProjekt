package srauhbaasch.chucks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

public class AddPlaceholderContentTask extends AsyncTask<Integer, Integer, Boolean>{
    private static final String TAG = "AddPlaceholderContentT";
    private Context context;

    AddPlaceholderContentTask(Context context){
        this.context = context;
    }


    @Override
    protected Boolean doInBackground(Integer... integers) {
        for(int i=0; i< integers[0]; i+= integers[1]){
            SystemClock.sleep(integers[2]);
            CategoryActivity.DataContainer.addContent("TestContent: " + i);
            publishProgress(i);
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Integer... progress){
        Log.v(TAG, String.format("onProgressUpdate(%d) "  + CategoryActivity.DataContainer.dataList, progress[0]));
        if(context!=null){
            Toast.makeText(context, "Progress: " + progress[0].toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onPostExecute(Boolean result){
        super.onPostExecute(result);
        Log.v(TAG, String.format("onProgressUpdate(%d)",result));

        if(context!=null){
            Toast.makeText(context, String.format("AsyncTask finished with result %b", result), Toast.LENGTH_LONG).show();
        }
    }
}
