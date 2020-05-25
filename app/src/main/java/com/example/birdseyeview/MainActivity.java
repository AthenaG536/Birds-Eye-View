package com.example.birdseyeview;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    Button btnGo;
    TextView txtVwTitle, txtVwInstMessage;
    EditText editTxtUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //RequestQueue requestQueue;

        btnGo = findViewById(R.id.btnGo);
        txtVwTitle = findViewById(R.id.txtVwTitle);
        txtVwInstMessage = findViewById(R.id.txtVwInstructionMessage);
        editTxtUserName = findViewById(R.id.editTxtUserName);

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Birding.class));

            }
        });
    }
}
