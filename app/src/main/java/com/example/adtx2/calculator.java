package com.example.adtx2;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import org.w3c.dom.Document;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class calculator extends AppCompatActivity {
    private static final int STORAGE_CODE=1000;
    Button btnExit2,accept,addItem,btnDeteleOrder,mkPDF;
    EditText number,name,orderN;
    TextView it_id,orderId,orderPrice;
    ListView item_list,prompt;
    List<String> items = new ArrayList<>();
    Boolean wasClick=false;
    String q;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        number = (EditText) findViewById(R.id.it_number);
        orderN = (EditText) findViewById(R.id.okon);
        name = (EditText) findViewById(R.id.it_name);
        addItem = (Button) findViewById(R.id.addItem);
        btnExit2 = (Button) findViewById(R.id.btnExit2);
        accept = (Button) findViewById(R.id.accept);
        orderId = (TextView) findViewById(R.id.orderId);
        item_list = (ListView) findViewById(R.id.item_list);
        prompt = (ListView) findViewById(R.id.prompt);
        it_id = (TextView) findViewById(R.id.it_id);
        orderPrice = (TextView) findViewById(R.id.OrderPrice);
        btnDeteleOrder = (Button) findViewById(R.id.delObj);
        mkPDF = (Button) findViewById(R.id.mkPDF);
        style(number);
        style(orderN);
        style(name);


        try {
            Integer ObjId = getIntent().getExtras().getInt("object");
            DatabaseHelper databaseHelper = new DatabaseHelper(calculator.this);
            orderId.setText(ObjId.toString());
            orderN.setText(databaseHelper.getOneObj(ObjId.toString()).getName());
            btnDeteleOrder.setVisibility(View.VISIBLE);
            mkPDF.setVisibility(View.VISIBLE);

            String[] a = databaseHelper.getOneObj(ObjId.toString()).getItems().split("-");
            for (String s : a) {
                if (s.equals("")) {
                } else {
                    items.add(s);
                }
            }
            ViewAll();
        } catch (Exception e) {
        }

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    q = name.getText().toString();
                    if (name.getText().length() == 0) {
                        find("");
                    }
                    wasClick = false;
                    name.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            if (s.length() == 0) {
                                it_id.setText("");
                                find("");
                            } else {
                                find(s.toString());
                            }
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                        }
                    });
                    prompt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            Item_Model clickedItem = (Item_Model) parent.getItemAtPosition(position);
                            String itName = clickedItem.getName();
                            int itId = clickedItem.getId();
                            it_id.setText(String.valueOf(itId));
                            name.setText(itName);
                            wasClick = true;
                            number.requestFocus();
                        }
                    });
                } else {
                    if (wasClick || q.equals(name.getText().toString())) {
                    } else {
                        name.setText("");
                        it_id.setText("");
                    }
                    find(null);
                }
            }
        });

        item_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DatabaseHelper databaseHelper = new DatabaseHelper(calculator.this);
                String[] a = items.get(position).split(":");
                it_id.setText(a[0]);
                name.setText(databaseHelper.getOne(a[0]).getName());
                find(null);
                number.setText(a[1]);
                number.requestFocus();
            }
        });
        item_list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                items.remove(position);
                ViewAll();
                Toast.makeText(calculator.this, "przedmiot został usunięty", Toast.LENGTH_SHORT).show();
                return false;
            }
        });


        addItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String x = it_id.getText().toString();
                String y = number.getText().toString();
                String myk = "";
                Boolean iT = true;
                int num = 0;
                if (x.length() == 0) {
                    Toast.makeText(calculator.this, "Nieprawidłowe dane", Toast.LENGTH_SHORT).show();
                } else {
                    if (y.length() == 0 || Integer.parseInt(y) == 0) {
                        y = "1";
                    }
                    myk = x + ":" + y;
                    for (int i = 0; i < items.size(); i++) {
                        String[] a = items.get(i).split(":");
                        if (x.equals(a[0])) {
                            num = i;
                            iT = false;
                            break;
                        } else {
                            iT = true;
                        }
                    }
                    if (iT) {
                        items.add(myk);
                    } else {
                        items.set(num, myk);
                    }
                    it_id.setText("");
                    name.setText("");
                    number.setText("");
                    find(null);
                    ViewAll();
                }
            }
        });

        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String allItems = "";
                Boolean b;
                if (orderN.getText().length() == 0) {
                    orderN.setText("Zamówienie");
                }
                for (int i = 0; i < items.size(); i++) {
                    allItems += items.get(i) + "-";
                }
                if (items.size() == 0) {
                    Toast.makeText(calculator.this, "Dodaj przedmioty do zamówienia", Toast.LENGTH_SHORT).show();
                } else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(calculator.this);
                    if (orderId.length() == 0) {
                        Obj_Model obj_model = new Obj_Model(-1, orderN.getText().toString(), items.size(), allItems);
                        b = databaseHelper.addOneObj(obj_model);

                    } else {
                        Obj_Model obj_model = new Obj_Model(Integer.parseInt(orderId.getText().toString()), orderN.getText().toString(), items.size(), allItems);
                        b = databaseHelper.modOneObj(obj_model);
                    }
                    if (b) {
                        Toast.makeText(calculator.this, "Zamówienie zostało dodane", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(calculator.this, order.class));
                    } else {
                        Toast.makeText(calculator.this, "Coś poszło nie tak", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btnExit2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(calculator.this, order.class));
            }
        });
        btnDeteleOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper databaseHelper = new DatabaseHelper(calculator.this);
                Obj_Model obj_model = databaseHelper.getOneObj(orderId.getText().toString());
                databaseHelper.deleteOneObj(obj_model);
                Toast.makeText(calculator.this, "Zamówienie usunięte", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(calculator.this, order.class));
            }
        });

        mkPDF.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                pdfmaker();
            }
        });

    }

    private void find(String s){
        DatabaseHelper databaseHelper = new DatabaseHelper(calculator.this);
        List<Item_Model>someone = databaseHelper.search(s);
        ArrayAdapter itemArrayAdapter = new ArrayAdapter<Item_Model>(calculator.this, android.R.layout.simple_list_item_1,someone);
        prompt.setAdapter(itemArrayAdapter);
    }
    public void ViewAll(){
        List<String>pyk=new ArrayList<>();
        DatabaseHelper databaseHelper=new DatabaseHelper(calculator.this);
        Integer price=0;
        for(int i=0;i<items.size();i++) {
            String[]a=items.get(i).split(":");
           String o= databaseHelper.getOne(a[0]).getName()+" x"+a[1]+" koszt: "+Integer.parseInt(a[1])*databaseHelper.getOne(a[0]).getPrice()+"zł";
           price+=Integer.parseInt(a[1])*databaseHelper.getOne(a[0]).getPrice();

            pyk.add(o);
        }
        ArrayAdapter ziu=new ArrayAdapter<String>(calculator.this,android.R.layout.simple_list_item_1,pyk);
        orderPrice.setText("Całkowity koszt: "+price.toString()+"zł");
        item_list.setAdapter(ziu);
    }

    private void style(final EditText et){
        et.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    et.setTextSize(25);
                }
                else{
                    et.setTextSize(20);
                    et.setBackgroundResource(android.R.color.transparent);
                }
            }
        });

    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void pdfmaker(){
        DatabaseHelper databaseHelper=new DatabaseHelper(calculator.this);
        Obj_Model obj_model=databaseHelper.getOneObj(orderId.getText().toString());
        PdfDocument document=new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(300,500,1).create();
        PdfDocument.Page page = document.startPage(pageInfo);
        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(20);
        canvas.drawText(obj_model.getName(),150,50,paint);
        paint.setTextSize(10);
        paint.setTextAlign(Paint.Align.LEFT);


        Integer price=0;
        int i;
        for(i=0;i<items.size();i++) {
            String[]a=items.get(i).split(":");
            String o= databaseHelper.getOne(a[0]).getName()+" x"+a[1]+"   |"+Integer.parseInt(a[1])*databaseHelper.getOne(a[0]).getPrice()+"zł";
            canvas.drawText(o, 20, 100+i*15, paint);
            canvas.drawLine(5,102+i*15,200,102+i*15,paint);
            price+=Integer.parseInt(a[1])*databaseHelper.getOne(a[0]).getPrice();
        }
        paint.setTextSize(12);
        i+=1;
        canvas.drawText("Wszystkich przedmiotów: "+items.size(), 20, 100+i*15, paint);
        canvas.drawLine(5,102+i*15,200,102+i*15,paint);
        canvas.drawText("Całkowity koszt: "+price.toString()+"zł", 20, 115+i*15, paint);
        canvas.drawLine(5,117+i*15,200,117+i*15,paint);


      //  canvas.drawText("Przedmiotów: "+items.size(),200,385,paint);
       // canvas.drawText("koszt: "+price.toString()+"zł",200,400,paint);




        document.finishPage(page);
        String directory_path = Environment.getExternalStorageDirectory().getPath()+"/zamówienia/";
        File file = new File(directory_path);
        if(!file.exists()){
            file.mkdirs();
        }
        String targetPdf = directory_path+obj_model.getName()+".pdf";
        File filePath = new File(targetPdf);
        try{
            document.writeTo(new FileOutputStream(filePath));
            Toast.makeText(this, "Plik znajdziesz w folderze Zamówienia", Toast.LENGTH_SHORT).show();

        }
        catch(IOException e){
            Log.e("main","error "+e.toString());
            Toast.makeText(calculator.this, "Something wrong: "+e.toString(), Toast.LENGTH_LONG).show();
        }
        document.close();

    }
}
