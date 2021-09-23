package com.abdul.secondapp

import android.Manifest
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CursorAdapter
import android.widget.ListView
import android.widget.SimpleCursorAdapter
import android.content.pm.PackageManager

import android.os.Build
import android.provider.CallLog
import androidx.core.app.ActivityCompat.requestPermissions







class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var listview:ListView = findViewById(R.id.cpList)
        val uriSms: Uri = Uri.parse(CallLog.Calls.CONTENT_URI.toString())
            //"content://sms/inbox") //url
            val cursor: Cursor? = getContentResolver().query(uriSms, null, null, null, null)

            var adapter: CursorAdapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_1, //row layout
                cursor,    //data cursor
                arrayOf(            android.provider.CallLog.Calls.NUMBER),
                    //"body", "address"), //column names
                intArrayOf(android.R.id.text1)
            ) //textview id
            listview.adapter = adapter


    }



}