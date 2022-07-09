package edu.neu.team38;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class LoginPage extends AppCompatActivity {
    TextView textView;
    Button send;
    Button receive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);


        textView = findViewById(R.id.tv_user);
        send = findViewById(R.id.btn_send);
        receive = findViewById(R.id.btn_receive);
        Intent intent = getIntent();
        if (intent != null) {
            Log.d("App", "onCreate: " + intent.getStringExtra("username"));
            textView.setText("Current user: " + intent.getStringExtra("username"));
        }

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, SendPage.class));
            }
        });

        receive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginPage.this, ReceivedPage.class));
            }
        });
    }
}