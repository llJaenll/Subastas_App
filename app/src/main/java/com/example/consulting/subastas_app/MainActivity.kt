package com.example.consulting.subastas_app

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import com.example.consulting.subastas_app.fragments.ListaCategoriaFragment
import com.example.consulting.subastas_app.fragments.homeFragment
import com.example.consulting.subastas_app.others.toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener{

    lateinit var tb: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Instanciar Toolbar
        tb = barra_navegacion as Toolbar
        tb.title = getString(R.string.app_name)

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
        }
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START))
            drawer.closeDrawer(GravityCompat.START)
        else
            super.onBackPressed()
    }

}

