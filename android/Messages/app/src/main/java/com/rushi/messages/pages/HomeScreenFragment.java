package com.rushi.messages.pages;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContentResolverCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.provider.BaseColumns;
import android.provider.Telephony;
import android.provider.Telephony.Sms;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rushi.messages.databinding.FragmentHomeScreenBinding;
import com.rushi.messages.modals.SmsConversationsItem;
import com.rushi.messages.pages.home.HomeScreenAdapter;
import com.rushi.messages.viewmodel.HomeViewModel;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

public class HomeScreenFragment extends Fragment {
    private FragmentHomeScreenBinding homeScreenBinding;
    private static final String TAG = "HomeScreenFragment";
    private HomeViewModel homeViewModel;
    private HomeScreenAdapter homeScreenAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeViewModel=new ViewModelProvider(requireActivity()).get(HomeViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        homeScreenBinding=FragmentHomeScreenBinding.inflate(inflater,container,false);
        return homeScreenBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       /* DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<DefaultDialog>(new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, @Nullable String url, @Nullable Object payload) {
                Picasso.get().load(url).into(imageView);
            }
        });
        dialogsListAdapter.setItems(DialogsFixtures.getDialogs());
        homeScreenBinding.dialogsList.setAdapter(dialogsListAdapter);*/
        //homeScreenBinding.recyclerView.setAdapter(new HomeScreenAdapter(requireContext()));
        //getSmsDetails();
        homeScreenBinding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        homeScreenBinding.recyclerView.setHasFixedSize(true);
        homeScreenAdapter=new HomeScreenAdapter(requireContext());
        homeScreenBinding.recyclerView.setAdapter(homeScreenAdapter);
        homeViewModel.getSmsConversationsItemsLive().observe(getViewLifecycleOwner(), new Observer<List<SmsConversationsItem>>() {
            @Override
            public void onChanged(List<SmsConversationsItem> smsConversationsItems) {
                homeScreenAdapter.setConversationsItemList(smsConversationsItems);
            }
        });
    }

    public void getSmsDetails(){
        List<Telephony.Sms> smsList=new ArrayList<>();
        ContentResolver cr=requireContext().getContentResolver();
        String projection[]=null;
        String selection="thread_id=?";
        String selectionArgs[]=new String[]{"123"};
        String sortOrder=null;
        Cursor cursor=cr.query(Sms.CONTENT_URI,projection,selection,selectionArgs,Sms.Conversations.DEFAULT_SORT_ORDER);
        Log.d(TAG, "getSmsDetails: "+Arrays.toString(cursor.getColumnNames()));
        while (cursor.moveToNext()){
            if(cursor.getPosition()==20){
                break;
            }
            //int msg_count=cursor.getInt(cursor.getColumnIndex("msg_count"));
            String _id=cursor.getString(cursor.getColumnIndex("_id"));
            String thread_id=cursor.getString(cursor.getColumnIndex("thread_id"));
            String body=cursor.getString(cursor.getColumnIndex("body"));
            String creator=cursor.getString(cursor.getColumnIndex("creator"));
            String address=cursor.getString(cursor.getColumnIndex("address"));
            String person=cursor.getString(cursor.getColumnIndex("person"));
            /*Cursor cursor1=cr.query(Telephony.TextBasedSmsColumns.CONTENT_URI,null, Sms.THREAD_ID+"=?",new String[]{thread_id},null);
            Log.d(TAG, "=>"+Arrays.toString(cursor1.getColumnNames()));
            while (cursor1.moveToNext()){
                String snippet1=cursor1.getString(cursor.getColumnIndex("snippet"));
                Log.d(TAG, "getSmsDetails: "+thread_id+":"+snippet1);
            }*/
            //cursor1.close();
            Log.d(TAG, "getSmsDetails: "+thread_id+"\n"+creator+"\n"+_id+"\n"+address+"\n"+person+"\n"+body);
        }
        cursor.close();
    }
}