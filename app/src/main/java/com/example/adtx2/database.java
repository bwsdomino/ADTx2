package com.example.adtx2;

import android.os.Bundle;
import android.app.Activity;
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




import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
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

                        if (name.length()==0 || price.length()==0) {
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
                                    toastMessage("Success: " + success);
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


            /* mDatabaseHelper = new DatabaseHelper(this);
             mListView=(ListView)findViewById(R.id.listView);

             populateListView();





             btnAdd.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     if (name.length() != 0&&price.length()!=0) {
                         AddData(name.getText().toString(),price.getText().toString());
                         ex();
                         populateListView();

                     } else {
                         toastMessage("You must put something in the text field!");
                     }
                 }
             });
         public void addItem(View view) {
             final EditText ET = new EditText(MainActivity.this);
             AlertDialog.Builder b=new AlertDialog.Builder(MainActivity.this)
                     .setTitle("Co chcesz dodać?")
                     .setView(ET)
                     .setPositiveButton("Dodaj", new DialogInterface.OnClickListener() {
                         @Override
                         public void onClick(DialogInterface dialog, int which) {

                             if (ET.length() != 0) {
                                 AddData(ET.getText().toString(),);
                                 ET.setText("");
                                 populateListView();

                             } else {
                                 toastMessage("You must put something in the text field!");
                             }

                         }
                     });
             AlertDialog dialog = b.create();
             dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
             dialog.show();
         }

         public void AddData(String name,String price, String unit) {

             boolean insertData = mDatabaseHelper.addData(name,price,unit);

             if (insertData) {
                 toastMessage("Data Succesfully Inserted!");
             } else {
                 toastMessage("Something went wrong");
             }
             populateListView();
         }





         private void populateListView() {

             Log.d(TAG, "populateListView: Displaying data in the ListView.");

             Cursor data = mDatabaseHelper.getData();
             ArrayList<String> listData = new ArrayList<>();
             while (data.moveToNext()) {
                 String a=data.getString(1)+","+data.getString(2)+","+data.getString(3);
                 listData.add(a);
                 ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listData);
                 mListView.setAdapter(adapter);

                 mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                     @Override
                     public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                     changeVisibility(position);
                     }
                 });
             }

         }
         private void ex(){
             changeVisibility(null);
             iV=false;
             btnCnc.setVisibility(View.INVISIBLE);
             btnRem.setVisibility(View.INVISIBLE);
             btnAdd.setText("Dodaj");
             name.setText("");
             price.setText("");
             unit.setText("");
         }
         private void changeVisibility(Integer i){
             if(iV==false) {
                 btnCnc.setVisibility(View.VISIBLE);
                 btnRem.setVisibility(View.VISIBLE);
                 btnAdd.setText("Modify");




                 name.setText("");
                 price.setText("");
                 unit.setText("");
                 iV=true;
             }
             else{}
         }
     */
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























