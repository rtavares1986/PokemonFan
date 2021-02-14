package com.example.pokemonfan.ui.pokemonlist

import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pokemonfan.R
import com.example.pokemonfan.data.model.Pokemon
import com.example.pokemonfan.data.model.PokemonList
import com.example.pokemonfan.utils.Status
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

const val POKEMON_KEY = "POKEMON_KEY"

@AndroidEntryPoint
class PokemonListFragment : Fragment(), PokemonListAdapter.OnPokemonClickListener {
    private val pokemonListViewModel: PokemonListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var _adapter: PokemonListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupObserver()
    }

    /**
     * The orientation of the RecyclerView was done programmatically, but the rows of the RecyclerView
     * were implemented using two different layouts (layout and layout-land)
     */
    private fun setupUI() {
        recyclerView = requireView().findViewById(R.id.pokemon_list)
        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE)
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        else
            recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                recyclerView.context,
                (recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        _adapter = PokemonListAdapter(this)
        recyclerView.adapter = _adapter

        requireView().findViewById<TextView>(R.id.btn_prev).setOnClickListener{
            pokemonListViewModel.fecthPreviousPage()
        }
        requireView().findViewById<TextView>(R.id.btn_next).setOnClickListener {
            pokemonListViewModel.fecthNextPage()
        }
    }

    private fun setupObserver() {
        pokemonListViewModel.pokemonList.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    it.data?.let { pokemonList ->
                        updateAdapter(pokemonList)
                    }
                }
                Status.LOADING -> {
                    Snackbar.make(requireView(), "Loading...", Snackbar.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    fun updateAdapter(pokemonList: PokemonList) {
        _adapter.setItems(pokemonList.results)
        recyclerView.scrollToPosition(0)
    }

    /**
     * The information about the details of the pokemon already exists in the 'PokemonList',
     * so the details of the selected pokemon are passed between Fragments via Bundle.
     */
    override fun onPokemonClick(pokemon: Pokemon) {
        pokemon.id.let {
            val bundle = Bundle()
            bundle.putSerializable(POKEMON_KEY, pokemon)
            findNavController().navigate(R.id.action_list_to_detail, bundle)
        }
    }
}