package com.example.actividad3evaluable_pmdm_yolandarabal

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    private lateinit var navController: androidx.navigation.NavController
    private var currentDestinationId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        setupActionBarWithNavController(navController)

        // 🔥 Cada vez que cambiamos de fragmento
        navController.addOnDestinationChangedListener { _, destination, _ ->
            currentDestinationId = destination.id

            // 🔥 Si estamos en HomeFragment, ocultamos la flecha de atrás
            supportActionBar?.setDisplayHomeAsUpEnabled(destination.id != R.id.homeFragment)

            invalidateOptionsMenu()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (currentDestinationId == R.id.homeFragment ||
            currentDestinationId == R.id.leagueFragment ||
            currentDestinationId == R.id.favouritesFragment) {
            menuInflater.inflate(R.menu.menu_main, menu)
            return true
        }
        return false
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_inicio -> {
                navController.navigate(R.id.homeFragment)
                true
            }
            R.id.menu_favoritos -> {
                navController.navigate(R.id.favouritesFragment)
                true
            }
            R.id.menu_salir -> {
                finishAffinity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
