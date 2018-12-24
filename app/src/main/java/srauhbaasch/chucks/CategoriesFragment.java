package srauhbaasch.chucks;


import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {
    private Fragment fragmentToUpdate;
    private String selectedCategory;

    public CategoriesFragment() {

    }

    public void setFragmentToUpdate(Fragment fragmentToUpdate) {
        this.fragmentToUpdate = fragmentToUpdate;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categogies, container, false);

        ListView categoryListView = view.findViewById(R.id.category_list);

        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("animal"));
        categoryList.add(new Category("career"));
        categoryList.add(new Category("celebrity"));
        categoryList.add(new Category("explicit"));
        categoryList.add(new Category("dev"));
        categoryList.add(new Category("fashion"));
        categoryList.add(new Category("food"));
        categoryList.add(new Category("history"));
        categoryList.add(new Category("money"));
        categoryList.add(new Category("movie"));
        categoryList.add(new Category("music"));
        categoryList.add(new Category("political"));
        categoryList.add(new Category("religion"));
        categoryList.add(new Category("science"));
        categoryList.add(new Category("sport"));
        categoryList.add(new Category("travel"));

        CategoryAdapter categoryAdapter = new CategoryAdapter(getActivity(), categoryList);
        categoryListView.setAdapter(categoryAdapter);

        categoryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = ((Category) parent.getItemAtPosition(position)).getCategoryName();
                if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                    Intent openJokes = new Intent(getActivity(), JokeActivity.class);
                    openJokes.putExtra("SELECTED_CATEGORY", selectedCategory);
                    startActivity(openJokes);
                } else {
                    if (fragmentToUpdate != null && fragmentToUpdate.getClass() == JokesFragment.class) {

                        ((JokesFragment) fragmentToUpdate).setSelectedCategory(selectedCategory);
                        ((JokesFragment) fragmentToUpdate).addRequests(true);
                    }
                }
            }
        });


        if (savedInstanceState != null) {
            selectedCategory = savedInstanceState.getString("SELECTED_CATEGORY");
        }

        return view;
    }

    public void continueLoadData() {
        if (selectedCategory != null && fragmentToUpdate != null) {
            ((JokesFragment) fragmentToUpdate).setSelectedCategory(selectedCategory);
            ((JokesFragment) fragmentToUpdate).addRequests(false);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        if (selectedCategory != null) {
            savedInstanceState.putString("SELECTED_CATEGORY", selectedCategory);
        }
    }
}
