package com.french.flash_cards;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.learn_french.common.fulldialog.activity.CardOverviewActivity;
import com.learn_french.common.fulldialog.utils.Utils;
import com.french.flash_cards.databinding.RowSampleBinding;

import java.util.List;

public class HomeRecyclerAdapter extends RecyclerView.Adapter<HomeRecyclerAdapter.SamplesViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final Activity activity;
    private final List<Sample> samples;

    public HomeRecyclerAdapter(Activity activity, List<Sample> samples) {
        this.activity = activity;
        this.samples = samples;
    }

    @Override
    public SamplesViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        RowSampleBinding binding = RowSampleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SamplesViewHolder(binding.getRoot());
    }

    private boolean isHeader(int position){
        boolean header = false;
        final Sample sample = samples.get(position);
        if(sample.getName().contains("Header")) {
            header = true;
        }
        return header;
    }

    private String getHeader(int position){
        String header = "";
        String[] parts;
        final Sample sample = samples.get(position);
        if(sample.getName().contains("Header")) {
            parts = sample.getName().split("-");
            header = parts[1];
        }
        return header;
    }

    private int convertDPtoPX(int yourdpmeasure){
        Resources r = activity.getResources();
        int px = (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                yourdpmeasure,
                r.getDisplayMetrics()
        );
        return px;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(final SamplesViewHolder viewHolder, final int position) {
        final Sample sample = samples.get(viewHolder.getAdapterPosition());
        viewHolder.binding.setSample(sample);

        if(isHeader(position)){

            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams)viewHolder.binding.cardView.getLayoutParams();
            params = new RecyclerView.LayoutParams(
                    RecyclerView.LayoutParams.MATCH_PARENT,
                    RecyclerView.LayoutParams.WRAP_CONTENT
            );
            //params.setMargins(convertDPtoPX(10), convertDPtoPX(25), convertDPtoPX(10), convertDPtoPX(0));
            viewHolder.binding.cardView.setLayoutParams(params);
            //viewHolder.binding.cardView.setBackgroundColor(R.color.app_color_1);
            viewHolder.binding.sampleLayout.setVisibility(View.GONE);
            viewHolder.binding.header.setVisibility(View.VISIBLE);
            viewHolder.binding.header.setText(getHeader(position));
        }else{
            viewHolder.binding.sampleLayout.setVisibility(View.VISIBLE);
            viewHolder.binding.header.setVisibility(View.GONE);
        }


        viewHolder.binding.sampleLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*switch (viewHolder.getAdapterPosition()) {
                    case 0:
                        //transitionToActivity(TransitionActivity1.class, sample);
                        transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
                        break;
                    case 1:
                        //transitionToActivity(SharedElementActivity.class, viewHolder, sample);
                        transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
                        break;
                    case 2:
                        //transitionToActivity(AnimationsActivity1.class, sample);
                        transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
                        break;
                    case 3:
                        transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
                        break;
                }*/

                Sample preSample;
                if (position != 0) {
                    preSample = samples.get(position - 1);
                } else {
                    preSample = samples.get(position);
                }
                if (sample.status == 0 && preSample.getName().contains("Header")) {
                    transitionToActivity(viewHolder.binding.sampleLayout,CardOverviewActivity.class, viewHolder, sample, R.string.transition_reveal1,sample.getName());
                } else if (sample.status == 0 && (preSample.status == 0 || preSample.status == 1)) {
                    showInfo();
                    //transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);//DELETE
                } else {
                    transitionToActivity(viewHolder.binding.sampleLayout,CardOverviewActivity.class, viewHolder, sample, R.string.transition_reveal1,sample.getName());
                }
            }
        });
    }

    private void showInfo() {
        AlertDialog alertDialog = new AlertDialog.Builder(activity).create();
        alertDialog.setTitle("Alert");
        alertDialog.setMessage("Please complete previous Levels");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();
    }


    private void transitionToActivity(View view, Class target, SamplesViewHolder viewHolder, Sample sample, int transitionName, String selectedLevel) {
        //final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
        //        new Pair<>(viewHolder.binding.sampleIcon, activity.getString(transitionName)));
        //startActivity(target, pairs, sample);

        Utils.animateIntent(view, activity,activity.getString(R.string.transition_string),target,selectedLevel );
    }

    @Override
    public int getItemCount() {
        return samples.size();
    }


    public class SamplesViewHolder extends RecyclerView.ViewHolder {
        final RowSampleBinding binding;

        public SamplesViewHolder(View rootView) {
            super(rootView);
            binding = DataBindingUtil.bind(rootView);

        }
    }
}
