package srauhbaasch.chucks;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;

public class PlaceholderTask extends AsyncTask<Integer, Integer, Boolean> {

    private static final String TAG = "AsyncTaskExample";
    private PlaceholderTaskListener placeholderTaskListener;

    public interface PlaceholderTaskListener {
        void doAction(int progress);
        void setUp();
        void cleanUp(boolean result);
    }

    public PlaceholderTask(PlaceholderTaskListener placeholderTaskListener) {
        this.placeholderTaskListener = placeholderTaskListener;
    }

    @Override
    protected Boolean doInBackground(Integer... listParameter) {
        CategoryActivity.DataContainer.dataList.clear();
        for (int i = 0; i <= listParameter[0]; i += listParameter[1]) {
            if (isCancelled()) {
                return false;
            }
            CategoryActivity.DataContainer.dataList.add ("Testjoke " + i);
            publishProgress(i, listParameter[0]);
            SystemClock.sleep(listParameter[2]);
        }
        return true;
    }

    @Override
    protected void onCancelled(){
        super.onCancelled();
        if (placeholderTaskListener != null){
            placeholderTaskListener.cleanUp(false);
        }
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        Log.v(TAG, String.format("onProgressUpdate(%d)", progress[0]));
        if (placeholderTaskListener != null) {
            if(progress[0]==0){
                placeholderTaskListener.setUp();
            }
            placeholderTaskListener.doAction(progress[0]*100/progress[1]);
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.v(TAG, String.format("onPostExecute(%b)", result));
        if (placeholderTaskListener != null) {
            placeholderTaskListener.cleanUp(result);
        }
    }
}



