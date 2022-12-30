package com.rushi.messages.pages.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.rushi.messages.MessageDetailActivity;
import com.rushi.messages.R;
import com.rushi.messages.databinding.MaterialListItemThreeLineBinding;
import com.rushi.messages.modals.SmsConversationsItem;

import java.util.ArrayList;
import java.util.List;

public class HomeScreenAdapter extends RecyclerView.Adapter<HomeScreenAdapter.HomeListViewHolder> {

    private Context context;
    private List<SmsConversationsItem> conversationsItemList;

    public HomeScreenAdapter(Context context) {
        this.context = context;
        this.conversationsItemList = new ArrayList<>();
    }

    @NonNull
    @Override
    public HomeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        MaterialListItemThreeLineBinding materialListItemThreeLineBinding = MaterialListItemThreeLineBinding.inflate(inflater, parent, false);
        HomeListViewHolder homeListViewHolder = new HomeListViewHolder(materialListItemThreeLineBinding);
        return homeListViewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull HomeListViewHolder holder, int position) {
        SmsConversationsItem smsConversationsItem = conversationsItemList.get(position);
        holder.bind(smsConversationsItem);
    }


    @Override
    public int getItemCount() {
        return conversationsItemList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 3;
    }

    public void setConversationsItemList(List<SmsConversationsItem> conversationsItemList) {
        this.conversationsItemList.clear();
        this.conversationsItemList.addAll(conversationsItemList);
        notifyDataSetChanged();
    }

    public class HomeListViewHolder extends RecyclerView.ViewHolder {
        private MaterialListItemThreeLineBinding itemThreeLineBinding;

        public HomeListViewHolder(MaterialListItemThreeLineBinding itemThreeLineBinding) {
            super(itemThreeLineBinding.getRoot());
            this.itemThreeLineBinding = itemThreeLineBinding;
        }

        public void bind(SmsConversationsItem smsConversationsItem) {
            itemThreeLineBinding.setSmsConversationsItem(smsConversationsItem);
            itemThreeLineBinding.root.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context,MessageDetailActivity.class);
                    intent.putExtra("thread_id",smsConversationsItem.getThreadId());
                    intent.putExtra("address",smsConversationsItem.getAddress());
                    context.startActivity(intent);
                }
            });
//            Drawable drawable=context.getResources().getDrawable(R.drawable.ic_baseline_account_circle_40,null);
//            drawable.setTint(context.getColor(smsConversationsItem.getAvatarColor()));
//            itemThreeLineBinding.mtrlListItemIcon.setImageDrawable(drawable);
            //itemThreeLineBinding.mtrlListItemIcon.setColorFilter(smsConversationsItem.getAvatarColor());
            itemThreeLineBinding.executePendingBindings();

        }
    }
}

