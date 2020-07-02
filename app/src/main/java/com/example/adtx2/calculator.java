package com.example.adtx2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class calculator extends AppCompatActivity {
    Button btnExit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        btnExit2=(Button)findViewById(R.id.btnExit2);












        exit2();
    }



    private void exit2(){
        btnExit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(calculator.this,MainActivity.class));
            }
        });
    }
}
