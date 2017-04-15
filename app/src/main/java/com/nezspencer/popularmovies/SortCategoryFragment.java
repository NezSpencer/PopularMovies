package com.nezspencer.popularmovies;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by nezspencer on 4/14/17.
 */

public class SortCategoryFragment extends DialogFragment {

    @Bind(R.id.rg_sort)RadioGroup sortGroup;
    @Bind(R.id.radio_popular)RadioButton popularRadioButton;
    @Bind(R.id.radio_top_rated)RadioButton topRatedRadioButton;
    private String currentSort;


    public static SortCategoryFragment newInstance(String currentSort){
        Bundle b =new Bundle();
        b.putString("key_sort",currentSort);

        SortCategoryFragment sortCategoryFragment = new SortCategoryFragment();
        sortCategoryFragment.setArguments(b);

        return sortCategoryFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view =inflater.inflate(R.layout.dialog_sort_category,container,false);
        getDialog().setTitle("Sort movies by");
        ButterKnife.bind(this,view);

        currentSort = "top rated";
        if (getArguments() != null)
            currentSort = getArguments().getString("key_sort","top rated");


        sortGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {

                switch (radioGroup.getCheckedRadioButtonId()){
                    case R.id.radio_popular:
                        if (!currentSort.equalsIgnoreCase("popular"))
                        {
                            //if this option is not the current currentSort value applied
                            Dashboard.initiateSort("popular");

                        }

                        getDialog().dismiss();
                        break;

                    case R.id.radio_top_rated:
                        if (!currentSort.equalsIgnoreCase("top rated"))
                            Dashboard.initiateSort("top rated");
                        getDialog().dismiss();
                        break;

                    default:

                }

            }
        });
        return view;
    }


}
