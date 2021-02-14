package com.example.pokemonfan.ui.pokemondetail

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.example.pokemonfan.R
import com.example.pokemonfan.utils.Status
import com.example.pokemonfan.data.model.Pokemon
import com.example.pokemonfan.data.model.PokemonPost
import com.example.pokemonfan.ui.pokemonlist.POKEMON_KEY
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_pokemon_detail.view.*

@AndroidEntryPoint
class PokemonDetailFragment : Fragment() {
    private val pokemonDetailViewModel: PokemonDetailViewModel by viewModels()
    private var pokemon: Pokemon? = null

    /**
     * The information about the details of the pokemon already exists in the 'PokemonList',
     * so the details of the selected pokemon are passed between Fragments via Bundle.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { args ->
            args.getSerializable(POKEMON_KEY)?.let {pokemon ->
                this.pokemon = pokemon as Pokemon
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_pokemon_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        updateUI()
        setupObserver()
}

    private fun setupObserver() {
        pokemonDetailViewModel.response.observe(viewLifecycleOwner, {
            when (it.status) {
                Status.SUCCESS -> {
                    requireView().pokemon_favorite.isChecked = true // only checks the star if successful
                    Snackbar.make(requireView(), "Success", Snackbar.LENGTH_SHORT).show()
                }
                Status.LOADING -> {
                    requireView().pokemon_favorite.isChecked = false
                    Snackbar.make(requireView(), "Sending...", Snackbar.LENGTH_SHORT).show()
                }
                Status.ERROR -> {
                    requireView().pokemon_favorite.isChecked = false
                    Snackbar.make(requireView(), it.message.toString(), Snackbar.LENGTH_SHORT).show()
                }
            }
        })
    }

    /**
     * Not the best way to display the information, but it was the faster way to implement it.
     */
    @SuppressLint("SetTextI18n")
    private fun updateUI() {
        if (pokemon == null)
            return
        requireView().pokemon_name.text = pokemon?.name + "\n"
        requireView().pokemon_height.text = "${pokemon?.height}\n"
        requireView().pokemon_weight.text = "${pokemon?.weight}\n"
        requireView().pokemon_base_experience.text = "${pokemon?.base_experience}\n"

        val image = requireView().imageView
        Glide.with(image.context)
            .load(pokemon?.sprites?.front_default)
            .into(image)

        var abilities = ""
        pokemon?.abilities?.map { ability -> abilities += "\u2022 " + ability.ability.name + "\n"}
        requireView().pokemon_abilities.text = abilities

        var moves = ""
        pokemon?.moves?.map { move -> moves += "\u2022 " + move.move.name + "\n"}
        requireView().pokemon_moves.text = moves

        requireView().pokemon_favorite.setOnClickListener { checkBox ->
            if ((checkBox as CheckBox).isChecked)
                pokemonDetailViewModel.postFavoritePokemon(PokemonPost(pokemon?.id!!, true))
        }
    }
}