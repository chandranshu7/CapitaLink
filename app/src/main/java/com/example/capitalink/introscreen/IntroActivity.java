package com.example.capitalink.introscreen;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.capitalink.MainActivity;
import com.example.capitalink.R;
import com.google.android.material.tabs.TabLayout;
import com.example.capitalink.loginSignup.login;

import java.util.ArrayList;
import java.util.List;


public class IntroActivity extends AppCompatActivity {

        private ViewPager screenPager;
        IntroViewPagerAdapter introViewPagerAdapter ;
        TabLayout tabIndicator;
        Button btnNext;
        int position = 0 ;
        Button btnGetStarted;
        Animation btnAnim ;
        TextView tvSkip;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);



          /*  SpannableString ss=new SpannableString(text);
            ForegroundColorSpan purple=new ForegroundColorSpan(Color.rgb(69, 39, 160));
            ss.setSpan(purple,6,7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            CapitaLink.setText(ss);*/


            // make the activity on full screen

            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);


            // when this activity is about to be launch we need to check if its openened before or not

            if (restorePrefData()) {

                Intent mainActivity = new Intent(getApplicationContext(), login.class );
                startActivity(mainActivity);
                finish();


            }

            setContentView(R.layout.activity_intro);




            // ini views
            btnNext = findViewById(R.id.btn_next);
            btnGetStarted = findViewById(R.id.btn_get_started);
            tabIndicator = findViewById(R.id.tab_indicator);
            btnAnim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.button_animation);
            tvSkip = findViewById(R.id.tv_skip);




            // fill list screen

            final List<ScreenItem> mList = new ArrayList<>();
            mList.add(new ScreenItem("Welcome to CapitaLink","Easiest way to communicate, earn\n" +
                    "and getting your work done. At home!",R.drawable.getstartedimage));
            mList.add(new ScreenItem("Find a service","We provide you with the services you’re looking for and you can directly make contact with your provider through chat",R.drawable.findaservice));
            mList.add(new ScreenItem("Provide Services","You can also become a seller or provide services"+"and even upload your previous works for customer reviews",R.drawable.serviceprovider));

            // setup viewpager
            screenPager =findViewById(R.id.screen_viewpager);
            introViewPagerAdapter = new IntroViewPagerAdapter(this,mList);
            screenPager.setAdapter(introViewPagerAdapter);

            // setup tablayout with viewpager

            tabIndicator.setupWithViewPager(screenPager);

            // next button click Listner

            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    position = screenPager.getCurrentItem();
                    if (position < mList.size()) {

                        position++;
                        screenPager.setCurrentItem(position);


                    }

                    if (position == mList.size()-1) { // when we rech to the last screen

                        // TODO : show the GETSTARTED Button and hide the indicator and the next button

                        loaddLastScreen();


                    }



                }
            });

            // tablayout add change listener


            tabIndicator.addOnTabSelectedListener(new TabLayout.BaseOnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {

                    if (tab.getPosition() == mList.size()-1) {

                        loaddLastScreen();

                    }


                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });



            // Get Started button click listener

            btnGetStarted.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    //open main activity

                    Intent mainActivity = new Intent(getApplicationContext(),login.class);
                    startActivity(mainActivity);
                    // also we need to save a boolean value to storage so next time when the user run the app
                    // we could know that he is already checked the intro screen activity
                    // i'm going to use shared preferences to that process
                    savePrefsData();
                    finish();



                }
            });

            // skip button click listener

            tvSkip.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    screenPager.setCurrentItem(mList.size());
                }
            });



        }

        private boolean restorePrefData() {


            SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
            Boolean isIntroActivityOpnendBefore = pref.getBoolean("isIntroOpnend",false);
            return  isIntroActivityOpnendBefore;



        }

        private void savePrefsData() {

            SharedPreferences pref = getApplicationContext().getSharedPreferences("myPrefs",MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putBoolean("isIntroOpnend",true);
            editor.commit();


        }

        // show the GETSTARTED Button and hide the indicator and the next button
        private void loaddLastScreen() {

            btnNext.setVisibility(View.INVISIBLE);
            btnGetStarted.setVisibility(View.VISIBLE);
            tvSkip.setVisibility(View.INVISIBLE);
            tabIndicator.setVisibility(View.INVISIBLE);
            // TODO : ADD an animation the getstarted button
            // setup animation
            btnGetStarted.setAnimation(btnAnim);



        }
    }

