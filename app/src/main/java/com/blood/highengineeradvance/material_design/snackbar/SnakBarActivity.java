package com.blood.highengineeradvance.material_design.snackbar;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.blood.highengineeradvance.R;

/**
 *  其余操作可以参考 SnackbarUtil 里的方法，比如添加自定义 view
 */
public class SnakBarActivity extends AppCompatActivity {

    private CoordinatorLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snak_bar);

        mContainer = findViewById(R.id.container);
    }

    public void showSnackbar(View view) {
        Snackbar snackbar = Snackbar.make(mContainer, "Snackbar", Snackbar.LENGTH_INDEFINITE) // 三种显示方式
                .setAction("click", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(SnakBarActivity.this, "I'm a Toast", Toast.LENGTH_SHORT).show();
                    }
                })
                .setDuration(5000) // show的时长，自主控制
                .setActionTextColor(Color.RED);// 设置 action 的字体颜色;
        // 弹出
        snackbar.show();
    }

    /*

    @NonNull
    public static Snackbar make(@NonNull View view, @NonNull CharSequence text, int duration) {
        ViewGroup parent = findSuitableParent(view); // 这里挺有意思的，往下面看
        if(parent == null) {
            throw new IllegalArgumentException("No suitable parent found from the given view. Please provide a valid view.");
        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            SnackbarContentLayout content = (SnackbarContentLayout)inflater.inflate(hasSnackbarButtonStyleAttr(parent.getContext())?layout.mtrl_layout_snackbar_include:layout.design_layout_snackbar_include, parent, false);
            Snackbar snackbar = new Snackbar(parent, content, content);
            snackbar.setText(text);
            snackbar.setDuration(duration);
            return snackbar;
        }
    }

    // 父布局必须是 CoordinatorLayout 或者 FrameLayout，不断往上找父布局
    private static ViewGroup findSuitableParent(View view) {
        ViewGroup fallback = null;

        do {
            if(view instanceof CoordinatorLayout) {
                return (ViewGroup)view;
            }

            if(view instanceof FrameLayout) {
                if(view.getId() == 16908290) {
                    return (ViewGroup)view;
                }

                fallback = (ViewGroup)view;
            }

            if(view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View?(View)parent:null;
            }
        } while(view != null);

        return fallback;
    }

    */

}
