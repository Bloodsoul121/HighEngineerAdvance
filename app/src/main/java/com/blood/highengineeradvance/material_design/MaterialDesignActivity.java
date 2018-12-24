package com.blood.highengineeradvance.material_design;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.material_design.appbar_layout.AppBarLayoutActivity;
import com.blood.highengineeradvance.material_design.bottom_sheet_dialog.BottomSheetDialogActivity;
import com.blood.highengineeradvance.material_design.collapsing_toolbar_layout.CollapsingToolbarActivity;
import com.blood.highengineeradvance.material_design.demo.Demo1Activity;
import com.blood.highengineeradvance.material_design.floating_action_button.FloatingActionButtonActivity;
import com.blood.highengineeradvance.material_design.nested_scrollview.NestedScrollViewActivity;
import com.blood.highengineeradvance.material_design.snackbar.SnakBarActivity;
import com.blood.highengineeradvance.material_design.tab_layout.TabLayoutActivity;
import com.blood.highengineeradvance.material_design.toolbar.Toolbar2Activity;
import com.blood.highengineeradvance.material_design.toolbar.ToolbarActivity;

public class MaterialDesignActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_material_design);
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }

    public void skipToToolbar(View view) {
        startActivity(ToolbarActivity.class);
    }

    public void skipToToolbar2(View view) {
        startActivity(Toolbar2Activity.class);
    }

    public void skipToSnackbar(View view) {
        startActivity(SnakBarActivity.class);
    }

    public void skipToAppBarLayout(View view) {
        startActivity(AppBarLayoutActivity.class);
    }

    public void skipToCollapsingToolbarLayout(View view) {
        startActivity(CollapsingToolbarActivity.class);
    }

    public void skipToFloatingActionButton(View view) {
        startActivity(FloatingActionButtonActivity.class);
    }

    public void skipToNestedScrollView(View view) {
        startActivity(NestedScrollViewActivity.class);
    }

    public void skipToBottomSheetDialog(View view) {
        startActivity(BottomSheetDialogActivity.class);
    }

    public void skipToDemo1(View view) {
        startActivity(Demo1Activity.class);
    }

    public void skipToTabLayout(View view) {
        startActivity(TabLayoutActivity.class);
    }
}
