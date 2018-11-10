package com.french.flash_cards.adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.french.flash_cards.R;

public class SlideAdapter extends PagerAdapter {
    Context context;
    LayoutInflater inflater;

    public String[] slideImages ={"cycle_animation.json","trophy.json","books.json","favourite_app_icon.json","play_button.json"};
    public String[] slideHeadings ={"Levels","Quiz","Catagories","Bookmark","Auto Play"};
    public String[] slideDescriptions =
            {"Every level contains a set of flash cards and on back of the card you can see the french word and english translation of the same word. Complete a level to become an expert from a beginner",
            "At the end of each level challenge yourself with quiz , master that level and earn the level trophy to proceed to the next level.",
            "Each category has a set of cards specific subject and thus choose the category of your interest.",
            "Book mark your favourite card or the card that you would like to revise using the book mark option available on to of the card in each level and revisit them in the bookmark tab.",
            "Not in a position to flip the card as you are driving or your hands are occupied. Not to worry. This autoplay option lets you to listen all the words of the level one by one. Learn on the Go"};

    public SlideAdapter(Context context){
        this.context=context;
    }
    @Override
    public int getCount() {
        return slideHeadings.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == (ConstraintLayout)object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.slide_layout,container,false);

        LottieAnimationView image = (LottieAnimationView)view.findViewById(R.id.slide_image);
        TextView heading = (TextView)view.findViewById(R.id.slide_heading);
        TextView description = (TextView)view.findViewById(R.id.slide_description);

        image.setAnimation(slideImages[position]);
        image.playAnimation();
        image.loop(true);
        heading.setText(slideHeadings[position]);
        description.setText(slideDescriptions[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
