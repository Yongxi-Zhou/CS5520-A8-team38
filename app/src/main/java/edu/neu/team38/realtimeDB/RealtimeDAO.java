package edu.neu.team38.realtimeDB;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.neu.team38.realtimeDB.models.Message;
import edu.neu.team38.realtimeDB.models.User;

public class RealtimeDAO {
    private DatabaseReference databaseReference;
    private static final String TAG = RealtimeDAO.class.getSimpleName();
    private ValueEventListener valueEventListener;
    private ArrayList<User> userList;

    public RealtimeDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userList = new ArrayList<>();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
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

    public boolean checkIfUserExist(String username) {
        Query query =  FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(valueEventListener);
        StringBuilder sb = new StringBuilder(1024);
        for (User user: userList) {
            sb.append(user.toString());
        }
        Log.i("userList", sb.toString());
        return userList.size() != 0;
    }

    public User getUser(String username) {
        Query query =  FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(valueEventListener);
        return userList.get(0);
    }

    public Task<Void> addUser(User user) {
        if (user == null) {
            Log.e(TAG, "no user input");
        }
        return databaseReference.child("users").push().setValue(user);
    }

    public Task<Void> addMessage(Message message) {
        if (message == null) {
            Log.e(TAG, "no message input");
        }
        return databaseReference.child("messages").push().setValue(message);
    }
}
