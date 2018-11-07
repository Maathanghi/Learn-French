package com.learnfrench.views.shimmer.fragment;

import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.learn_french.common.fulldialog.activity.CardFlipActivity;
import com.learnfrench.views.shimmer.R;
import com.learnfrench.views.shimmer.ShimmerRecyclerView;
import com.learnfrench.views.shimmer.adapters.CardAdapter;
import com.learnfrench.views.shimmer.models.ItemCard;
import com.learnfrench.views.shimmer.utils.BaseUtils;
import com.learnfrench.views.shimmer.utils.DemoConfiguration;

public class CategoryFragment extends Fragment implements CardAdapter.OnItemClickListener {

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

        mAdapter = new CardAdapter(this);
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
    }

    private void loadCards() {
        mAdapter.setCards(BaseUtils.getCards(getResources(), BaseUtils.TYPE_GRID,getContext()));
        shimmerRecycler.hideShimmerAdapter();
    }

    @Override
    public void onItemClick(ItemCard item) {
        presentActivity(shimmerRecycler,true,item.getTitle());
    }

    public void presentActivity(View view, boolean isLesson, String selectedCategory) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(getActivity(), view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(getContext(), CardFlipActivity.class);
        intent.putExtra(CardFlipActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(CardFlipActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);
        intent.putExtra(CardFlipActivity.EXTRA_SELECTED_LEVEL, "");
        intent.putExtra(CardFlipActivity.EXTRA_Is_LESSON, isLesson);
        intent.putExtra(CardFlipActivity.EXTRA_IS_CATEGORY, true);
        intent.putExtra(CardFlipActivity.EXTRA_CATEGORY_NAME, selectedCategory);

        ActivityCompat.startActivity(getContext(), intent, options.toBundle());
    }
}
