package edu.neu.team38;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.team38.realtimeDB.RealtimeDAO;
import edu.neu.team38.realtimeDB.models.Message;
import edu.neu.team38.realtimeDB.models.User;

public class LoginPage extends AppCompatActivity {
    TextView textView;
    Button send;
    Button receive;
    EditText messageContent;
    EditText toUser;
    Button sendMessage;
    RealtimeDAO dao;
    List<User> userList;
    String curUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        textView = findViewById(R.id.tv_user);
        send = findViewById(R.id.btn_send);
        receive = findViewById(R.id.btn_receive);
        messageContent = findViewById(R.id.ed_messageContent);
        toUser = findViewById(R.id.ed_toUser);
        sendMessage = findViewById(R.id.btn_sendMessage);
        dao = new RealtimeDAO();
        userList = new ArrayList<>();

        Intent intent = getIntent();
        if (intent != null) {
            Log.d("App", "onCreate: " + intent.getStringExtra("username"));
            curUser = intent.getStringExtra("username");
            textView.setText("Current user: " + curUser);
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(LoginPage.this, SendPage.class);
                newIntent.putExtra("username", curUser);
                startActivity(newIntent);
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent newIntent = new Intent(LoginPage.this, ReceivedPage.class);
                newIntent.putExtra("username", curUser);
                startActivity(newIntent);
            }
        });

        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toUsername = toUser.getText().toString();
                String content = messageContent.getText().toString();
                String fromUsername;
                User toUserObj, fromUserObj;
                Message message;

                Log.i("toUsername", toUsername);
                // 1. check the user is already exist if the user received the message exists
                if (dao.checkIfUserExist(toUsername)) {
                    // 2. create Message and add it to the Firebase
                    fromUsername = intent.getStringExtra("username");
//                    toUserObj = dao.getUser(toUsername);
//                    fromUserObj = dao.getUser(fromUsername);
                    message = new Message(content, fromUsername, toUsername);

                    dao.addMessage(message);
                    toUser.setText("");
                    messageContent.setText("");
                    Toast.makeText(LoginPage.this, "message sent successfully!", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(LoginPage.this, "user does not exist or try again", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            userList = new ArrayList<>();
            if (snapshot.exists()) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };
}