package com.example.datnguyen.test_contact_sqlite;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    Context context;
    int layout;
    ArrayList<Contact> contacts;

    public ContactAdapter(Context context, int layout, ArrayList<Contact> contacts) {
        this.context = context;
        this.layout = layout;
        this.contacts = contacts;
    }

    @Override
    public int getCount() {
        return contacts.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    private class ViewHolder{
            ImageView imgContact;
            TextView txtName, txtPhone;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(layout,null);
            viewHolder.imgContact = convertView.findViewById(R.id.imgContact);
            viewHolder.txtName = convertView.findViewById(R.id.txtName);
            viewHolder.txtPhone = convertView.findViewById(R.id.txtPhone);
            convertView.setTag(viewHolder);
        }
        else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Contact contact = contacts.get(position);
        viewHolder.imgContact.setImageResource(R.drawable.contacts_icon);
        viewHolder.txtPhone.setText(contact.getPhone());
        viewHolder.txtName.setText(contact.getName());
        return convertView;
    }
}
