package srauhbaasch.chucks;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriesFragment extends Fragment {


    public CategoriesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_categogies, container, false);

        ListView categoryListView = view.findViewById(R.id.category_list);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("animals"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
        categoryListView.setAdapter(categoryAdapter);

        return view;
    }

}
