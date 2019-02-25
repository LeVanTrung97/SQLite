package com.example.datnguyen.test_contact_sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Add_Contact extends AppCompatActivity {
    EditText edtName, edtPhone;
    Button btnSave, btnCancel;
    String flag;
    int idContact;
    DatabaseHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__contact);
        db = new DatabaseHandler(this);
        init();
        //Get Intent
        Intent intent = getIntent();
        flag = intent.getStringExtra("flag");

        // Handle either edit or add
        if (flag.equals("edit"))
        {
            Contact contact = (Contact) intent.getSerializableExtra("contact");
            edtName.setText(contact.getName());
            edtPhone.setText(contact.getPhone());
            idContact = contact.getId();
        }
        // Button Cancel
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Button Save
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                if (name.isEmpty() || phone.isEmpty()){
                    Toast.makeText(Add_Contact.this, "Please fill out all the fields!!", Toast.LENGTH_SHORT).show();
                }
                else if (flag.equals("add"))
                {
                    Contact contact = new Contact(name,phone);
                    int id = (int) db.addContact(contact);
                    contact.setId(id);
                    Intent i = new Intent();
                    i.putExtra("contact",contact);
                    setResult(1,i);
                    finish();
                }
                else if (flag.equals("edit"))
                {
                    Contact contact = new Contact(idContact,name,phone);
                    db.updateContact(contact);
                    Intent i = new Intent();
                    i.putExtra("contact", contact);
                    setResult(2,i);
                    finish();
                }
            }
        });
    }

    private void init() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        btnSave = findViewById(R.id.btnSave);
        btnCancel = findViewById(R.id.btnCancel);
    }

}
