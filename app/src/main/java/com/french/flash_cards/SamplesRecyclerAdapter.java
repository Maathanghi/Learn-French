package com.french.flash_cards;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.french.flash_cards.databinding.RowSampleBinding;

import java.util.List;

public class SamplesRecyclerAdapter extends RecyclerView.Adapter<SamplesRecyclerAdapter.SamplesViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private final Activity activity;
    private final List<Sample> samples;

    public SamplesRecyclerAdapter(Activity activity, List<Sample> samples) {
        this.activity = activity;
        this.samples = samples;
    }

    @Override
    public SamplesViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        RowSampleBinding binding = RowSampleBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SamplesViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final SamplesViewHolder viewHolder, final int position) {
        final Sample sample = samples.get(viewHolder.getAdapterPosition());
        viewHolder.binding.setSample(sample);
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
                if (sample.status == 0 && (position == 0)) {
                    transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
                } else if (sample.status == 0 && (preSample.status == 0 || preSample.status == 1)) {
                    //showInfo();
                    transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);//DELETE
                } else {
                    transitionToActivity(RevealActivity.class, viewHolder, sample, R.string.transition_reveal1);
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

    private void transitionToActivity(Class target, Sample sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, true);
        startActivity(target, pairs, sample);
    }


    private void transitionToActivity(Class target, SamplesViewHolder viewHolder, Sample sample, int transitionName) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(viewHolder.binding.sampleIcon, activity.getString(transitionName)));
        startActivity(target, pairs, sample);
    }

    private void transitionToActivity(Class target, SamplesViewHolder viewHolder, Sample sample) {
        final Pair<View, String>[] pairs = TransitionHelper.createSafeTransitionParticipants(activity, false,
                new Pair<>(viewHolder.binding.sampleIcon, activity.getString(R.string.square_blue_name)),
                new Pair<>(viewHolder.binding.sampleName, activity.getString(R.string.sample_blue_title)));
        startActivity(target, pairs, sample);
    }

    private void startActivity(Class target, Pair<View, String>[] pairs, Sample sample) {
        Intent i = new Intent(activity, target);
        ActivityOptionsCompat transitionActivityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(activity, pairs);
        i.putExtra("sample", sample);
        activity.startActivity(i, transitionActivityOptions.toBundle());
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
