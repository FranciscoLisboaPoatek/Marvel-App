package com.example.marvel_app.feature_character

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.marvel_app.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_nav_main)
        navController.addOnDestinationChangedListener { _, destination, _ ->

            when (destination.id) {
                R.id.characterDetailFragment -> {
                    val backStackEntry = navController.previousBackStackEntry
                    val prevDestinationId = backStackEntry?.destination?.id
                    when (prevDestinationId) {
                        R.id.charactersFragment -> bottomNavigationView.menu.findItem(R.id.charactersFragment).isChecked = true
                        R.id.favoriteCharactersFragment -> bottomNavigationView.menu.findItem(R.id.favoriteCharactersFragment).isChecked = true
                    }
                }
                R.id.charactersFragment -> bottomNavigationView.menu.findItem(R.id.charactersFragment).isChecked = true
                R.id.favoriteCharactersFragment -> bottomNavigationView.menu.findItem(R.id.favoriteCharactersFragment).isChecked = true
            }
        }
        bottomNavigationView.setupWithNavController(navController)
    }
}