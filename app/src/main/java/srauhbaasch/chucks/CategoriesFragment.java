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
        categoryList.add(new Category("career"));
        categoryList.add(new Category("celebrity"));
        categoryList.add(new Category("chuck2"));
        categoryList.add(new Category("developer"));
        categoryList.add(new Category("fashion"));
        categoryList.add(new Category("food"));
        categoryList.add(new Category("history"));
        categoryList.add(new Category("money"));
        categoryList.add(new Category("movie"));
        categoryList.add(new Category("music"));
        categoryList.add(new Category("politics"));
        categoryList.add(new Category("religion"));
        categoryList.add(new Category("science"));
        categoryList.add(new Category("sport"));
        categoryList.add(new Category("travel"));


        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
        categoryListView.setAdapter(categoryAdapter);

        return view;
    }

}
