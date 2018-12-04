package com.example.consulting.subastas_app.models

data class SubastaProd(val descripSubasta:String="", var fecFin:String, val fecIni:String="", val horaFin:String="", val horaInicio:String="",
                       val idCategoria:String="",
                       val idEstadoSubasta:String="",
                       val imagenSubasta:String="",
                       val nombreSubasta:String="",
                       val idVendedor:String="",
                       val preBase:String=""
                       )
