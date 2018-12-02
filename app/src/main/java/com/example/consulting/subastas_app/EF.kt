package com.example.consulting.subastas_app

import android.app.Activity
import android.content.Intent
import android.widget.Toast


fun Activity.toast(m:CharSequence) = Toast.makeText(this,m,Toast.LENGTH_SHORT).show()

inline fun <reified T:Activity>Activity.goActivity(noinline ext:Intent.()-> Unit = {}){
    val intent = Intent(this,T::class.java)
    intent.ext()
    startActivity(intent)
}