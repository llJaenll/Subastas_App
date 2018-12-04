package com.example.consulting.subastas_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.SimpleAdapter
import com.example.consulting.subastas_app.models.Categoria
import com.example.consulting.subastas_app.models.SubastaProd
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_vender.*
import kotlinx.android.synthetic.main.card_categoria.view.*
import java.util.*
import android.preference.PreferenceManager
import android.content.SharedPreferences
import com.example.consulting.subastas_app.fragments.ListaCategoriaFragment
import com.example.consulting.subastas_app.others.goActivity
import kotlin.collections.ArrayList


open class ListProdActivity : AppCompatActivity() {
    //var myPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    //var cate = myPreferences.getString("idCat", "unknown")

    private val subastaStore = FirebaseFirestore.getInstance()
    private var subastaDB = subastaStore.collection("tbSubas").whereEqualTo("idCategoria","Automovil")
    private var subastaReg: ListenerRegistration? = null
    private val lista: ArrayList<SubastaProd> = ArrayList()
    private val lista1: ArrayList<Any> = ArrayList()
    private var listaTitulo: ArrayList<String> = ArrayList()
    private var listaImage: ArrayList<String> = ArrayList()
    private var listaDescripcion: ArrayList<String> = ArrayList()


    var listviewTitle = arrayOf("ListView Title 1", "ListView Title 2", "ListView Title 3", "ListView Title 4", "ListView Title 5", "ListView Title 6", "ListView Title 7", "ListView Title 8")

    var listviewImage = intArrayOf(R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario, R.drawable.usuario)

    var listviewShortDescription = arrayOf("Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description", "Android ListView Short Description")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_prod)
        /*val aList = ArrayList<HashMap<String, String>>()

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
        androidListView.setAdapter(simpleAdapter)*/
        cargarLista()
    }
    private fun cargarLista() :ArrayList<SubastaProd>{
        return object :ArrayList<SubastaProd>(){
            init {
                subastaReg=subastaDB.addSnapshotListener(object: EventListener,com.google.firebase.firestore.EventListener<QuerySnapshot>{
                    override fun onEvent(snap: QuerySnapshot?, ex: FirebaseFirestoreException?) {
                        ex?.let {
                            return
                        }
                        snap?.let {
                            clear()
                            listaTitulo.clear()
                            listaImage.clear()
                            listaDescripcion.clear()
                            it.documents.forEach { categ->

                                lista.add(SubastaProd(categ.data!!.values.elementAt(5).toString(),
                                        categ.data!!.values.elementAt(0).toString(),
                                        categ.data!!.values.elementAt(1).toString(),
                                        categ.data!!.values.elementAt(8).toString(),
                                        categ.data!!.values.elementAt(6).toString(),
                                        categ.data!!.values.elementAt(2).toString(),
                                        categ.data!!.values.elementAt(6).toString(),
                                        categ.data!!.values.elementAt(9).toString(),
                                        categ.data!!.values.elementAt(3).toString(),
                                        categ.data!!.values.elementAt(10).toString(),
                                        categ.data!!.values.elementAt(7).toString()

                                ))
                            }
                            //  var pe = it.toObjects(SubastaProd::class.java)
                            //     lista.addAll(pe)

                        lista.forEachIndexed { index, element ->
                            listaTitulo.add(element.nombreSubasta)
                            listaImage.add(element.imagenSubasta)
                            listaDescripcion.add(element.descripSubasta)
                        }
                        //llenarlista
                        val aList = ArrayList<HashMap<String, String>>()
                        for (i in 0..lista.size-1) {
                            val hm = HashMap<String, String>()
                            hm["listview_title"] = listaTitulo[i]
                            hm["listview_discription"] = listaDescripcion[i]
                            hm["listview_image"] = Integer.toString(listviewImage[i])
                            //Picasso.get().load(listaImage[i]).into(hm["listview_image"])
                            aList.add(hm)
                        }
                        val from = arrayOf("listview_image","listview_title", "listview_discription")
                        val to = intArrayOf(R.id.listview_img,R.id.listview_titulo, R.id.listview_descripcion)

                        val simpleAdapter = SimpleAdapter(baseContext, aList, R.layout.listview_activity, from, to)
                        val androidListView = findViewById<View>(R.id.list_view) as ListView
                        androidListView.setAdapter(simpleAdapter)
                    }
                    }
                })
            }
        }


    }
}
