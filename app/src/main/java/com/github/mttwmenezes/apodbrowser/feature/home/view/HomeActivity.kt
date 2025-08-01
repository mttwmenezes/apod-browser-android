package com.github.mttwmenezes.apodbrowser.feature.home.view

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.github.mttwmenezes.apodbrowser.R
import com.github.mttwmenezes.apodbrowser.databinding.ActivityHomeBinding
import com.github.mttwmenezes.apodbrowser.feature.other.delegate.HomeLayoutDelegate
import com.github.mttwmenezes.apodbrowser.feature.other.event.ExploreActionClicked
import com.github.mttwmenezes.apodbrowser.feature.other.event.RefreshActionClicked
import com.github.mttwmenezes.apodbrowser.feature.other.extension.setOnQueryTextChangedListener
import com.github.mttwmenezes.apodbrowser.infrastructure.event.EventPublisher
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeActivity : AppCompatActivity(), HomeLayoutDelegate {

    private lateinit var binding: ActivityHomeBinding

    private lateinit var navController: NavController

    @Inject lateinit var eventPublisher: EventPublisher

    override val navigationBar: BottomNavigationView
        get() = binding.navigationBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureNavController()
        configureUi()
        configureOnDestinationChangedListener()
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

    private fun configureOnDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.latest -> configureLatestTopAppBarMenu()
                R.id.bookmarks -> configureBookmarksTopAppBarMenu()
            }
        }
    }

    private fun configureLatestTopAppBarMenu() = with(binding.topAppBar) {
        menu.clear()
        inflateMenu(R.menu.latest_top_app_bar_menu)
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.explore_action -> {
                    eventPublisher.publish(ExploreActionClicked)
                    true
                }

                R.id.refresh_action -> {
                    eventPublisher.publish(RefreshActionClicked)
                    true
                }

                R.id.official_website_action -> {
                    // TODO To be implemented
                    true
                }

                R.id.settings_action -> {
                    // TODO To be implemented
                    true
                }

                else -> false
            }
        }
    }

    private fun configureBookmarksTopAppBarMenu() = with(binding.topAppBar) {
        menu.clear()
        inflateMenu(R.menu.bookmarks_top_app_bar_menu)
        configureBookmarksSearchAction()
        setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings_action -> {
                    // TODO To be implemented
                    true
                }

                else -> false
            }
        }
    }

    private fun configureBookmarksSearchAction() = with(binding.topAppBar.menu) {
        val searchActionView = findItem(R.id.search_action).actionView as SearchView
        searchActionView.queryHint = getString(R.string.action_title_search_bookmarks)
        searchActionView.setOnQueryTextChangedListener {
            // TODO To be implemented
        }
    }
}
