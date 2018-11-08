package com.blood.highengineeradvance.ui.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.ui.customview.imitate.MapView;
import com.blood.highengineeradvance.ui.customview.imitate.StarView;

public class ImitateActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    private StarView mStarView;
    private EditText mEditView;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imitate);

        mStarView = findViewById(R.id.starview);
        mEditView = findViewById(R.id.editview);
        mButton = findViewById(R.id.confirm);

        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String num = mEditView.getText().toString();
                if (TextUtils.isEmpty(num)) {
                    Toast.makeText(ImitateActivity.this, "请输入数字", Toast.LENGTH_SHORT).show();
                    return;
                }
                mStarView.setCurrentNum(Integer.valueOf(num));
                mEditView.clearFocus();
            }
        });

        initMapview();
    }

    private void initMapview() {
        final MapView mapView = findViewById(R.id.mapview);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(mapView, "degreeY", 0, -45);
        animator1.setDuration(1000);
        animator1.setStartDelay(500);

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(mapView, "degreeZ", 0, 270);
        animator2.setDuration(800);
        animator2.setStartDelay(500);

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(mapView, "fixDegreeY", 0, 30);
        animator3.setDuration(500);
        animator3.setStartDelay(500);

        final AnimatorSet set = new AnimatorSet();
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mapView.reset();
                                set.start();
                            }
                        });
                    }
                }, 500);
            }
        });
        set.playSequentially(animator1, animator2, animator3);
        set.start();
    }

}
