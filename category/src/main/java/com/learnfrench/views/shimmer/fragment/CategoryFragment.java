/**
 * Copyright 2017 Harish Sridharan
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.learnfrench.views.shimmer.fragment;

import android.animation.ObjectAnimator;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learnfrench.views.shimmer.R;
import com.learnfrench.views.shimmer.ShimmerRecyclerView;
import com.learnfrench.views.shimmer.adapters.CardAdapter;
import com.learnfrench.views.shimmer.utils.BaseUtils;
import com.learnfrench.views.shimmer.utils.DemoConfiguration;

public class CategoryFragment extends Fragment {

    public static final String EXTRA_TYPE = "type";

    private ShimmerRecyclerView shimmerRecycler;
    private CardAdapter mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_grid, container, false);

        RecyclerView.LayoutManager layoutManager;

        final DemoConfiguration demoConfiguration = BaseUtils.getDemoConfiguration(BaseUtils.TYPE_GRID, getContext());
        layoutManager = demoConfiguration.getLayoutManager();

        shimmerRecycler = rootView.findViewById(R.id.shimmer_recycler_view);

        if (demoConfiguration.getItemDecoration() != null) {
            shimmerRecycler.addItemDecoration(demoConfiguration.getItemDecoration());
        }

        mAdapter = new CardAdapter();
        mAdapter.setType(BaseUtils.TYPE_GRID);

        shimmerRecycler.setLayoutManager(layoutManager);
        shimmerRecycler.setAdapter(mAdapter);
        shimmerRecycler.showShimmerAdapter();

        shimmerRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCards();
            }
        }, 3000);
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerRecycler.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadCards();
            }
        }, 3000);
    }

    private void loadCards() {

        mAdapter.setCards(BaseUtils.getCards(getResources(), BaseUtils.TYPE_GRID));
        shimmerRecycler.hideShimmerAdapter();
    }

}
