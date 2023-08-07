package com.stautnerindustries.lightswag3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request


private var buttonNames = arrayOf<String?>("1", "2", "3", "4", "5", "6", "7")

class ButtonsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.buttons_activity)
        var buttonsListView: ListView? = findViewById(R.id.buttonsListView)
        val buttons = arrayOfNulls<Button>(buttonNames.size)

        // Buttons zur ListView hinzufügen
        for (i in 0..buttons.size-1) {
            val button = Button(this)
            button.setText(buttonNames.get(i))
            button.setOnClickListener { v: View? ->
                Log.i("Button"+i, "Button " + buttonNames.get(i) + " was pressed.")
                GlobalScope.launch(Dispatchers.IO) {
                    sendHttpGetRequest(i)
                }
            }
            buttons[i] = button
        }
        buttonsListView?.adapter = object : ArrayAdapter<Button>(this, 0, buttons) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return buttons[position]!!
            }

        }

    }

    fun sendHttpGetRequest(id: Int) {
        val client = OkHttpClient()
        val baseUrl = getSharedPreferences("TODO", MODE_PRIVATE)
            .getString("dns","http://SwagBox3")
        val request = Request.Builder()
            .url("$baseUrl/buttons/$id")
            .build()

        client.newCall(request).execute().use { response ->
            GlobalScope.launch(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@ButtonsActivity, "Song started", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(this@ButtonsActivity, "Unexpected error. URL no available.", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}