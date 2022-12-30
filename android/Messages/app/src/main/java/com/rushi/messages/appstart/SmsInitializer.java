package com.rushi.messages.appstart;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.Telephony;

import androidx.annotation.NonNull;
import androidx.startup.Initializer;

import com.rushi.messages.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

public class SmsInitializer implements Initializer<Cursor> {

    @NonNull
    @Override
    public Cursor create(@NonNull Context context) {
        ContentResolver cr=context.getContentResolver();
        /*String projection[]=null;
        String selection=null;
        String selectionArgs[]=null;*/
        return cr.query(Telephony.Sms.Conversations.CONTENT_URI,null,null,null,Telephony.Sms.Conversations.DEFAULT_SORT_ORDER);
    }

    @NonNull
    @Override
    public List<Class<? extends Initializer<?>>> dependencies() {
        return new ArrayList<>();
    }
}
