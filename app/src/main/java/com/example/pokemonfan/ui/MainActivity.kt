package com.example.pokemonfan.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.pokemonfan.R
import dagger.hilt.android.AndroidEntryPoint

/**
 * This application was made with a Hilt + Retrofit + ViewModel approach.
 * It consists of an Activity with two fragments: PokemonListFragment and PokemonDetailFragment.
 * Each Fragment as a ViewModel and there is only one Module for all the application's lifetime.
 * The Repository only has access to the API services.
 */

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
    }
}