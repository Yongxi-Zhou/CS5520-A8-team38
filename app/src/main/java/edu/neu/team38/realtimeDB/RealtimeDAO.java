package edu.neu.team38.realtimeDB;

import android.util.Log;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import edu.neu.team38.realtimeDB.models.Message;
import edu.neu.team38.realtimeDB.models.User;

public class RealtimeDAO {
    private DatabaseReference databaseReference;
    private static final String TAG = RealtimeDAO.class.getSimpleName();

    public RealtimeDAO() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    public Query checkIfUserExist(String username) {
        return FirebaseDatabase.getInstance().getReference("users").orderByChild("username").equalTo(username);
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
