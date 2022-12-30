package com.rushi.messages.viewmodel;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rushi.messages.AppConstants;
import com.rushi.messages.R;
import com.rushi.messages.appstart.SmsInitializer;
import com.rushi.messages.helpers.TextClassificationClient;
import com.rushi.messages.modals.SmsConversationsItem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;

import javax.inject.Inject;

public class HomeViewModel extends AndroidViewModel {
    private MutableLiveData<List<SmsConversationsItem>> smsConversationsItemsLive;
    private static final String TAG = "HomeViewModel";
    private Random random;
    private ExecutorService executorService;
    private int[] avatorColors=new int[]{1,2,3,4,5,6,7};
    //private int totalMsgCount=0;
    @ViewModelInject
    public HomeViewModel(@NonNull Application application, ExecutorService executorService) {
        super(application);
        this.executorService=executorService;
        smsConversationsItemsLive=new MutableLiveData<>();
        smsConversationsItemsLive.setValue(new ArrayList<>());
        random=new Random();
        initializeSmsConversationsItems();
    }
    public void initializeSmsConversationsItems(){
        ContentResolver cr=getApplication().getContentResolver();
        /*String projection[]=null;
        String selection=null;
        String selectionArgs[]=null;*/
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=cr.query(Telephony.Sms.Conversations.CONTENT_URI,null,null,null,Telephony.Sms.Conversations.DEFAULT_SORT_ORDER);
                SmsConversationsItem smsConversationsItem=null;
                Integer msg_count=null,thread_id=null;
                String snippet=null,address=null;
                Cursor cursorInner=null;
                List<SmsConversationsItem> smsConversationsItems=new ArrayList<>();
                while (cursor.moveToNext()){
                    msg_count=cursor.getInt(cursor.getColumnIndex("msg_count"));
                    thread_id=cursor.getInt(cursor.getColumnIndex("thread_id"));
                    snippet=cursor.getString(cursor.getColumnIndex("snippet"));
//            totalMsgCount=totalMsgCount+msg_count;
                    cursorInner=cr.query(Telephony.Sms.CONTENT_URI, new String[]{"address"},"thread_id=?",new String[]{String.valueOf(thread_id)}, Telephony.Sms.Conversations.DEFAULT_SORT_ORDER);
                    if(cursorInner.moveToFirst()) {
                        address = cursorInner.getString(cursorInner.getColumnIndex("address"));
                        //random.setSeed(thread_id);
                        if(cursor.getPosition()==0)
                            address="+918979286635";
                        //Log.d(TAG, "run: ,"+cursor.getPosition());
                        smsConversationsItems.add(new SmsConversationsItem(thread_id, msg_count, snippet, address, random.nextInt(avatorColors.length+1)));
                    }
                    //cursorInner.close();
                }
                //Log.d(TAG, "initializeSmsConversationsItems: "+smsConversationsItemsLive.getValue().size());
                //Log.d(TAG, "initializeSmsConversationsItems: "+smsConversationsItems.size());
                smsConversationsItemsLive.postValue(smsConversationsItems);
                cursor.close();
            }
        });

    }

    public LiveData<List<SmsConversationsItem>> getSmsConversationsItemsLive() {
        return smsConversationsItemsLive;
    }
}
