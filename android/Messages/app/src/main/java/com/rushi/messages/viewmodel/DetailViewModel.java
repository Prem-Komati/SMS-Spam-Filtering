package com.rushi.messages.viewmodel;

import android.app.Application;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.Telephony;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rushi.messages.OnCompletion;
import com.rushi.messages.R;
import com.rushi.messages.helpers.TextClassificationClient;
import com.rushi.messages.modals.SPAM;
import com.rushi.messages.modals.SmsConversationsItem;
import com.rushi.messages.modals.SmsItem;

import org.tensorflow.lite.support.label.Category;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;

public class DetailViewModel extends AndroidViewModel {
    private MutableLiveData<List<SmsItem>> smsItemsLive;
    private static final String TAG = "DetailViewModel";
    private ExecutorService executorService;
    private TextClassificationClient client;
    private MutableLiveData<SPAM> isSpamLive;
    //private int totalMsgCount=0;
    @ViewModelInject
    public DetailViewModel(@NonNull Application application, ExecutorService executorService) {
        super(application);
        this.executorService=executorService;
        smsItemsLive=new MutableLiveData<>();
        isSpamLive=new MutableLiveData<>();
        smsItemsLive.setValue(new ArrayList<>());
        client = new TextClassificationClient(getApplication().getApplicationContext(),executorService);
        client.load();
    }
    public void getSmsDetailsByThread(int threadId){
        ContentResolver cr=getApplication().getContentResolver();
        String projection[]=new String[]{"_id","thread_id","type","date","date_sent","read","address","body"};
        String selection="thread_id=? AND (type=? OR type=?)";
        //AND type in(?,?,?,?)
        String selectionArgs[]=new String[]{String.valueOf(threadId),"1","2"};
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                Cursor cursor=cr.query(Telephony.Sms.CONTENT_URI,projection,selection,selectionArgs, Telephony.Sms.Conversations.DEFAULT_SORT_ORDER);
                //Log.d(TAG, "getSmsDetails: "+ Arrays.toString(cursor.getColumnNames()));
                int _id,thread_id,type;
                long date,date_sent;
                boolean read,spam;
                String address,body;
                List<SmsItem> smsItems=new ArrayList<>();
                while (cursor.moveToNext()){
                    //int msg_count=cursor.getInt(cursor.getColumnIndex("msg_count"));
                    _id=cursor.getInt(cursor.getColumnIndex("_id"));
                    thread_id=cursor.getInt(cursor.getColumnIndex("thread_id"));
                    type=cursor.getInt(cursor.getColumnIndex("type"));
                    date=cursor.getLong(cursor.getColumnIndex("date"));
                    date_sent=cursor.getLong(cursor.getColumnIndex("date_sent"));
                    read= cursor.getInt(cursor.getColumnIndex("read")) == 1;
                    address=cursor.getString(cursor.getColumnIndex("address"));
                    body=cursor.getString(cursor.getColumnIndex("body"));
                    smsItems.add(new SmsItem(_id,threadId,date,date_sent,read,type,address,body,null));
                    //Log.d(TAG, "getSmsDetails: "+_id+"\n"+body+"\n"+type);
                    /*Log.d(TAG, "getSmsDetails: "+_id+"\n"+thread_id+"\n"+address+"\n"+person+"\n"+date+"\n"+date_sent
                    +"\n"+protocol+"\n"+read+"\n"+status+"\n"+type+"\n"+reply_path_present+"\n"+subject+
                            "\n"+body+"\n"+service_center+"\n"+locked+"\n"+type+"\n"+seen+"\n"+creator);*/
                }
                smsItemsLive.postValue(smsItems);
                predictStart(smsItems);
                cursor.close();
            }
        });
    }

    private void predictStart(List<SmsItem> smsItems){
        for (int i = 0; i < smsItems.size(); i++) {
            isSpamLive.postValue(new SPAM(i,predict(smsItems.get(i).getBody())));
           // Log.d(TAG, "predictStart: ");
        }
    }

    private boolean predict(String sms){
        List<Category> results = client.classify(sms);
        //Log.d(TAG, "predict: " + results.toArray().toString());
        float score = results.get(1).getScore();
        if (score > 0.8) {
//            mainBinding.statusOutput.setText("Your message was detected as spam with a score of " + Float.toString(score) + " and not sent!");
            return true;
        } else {
            //mainBinding.statusOutput.setText("Message sent! Spam score was: " + Float.toString(score));
            return false;
        }
    }

    public void predictOnComplete(String sms, OnCompletion<Boolean> onCompletion){
        List<Category> results = client.classify(sms);
        //Log.d(TAG, "predict: " + results.toArray().toString());
        float score = results.get(1).getScore();
        if (score > 0.8) {
//            mainBinding.statusOutput.setText("Your message was detected as spam with a score of " + Float.toString(score) + " and not sent!");
            onCompletion.onComplete(true);
        } else {
            //mainBinding.statusOutput.setText("Message sent! Spam score was: " + Float.toString(score));
            onCompletion.onComplete(false);
        }
    }

    @Override
    protected void onCleared() {
        client.unload();
    }

    public LiveData<List<SmsItem>> getSmsItemsLive() {
        return smsItemsLive;
    }

    public LiveData<SPAM> getSpamLive() {
        return isSpamLive;
    }
}
