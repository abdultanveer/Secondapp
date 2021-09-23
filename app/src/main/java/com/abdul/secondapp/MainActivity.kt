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
import androidx.core.app.ActivityCompat.requestPermissions







class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var listview:ListView = findViewById(R.id.cpList)
        val uriSms: Uri = Uri.parse("content://sms/inbox") //url
        onPermissionCallBack?.let { getReadSMSPermission(it) }
            val cursor: Cursor? = getContentResolver().query(uriSms, null, null, null, null)

            var adapter: CursorAdapter = SimpleCursorAdapter(
                this,
                android.R.layout.simple_list_item_2, //row layout
                cursor,    //data cursor
                arrayOf("body", "address"), //column names
                intArrayOf(android.R.id.text1, android.R.id.text2)
            ) //textview id
            listview.adapter = adapter


    }

    private fun checkReadSMSPermission(): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.READ_SMS) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                false
            }
        } else {
            true
        }
    }

    var onPermissionCallBack: RequestPermissionAction? = null
    private val REQUEST_READ_SMS_PERMISSION = 3004


    fun getReadSMSPermission(monPermissionCallBack: RequestPermissionAction) {
        onPermissionCallBack = monPermissionCallBack
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) { //if the app is on marshmellow invoke runtime permissions
            
            if (!checkReadSMSPermission()) {
                requestPermissions(
                    arrayOf(Manifest.permission.READ_SMS),
                    REQUEST_READ_SMS_PERMISSION
                )
                return
            }
        }
        if (monPermissionCallBack != null) monPermissionCallBack.permissionGranted()
    }

    interface RequestPermissionAction {
        fun permissionDenied()
        fun permissionGranted()
    }



    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (REQUEST_READ_SMS_PERMISSION == requestCode) {
                // TODO Request Granted for READ_SMS.
                println("REQUEST_READ_SMS_PERMISSION Permission Granted")
            }
            if (onPermissionCallBack != null) onPermissionCallBack!!.permissionGranted()
        } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
            if (REQUEST_READ_SMS_PERMISSION == requestCode) {
                // TODO REQUEST_READ_SMS_PERMISSION Permission is not Granted.
                // TODO Request Not Granted.


                // This code is for get permission from setting.
                //final Intent i = new Intent();
                //i.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                //i.addCategory(Intent.CATEGORY_DEFAULT);
                //i.setData(Uri.parse("package:" + getPackageName()));
                //i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //i.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //i.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                //startActivity(i);
            }
            if (onPermissionCallBack != null) onPermissionCallBack!!.permissionDenied()
        }
    }
}