package com.example.consulting.subastas_app.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.consulting.subastas_app.Dummy
import com.example.consulting.subastas_app.R
import com.example.consulting.subastas_app.adapters.CategoriaAdapter
import com.example.consulting.subastas_app.listeners.CategoriaAdapterListener
import com.example.consulting.subastas_app.models.Categoria
import com.example.consulting.subastas_app.others.toast
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.fragment_lista_categoria.view.*
import java.util.*

class ListaCategoriaFragment : Fragment() {

    private lateinit var  recicler: RecyclerView
    private lateinit var adaptador: CategoriaAdapter

    private val lm by lazy { GridLayoutManager(context,2) }
    private val lista: ArrayList<Categoria> = ArrayList()

    //Libreria Firestore
    private val categoriaStore = FirebaseFirestore.getInstance()
    private var categoriaDB = categoriaStore.collection("tbCategoria")
    private var categoriaReg: ListenerRegistration? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_lista_categoria,container,false)
        recicler = root.recyclerCategorias as RecyclerView
        setupRecycler()
        getCategorias()
        return root
    }

    private fun setupRecycler(){
        recicler.setHasFixedSize(true)
        recicler.itemAnimator = DefaultItemAnimator()
        recicler.layoutManager = lm
        adaptador = CategoriaAdapter(lista,object : CategoriaAdapterListener {
            override fun onClick(categoria: Categoria, pos: Int) {
                var intent = Intent(context, Dummy::class.java)
                startActivity(intent)
            }
        })
        recicler.adapter = adaptador
    }

    private fun getCategorias(){
        categoriaReg=categoriaDB.addSnapshotListener(object: EventListener,com.google.firebase.firestore.EventListener<QuerySnapshot>{
            override fun onEvent(snap: QuerySnapshot?, ex: FirebaseFirestoreException?) {
                ex?.let {
                    activity?.toast("Error")
                    return
                }
                snap?.let {
                    lista.clear()
                    val pe = it.toObjects(Categoria::class.java)
                    lista.addAll(pe)
                    adaptador.notifyDataSetChanged()
                }
            }

        })
    }

    override fun onDestroyView() {
        categoriaReg?.remove()
        super.onDestroyView()
    }



}
