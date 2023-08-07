package com.stautnerindustries.lightswag3

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL


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
            }
            buttons[i] = button
        }
        buttonsListView?.adapter = object : ArrayAdapter<Button>(this, 0, buttons) {

            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return buttons[position]!!
            }

        }

    }

    fun sendHttpRequest(urlString: String): String {
        val url = URL(urlString)
        val connection = url.openConnection() as HttpURLConnection

        try {
            val reader = BufferedReader(InputStreamReader(connection.inputStream))
            val response = StringBuilder()
            var line: String?

            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }

            return response.toString()
        } finally {
            connection.disconnect()
        }
    }
}