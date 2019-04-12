package com.example.fatim.makeawish;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
//        setSupportActionBar(toolbar);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layoutSearch);
        tabLayout.addTab(tabLayout.newTab().setText("Users"));
        tabLayout.addTab(tabLayout.newTab().setText("Items"));
        tabLayout.addTab(tabLayout.newTab().setText("Charities"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        final ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        TabsAdapterSearch tabsAdapter = new TabsAdapterSearch(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(tabsAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        //Navigation bar
        BottomNavigationView bottom = findViewById(R.id.navigationView);

        bottom.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.navigation_history:
                        startActivity(new Intent(Search.this,History.class));break;
                    case R.id.navigation_todo:
                        startActivity(new Intent(Search.this,ToDo.class));break;
                    case R.id.navigation_profile:
                        startActivity(new Intent(Search.this,Profile.class));break;

                    case R.id.navigation_search:
                        startActivity(new Intent(Search.this,Search.class));break;
                    case R.id.navigation_settings:
                        startActivity(new Intent(Search.this,Settings.class));break;
                    default:



                }
                return false;
            }
        });

    }
}
