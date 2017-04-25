package sridhar.com.developscripttest.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.Profile;
import com.facebook.login.LoginManager;

import java.util.ArrayList;
import java.util.List;

import sridhar.com.developscripttest.MainActivity;
import sridhar.com.developscripttest.R;
import sridhar.com.developscripttest.utils.SharedPreference;

/**
 * Created by SriMaddy on 4/25/2017.
 */

public class StepMainFragment extends Fragment {

    // UI
    private ViewPager viewPager;
    private static ImageButton btnBack;
    private static ImageButton btnNext;
    private TextView circle1;
    private TextView circle2;
    private TextView circle3;
    private TextView circle4;
    private ImageButton btnMoreMenu;

    private List<TextView> circles;

    private Activity mActivity;
    private ViewPagerAdapter adapter;
    private int mPosition;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        circles = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_main, container, false);

        findViewsById(view);
        setupViewPager();
        setViewPagerListener();
        hideBackButton();
        addCircles();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        Profile profile = Profile.getCurrentProfile();
        if(profile != null) {
            Toast.makeText(mActivity, "Logged in as " + profile.getFirstName() + " " + profile.getLastName(), Toast.LENGTH_SHORT).show();
        }
    }

    private void findViewsById(View view) {
        viewPager = (ViewPager) view.findViewById(R.id.view_pager);
        btnBack = (ImageButton) view.findViewById(R.id.back_btn);
        btnNext = (ImageButton) view.findViewById(R.id.next_btn);
        circle1 = (TextView) view.findViewById(R.id.circle1);
        circle2 = (TextView) view.findViewById(R.id.circle2);
        circle3 = (TextView) view.findViewById(R.id.circle3);
        circle4 = (TextView) view.findViewById(R.id.circle4);
        btnMoreMenu = (ImageButton) mActivity.findViewById(R.id.more_menu_btn);
        btnMoreMenu.setVisibility(View.VISIBLE);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mPosition - 1, true);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewPager.setCurrentItem(mPosition + 1, true);
            }
        });

        btnMoreMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(mActivity, btnMoreMenu);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());

                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        LoginManager.getInstance().logOut();
                        new SharedPreference().clearAll(mActivity);
                        mActivity.startActivity(new Intent(mActivity, MainActivity.class));
                        mActivity.finish();
                        return true;
                    }
                });

                popup.show();
            }
        });
    }

    public static void hideBackButton() {
        btnBack.setVisibility(View.GONE);
    }

    public static void showBackButton() {
        btnBack.setVisibility(View.VISIBLE);
    }

    public static void showNextButton() {
        btnNext.setVisibility(View.VISIBLE);
    }

    public static void hideNextButton() {
        btnNext.setVisibility(View.GONE);
    }

    private void addCircles() {
        circles.clear();
        circles.add(circle1);
        circles.add(circle2);
        circles.add(circle3);
        circles.add(circle4);
    }

    private void setupViewPager() {
        viewPager.setOffscreenPageLimit(1);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new StepOneFragment());
        fragments.add(new StepTwoFragment());
        fragments.add(new StepThreeFragment());
        fragments.add(new StepFourFragment());
        adapter = new ViewPagerAdapter(getFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
    }

    private void setViewPagerListener() {
        final int[] mCurrentFragmentPosition = new int[1];
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d("onPageScrolled", "called " + position);
                Log.d("onPageScrolled", "called " + positionOffset);

                boolean isGoingToRightPage = position == mCurrentFragmentPosition[0];
                if(isGoingToRightPage) {
                    // user is going to the right page
                    if(position < 3) {
                        try {
                            adapter.setCount(position + 2);
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                else {
                    // user is going to the left page
                }
            }

            @Override
            public void onPageSelected(int position) {
                Log.d("onPageSelected", "called " + position);
                mCurrentFragmentPosition[0] = position;
                mPosition = position;
                checkCircleBG(position);
                setVisibilityToBackNextButtons(position);
            }

            private void setVisibilityToBackNextButtons(int position) {
                switch (position) {
                    case 0:
                        hideBackButton();
                        showNextButton();
                        break;
                    case 1:
                        showBackButton();
                        showNextButton();
                        break;
                    case 2:
                        showBackButton();
                        showNextButton();
                        break;
                    case 3:
                        showBackButton();
                        hideNextButton();
                        break;
                }
            }

            private void checkCircleBG(int position) {
                for(TextView circle : circles) {
                    circle.setBackground(mActivity.getResources().getDrawable(R.drawable.unselected_circle_bg));
                }

                circles.get(position).setBackground(mActivity.getResources().getDrawable(R.drawable.selected_circle_bg));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private class ViewPagerAdapter extends FragmentPagerAdapter {

        private int count = 1;
        private List<Fragment> fragments;

        public void setCount(int count) {
            this.count = count;
        }

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return this.count;
        }
    }
}
