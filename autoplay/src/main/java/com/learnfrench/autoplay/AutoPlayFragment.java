
package com.learnfrench.autoplay;


import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.learn_french.common.fulldialog.contracts.FullScreenDialogContent;
import com.learn_french.common.fulldialog.contracts.FullScreenDialogController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class AutoPlayFragment extends Fragment implements FullScreenDialogContent ,AutoPlayAdapter.PlayControl, TextToSpeech.OnUtteranceCompletedListener{

    public static final String EXTRA_NAME = "EXTRA_NAME";
    public static final String RESULT_FULL_NAME = "RESULT_FULL_NAME";


    private List<AutoPlay> movieList = new ArrayList<>();
    private RecyclerView recyclerView;
    private AutoPlayAdapter mAdapter;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton mFab;
    private boolean isPlaying = false;

    private TextToSpeech mTts;

    private FullScreenDialogController dialogController;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_autoplay, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        bottomAppBar = view.findViewById(R.id.bottom_appbar);
        mFab = view.findViewById(R.id.fab);
        bottomAppBar.replaceMenu(R.menu.menu_main);

        mTts = getTts();

        return view;
    }

    @Override
    public void onDialogCreated(final FullScreenDialogController dialogController) {
        this.dialogController = dialogController;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mAdapter = new AutoPlayAdapter(movieList,getContext(),  this,mTts);

        recyclerView.setHasFixedSize(true);

        // vertical RecyclerView
        // keep movie_list_row.xml width to `match_parent`
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());

        // horizontal RecyclerView
        // keep movie_list_row.xml width to `wrap_content`
        // RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);

        recyclerView.setLayoutManager(mLayoutManager);

        // adding inbuilt divider line
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));

        // adding custom divider line with padding 16dp
        // recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.HORIZONTAL, 16));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.setAdapter(mAdapter);

        // row click listener
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                AutoPlay movie = movieList.get(position);
                Toast.makeText(getContext(), movie.getTitle() + " is selected!", Toast.LENGTH_SHORT).show();
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

    @Override
    public boolean onConfirmClick(FullScreenDialogController dialog) {
        Bundle result = new Bundle();
        result.putString(RESULT_FULL_NAME, getArguments().getString(EXTRA_NAME));
        dialog.confirm(result);
        return true;
    }

    @Override
    public boolean onDiscardClick(FullScreenDialogController dialog) {
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.discard_confirmation_title)
                .setMessage(R.string.discard_confirmation_message)
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialogController.discard();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Nothing to do
                    }
                }).show();

        return true;
    }

    @Override
    public boolean onExtraActionClick(MenuItem actionItem, FullScreenDialogController dialogController) {
        Toast.makeText(getContext(), actionItem.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
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


    private TextToSpeech getTts() {
        if (mTts == null) {
            // Implement Text to speech feature
            mTts = new TextToSpeech(getContext(), new TextToSpeech.OnInitListener() {

                @Override
                public void onInit(int status) {
                    Log.d("LOOK AT ME!!!", "ttsInitListener - onInit");

                    if (status == TextToSpeech.SUCCESS) {
                        mTts.setLanguage(Locale.ENGLISH);
                    } else {
                        mTts = null;
                        mTts.setLanguage(Locale.ENGLISH);
                        Toast.makeText(getContext(),
                                "Failed to initialize TTS engine.",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        return mTts;
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


    @Override
    public void onDestroy() {
        if (mTts != null)
        {
            mTts.stop();
            mTts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(mTts != null){
            mTts.shutdown();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("LOOK AT ME!!!", "onResume");

        //if (mTts == null) {
            mTts = getTts();
        //}
    }

    @Override
    public void onUtteranceCompleted(String s) {
        Log.i("onUtteranceCompleted ", "onUtteranceCompleted "+s); //utteranceId == "SOME MESSAGE"
    }
}