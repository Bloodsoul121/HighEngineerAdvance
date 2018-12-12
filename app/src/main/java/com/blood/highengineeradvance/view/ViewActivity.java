package com.blood.highengineeradvance.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.view.recyclerview.RecyclerViewActivity;

public class ViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void clickBtn1(View view) {
        startActivity(RecyclerViewActivity.class);
    }
}
