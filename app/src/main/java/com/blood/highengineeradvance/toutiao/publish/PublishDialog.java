package com.blood.highengineeradvance.toutiao.publish;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blood.highengineeradvance.R;

public class PublishDialog extends Dialog implements DialogInterface.OnShowListener, View.OnClickListener, DialogInterface.OnDismissListener {

    private Context context;      // 上下文
    private int layoutResID;      // 布局文件id
    private LinearLayout mOptionsContainer;
    private ImageView mCancelView;

    public PublishDialog(Context context) {
        super(context, R.style.dialog_custom_options);
        this.context = context;
        this.layoutResID = R.layout.layout_publish_view;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        assert window != null;
        window.setGravity(Gravity.BOTTOM); // 底部显示
//        window.setWindowAnimations(R.style.bottom_options_animation); // 添加动画效果
        window.setWindowAnimations(-1);
        setContentView(layoutResID);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        DisplayMetrics dm = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = dm.widthPixels;
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失

        init();
    }

    private void init() {
        mOptionsContainer = findViewById(R.id.options);
        mCancelView = findViewById(R.id.cancel);
        mCancelView.setOnClickListener(this);
        setOnShowListener(this);
        setOnDismissListener(this);
    }

    @Override
    public void onShow(DialogInterface dialogInterface) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(mOptionsContainer, "scaleX", 0.8f, 1.1f, 1f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(mOptionsContainer, "scaleY", 0.5f, 1.1f, 1f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(mOptionsContainer, "alpha", 0.5f, 1f);
        ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(mCancelView, "rotation", -50, 0);
        animatorSet.playTogether(animatorScaleX, animatorScaleY, animatorAlpha, animatorRotate);
        animatorSet.setDuration(300);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }

    @Override
    public void onDismiss(DialogInterface dialogInterface) {
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animatorScaleX = ObjectAnimator.ofFloat(mOptionsContainer, "scaleX", 1f, 0.8f);
        ObjectAnimator animatorScaleY = ObjectAnimator.ofFloat(mOptionsContainer, "scaleY", 1f, 0.5f);
        ObjectAnimator animatorAlpha = ObjectAnimator.ofFloat(mOptionsContainer, "alpha", 1f, 0.5f);
        ObjectAnimator animatorRotate = ObjectAnimator.ofFloat(mCancelView, "rotation", 0, -50);
        animatorSet.playTogether(animatorScaleX, animatorScaleY, animatorAlpha, animatorRotate);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancel:
                dismiss();
                break;
        }
    }
}
