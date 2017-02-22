package com.bdunkic.ethosmonitor;

import android.content.Intent;
import android.content.ReceiverCallNotAllowedException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText etUser;
    private Button btnSend;

    public static final String EXTRA_KEY = "EXTRA_KEY";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initWidgets();
        setupListeners();
    }

    private void initWidgets() {
        etUser = (EditText)findViewById(R.id.etUser);
        btnSend= (Button)findViewById(R.id.btnSend);


    }

    private void setupListeners() {

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendIntentWithExtra();

            }
        });

    }

    private void sendIntentWithExtra() {

        String text = etUser.getText().toString();

        Intent intent = new Intent(MainActivity.this, RecieveingActivity.class);

        intent.putExtra(EXTRA_KEY,text);

        startActivity(intent);


    }
}
