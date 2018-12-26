package srauhbaasch.chucks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.lang.ref.WeakReference;

public class PlaceholderTask extends AsyncTask<Integer, String, Boolean> {

    private static final String TAG = "AsyncTaskExample";
    private WeakReference<Context> context;
    private WeakReference<JokeFragment> jokesFragment;
    private WeakReference<ProgressBar> progressBar;
    private int count;


    public PlaceholderTask(Context context, JokeFragment jokeFragment) {
        this.context = new WeakReference<>(context);
        this.jokesFragment = new WeakReference<>(jokeFragment);
        this.progressBar = new WeakReference<>(jokeFragment.getProgressBar());
    }

    @Override
    public void onPreExecute() {
        if(progressBar.get() != null) {
            progressBar.get().setProgress(0);
        }
    }

    @Override
    protected Boolean doInBackground(Integer... listParameter) {
        //CategoryActivity.DataContainer.dataList.clear();
        count = listParameter[1];
        for (int i = listParameter[1]; i <= listParameter[0]; i += listParameter[2]) {
            if (isCancelled()) {
                return false;
            }
            //progress = listParameter[0] * 100 / listParameter[1];
            publishProgress("Testjoke " + i);
            SystemClock.sleep(listParameter[3]);
        }
        return true;
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        if (context.get() != null && progressBar.get() != null) {
            progressBar.get().setVisibility(View.GONE);
            Toast.makeText(context.get(), R.string.download_error, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onProgressUpdate(String... value) {
        Log.v(TAG, String.format("onPostExecute(%d)", count));
        if(count == 0 && progressBar.get() != null){
            progressBar.get().setVisibility(View.VISIBLE);
        }

        if (jokesFragment.get() != null && progressBar.get() != null) {
            CategoryActivity.DataContainer.dataList.add(value[0]);

            jokesFragment.get().updateAdapter();
            progressBar.get().setProgress(count);

            count++;
        }
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);
        Log.v(TAG, String.format("onPostExecute(%b)", result));
        if (context.get() != null) {
            if (result) {
                Toast.makeText(context.get(), R.string.download_success, Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context.get(), R.string.download_error, Toast.LENGTH_SHORT).show();
            }
        }
        if (progressBar.get() != null) {
            progressBar.get().setVisibility(View.GONE);
        }
    }
}



