package com.example.consulting.subastas_app.adapters

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.example.consulting.subastas_app.R
import com.example.consulting.subastas_app.others.inflar
import com.example.consulting.subastas_app.listeners.CategoriaAdapterListener
import com.example.consulting.subastas_app.models.Categoria
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.card_categoria.view.*

class CategoriaAdapter(private val cat:List<Categoria>, private  val listener:CategoriaAdapterListener)
    :RecyclerView.Adapter<CategoriaAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
            =ViewHolder(parent.inflar(R.layout.card_categoria))

    override fun getItemCount()
            = cat.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
            =holder.bind(cat[position],listener)

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        fun bind(c:Categoria,listen:CategoriaAdapterListener)
            = with(itemView){
            tvNombreCategoria.text = c.nomCat
            Picasso.get().load(c.imgCat).into(itemView.ivImgCategoria)

            setOnClickListener { 
                listen.onClick(c,adapterPosition)
            }
        }
    }
}