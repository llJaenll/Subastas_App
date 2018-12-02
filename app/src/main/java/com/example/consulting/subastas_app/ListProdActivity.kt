package com.example.consulting.subastas_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ListView
import android.widget.SimpleAdapter



class ListProdActivity : AppCompatActivity() {

    var listviewTitle = arrayOf("ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4", "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8")


    var listviewImage = intArrayOf(R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario)

    var listviewShortDescription = arrayOf("Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_prod)
        val aList = ArrayList<HashMap<String, String>>()

        for (i in 0..7) {
            val hm = HashMap<String, String>()
            hm["listview_title"] = listviewTitle[i]
            hm["listview_discription"] = listviewShortDescription[i]
            hm["listview_image"] = Integer.toString(listviewImage[i])
            aList.add(hm)
        }

        val from = arrayOf("listview_image", "listview_title", "listview_discription")
        val to = intArrayOf(R.id.listview_img, R.id.listview_titulo, R.id.listview_descripcion)

        val simpleAdapter = SimpleAdapter(baseContext, aList, R.layout.listview_activity, from, to)
        val androidListView = findViewById<View>(R.id.list_view) as ListView
        androidListView.setAdapter(simpleAdapter)

    }
}
