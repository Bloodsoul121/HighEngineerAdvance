package com.blood.highengineeradvance.material_design.bottom_sheet_dialog;

import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blood.highengineeradvance.R;

/**
 *  使用方式跟一般的Dialog没有太大区别
 */
public class BottomSheetDialogActivity extends AppCompatActivity {

    private BottomSheetDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_sheet_dialog);

        createBottomDialog();
    }

    private void createBottomDialog() {
        mDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_bottom_dialog, null);
        mDialog.setContentView(view);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });
    }

    public void show(View view) {
        mDialog.show();
    }

    public void hide(View view) {
        mDialog.hide();
    }
}
