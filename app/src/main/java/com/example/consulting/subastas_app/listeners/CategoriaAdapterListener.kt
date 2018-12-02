package com.example.consulting.subastas_app.listeners

import com.example.consulting.subastas_app.models.Categoria

interface CategoriaAdapterListener{
    fun onClick(categoria: Categoria, pos:Int)
}