package com.example.datnguyen.test_contact_sqlite;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    ArrayList<Contact> contacts;
    ContactAdapter adapter;
    int REQUEST_CODE = 123;
    int position;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHandler(this);
        init();
        registerForContextMenu(listView);
    }

    private void init() {
        listView = findViewById(R.id.lvContact);
        contacts = db.getAllContacts();
        adapter = new ContactAdapter(this,R.layout.contact_row,contacts);
        listView.setAdapter(adapter);
    }

    // Create option menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.addMenu)
        {
            Intent intent = new Intent(MainActivity.this,Add_Contact.class);
            intent.putExtra("flag","add");
            startActivityForResult(intent,REQUEST_CODE);
        }
        return super.onOptionsItemSelected(item);
    }

    // Create context menu

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.context_menu,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.editMenu)
        {
            Intent intent = new Intent(MainActivity.this,Add_Contact.class);
            // index of Listview
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            position = adapterContextMenuInfo.position;
            Contact contact = contacts.get(position);

            intent.putExtra("flag","edit");
            intent.putExtra("contact",contact);
            startActivityForResult(intent,REQUEST_CODE);


        }
        if (id == R.id.deleteMenu)
        {
            // index of Listview
            AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            position = adapterContextMenuInfo.position;
            showAlertDialog(position);
        }
        return super.onContextItemSelected(item);
    }

    private void showAlertDialog(final int index) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Warning");
        builder.setIcon(android.R.drawable.ic_delete);
        builder.setMessage("Are you sure to delete this contact?" +contacts.get(index).getName());
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                db.deleteContact(contacts.get(index).getId());
                contacts.remove(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Deleted!!", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNeutralButton("Skip", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();

    }
    // Get Results

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == REQUEST_CODE)
        {
            // add function
            if (resultCode == 1)
            {
                Contact contact = (Contact) data.getSerializableExtra("contact");
                contacts.add(contact);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Added!!", Toast.LENGTH_SHORT).show();
            }
            //edit function
            if (resultCode == 2)
            {
                Contact contact = (Contact) data.getSerializableExtra("contact");
                contacts.set(position,contact);
                adapter.notifyDataSetChanged();
                Toast.makeText(this, "Edited!!", Toast.LENGTH_SHORT).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
