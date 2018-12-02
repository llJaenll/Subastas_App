package com.example.consulting.subastas_app

import android.graphics.Color
import android.os.Bundle
import com.hololo.tutorial.library.TutorialActivity
import com.example.consulting.subastas_app.others.goActivity
import com.hololo.tutorial.library.Step


class InicioActivity : TutorialActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //primer slider
        addFragment(Step.Builder().setTitle("This is fondo 1")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#a01616")) // int background color
                .setDrawable(R.drawable.fondo1) // int top drawable
                .setSummary("This is summary")
                .build())
        //segundo slider
        addFragment(Step.Builder().setTitle("This is fondo 2")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#a01616")) // int background color
                .setDrawable(R.drawable.fondo2) // int top drawable
                .setSummary("This is summary")
                .build())
        //tercer slider
        addFragment(Step.Builder().setTitle("This is fondo 3")
                .setContent("This is content")
                .setBackgroundColor(Color.parseColor("#a01616")) // int background color
                .setDrawable(R.drawable.fondo3) // int top drawable
                .setSummary("This is summary")
                .build())
        setPrevText("Atras") // Previous button text
        setNextText("Siguiente") // Next button text
        setFinishText("Ingresar") // Finish button text
        setCancelText("Salir") // Cancel button text
    }

    override fun finishTutorial() {
        goActivity<LoginActivity>()
    }
}
