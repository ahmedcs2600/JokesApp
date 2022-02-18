package com.example.lillydooassignment.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.lillydooassignment.R
import com.example.lillydooassignment.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    private val navHostFragment : NavHostFragment
    get() = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id) as NavHostFragment

    private val dialogs = listOf(R.id.jokeDetailDialog)
    private val noActionBarScreens = listOf(R.id.splashFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        bindDestinationChangedListener()
    }

    private fun bindDestinationChangedListener() {
        navHostFragment.navController.addOnDestinationChangedListener { _, destination, _ ->
            if(noActionBarScreens.contains(destination.id)) {
                supportActionBar?.hide()
            } else if(!dialogs.contains(destination.id)) {
                supportActionBar?.show()
                title = destination.label
            }
        }
    }
}