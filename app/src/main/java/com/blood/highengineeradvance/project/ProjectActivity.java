package com.blood.highengineeradvance.project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.blood.highengineeradvance.R;
import com.blood.highengineeradvance.project.cases.Case1Activity;
import com.blood.highengineeradvance.project.cases.Case2Activity;
import com.blood.highengineeradvance.project.case3.Case3Activity;

import java.util.ArrayList;
import java.util.List;

public class ProjectActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private List<String> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project);

        ListView listView = findViewById(R.id.listview);

        initData();

        listView.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return mDatas.size();
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return 0;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                ViewHolder holder;
                if (convertView == null) {
                    convertView = LayoutInflater.from(ProjectActivity.this).inflate(android.R.layout.simple_list_item_1, parent, false);
                    holder = new ViewHolder(convertView);
                    convertView.setTag(holder);
                } else {
                    holder = (ViewHolder) convertView.getTag();
                }
                holder.mTextView.setText(mDatas.get(position));
                return convertView;
            }

        });

        listView.setOnItemClickListener(this);
    }
    private void initData() {
        mDatas = new ArrayList<>();
        mDatas.add("精彩编程200例");
        mDatas.add("1.仿微信右上角弹出菜单");
        mDatas.add("2.抽屉式公告（右边拉出）");
        mDatas.add("3.仿QQ侧滑菜单");
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 1:
                startActivity(Case1Activity.class);
                break;
            case 2:
                startActivity(Case2Activity.class);
                break;
            case 3:
                startActivity(Case3Activity.class);
                break;
        }
    }

    private static class ViewHolder {
        private TextView mTextView;
        private ViewHolder(View view) {
            mTextView = view.findViewById(android.R.id.text1);
        }
    }

    private void startActivity(Class clazz) {
        startActivity(new Intent(this, clazz));
    }
}
