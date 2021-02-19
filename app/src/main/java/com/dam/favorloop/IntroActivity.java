package com.dam.favorloop;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.dam.favorloop.adapters.IntroAdapter;

public class IntroActivity extends AppCompatActivity {

    ViewPager slideViewPager;
    IntroAdapter adapter;
    LinearLayout dotsSlider;
    TextView[] mDots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        slideViewPager = findViewById(R.id.slideViewPager);
        dotsSlider = findViewById(R.id.dotsSlider);
        adapter = new IntroAdapter(this);
        slideViewPager.setAdapter(adapter);
        slideViewPager.addOnPageChangeListener(viewListener);
        addDotsIndicator(0);
    }

    public void addDotsIndicator(int position) {
        mDots = new TextView[3];
        dotsSlider.removeAllViews();

        for (int i = 0; i < mDots.length; i++) {
            mDots[i] = new TextView(this);
            mDots[i].setText(Html.fromHtml("&#8226;"));
            mDots[i].setTextSize(35);
            mDots[i].setTextColor(getResources().getColor(R.color.dots));

            dotsSlider.addView(mDots[i]);
        }

        if (mDots.length > 0) {
            mDots[position].setTextColor(getResources().getColor(R.color.colorPrimary));
        }
    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        @Override
        public void onPageSelected(int i) {
            addDotsIndicator(i);
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    };

    public void irMain(View view) {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}