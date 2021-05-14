package dev.moutamid.facefilters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    private ViewPagerFragmentAdapter adapter;
    private ViewPager viewPager;

    private Context context = HomeActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        viewPager = findViewById(R.id.view_pager_homeactivity);
        findViewById(R.id.middleBtn_home).setOnClickListener(middleBtnClickListener());
        findViewById(R.id.homeBtn_home).setOnClickListener(homeBtnClickListener());
        findViewById(R.id.personalBtn_home).setOnClickListener(personalBtnClickListener());
        adapter = new ViewPagerFragmentAdapter(getSupportFragmentManager());

        // Setting up the view Pager
        setupViewPager(viewPager);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null) {
            finish();
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
//            return;
        }


    }

    private View.OnClickListener personalBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView homeImage = findViewById(R.id.homeBtn_home);
                ImageView personalImage = findViewById(R.id.personalBtn_home);
                ImageView homeImageUnselected = findViewById(R.id.homeBtn_home_unselected);
                ImageView personalImageUnselected = findViewById(R.id.personalBtn_home_unclicked);

//                ImageView middleImage = findViewById(R.id.middleBtn_home);

                personalImage.setVisibility(View.VISIBLE);
                personalImageUnselected.setVisibility(View.GONE);
                homeImage.setVisibility(View.GONE);
                homeImageUnselected.setVisibility(View.VISIBLE);


//                middleImage.setBackgroundResource(R.drawable.ic_baseline_add_24);

                viewPager.setCurrentItem(1);

            }
        };
    }

    private View.OnClickListener homeBtnClickListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView homeImage = findViewById(R.id.homeBtn_home);
                ImageView personalImage = findViewById(R.id.personalBtn_home);
                ImageView homeImageUnselected = findViewById(R.id.homeBtn_home_unselected);
                ImageView personalImageUnselected = findViewById(R.id.personalBtn_home_unclicked);

//                ImageView middleImage = findViewById(R.id.middleBtn_home);

                personalImage.setVisibility(View.GONE);
                personalImageUnselected.setVisibility(View.VISIBLE);
                homeImage.setVisibility(View.VISIBLE);
                homeImageUnselected.setVisibility(View.GONE);

//                middleImage.setBackgroundResource(R.drawable.ic_baseline_camera_alt_24);

                viewPager.setCurrentItem(0);

            }
        };
    }

    private View.OnClickListener middleBtnClickListener() {

        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (viewPager.getCurrentItem() == 0)
                    Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(context, FaceFilterActivity.class));

                else Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
//                else Add filters from gallery


            }
        };
    }

    private void setupViewPager(ViewPager viewPager) {

        // Adding Fragments to Adapter
        adapter.addFragment(new HomeFragment());
        adapter.addFragment(new PersonalFragment());
        viewPager.setOffscreenPageLimit(2);
        viewPager.setAdapter(adapter);

        Log.d(TAG, "setupViewPager: adapter attached");

//        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float v, int i1) {
//
//                switch (position) {
//                    case 0:
//
//                        break;
//                    case 1:
//
//                        break;
//                }
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int i) {
//
//            }
//        });

    }

    @Override
    public void onBackPressed() {

        if (viewPager.getCurrentItem() == 0) super.onBackPressed();

        else viewPager.setCurrentItem(0);

    }

    public static class ViewPagerFragmentAdapter extends FragmentStatePagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();

        public void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        public ViewPagerFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
    }

}