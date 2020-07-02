package com.example.adtx2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class order extends AppCompatActivity {
    Button btnExit3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        btnExit3 = (Button)findViewById(R.id.btnExit3);























      exit3();
    }


    private void exit3(){
        btnExit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(order.this,MainActivity.class));
            }
        });
    }

}
