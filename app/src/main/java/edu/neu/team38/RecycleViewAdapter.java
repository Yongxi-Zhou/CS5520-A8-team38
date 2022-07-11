package edu.neu.team38;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.neu.team38.realtimeDB.models.Message;
import edu.neu.team38.realtimeDB.models.User;

public class RecycleViewAdapter extends RecyclerView.Adapter<RecycleViewAdapter.MyViewHolder> {

    List<Message> messageList;
    Context context;

    public RecycleViewAdapter(List<Message> messageList, Context context) {
        this.messageList = messageList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecycleViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // implement the recycleView to layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycleview, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.tv_receUser.setText(message.getReceivedByUser());
        holder.tv_sentUser.setText(message.getSentByUser());
        holder.tv_conent.setText(message.getContent());
        holder.tv_date.setText(message.getDate());
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    // pass all property to target widget
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv_receUser;
        TextView tv_sentUser;
        TextView tv_date;
        TextView tv_conent;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_receUser = itemView.findViewById(R.id.tv_toUser);
            tv_sentUser = itemView.findViewById(R.id.tv_sentUser);
            tv_date = itemView.findViewById(R.id.tv_sentDate);
            tv_conent = itemView.findViewById(R.id.tv_messageContent);
        }
    }
}
