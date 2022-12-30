package com.rushi.messages.pages.detail;

import android.content.Context;
import android.provider.Telephony;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rushi.messages.AppConstants;
import com.rushi.messages.OnCompletion;
import com.rushi.messages.databinding.ReceiveMessageItemBinding;
import com.rushi.messages.databinding.SentMessageItemBinding;
import com.rushi.messages.modals.SmsConversationsItem;
import com.rushi.messages.modals.SmsItem;
import com.rushi.messages.viewmodel.DetailViewModel;

import java.util.ArrayList;
import java.util.List;

public class DetailScreenAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<SmsItem> smsItems;
    private DetailViewModel detailViewModel;
    private static final String TAG = "DetailScreenAdapter";

    public DetailScreenAdapter(Context context, DetailViewModel detailViewModel) {
        this.context = context;
        this.smsItems = new ArrayList<>();
        this.detailViewModel = detailViewModel;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == AppConstants.SENT_VIEW_TYPE) {
            SentMessageItemBinding sentMessageItemBinding = SentMessageItemBinding.inflate(inflater, parent, false);
            return new SentViewHolder(sentMessageItemBinding);
        } else {
            ReceiveMessageItemBinding receiveMessageItemBinding = ReceiveMessageItemBinding.inflate(inflater, parent, false);
            return new ReceiveViewHolder(receiveMessageItemBinding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        SmsItem smsItem = smsItems.get(position);
        if (getItemViewType(position) == AppConstants.SENT_VIEW_TYPE) {
            SentViewHolder sentViewHolder = (SentViewHolder) holder;
            sentViewHolder.bind(smsItem);
        } else {
            ReceiveViewHolder receiveViewHolder = (ReceiveViewHolder) holder;
            receiveViewHolder.bind(smsItem);
        }
    }


    @Override
    public int getItemCount() {
        return smsItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        switch (smsItems.get(position).getType()) {
            case 1:
                return AppConstants.RECEIVE_VIEW_TYPE;
            case 2:
                return AppConstants.SENT_VIEW_TYPE;
            default:
                return AppConstants.VIEW_TYPE_UNKNOWN;

        }
    }

    public void setSmsItems(List<SmsItem> smsItems) {
        this.smsItems.clear();
        this.smsItems.addAll(smsItems);
        notifyDataSetChanged();
    }

    public void setSmsItemSpamStatus(int pos,Boolean spam) {
        smsItems.get(pos).setSpam(spam);
       // Log.d(TAG, "setSmsItemSpamStatus: "+spam);
        notifyItemChanged(pos);
    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {
        private ReceiveMessageItemBinding receiveMessageItemBinding;

        public ReceiveViewHolder(ReceiveMessageItemBinding receiveMessageItemBinding) {
            super(receiveMessageItemBinding.getRoot());
            this.receiveMessageItemBinding = receiveMessageItemBinding;
        }

        public void bind(SmsItem smsItem) {
            receiveMessageItemBinding.setBody(smsItem.getBody());
            receiveMessageItemBinding.setSpam(smsItem.isSpam());
            /*detailViewModel.predictOnComplete(smsItem.getBody(), new OnCompletion<Boolean>() {
                @Override
                public void onComplete(Boolean result) {
                    receiveMessageItemBinding.setSpam(result);
                }
            });*/
           /* itemThreeLineBinding.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,MessageDetailActivity.class);
                    intent.putExtra("thread_id",smsConversationsItem.getThreadId());
                    intent.putExtra("address",smsConversationsItem.getAddress());
                    context.startActivity(intent);
                }
            });*/
//            Drawable drawable=context.getResources().getDrawable(R.drawable.ic_baseline_account_circle_40,null);
//            drawable.setTint(context.getColor(smsConversationsItem.getAvatarColor()));
//            itemThreeLineBinding.mtrlListItemIcon.setImageDrawable(drawable);
            //itemThreeLineBinding.mtrlListItemIcon.setColorFilter(smsConversationsItem.getAvatarColor());
            receiveMessageItemBinding.executePendingBindings();

        }
    }

    public class SentViewHolder extends RecyclerView.ViewHolder {
        private SentMessageItemBinding sentMessageItemBinding;

        public SentViewHolder(SentMessageItemBinding sentMessageItemBinding) {
            super(sentMessageItemBinding.getRoot());
            this.sentMessageItemBinding = sentMessageItemBinding;
        }

        public void bind(SmsItem smsItem) {
            sentMessageItemBinding.setBody(smsItem.getBody());
           /* itemThreeLineBinding.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,MessageDetailActivity.class);
                    intent.putExtra("thread_id",smsConversationsItem.getThreadId());
                    intent.putExtra("address",smsConversationsItem.getAddress());
                    context.startActivity(intent);
                }
            });*/
//            Drawable drawable=context.getResources().getDrawable(R.drawable.ic_baseline_account_circle_40,null);
//            drawable.setTint(context.getColor(smsConversationsItem.getAvatarColor()));
//            itemThreeLineBinding.mtrlListItemIcon.setImageDrawable(drawable);
            //itemThreeLineBinding.mtrlListItemIcon.setColorFilter(smsConversationsItem.getAvatarColor());
            sentMessageItemBinding.executePendingBindings();

        }
    }

}

