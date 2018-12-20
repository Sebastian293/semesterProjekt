package srauhbaasch.chucks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class JokesFragment extends Fragment {
    private JokesAdapter jokesAdapter;
    private ProgressBar progressBar;

    public JokesFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
    public void setProgressBar(int progress){
        progressBar.setProgress(progress);
    }
    public void hideProgressBar(){
        progressBar.setVisibility(View.GONE);
    }
    public void showProgressBar(){
        progressBar.setVisibility(View.VISIBLE);
    }
}


