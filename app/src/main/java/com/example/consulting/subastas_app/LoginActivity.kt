package com.example.consulting.subastas_app

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import android.app.ProgressDialog
import com.google.firebase.auth.FirebaseAuthUserCollisionException


class LoginActivity : AppCompatActivity() {

    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private  val mGsiC:GoogleSignInClient by lazy { getGoogleSignInCliente() }
    private val AUTH = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        btnLoginGoogle.setOnClickListener {
            val singInClient = mGsiC.signInIntent
            startActivityForResult(singInClient,AUTH)
        }
        btnRegistrar.setOnClickListener {
            registrarUsuario()
        }
        btnIngresar.setOnClickListener {
            loguearUsuario()
        }
    }
    fun getGoogleSignInCliente():GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        return GoogleSignIn.getClient(this,gso)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when(requestCode){
            AUTH -> {
                val resultado = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (resultado.isSuccess)
                    loginGoogleFirebase(resultado.signInAccount!!)
            }
        }
    }
    private fun loginGoogleFirebase(cuenta:GoogleSignInAccount){
        val credenciales = GoogleAuthProvider.getCredential(cuenta.idToken,null)

        mAuth.signInWithCredential(credenciales).addOnCompleteListener(this){
            if (it.isSuccessful){
                toast("Bienvenido ${mAuth.currentUser?.displayName?.toUpperCase()}")
                mGsiC.signOut()
                goActivity<MainActivity>{
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
            }
        }

    }
    //Registrar usuario
    fun registrarUsuario(){
        var email = txtcorreo.text.toString().trim()
        var password = txtcorreo.text.toString().trim()
        if(TextUtils.isEmpty(email)){
            toast("Se debe ingresar un email")
            return
        }
        if(TextUtils.isEmpty(password)){
            toast("Falta ingresar la contraseña")
            return
        }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Realizando registro en linea...")
        progressDialog.show()
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    //checking if success
                    if (it.isSuccessful) {
                        toast("Se ha registrado el usuario con el email:"+email)
                    } else if (it.exception is FirebaseAuthUserCollisionException) {
                            toast("Ese usuario ya existe ")
                    } else {
                            toast("No se pudo registrar el usuario ")
                    }
                    progressDialog.dismiss()
                }

    }
    //loguearsecon correo
    fun loguearUsuario(){
        var email = txtcorreo.text.toString().trim()
        var password = txtcorreo.text.toString().trim()
        if(TextUtils.isEmpty(email)){
            toast("Se debe ingresar un email")
            return
        }
        if(TextUtils.isEmpty(password)){
            toast("Falta ingresar la contraseña")
            return
        }
        val progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Realizando registro en linea...")
        progressDialog.show()
        //loguear usuario
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) {
                    //checking if success
                    if (it.isSuccessful) {
                        toast("Bienvenido"+email)
                        goActivity<MainActivity>{
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        }
                    } else if (it.exception is FirebaseAuthUserCollisionException) {
                        toast("Ese usuario ya existe ")
                    } else {
                        toast("El correo no existe")
                    }
                    progressDialog.dismiss()
                }
    }
}
