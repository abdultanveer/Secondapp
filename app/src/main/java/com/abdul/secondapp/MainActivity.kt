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
import android.os.PersistableBundle
import android.provider.CallLog
import android.util.Log
import android.view.View
import androidx.core.app.ActivityCompat.requestPermissions
import com.google.firebase.firestore.FirebaseFirestore
import androidx.annotation.NonNull

import com.google.android.gms.tasks.OnFailureListener

import com.google.firebase.firestore.DocumentReference

import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.firestore.QueryDocumentSnapshot

import com.google.firebase.firestore.QuerySnapshot

import com.google.android.gms.tasks.OnCompleteListener
















class MainActivity : AppCompatActivity() {
    var TAG = MainActivity::class.java.simpleName
    lateinit var db:  FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseFirestore.setLoggingEnabled(true);

        db = FirebaseFirestore.getInstance()

       // setupListView()


    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
    }


    private fun setupListView() {
        //var listview: ListView = findViewById(R.id.cpList)
        val uriSms: Uri = Uri.parse(CallLog.Calls.CONTENT_URI.toString())
        //"content://sms/inbox") //url
        val cursor: Cursor? = getContentResolver().query(uriSms, null, null, null, null)

        var adapter: CursorAdapter = SimpleCursorAdapter(
            this,
            android.R.layout.simple_list_item_1, //row layout
            cursor,    //data cursor
            arrayOf(CallLog.Calls.NUMBER),
            //"body", "address"), //column names
            intArrayOf(android.R.id.text1)
        ) //textview id
        //listview.adapter = adapter
    }

    fun firestoreHandler(view: View) {
        when(view.id){
            R.id.btnSend ->  {
                sendDataFirestore()
            }
            R.id.btnGet -> {
                getDataFireStore()
            }
        }
    }

    private fun getDataFireStore(){
        db.collection("users")
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d(TAG, document.id + " => " + document.data)
                    }
                } else {
                    Log.w(TAG, "Error getting documents.", task.exception)
                }
            }
    }

    private fun sendDataFirestore() {
        // Create a new user with a first and last name
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e -> Log.w(TAG, "Error adding document", e) }
    }


}