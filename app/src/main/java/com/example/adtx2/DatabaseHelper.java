package com.example.adtx2;

import android.content.ClipData;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ListView;
import android.widget.Switch;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper{

    public static final String ITEM_TABLE = "ITEM_TABLE";
    public static final String COLUMN_ITEM_NAME = "ITEM_NAME";
    public static final String COLUMN_ITEM_PRICE = "ITEM_PRICE";
    public static final String COLUMN_ID = "ID";
    public static final String OBJ_TABLE = "OBJ_TABLE";
    public static final String COLUMN_OBJ_NAME = "OBJ_NAME";
    public static final String COLUMN_OBJ_SIZE = "OBJ_SIZE";
    public static final String COLUMN_OBJ_ITEMS = "COLUMN_OBJ_ITEMS";



    public DatabaseHelper(@Nullable Context context) {
        super(context, "item.db",null ,1 );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable= "CREATE TABLE " + ITEM_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_ITEM_NAME + " TEXT," + COLUMN_ITEM_PRICE + " INTEGER)";
        String createTable2="CREATE TABLE " + OBJ_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_OBJ_NAME + " TEXT," + COLUMN_OBJ_SIZE + " INTEGER, "+COLUMN_OBJ_ITEMS+" TEXT)";
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


        public boolean addOne(Item_Model item_model){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_ITEM_NAME,item_model.getName());
        cv.put(COLUMN_ITEM_PRICE,item_model.getPrice());


            long insert = db.insert(ITEM_TABLE, null, cv);
            if(insert==-1){
                return false;
        }
else {
                return true;
            }
        }
    public boolean addOneObj(Obj_Model obj_model){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_OBJ_NAME,obj_model.getName());
        cv.put(COLUMN_OBJ_SIZE,obj_model.getSize());
        cv.put(COLUMN_OBJ_ITEMS,obj_model.getItems());


        long insert = db.insert(OBJ_TABLE, null, cv);
        if(insert==-1){
            return false;
        }
        else {
            return true;
        }
    }




        public List<Item_Model> getEveryone(){
        List<Item_Model>returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ITEM_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString,null);

            if(cursor.moveToFirst()){

                do{
                    int itemID=cursor.getInt(0);
                    String itemName = cursor.getString(1);
                    int itemPrice = cursor.getInt(2);

                    Item_Model newItem = new Item_Model(itemID,itemName,itemPrice);
                    returnList.add(newItem);

                } while(cursor.moveToNext());

            }

            else{}
            cursor.close();
            db.close();

            return returnList;
        }


    public List<Obj_Model> getEveryObj(){
        List<Obj_Model>returnList = new ArrayList<>();
        String queryString = "SELECT * FROM "+ITEM_TABLE;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString,null);

        if(cursor.moveToFirst()){

            do{
                int ObjID=cursor.getInt(0);
                String ObjName = cursor.getString(1);
                int ObjSize = cursor.getInt(2);
                String ObjItems = cursor.getString(3);


                Obj_Model newItem = new Obj_Model(ObjID,ObjName,ObjSize,ObjItems);
                returnList.add(newItem);

            } while(cursor.moveToNext());

        }

        else{}
        cursor.close();
        db.close();

        return returnList;
    }







        public List<Item_Model> search(String s) {

            List<Item_Model> returnList = new ArrayList<>();
            String queryString = "SELECT * FROM " + ITEM_TABLE + " WHERE "+ COLUMN_ITEM_NAME+" LIKE "+"'%"+s+"%'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(queryString, null);

            if (cursor.moveToFirst()) {

                do {
                    int itemID = cursor.getInt(0);
                    String itemName = cursor.getString(1);
                    int itemPrice = cursor.getInt(2);

                    Item_Model newItem = new Item_Model(itemID, itemName, itemPrice);
                    returnList.add(newItem);

                } while (cursor.moveToNext());

            }

            else{}
            cursor.close();
            db.close();

            return returnList;
        }




        public boolean deleteOne(Item_Model item_model){

        SQLiteDatabase db=this.getWritableDatabase();
        String queryString = "DELETE FROM "+ITEM_TABLE+ " WHERE "+COLUMN_ID+" = "+item_model.getId();

            Cursor cursor = db.rawQuery(queryString, null);
if(cursor.moveToFirst()){
    return true;
}
else{
    return false;
}

        }

    public boolean deleteOneObj(Obj_Model obj_model){

        SQLiteDatabase db=this.getWritableDatabase();
        String queryString = "DELETE FROM "+OBJ_TABLE+ " WHERE "+COLUMN_ID+" = "+obj_model.getId();

        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

    }







        public boolean modOne(Item_Model item_model){
        SQLiteDatabase db=this.getWritableDatabase();
        String queryString = "UPDATE "+ITEM_TABLE+" SET "+COLUMN_ITEM_NAME+ " = " +'"'+item_model.getName()+'"'+","+ COLUMN_ITEM_PRICE +" = "+'"'+item_model.getPrice()+'"'+" WHERE "+COLUMN_ID+ "="+item_model.getId();
                Cursor cursor = db.rawQuery(queryString,null);

                if(cursor.moveToFirst()){
                    return true;
                }
                else{
                    return false;
                }


        }

}
