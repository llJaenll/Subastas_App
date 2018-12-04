package com.example.consulting.subastas_app

import android.app.*
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.activity_vender.*
import java.util.*
import kotlin.collections.HashMap
import android.widget.ArrayAdapter
import android.widget.Spinner
import com.example.consulting.subastas_app.adapters.CategoriaAdapter
import com.example.consulting.subastas_app.models.Categoria
import com.example.consulting.subastas_app.others.goActivity
import com.google.firebase.firestore.FirebaseFirestoreException
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.QuerySnapshot
import kotlin.collections.ArrayList


class VenderActivity : AppCompatActivity() {

    private val categoriaStore = FirebaseFirestore.getInstance()
    private var categoriaDB = categoriaStore.collection("tbCate").orderBy("nomCat")
    private var categoriaReg: ListenerRegistration? = null
    private val lista: ArrayList<Categoria> = ArrayList()
    private lateinit var adaptador: CategoriaAdapter

    var mRootReference =FirebaseFirestore.getInstance()
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val user = mAuth.currentUser!!
    var storage = FirebaseStorage.getInstance()
    var storageRef = storage.reference
    private val GALLERY_INTENT=1
    private var urlGlobalProd:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_vender)
        btnhora.setOnClickListener {
            mostrarHora()
        }
        btnFecha.setOnClickListener {
            mostrarFecha()
        }
        //mRootReference = FirebaseFirestore.getInstance()
        btnSubirDatos.setOnClickListener {
            subirDatos()
        }
        btnSubirFoto.setOnClickListener {
            subirFoto()
        }
        cargarCombo()
    }
    fun cargarCombo(){
        categoriaReg=categoriaDB.addSnapshotListener(object: EventListener,com.google.firebase.firestore.EventListener<QuerySnapshot>{
            override fun onEvent(snap: QuerySnapshot?, ex: FirebaseFirestoreException?) {
                ex?.let {

                    return
                }
                snap?.let {
                    lista.clear()
                    val pe = it.toObjects(Categoria::class.java)
                    lista.addAll(pe)
                    var lista2:ArrayList<String> = ArrayList()
                    lista.forEachIndexed { index, element ->
                        lista2.add(element.nomCat)
                    }

                    spCategoria.adapter=ArrayAdapter<String>(this@VenderActivity,android.R.layout.simple_spinner_dropdown_item,lista2)

                }
            }

        })
    }
    fun subirFoto(){
        intent = Intent(Intent.ACTION_PICK)
        intent.setType("image/*")
        startActivityForResult(intent,GALLERY_INTENT)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)

        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Subiendo Foto")
        progressDialog.show()
        if (requestCode == GALLERY_INTENT && resultCode == Activity.RESULT_OK && data != null && data.data != null) {

            val file = data.data

            var filePath = storageRef.child("productos").child(file.lastPathSegment)


            filePath.putFile(file).addOnSuccessListener {
                progressDialog.dismiss()
            }.addOnCompleteListener{
                it.result!!.storage.downloadUrl.addOnSuccessListener {
                    urlGlobalProd = it.toString()
                }
            }


        }

    }

    fun subirDatos(){
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Guardando...")
        progressDialog.show()
        val subasta = HashMap<String,Any>()
        val c = Calendar.getInstance()
        val dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = c.get(Calendar.MONTH)
        val anio = c.get(Calendar.YEAR)
        val hora = c.get(Calendar.HOUR_OF_DAY)
        val minutos = c.get(Calendar.MINUTE)
        val segundo = c.get(Calendar.SECOND)
        val fechaIni="$dia/$mes/$anio"
        val horaIni= "$hora:$minutos:$segundo"
        var descripcion = txtDescripcion.text.toString()
        var fechaFin = dtpFechaFin.text.toString()
        var horaFin = tmHora.text.toString()
        var estado = "1"
        var idCat = spCategoria.selectedItem.toString()
        var imagen = urlGlobalProd
        var titulo = txtTitulo.text.toString()
        var precio = txtPrecio.text.toString()
        var vendedor = user.email.toString()

        subasta.put("descripSubasta",descripcion)
        subasta.put("fecFin ",fechaFin)
        subasta.put("fecIni ",fechaIni)//
        subasta.put("horaFin",horaFin)
        subasta.put("horaInicio",horaIni)//
        subasta.put("idCategoria",idCat)
        subasta.put("idEstadoSubasta ",estado)
        subasta.put("idVendedor ",vendedor)
        subasta.put("imagenSubasta  ",imagen)
        subasta.put("nombreSubasta ",titulo)
        subasta.put("preBase  ",precio)
        mRootReference?.collection("tbSubas")!!.document().set(subasta).addOnSuccessListener {
            goActivity<MainActivity> {  }
            progressDialog.dismiss()
        }


        //mRootReference?.collection("Subasta")!!.
        //mRootReference?.collection("Subasta")!!.push().setValue(subasta)
    }

    fun mostrarFecha(){
        val c = Calendar.getInstance()
        val dia = c.get(Calendar.DAY_OF_MONTH)
        val mes = c.get(Calendar.MONTH)
        val anio = c.get(Calendar.YEAR)
        val datePickerDialog =
                DatePickerDialog(this, DatePickerDialog.OnDateSetListener {
                    view, year, monthOfYear, dayOfMonth -> dtpFechaFin.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year)
                }, dia, mes, anio)
        datePickerDialog.show()
    }
    fun mostrarHora(){
        val c = Calendar.getInstance()
        val hora = c.get(Calendar.HOUR_OF_DAY)
        val minutos = c.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute -> tmHora.setText(hourOfDay.toString() + ":" + minute) }, hora, minutos, false)
        timePickerDialog.show()
    }
}
