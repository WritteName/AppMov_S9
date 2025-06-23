package com.example.appmov_s9

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlin.toString

class Account : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper

    private lateinit var txtNombre: TextView
    private lateinit var txtCorreo: TextView
    private lateinit var etCorreo: EditText
    private lateinit var etEdad: EditText
    private lateinit var txtEdad: TextView
    private lateinit var btnEliminar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar2)
        setSupportActionBar(toolbar)

        txtNombre = findViewById(R.id.txtCurrentUser)

        txtCorreo = findViewById(R.id.txtCurrentEmail)
        etCorreo = findViewById(R.id.etCorreoActual)

        txtEdad = findViewById(R.id.txtCurrentAge)
        etEdad = findViewById(R.id.etEdadActual)

        btnEliminar = findViewById(R.id.btnDelete)

        cargarPerfil()

        btnEliminar.setOnClickListener {
            eliminarCuenta()
        }

        toolbar.setNavigationOnClickListener {
            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_index, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_dark -> {
                val isDarkMode = !sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_THEME_MODE, false)
                sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_THEME_MODE, isDarkMode)
                aplicarTema(isDarkMode)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun cargarPerfil() {
        val nombre = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_USERNAME, "")
        val correo = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_EMAIL, "")
        val edad = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_AGE, 0)

        txtNombre.text = nombre
        etCorreo.setText(correo)
        etEdad.setText(edad.toString())
    }

    private fun eliminarCuenta() {
        sharedPreferencesHelper.clearAll()
        Toast.makeText(this, "Perfil eliminado.", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun aplicarTema(isDarkMode: Boolean) {
        val rootLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        val appBar = findViewById<MaterialToolbar>(R.id.topAppBar2)

        val txtColor = if (isDarkMode) R.color.txtLight else R.color.txtDark
        val bgColor = if (isDarkMode) R.color.bgDark else R.color.bgLight
        val appBarColor = if (isDarkMode) R.color.barDark else R.color.barLight
        val btnColor = if (isDarkMode) R.color.btnDarkNoBG else android.R.color.transparent

        rootLayout.setBackgroundColor(resources.getColor(bgColor, theme))
        appBar.setBackgroundColor(resources.getColor(appBarColor, theme))

        txtNombre.setTextColor(resources.getColor(txtColor, theme))
        txtCorreo.setTextColor(resources.getColor(txtColor, theme))

        txtEdad.setTextColor(resources.getColor(txtColor, theme))

        btnEliminar.setBackgroundColor(resources.getColor(btnColor, theme))
    }
}