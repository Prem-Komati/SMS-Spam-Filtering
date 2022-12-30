package com.rushi.messages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.appbar.MaterialToolbar;
import com.rushi.messages.modals.SPAM;
import com.rushi.messages.modals.SmsItem;
import com.rushi.messages.pages.detail.DetailScreenAdapter;
import com.rushi.messages.pages.home.HomeScreenAdapter;
import com.rushi.messages.viewmodel.DetailViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MessageDetailActivity extends AppCompatActivity {
    private MaterialToolbar toolbar;
    private AppBarConfiguration mAppBarConfiguration;
    private DetailViewModel detailViewModel;
    private RecyclerView recyclerView;
    private DetailScreenAdapter detailScreenAdapter;
    private static final String TAG = "MainActivity";
    private Integer thread_id;
    private String address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_detial);
        toolbar= findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        thread_id=getIntent().getIntExtra("thread_id",0);
        address=getIntent().getStringExtra("address");
        toolbar.setTitle(address);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MessageDetailActivity.this.finish();
            }
        });
        recyclerView=findViewById(R.id.recycler_view_detail);


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_mobile);
        //NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        detailViewModel =new ViewModelProvider(this).get(DetailViewModel.class);
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
        detailScreenAdapter=new DetailScreenAdapter(this,detailViewModel);
        recyclerView.setAdapter(detailScreenAdapter);
        /*detialViewModel.getSmsConversationsItemsLive().observe(this, new Observer<List<SmsConversationsItem>>() {
            @Override
            public void onChanged(List<SmsConversationsItem> smsConversationsItems) {
                homeScreenAdapter.setConversationsItemList(smsConversationsItems);
                Log.d(TAG, "onChanged: "+smsConversationsItems.size());
            }
        });*/
        detailViewModel.getSmsDetailsByThread(thread_id);
        detailViewModel.getSmsItemsLive().observe(this, new Observer<List<SmsItem>>() {
            @Override
            public void onChanged(List<SmsItem> smsItems) {
                detailScreenAdapter.setSmsItems(smsItems);
                //recyclerView.scrollToPosition(detailScreenAdapter.getItemCount()-1);
                //recyclerView.smoothScrollToPosition(detailScreenAdapter.getItemCount()-1);
            }
        });

        detailViewModel.getSpamLive().observe(this, new Observer<SPAM>() {
            @Override
            public void onChanged(SPAM spam) {
               // Log.d(TAG, "onChanged:sd "+spam.getSpam());
                detailScreenAdapter.setSmsItemSpamStatus(spam.getPosition(),spam.getSpam());
                //recyclerView.smoothScrollToPosition(detailScreenAdapter.getItemCount()-1);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}