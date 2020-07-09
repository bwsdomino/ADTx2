package com.example.adtx2;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.List;
public class database extends AppCompatActivity {
    private Button btnAdd, btnCnc, btnRem,btnExit;
    private EditText name,price,search;
    private TextView itemID;
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_database);
                btnAdd = (Button) findViewById(R.id.btnAdd);
                btnCnc = (Button) findViewById(R.id.btnCnc);
                btnRem = (Button) findViewById(R.id.btnRem);
                btnExit = (Button) findViewById(R.id.btnExit);
                name = (EditText) findViewById(R.id.name);
                price = (EditText) findViewById(R.id.price);
                search =(EditText) findViewById(R.id.Et_search);
                itemID=(TextView)findViewById(R.id.id);
                mListView=(ListView) findViewById(R.id.listView);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Item_Model item_model;
                        if (name.getText().toString().trim().length()==0 || price.length()==0) {
                            toastMessage("proszę wpisać dane");
                        } else {
                            if (itemID.getText() != "") {
                                item_model = new Item_Model(Integer.parseInt(itemID.getText().toString()), name.getText().toString(), Integer.parseInt(price.getText().toString()));
                                DatabaseHelper databaseHelper = new DatabaseHelper(database.this);
                                databaseHelper.modOne(item_model);
                            } else {
                                try {
                                    item_model = new Item_Model(-1, name.getText().toString(), Integer.parseInt(price.getText().toString()));
                                    DatabaseHelper databaseHelper = new DatabaseHelper(database.this);
                                    boolean success = databaseHelper.addOne(item_model);
                                    toastMessage("Dodawanie udane");
                                    name.setText("");
                                    price.setText("");

                                } catch (Exception e) {
                                    toastMessage("dodawanie nie powiodło się");
                                }
                            }
                            search.setText("");
                        }
                    }
                });

                btnRem.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            DatabaseHelper databaseHelper=new DatabaseHelper(database.this);
                            Item_Model item_model = new Item_Model(Integer.parseInt(itemID.getText().toString()), name.getText().toString(), Integer.parseInt(price.getText().toString()));
                            databaseHelper.deleteOne((item_model));
                            toastMessage("Usunięty dziad: ");
                            name.setText("");
                            price.setText("");
                            itemID.setText("");
                        }
                        catch(Exception e){
                            toastMessage("cos poszlo nie tak");
                        }
                        search.setText("");
                    }
                });
                btnCnc.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        btnCnc.setVisibility(View.INVISIBLE);
                        btnRem.setVisibility(View.INVISIBLE);
                        itemID.setVisibility(View.INVISIBLE);
                        btnAdd.setText("Dodaj");
                        name.setText("");
                        price.setText("");
                        itemID.setText("");
                    }
                });
                mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Item_Model clickedItem = (Item_Model) parent.getItemAtPosition(position);

                        String itName = clickedItem.getName();
                        int itPrice= clickedItem.getPrice();
                        int itID=clickedItem.getId();


                        btnCnc.setVisibility(View.VISIBLE);
                        btnRem.setVisibility(View.VISIBLE);
                        itemID.setVisibility(View.VISIBLE);
                        btnAdd.setText("Modifikuj");


                        itemID.setText(String.valueOf(itID));
                        name.setText(itName);
                        price.setText(String.valueOf(itPrice));
                    }
                });
                search.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                    }
                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        find(s.toString());
                    }
                    @Override
                    public void afterTextChanged(Editable s) {
                    }
                });
                ViewAll();
                exit();
            }
            private void ViewAll(){
                DatabaseHelper databaseHelper = new DatabaseHelper(database.this);
                List<Item_Model> everyone = databaseHelper.getEveryone();
                ArrayAdapter itemArrayAdapter = new ArrayAdapter<Item_Model>(database.this, android.R.layout.simple_list_item_1,everyone);
                mListView.setAdapter(itemArrayAdapter);
            }

            private void find(String s){
                DatabaseHelper databaseHelper = new DatabaseHelper(database.this);
                List<Item_Model>someone = databaseHelper.search(s);
                ArrayAdapter itemArrayAdapter = new ArrayAdapter<Item_Model>(database.this, android.R.layout.simple_list_item_1,someone);
                mListView.setAdapter(itemArrayAdapter);
            }

            private void toastMessage(String message) {
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
            }

    private void exit(){
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(database.this,MainActivity.class));
            }
        });
    }


        }























