package com.havverton.cinemapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val detailsButton = findViewById<Button>(R.id.toDetailsBtn)

        detailsButton.setOnClickListener {
            val intent = Intent(this, MovieDetailsActivity::class.java)
        }
    }



}