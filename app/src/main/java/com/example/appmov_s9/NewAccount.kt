package com.example.appmov_s9

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton

class NewAccount  : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var etNombre: EditText
    private lateinit var etCorreo: EditText
    private lateinit var etEdad: EditText
    private lateinit var btnRegistrar: MaterialButton
    private lateinit var btnRegresar: MaterialButton
    private lateinit var txtTitle: TextView
    private lateinit var txtName: TextView
    private lateinit var txtEmail: TextView
    private lateinit var txtAge: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fg_new_account)

        val toolbar = findViewById<MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
        sharedPreferencesHelper = SharedPreferencesHelper(this)

        txtTitle = findViewById(R.id.tvTitulo)

        txtName = findViewById(R.id.txtName)
        etNombre = findViewById(R.id.etNombre)

        txtEmail = findViewById(R.id.txtEmail)
        etCorreo = findViewById(R.id.etCorreo)

        txtAge = findViewById(R.id.txtAge)
        etEdad = findViewById(R.id.etEdad)

        btnRegistrar = findViewById(R.id.btnRegistrar)
        btnRegresar = findViewById<MaterialButton>(R.id.btnRegresar)

        btnRegresar.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        btnRegistrar.setOnClickListener {
            registrarUsuario()
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

    private fun registrarUsuario() {
        val nombre = etNombre.text.toString().trim()
        val correo = etCorreo.text.toString().trim()
        val edad = etEdad.text.toString().trim()

        if (nombre.isEmpty() || correo.isEmpty() || edad.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show()
            return
        }

        // Save en SharedPreferences
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_USERNAME, nombre)
        sharedPreferencesHelper.saveString(SharedPreferencesHelper.KEY_EMAIL, correo)
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_AGE, edad.toIntOrNull() ?: 0)
        sharedPreferencesHelper.saveBoolean(SharedPreferencesHelper.KEY_IS_FIRST_TIME, false)

        Toast.makeText(this, "Usuario registrado exitosamente", Toast.LENGTH_SHORT).show()

        // Regresamos al MainActivity
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun aplicarTema(isDarkMode: Boolean) {
        val rootLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        val appBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)

        val txtColor = if (isDarkMode) R.color.txtLight else R.color.txtDark // OSCURO ACTIVO - OSCURO DESACTIVADO
        val txtColor1 = if (isDarkMode) R.color.emerald_primary else R.color.emerald_dark // OSCURO ACTIVO - OSCURO DESACTIVADO
        val bgColor = if (isDarkMode) R.color.bgDark else R.color.bgLight
        val appBarColor = if (isDarkMode) R.color.barDark else R.color.barLight
        val btnColor = if (isDarkMode) R.color.btnDark else R.color.btnLight
        val btnColorNoBG = if (isDarkMode) R.color.btnDarkNoBG else R.color.white

        // Fondo general
        rootLayout.setBackgroundColor(resources.getColor(bgColor, theme))

        // AppBar
        appBar.setBackgroundColor(resources.getColor(appBarColor, theme))

        // Textos
        txtTitle.setTextColor(resources.getColor(txtColor1, theme))
        txtName.setTextColor(resources.getColor(txtColor, theme))
        txtEmail.setTextColor(resources.getColor(txtColor, theme))
        txtAge.setTextColor(resources.getColor(txtColor, theme))

        // Botones
        btnRegistrar.setBackgroundColor(resources.getColor(btnColor, theme))
        btnRegresar.setBackgroundColor(resources.getColor(btnColorNoBG, theme))
    }
}