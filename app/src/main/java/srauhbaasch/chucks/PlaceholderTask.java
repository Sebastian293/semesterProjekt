package srauhbaasch.chucks;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;


public class PlaceholderTask extends AsyncTask<Integer, Integer, Boolean> {

    private static final String TAG = "AsyncTaskExample";
    private JokesFragment jokesFragment;
    private ProgressBar progressBar;





    public PlaceholderTask(JokesFragment jokesFragment) {
        this.jokesFragment = jokesFragment;
        this.progressBar = jokesFragment.getProgressBar();

    }


    @Override
    protected Boolean doInBackground(Integer... listParameter) {
        CategoryActivity.DataContainer.dataList.clear();
        for (int i = 0; i <= listParameter[0]; i += listParameter[1]) {
            SystemClock.sleep(listParameter[2]);
            CategoryActivity.DataContainer.addContent("Testjoke " + i);
            publishProgress(i, listParameter[0]);
        }
        return true;
    }


    @Override
    protected void onProgressUpdate(Integer... progress) {
        Log.v(TAG, String.format("onProgressUpdate(%d)", progress[0]));
        jokesFragment.updateAdapter();
        if (progressBar != null) {
            progressBar.setProgress((progress[0]*100/progress[1]));
        }
    }


    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.v(TAG, String.format("onPostExecute(%b)", result));
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

    }

    @Override
    protected  void onPreExecute(){
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
        }
    }
}



