package edu.neu.team38;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.neu.team38.realtimeDB.RealtimeDAO;
import edu.neu.team38.realtimeDB.models.User;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btn;
    RealtimeDAO dao;
    List<User> userList;
    int flag = 0;

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            userList.clear();
            if (snapshot.exists()) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                    if (userList.size() != 0) {
                        flag = 1;
                    }
                }
                if (userList.size() == 0) {
                    flag = -1;
                }
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new RealtimeDAO();
        userList = new ArrayList<>();

        editText = findViewById(R.id.edit_username);
        btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText.getText().toString();
//                Query query = FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);
//                query.addListenerForSingleValueEvent(valueEventListener);
//                StringBuilder sb = new StringBuilder(1024);
//                for (User user: userList) {
//                    sb.append(user.toString());
//                }
//                Log.i("userList", sb.toString());
//                if (userList.size() == 0) {
//                    User user = new User(username);
//                    dao.addUser(user);
//                }

                User user = new User(username);
                dao.addUser(user);
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                intent.putExtra("username", username);
                editText.setText("");
                startActivity(intent);
            }
        });
    }
}