package com.example.adtx2;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class order extends AppCompatActivity {
    Button btnExit3,toAdd;
    ListView listView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        btnExit3 = (Button)findViewById(R.id.btnExit3);
        toAdd = (Button)findViewById(R.id.toAdd);
        listView=(ListView)findViewById(R.id.orders);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




        final int witch_item=position;
        final DatabaseHelper databaseHelper=new DatabaseHelper(order.this);
        List<Obj_Model> everyone = databaseHelper.getEveryObj();
        final Obj_Model obj_model=everyone.get(witch_item);
        startActivity(new Intent(order.this,calculator.class).putExtra("object", obj_model.getId()));
    }
        });
        ViewAll();
        click();
    }

    private void click(){
        btnExit3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(order.this,MainActivity.class));
            }
        });

        toAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(order.this,calculator.class));
            }
        });

    }
    private void ViewAll(){
        DatabaseHelper databaseHelper = new DatabaseHelper(order.this);
        List<Obj_Model> everyone = databaseHelper.getEveryObj();
        List<String>list = new ArrayList<>();
        for(int i=0;i<everyone.size();i++){
            list.add(everyone.get(i).getName());
        }


        ArrayAdapter itemArrayAdapter = new ArrayAdapter<String>(order.this, android.R.layout.simple_list_item_1,list);
        listView.setAdapter(itemArrayAdapter);
    }

}
