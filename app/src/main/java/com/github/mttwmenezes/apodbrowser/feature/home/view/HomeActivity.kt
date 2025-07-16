package com.github.mttwmenezes.apodbrowser.feature.home.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.databinding.ActivityHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureNavController()
        configureUi()
    }

    private fun configureNavController() {
        val navHost = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHost.navController
    }

    private fun configureUi() = with(binding) {
        navigationBar.setupWithNavController(navController)
        collapsingToolbar.setupWithNavController(
            toolbar = topAppBar,
            navController = navController,
            configuration = AppBarConfiguration(
                topLevelDestinationIds = setOf(R.id.latest, R.id.bookmarks)
            )
        )
    }
}
