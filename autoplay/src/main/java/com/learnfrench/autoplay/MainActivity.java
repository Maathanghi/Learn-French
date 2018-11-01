package com.learnfrench.autoplay;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AutoPlayAdapter.PlayControl {
    private List<AutoPlay> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AutoPlayAdapter mAdapter;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton mFab;
    private boolean isPlaying = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        bottomAppBar = findViewById(R.id.bottom_appbar);
        mFab = findViewById(R.id.fab);
        bottomAppBar.replaceMenu(R.menu.menu_main);

        mAdapter = new AutoPlayAdapter(movieList,this,  this);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AutoPlay movie = movieList.get(position);
                Toast.makeText(getApplicationContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isPlaying){
                    endToCenterAnimation();
                    mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_play));
                    recyclerView.removeAllViews();
                    mAdapter.
                    //prepareMovieData();
                    isPlaying = false;
                }else{
                    centerToEndAnimation();
                    mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
                    isPlaying = true;
                }

            }
        });

        isPlaying = true;
        if(isPlaying){
            centerToEndAnimation();
            mFab.setImageDrawable(getResources().getDrawable(R.drawable.ic_stop));
        }

        prepareMovieData();

        autoScroll(0);
    }

    public void autoScroll(final int pos){
        final int speedScroll = 4000;
        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            int count = pos;
            @Override
            public void run() {
                if(count < mAdapter.getItemCount() && isPlaying){
                    recyclerView.smoothScrollToPosition(count);
                    if(null != recyclerView.findViewHolderForAdapterPosition(count))
                        recyclerView.findViewHolderForAdapterPosition(count).itemView.performClick();
                    handler.postDelayed(this,speedScroll);
                    count++;
                }
            }
        };

        handler.postDelayed(runnable,speedScroll);
    }

    /**
     * Prepares sample data to provide data set to adapter
     */
    private void prepareMovieData() {
        AutoPlay movie = new AutoPlay("Mad Max: Fury Road", "Action & Adventure", "2015");
        movieList.add(movie);

        movie = new AutoPlay("Inside Out", "Animation, Kids & Family", "2015");
        movieList.add(movie);

        movie = new AutoPlay("Star Wars: Episode VII - The Force Awakens", "Action", "2015");
        movieList.add(movie);

        movie = new AutoPlay("Shaun the Sheep", "Animation", "2015");
        movieList.add(movie);

        movie = new AutoPlay("The Martian", "Science Fiction & Fantasy", "2015");
        movieList.add(movie);

        movie = new AutoPlay("Mission: Impossible Rogue Nation", "Action", "2015");
        movieList.add(movie);

        movie = new AutoPlay("Up", "Animation", "2009");
        movieList.add(movie);

        movie = new AutoPlay("Star Trek", "Science Fiction", "2009");
        movieList.add(movie);

        movie = new AutoPlay("The LEGO AutoPlay", "Animation", "2014");
        movieList.add(movie);

        movie = new AutoPlay("Iron Man", "Action & Adventure", "2008");
        movieList.add(movie);

        movie = new AutoPlay("Aliens", "Science Fiction", "1986");
        movieList.add(movie);

        movie = new AutoPlay("Chicken Run", "Animation", "2000");
        movieList.add(movie);

        movie = new AutoPlay("Back to the Future", "Science Fiction", "1985");
        movieList.add(movie);

        movie = new AutoPlay("Raiders of the Lost Ark", "Action & Adventure", "1981");
        movieList.add(movie);

        movie = new AutoPlay("Goldfinger", "Action & Adventure", "1965");
        movieList.add(movie);

        movie = new AutoPlay("Guardians of the Galaxy", "Science Fiction & Fantasy", "2014");
        movieList.add(movie);

        // notify adapter about data set changes
        // so that it will render the list with new data
        mAdapter.notifyDataSetChanged();
    }




    //fab animation from center to end
    protected void centerToEndAnimation(){
        detachFab();
        moveToEnd();
    }

    //fab animation from end to center
    protected void endToCenterAnimation(){
        detachFab();
        moveToCenter();
    }

    protected void detachFab() {
        bottomAppBar.setFabAttached(false);
    }

    protected void moveToEnd() {
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_END);
        attachFab();
    }

    protected void moveToCenter() {
        bottomAppBar.setFabAlignmentMode(BottomAppBar.FAB_ALIGNMENT_MODE_CENTER);
        attachFab();
    }

    protected void attachFab() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                bottomAppBar.setFabAttached(true);
            }
        }, 150);
    }


    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public void playClicked(boolean isPlay, int currentPos) {
        isPlaying = isPlay;
        autoScroll(currentPos);
    }
}
