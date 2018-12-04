package com.example.consulting.subastas_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.widget.LinearLayout
import com.example.consulting.subastas_app.fragments.ListaCategoriaFragment
import com.example.consulting.subastas_app.fragments.homeFragment
import com.example.consulting.subastas_app.others.goActivity
import com.example.consulting.subastas_app.others.toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.view.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.navheader.*
import kotlinx.android.synthetic.main.navheader.view.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{
    private val mAuth: FirebaseAuth by lazy { FirebaseAuth.getInstance() }
    private val user = mAuth.currentUser!!
    lateinit var tb: Toolbar
    lateinit var nave: NavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instanciar Toolbar
        tb = barra_navegacion as Toolbar
        tb.title = getString(R.string.app_name)
        nave = nav as NavigationView
        //modificar el nav con valores del correo
        nave.getHeaderView(0).txtNombreNav.text = user.displayName
        nave.getHeaderView(0).txtCorreoNav.text = user.email

        //txtCorreoNav.text = "ddd"//user.email
        //txtNombreNav.text ="ddd"// user.displayName

        //Instanciar navigation
        setupNavigation()
        setupFragments(homeFragment())
        nav.menu.getItem(0).isChecked = true
    }

    private fun setupNavigation() {
        val toogle = ActionBarDrawerToggle(this,drawer, tb,R.string.drawer_abierto,R.string.drawer_cerrado)
        toogle.isDrawerIndicatorEnabled=true
        drawer.addDrawerListener(toogle)
        toogle.syncState()
        nav.setNavigationItemSelectedListener(this)

    }

    private fun setupFragments(fragment: Fragment){
        supportFragmentManager.beginTransaction().replace(R.id.frame,fragment).commit()
    }



    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.home->setupFragments(homeFragment())
            R.id.categorias->setupFragments(ListaCategoriaFragment())
            R.id.perfil->toast("Estás en el perfil")
            R.id.cerrarsesion->cerrarSession()
            R.id.vender->goActivity<VenderActivity>()

        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }
    fun cerrarSession(){
        mAuth.signOut()
       goActivity<LoginActivity>()
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

}

