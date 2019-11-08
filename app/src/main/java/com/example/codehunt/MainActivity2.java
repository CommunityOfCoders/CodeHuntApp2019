package com.example.codehunt;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

public class MainActivity2 extends AppCompatActivity {
    private static final String TAG = "CodeHunt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SectionsPageAdapter mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // setup the viewpager with the section adapter
        ViewPager mViewPager = findViewById(R.id.container);
        setupviewPager(mViewPager);

        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupviewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        adapter.addFragment(new QuestionFragment(), "QUESTION");
        adapter.addFragment(new LeaderboardFragment(), "LEADERBOARD");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(this, "There's no looking back ;)", Toast.LENGTH_LONG).show();
    }
}