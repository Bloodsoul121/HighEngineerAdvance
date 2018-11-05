package com.blood.highengineeradvance;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blood.highengineeradvance.toutiao.ToutiaoActivity;
import com.blood.highengineeradvance.ui.customview.CustomLayoutActivity;
import com.blood.highengineeradvance.ui.customview.CustomViewActivity;
import com.blood.highengineeradvance.ui.customview.ImitateActivity;
import com.blood.highengineeradvance.util.LogUtil;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Consumer;

public class MainActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("native-lib");
    }

    private List<String> mDatas;
    private LayoutInflater mLayoutInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLayoutInflater = LayoutInflater.from(this);

        initData();

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        RecyclerAdapter adapter = new RecyclerAdapter();
        recyclerView.setAdapter(adapter);

        LogUtil.i("imsi " + getIMSI());

        requestPermissions();
    }

    private void requestPermissions() {
        RxPermissions rxPermission = new RxPermissions(this);
        rxPermission
                .requestEach(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_CALENDAR,
                        Manifest.permission.READ_CALL_LOG,
                        Manifest.permission.READ_CONTACTS,
                        Manifest.permission.READ_PHONE_STATE,
                        Manifest.permission.READ_SMS,
                        Manifest.permission.RECORD_AUDIO,
                        Manifest.permission.CAMERA,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.SEND_SMS)
                .subscribe(new Consumer<Permission>() {
                    @Override
                    public void accept(Permission permission) throws Exception {
                        if (permission.granted) {
                            // 用户已经同意该权限
                            LogUtil.i("requestPermissions / " + permission.name + " is granted.");
                        } else if (permission.shouldShowRequestPermissionRationale) {
                            // 用户拒绝了该权限，没有选中『不再询问』（Never ask again）,那么下次再次启动时，还会提示请求权限的对话框
                            LogUtil.i("requestPermissions / " + permission.name + " is denied. More info should be provided.");
                        } else {
                            // 用户拒绝了该权限，并且选中『不再询问』
                            LogUtil.i("requestPermissions / " + permission.name + " is denied.");
                        }
                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        onClick(4);
    }

    @SuppressLint("MissingPermission")
    public String getIMSI() {
        try {
            TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
            return tm != null ? tm.getSubscriberId() : null;
        } catch (Exception e) {}
        return null;
    }

    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("");
        mDatas.add("自定义 View");
        mDatas.add("仿酷 View");
        mDatas.add("自定义布局 View");
        mDatas.add("仿 今日头条");
    }

    private void onClick(int position) {
        switch (position) {
            case 1:
                startActivity(CustomViewActivity.class);
                break;
            case 2:
                startActivity(ImitateActivity.class);
                break;
            case 3:
                startActivity(CustomLayoutActivity.class);
                break;
            case 4:
                startActivity(ToutiaoActivity.class);
                break;
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerHolder> {

        @Override
        public RecyclerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = mLayoutInflater.inflate(R.layout.item_main_recyclerview, parent, false);
            return new RecyclerHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerHolder holder, final int position) {
            holder.mTv.setText(mDatas.get(position));
            holder.mTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.this.onClick(position);
                }
            });
        }

        @Override
        public int getItemCount() {
            return mDatas.size();
        }
    }

    private class RecyclerHolder extends RecyclerView.ViewHolder {

        public final TextView mTv;

        public RecyclerHolder(View itemView) {
            super(itemView);
            mTv = itemView.findViewById(R.id.tv);
        }
    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI();
}
