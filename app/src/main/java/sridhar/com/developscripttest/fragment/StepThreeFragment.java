package sridhar.com.developscripttest.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import sridhar.com.developscripttest.R;
import sridhar.com.developscripttest.utils.SharedPreference;

/**
 * Created by SriMaddy on 4/25/2017.
 */
public class StepThreeFragment extends Fragment implements Animation.AnimationListener {

    // UI
    private EditText dateTxt;
    private ImageView dateImg;

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
        View view = inflater.inflate(R.layout.fragment_step_three, container, false);

        findViewsById(view);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        SharedPreference sharedPreference = new SharedPreference();
        boolean isAnimated = sharedPreference.isStepThreeAnimated(mActivity);
        if(!isAnimated) {
            setAnimationListener();
            setAnimationToImage();
        }
    }

    private void findViewsById(View view) {
        dateTxt = (EditText) view.findViewById(R.id.date_txt);
        dateImg = (ImageView) view.findViewById(R.id.date_img);

        final Calendar myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(myCalendar);
            }

        };

        dateTxt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(mActivity, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateLabel(Calendar myCalendar) {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        dateTxt.setText(sdf.format(myCalendar.getTime()));
    }

    private void setAnimationListener() {
        animation = AnimationUtils.loadAnimation(mActivity, R.anim.move_up);
        animation.setAnimationListener(this);
    }

    private void setAnimationToImage() {
        dateImg.setAnimation(animation);
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        SharedPreference sharedPreference = new SharedPreference();
        sharedPreference.saveStepThreeAnimated(mActivity, true);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}
