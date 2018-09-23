package com.a9gag.nick.testapplication;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TrendingPageFragment extends Fragment {

    ListView list;
    View mProgressBarFooter;
    public static boolean isLoading = false;
    CustomListAdapter customList;
    ArrayList<GagsData> gagsData;
    View view = null;
    public TrendingPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_trending_page, container, false);
        list = (ListView) view.findViewById(R.id.jsonlist);

        mProgressBarFooter = ((LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE))
            .inflate(R.layout.progress_bar_footer, null, false);
        gagsData = new ArrayList<>();
        customList = new CustomListAdapter(getActivity(), gagsData);
        list.setAdapter(customList);
        fetchDataAsync();

        list.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                fetchDataAsync();
                return true;
            }
        });
        return view;
    }

    public void fetchDataAsync() {
        FetchDataAsync mTask = new FetchDataAsync(
            list,
            mProgressBarFooter,
            customList
        );
        mTask.execute();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && view != null) {
            fetchDataAsync();
        }
    }
}
