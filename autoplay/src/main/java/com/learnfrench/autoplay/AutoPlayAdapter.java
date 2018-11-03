package com.learnfrench.autoplay;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import java.util.List;
import java.util.Locale;

public class AutoPlayAdapter extends RecyclerView.Adapter<AutoPlayAdapter.MyViewHolder> {

    private List<AutoPlay> moviesList;
    private int focusedItem = 0;
    private Context ctx;
    PlayControl mPlayControl;
    Handler handler;
    boolean isStart;
    int progressBarValue = 0;
    boolean isPlaying = true;
    private TextToSpeech mTts;




    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView english, year, french;
        public ProgressBar progress;
        public ImageView playButton;

        public MyViewHolder(View view) {
            super(view);
            english = (TextView) view.findViewById(R.id.english);
            french = (TextView) view.findViewById(R.id.french);
            year = (TextView) view.findViewById(R.id.year);
            progress = (ProgressBar) view.findViewById(R.id.progressBar);
            playButton = (ImageView) view.findViewById(R.id.play);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            // Below line is just like a safety check, because sometimes holder could be null,
            // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(focusedItem);
            focusedItem = getAdapterPosition();
            notifyItemChanged(focusedItem);

            speak(english.getText().toString(), Locale.ENGLISH);
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    speak(french.getText().toString(), Locale.ENGLISH);
                }
            }, 2000);
        }
    }


    public AutoPlayAdapter(List<AutoPlay> moviesList, Context context, PlayControl playControl,TextToSpeech tts) {
        this.moviesList = moviesList;
        this.ctx = context;
        this.mPlayControl = playControl;
        mTts = tts;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_list_row, parent, false);
        handler = new Handler();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        // Here I am just highlighting the background
        isPlaying = true;
        holder.itemView.setBackgroundColor(focusedItem == position ? ctx.getResources().getColor(R.color.selectedRow) : Color.TRANSPARENT);
            if(focusedItem == position){
                holder.english.setTextColor(ctx.getResources().getColor(R.color.app_color_3));
                holder.french.setTextColor(ctx.getResources().getColor(R.color.app_color_3));
                holder.progress.setVisibility(View.VISIBLE);
                holder.playButton.setVisibility(View.VISIBLE);
                holder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        isStart = false;
                        if(isPlaying){
                            isPlaying = false;
                            holder.playButton.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_stop));
                            holder.progress.setVisibility(View.GONE);
                            mPlayControl.playClicked(isPlaying,position);
                        }else{
                            isPlaying = true;
                            holder.playButton.setImageDrawable(ctx.getResources().getDrawable(R.drawable.ic_play));
                            holder.progress.setVisibility(View.VISIBLE);
                            mPlayControl.playClicked(isPlaying,position);
                        }
                    }
                });
            }else{
                holder.english.setTextColor(ctx.getResources().getColor(R.color.title));
                holder.french.setTextColor(ctx.getResources().getColor(R.color.title));
                holder.progress.setVisibility(View.GONE);
                holder.playButton.setVisibility(View.GONE);
            }
        AutoPlay movie = moviesList.get(position);
        holder.english.setText(movie.getTitle());
        holder.french.setText(movie.getGenre());
        holder.year.setText(movie.getYear());

        progressBarValue = 0;
        handler = new Handler()
        {

            public void handleMessage(android.os.Message msg)
            {
                if(isStart)
                {
                    progressBarValue++;
                }
                holder.progress.setProgress(progressBarValue);
                handler.sendEmptyMessageDelayed(0, 10);
            }
        };

        handler.sendEmptyMessage(0);

    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }

    public interface PlayControl{
        boolean isPlaying();
        void playClicked(boolean isPlay, int currentPos);
    }


    private void speak(String text, Locale locale) {
        if(text != null) {
            mTts.setLanguage(locale);
            mTts.speak(text, TextToSpeech.QUEUE_FLUSH, null);
        }
    }


}


