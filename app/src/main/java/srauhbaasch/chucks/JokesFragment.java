package srauhbaasch.chucks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class JokesFragment extends Fragment {
    private JokesAdapter jokesAdapter;
    private ArrayList<String> jokesContentList;


    public JokesFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jokes, container, false);

        ListView jokesListView = view.findViewById(R.id.jokeListView);

        jokesAdapter = new JokesAdapter(getActivity(), new ArrayList<String>());
        jokesListView.setAdapter(jokesAdapter);

        return view;
    }

    public void updateData(){
        if(CategoryActivity.DataContainer.dataList.isEmpty()) {
            jokesContentList = CategoryActivity.DataContainer.createData();
            jokesContentList.addAll(CategoryActivity.DataContainer.createData());
            jokesAdapter.updateData(jokesContentList);
        }else{
            jokesContentList = CategoryActivity.DataContainer.dataList;
            jokesAdapter.updateData(jokesContentList);
        }


    }

}
