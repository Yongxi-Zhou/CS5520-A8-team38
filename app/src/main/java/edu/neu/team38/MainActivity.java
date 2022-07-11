package edu.neu.team38;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
                Query query = dao.checkIfUserExist(username);
                query.addListenerForSingleValueEvent(valueEventListener);
                if (userList.size() == 0) {
                    User user = new User(username);
                    dao.addUser(user);
                }
                Intent intent = new Intent(MainActivity.this, LoginPage.class);
                intent.putExtra("username", username);
                editText.setText("");
                startActivity(intent);
            }
        });
    }
}