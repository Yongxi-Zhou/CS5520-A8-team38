package edu.neu.team38;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import edu.neu.team38.realtimeDB.RealtimeDAO;
import edu.neu.team38.realtimeDB.models.User;

public class MainActivity extends AppCompatActivity {
    EditText editText;
    Button btn;
    RealtimeDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dao = new RealtimeDAO();

        editText = findViewById(R.id.edit_username);
        btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editText.getText().toString();
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