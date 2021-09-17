package com.sahil.fragmentsdatatransferapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdReceiver
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import com.sahil.fragmentsdatatransferapplication.data.NotificationData
import com.sahil.fragmentsdatatransferapplication.data.PushNotification
import com.sahil.fragmentsdatatransferapplication.data.RetrofitInstance
import com.sahil.fragmentsdatatransferapplication.services.FirebaseService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

const val TOPICS = "/topics/myTopic"
class MainActivity : AppCompatActivity(){

    private  val TAG = "MainActivity"

    var button:Button?=null
    var et_tittle:EditText?=null
    var et_message:EditText?=null
    var et_token:EditText?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        FirebaseMessaging.getInstance().subscribeToTopic(TOPICS)
        //findids
        button = findViewById(R.id.button)
        et_tittle = findViewById(R.id.et_tittle)
        et_message = findViewById(R.id.et_message)
        et_token =findViewById(R.id.et_token)

        val token:String = FirebaseInstanceId.getInstance().token.toString()


        et_token!!.setText(token)
        button!!.setOnClickListener(View.OnClickListener {
            if(et_tittle!!.text.toString().isNotEmpty() || et_message!!.text.toString().isNotEmpty()){
                if(et_token!!.text.toString().isEmpty()) {
                    PushNotification(
                        NotificationData(et_tittle!!.text.toString(), et_message!!.text.toString()),
                        TOPICS
                    ).also {
                        sendNotification(it)
                    }
                }
                else{
                    PushNotification(
                        NotificationData(et_tittle!!.text.toString(), et_message!!.text.toString()),
                        et_token!!.text.toString()
                    ).also {
                        sendNotification(it)
                    }
                }

            }
        })



    }
    private fun sendNotification(notification: PushNotification) = CoroutineScope(Dispatchers.IO).launch {
        try {
            val response = RetrofitInstance.api.postNotificaion(notification)
            if(response.isSuccessful) {
                Log.d(TAG, "Response: ${Gson().toJson(response.body())}")
            } else {
                Log.e(TAG, response.errorBody().toString())
            }
        } catch(e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

}