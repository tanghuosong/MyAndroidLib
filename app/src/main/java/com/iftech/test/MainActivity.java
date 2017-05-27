package com.iftech.test;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;
import com.widget.MySearchView;
import com.widget.MyTabBar;

public class MainActivity extends AppCompatActivity {

    private MySearchView my_search;
    private MyTabBar myTabBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTabBar = (MyTabBar) findViewById(R.id.tabBar);
        my_search = (MySearchView) findViewById(R.id.my_search);

        initEvents();
    }

    private void initEvents(){
        my_search.setOnSearchListener(new MySearchView.OnSearchListener() {
            @Override
            public void searchClick(String searchStr) {
                Toast.makeText(getApplicationContext(),searchStr,Toast.LENGTH_SHORT).show();
            }
        });

        myTabBar.setOnItemMenuClick(new MyTabBar.OnItemMenuClickListener() {
            @Override
            public void onThisClick(int eachItem) {
                Toast.makeText(getApplicationContext(),"点击："+eachItem,Toast.LENGTH_SHORT).show();
            }
        });
    }
}
