package edu.neu.team38;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.team38.realtimeDB.models.Message;

public class ReceivedPage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter myAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private TextView textView;
    private String curUser;
    private List<Message> messageList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_received_page);

        textView = findViewById(R.id.tv_receUser);
        Intent intent = getIntent();
        if (intent != null) {
            Log.d("App", "onCreate: " + intent.getStringExtra("username"));
            curUser = intent.getStringExtra("username");
            textView.setText("Current user: " + curUser);
        }

        // bind recycleView id - Widget
        recyclerView = findViewById(R.id.receivedRecycle);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        messageList = new ArrayList<>();
        myAdapter = new RecycleViewAdapter(messageList, this);
        recyclerView.setAdapter(myAdapter);

        Query query = FirebaseDatabase.getInstance().getReference("messages")
                .orderByChild("receivedByUser")
                .equalTo(curUser);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            messageList.clear();
            if (dataSnapshot.exists()) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Message message = snapshot.getValue(Message.class);
                    messageList.add(message);
                    Log.i("query", "123");
                }
            }
            myAdapter = new RecycleViewAdapter(messageList, ReceivedPage.this);
            recyclerView.setAdapter(myAdapter);
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };
}