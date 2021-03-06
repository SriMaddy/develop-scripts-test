package sridhar.com.developscripttest.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;

import sridhar.com.developscripttest.R;
import sridhar.com.developscripttest.utils.SharedPreference;

/**
 * Created by SriMaddy on 4/25/2017.
 */
public class StepTwoFragment extends Fragment implements Animation.AnimationListener {

    // UI
    private EditText groomTxt;
    private ImageView groomImg;

    private Activity mActivity;
    private Animation animation;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_step_two, container, false);

        findViewsById(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreference sharedPreference = new SharedPreference();
        boolean isAnimated = sharedPreference.isStepTwoAnimated(mActivity);
        if(!isAnimated) {
            setAnimationListener();
            setAnimationToImage();
        }
    }

    private void findViewsById(View view) {
        groomTxt = (EditText) view.findViewById(R.id.groom_txt);
        groomImg = (ImageView) view.findViewById(R.id.groom_img);
    }

    private void setAnimationListener() {
        animation = AnimationUtils.loadAnimation(mActivity, R.anim.move_up);
        animation.setAnimationListener(this);
    }

    private void setAnimationToImage() {
        groomImg.setAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.saveStepTwoAnimated(mActivity, true);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
