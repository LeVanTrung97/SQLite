package com.example.datnguyen.test_contact_sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "contact_demo.sql";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "contact";

    // columns
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone";

    //query
    private String create_table = "CREATE TABLE "+TABLE_NAME+" ("
            +ID +" INTEGER PRIMARY KEY, "+
            NAME +" TEXT, "+
            PHONE+" TEXT)";
    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(create_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
    //insert
    public long addContact(Contact contact){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,contact.getName());
        values.put(PHONE,contact.getPhone());
        long value = sqLiteDatabase.insert(TABLE_NAME,null,values);
        sqLiteDatabase.close();
        return value;
    }
    // update
    public void updateContact(Contact contact){
        SQLiteDatabase sqLiteDatabase =getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME,contact.getName());
        values.put(PHONE,contact.getPhone());
        sqLiteDatabase.update(TABLE_NAME, values, ID + " = ?", new String[] { String.valueOf(contact.getId()) });
        sqLiteDatabase.close();

    }
    //delete
    public void deleteContact(int contact){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        sqLiteDatabase.delete(TABLE_NAME,ID+" =?",new String[]{String.valueOf(contact)});
        sqLiteDatabase.close();
    }
    //get all contacts
    public ArrayList<Contact> getAllContacts(){
         ArrayList<Contact> contacts = new ArrayList<>();
         String selectQuery = "SELECT * FROM "+TABLE_NAME;
         SQLiteDatabase sqLiteDatabase = getReadableDatabase();
         Cursor cursor = sqLiteDatabase.rawQuery(selectQuery,null);
         cursor.moveToFirst();
         while (cursor.isAfterLast() == false)
         {
                Contact contact = new Contact(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
                contacts.add(contact);
                cursor.moveToNext();
         }
         return contacts;
    }
}
