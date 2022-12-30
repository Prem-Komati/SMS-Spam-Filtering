package com.rushi.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;
import com.rushi.messages.modals.SmsConversationsItem;
import com.rushi.messages.pages.home.HomeScreenAdapter;
import com.rushi.messages.viewmodel.HomeViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private MaterialToolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;
    private HomeScreenAdapter homeScreenAdapter;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_homescreen)
                .build();

        recyclerView=findViewById(R.id.recycler_view);

        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_mobile);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        homeViewModel=new ViewModelProvider(this).get(HomeViewModel.class);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        homeScreenAdapter=new HomeScreenAdapter(this);
        recyclerView.setAdapter(homeScreenAdapter);
        homeViewModel.getSmsConversationsItemsLive().observe(this, new Observer<List<SmsConversationsItem>>() {
            @Override
            public void onChanged(List<SmsConversationsItem> smsConversationsItems) {
                homeScreenAdapter.setConversationsItemList(smsConversationsItems);
                Log.d(TAG, "onChanged: "+smsConversationsItems.size());
            }
        });
    }

    /*@Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_mobile);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }*/
}