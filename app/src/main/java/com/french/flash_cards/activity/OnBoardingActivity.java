package com.french.flash_cards.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.french.flash_cards.MainActivity;
import com.french.flash_cards.R;
import com.french.flash_cards.adapter.SlideAdapter;

public class OnBoardingActivity extends AppCompatActivity {

    private SlideAdapter slideAdapter;
    private ViewPager pager;
    private Button front;
    private Button back;
    private LinearLayout dotTayout;
    private TextView[] dots;
    private int mCurrentPage;

    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.onboarding);

        pager = (ViewPager) findViewById(R.id.pager);
        back = (Button) findViewById(R.id.back);
        front = (Button) findViewById(R.id.front);
        dotTayout = (LinearLayout) findViewById(R.id.dots);
        slideAdapter = new SlideAdapter(this);
        pager.setAdapter(slideAdapter);
        addDotsinIndicator(0);
        pager.addOnPageChangeListener(listener);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pager.setCurrentItem(mCurrentPage-1);
            }
        });

        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(front.getText().equals("Finish")){
                    Intent mainIntent = new Intent(OnBoardingActivity.this,MainActivity.class);
                    OnBoardingActivity.this.startActivity(mainIntent);
                    OnBoardingActivity.this.finish();
                }
                pager.setCurrentItem(mCurrentPage+1);

            }
        });

    }

    public void addDotsinIndicator(int pos) {
        dots = new TextView[3];
        dotTayout.removeAllViews();

        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.grey));
            dotTayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[pos].setTextColor(getResources().getColor(R.color.white));
        }
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDotsinIndicator(position);
            mCurrentPage = position;
            if (position == 0) {
                front.setEnabled(true);
                back.setEnabled(false);
                back.setVisibility(View.INVISIBLE);
                front.setText("Next");
                back.setText("");
            } else if (position == dots.length - 1) {
                front.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                front.setText("Finish");
                back.setText("Back");
            } else {
                front.setEnabled(true);
                back.setEnabled(true);
                back.setVisibility(View.VISIBLE);
                front.setText("Next");
                back.setText("Back");
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }

    };
    }



