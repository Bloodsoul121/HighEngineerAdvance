package com.blood.highengineeradvance.toutiao.channel;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blood.highengineeradvance.R;

public class ChannelDialog extends Dialog implements View.OnClickListener {

    private Context context;      // 上下文
    private int layoutResID;      // 布局文件id
    private int[] listenedItems;  // 要监听的控件id
    private OnChannelItemClickListener listener;

    public ChannelDialog(Context context, int layoutResID, int[] listenedItems) {
        super(context, R.style.dialog_custom); //dialog的样式
        this.context = context;
        this.layoutResID = layoutResID;
        this.listenedItems = listenedItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setGravity(Gravity.CENTER); // 此处可以设置dialog显示的位置为居中
        window.setWindowAnimations(R.style.bottom_menu_animation); // 添加动画效果
        setContentView(layoutResID);

        WindowManager windowManager = ((Activity) context).getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = display.getWidth();
        lp.height = display.getHeight();
        window.setAttributes(lp);
        setCanceledOnTouchOutside(true);// 点击Dialog外部消失
        //遍历控件id,添加点击事件
        for (int id : listenedItems) {
            findViewById(id).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        dismiss();//注意：我在这里加了这句话，表示只要按任何一个控件的id,弹窗都会消失，不管是确定还是取消。
        listener.OnChannelItemClick(this, view);
    }

    public interface OnChannelItemClickListener {
        void OnChannelItemClick(ChannelDialog dialog, View view);
    }

    public void setOnChannelItemClickListener(OnChannelItemClickListener listener) {
        this.listener = listener;
    }

}
