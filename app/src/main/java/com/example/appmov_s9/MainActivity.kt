package com.example.appmov_s9

import android.R.attr.textColor
import android.content.ClipDescription
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.widget.Toast
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    private lateinit var txtWelcome: TextView
    private lateinit var txtDescription: TextView
    private lateinit var btnIniciarSesion: MaterialButton
    private lateinit var btnCrearCuenta: MaterialButton
    private lateinit var btnVerPerfil: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPreferencesHelper = SharedPreferencesHelper(this)

        txtDescription = findViewById(R.id.txtDescription)
        txtWelcome = findViewById(R.id.txtWelcome)
        btnIniciarSesion = findViewById(R.id.btn01)
        btnCrearCuenta = findViewById(R.id.btn02)
        btnVerPerfil = findViewById(R.id.btn03)

        // Verificación inicial
        cargarPerfil()

        btnIniciarSesion.setOnClickListener {
            cargarPerfil()
        }

        btnCrearCuenta.setOnClickListener {
            val intent = Intent(this, NewAccount::class.java)
            startActivity(intent)
        }

        btnVerPerfil.setOnClickListener {
            val intent = Intent(this, Account::class.java)
            startActivity(intent)
        }

        incrementarContadorVisitas()

        aplicarTema(sharedPreferencesHelper.getBoolean(SharedPreferencesHelper.KEY_THEME_MODE, false))

        val toolbar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        setSupportActionBar(toolbar)
    }

    override fun onResume() {
        super.onResume()

        val nombre = sharedPreferencesHelper.getString(SharedPreferencesHelper.KEY_USERNAME, "")

        if (nombre.isEmpty()) { // NO HAY PERFIL
            btnCrearCuenta.visibility = View.VISIBLE
            btnVerPerfil.visibility = View.INVISIBLE
        } else { // SI HAY PERFIL
            btnCrearCuenta.visibility = View.INVISIBLE
            btnVerPerfil.visibility = View.VISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_index, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_reset -> {
                sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)
                Toast.makeText(this, "Contador reiniciado", Toast.LENGTH_SHORT).show()
                cargarPerfil() // Actualizar la pantalla
                true
            }
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

        if (nombre.isNotEmpty()) {
            val visitas = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)

            txtWelcome.text = "¡Bienvenido a TestPreferences, $nombre!"
            txtDescription.text = "Has entrado a la aplicación [$visitas] veces"
            Toast.makeText(this, "Perfil cargado exitosamente", Toast.LENGTH_SHORT).show()
        } else {}
    }

    private fun incrementarContadorVisitas() {
        val visitasActuales = sharedPreferencesHelper.getInt(SharedPreferencesHelper.KEY_VISIT_COUNT, 0)
        val nuevasVisitas = visitasActuales + 1
        sharedPreferencesHelper.saveInt(SharedPreferencesHelper.KEY_VISIT_COUNT, nuevasVisitas)
    }

    private fun aplicarTema(isDarkMode: Boolean) {
        val rootLayout = findViewById<androidx.constraintlayout.widget.ConstraintLayout>(R.id.main)
        val appBar = findViewById<com.google.android.material.appbar.MaterialToolbar>(R.id.topAppBar)
        val btnIniciarSesion = findViewById<MaterialButton>(R.id.btn01)
        val btnCrearCuenta = findViewById<MaterialButton>(R.id.btn02)

        val txtColor = if (isDarkMode) R.color.txtLight else R.color.txtDark // OSCURO ACTIVO - OSCURO DESACTIVADO
        val bgColor = if (isDarkMode) R.color.bgDark else R.color.bgLight
        val appBarColor = if (isDarkMode) R.color.barDark else R.color.barLight
        val btnColor = if (isDarkMode) R.color.btnDark else R.color.btnLight
        val btnColorNoBG = if (isDarkMode) R.color.btnDarkNoBG else R.color.white

        // Fondo general
        rootLayout.setBackgroundColor(resources.getColor(bgColor, theme))

        // AppBar
        appBar.setBackgroundColor(resources.getColor(appBarColor, theme))

        // Textos
        txtWelcome.setTextColor(resources.getColor(txtColor, theme))
        txtDescription.setTextColor(resources.getColor(txtColor, theme))

        // Botones
        btnIniciarSesion.setBackgroundColor(resources.getColor(btnColor, theme))
        btnCrearCuenta.setBackgroundColor(resources.getColor(btnColorNoBG, theme))
    }
}