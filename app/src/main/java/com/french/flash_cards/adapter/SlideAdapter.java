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

    public String[] slideImages ={"cycle_animation.json","trophy.json","loading_book.json","like.json","play.json"};
    public String[] slideHeadings ={"Levels","Quiz","Catagories","Bookmark","Auto Play"};
    public String[] slideDescriptions =
            {"Test ipsum dolor sit amet, consectetur adipiscing elit. Sed in massa nec ex commodo condimentum ut ut enim. Vestibulum at ex aliquet, sodales lectus at, blandit urna. Aenean pretium dictum lectus eget dignissim.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in massa nec ex commodo condimentum ut ut enim. Vestibulum at ex aliquet, sodales lectus at, blandit urna. Aenean pretium dictum lectus eget dignissim.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in massa nec ex commodo condimentum ut ut enim. Vestibulum at ex aliquet, sodales lectus at, blandit urna. Aenean pretium dictum lectus eget dignissim.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in massa nec ex commodo condimentum ut ut enim. Vestibulum at ex aliquet, sodales lectus at, blandit urna. Aenean pretium dictum lectus eget dignissim.",
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed in massa nec ex commodo condimentum ut ut enim. Vestibulum at ex aliquet, sodales lectus at, blandit urna. Aenean pretium dictum lectus eget dignissim."};

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
