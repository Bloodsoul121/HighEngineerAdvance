package com.blood.highengineeradvance.material_design.appbar_layout;

import android.annotation.SuppressLint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.blood.highengineeradvance.R;

public class AppBarLayoutActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_bar_layout);

        TextView tv = findViewById(R.id.tv_content);
        tv.setText("我们可以通过给Appbar下的子View添加app:layout_scrollFlags来设置各子View执行的动作. scrollFlags可以设置的动作如下:\n" +
                "\n" +
                "(1) scroll: 值设为scroll的View会跟随滚动事件一起发生移动。就是当指定的ScrollView发生滚动时，该View也跟随一起滚动，就好像这个View也是属于这个ScrollView一样。\n" +
                "\n" +
                "上面这个效果就是设置了scroll之后的.\n" +
                "\n" +
                "(2) enterAlways: 值设为enterAlways的View,当任何时候ScrollView往下滚动时，该View会直接往下滚动。而不用考虑ScrollView是否在滚动到最顶部还是哪里.\n" +
                "\n" +
                "我们把layout_scrollFlags改动如下:\n" +
                "\n" +
                "app:layout_scrollFlags=\"scroll|enterAlways\"\n" +
                "效果如下:\n" +
                "\n" +
                "\n" +
                "appbar_3.gif\n" +
                "(3) exitUntilCollapsed：值设为exitUntilCollapsed的View，当这个View要往上逐渐“消逝”时，会一直往上滑动，直到剩下的的高度达到它的最小高度后，再响应ScrollView的内部滑动事件。\n" +
                "\n" +
                "怎么理解呢？简单解释：在ScrollView往上滑动时，首先是View把滑动事件“夺走”，由View去执行滑动，直到滑动最小高度后，把这个滑动事件“还”回去，让ScrollView内部去上滑。\n" +
                "\n" +
                "把属性改下再看效果\n" +
                "\n" +
                "<android.support.v7.widget.Toolbar\n" +
                "    ...\n" +
                "    android:layout_height=\"?attr/actionBarSize\"\n" +
                "    android:minHeight=\"20dp\"\n" +
                "    app:layout_scrollFlags=\"scroll|exitUntilCollapsed\"\n" +
                "/>\n" +
                "appbar_4.gif\n" +
                "(4) enterAlwaysCollapsed：是enterAlways的附加选项，一般跟enterAlways一起使用，它是指，View在往下“出现”的时候，首先是enterAlways效果，当View的高度达到最小高度时，View就暂时不去往下滚动，直到ScrollView滑动到顶部不再滑动时，View再继续往下滑动，直到滑到View的顶部结束\n" +
                "\n" +
                "作者：朋朋彭哥\n" +
                "链接：https://www.jianshu.com/p/bbc703a0015e\n" +
                "來源：简书\n" +
                "简书著作权归作者所有，任何形式的转载都请联系作者获得授权并注明出处。");
    }
}
