package com.example.pokemonfan.ui.pokemonlist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pokemonfan.R
import com.example.pokemonfan.data.model.Pokemon
import kotlinx.android.synthetic.main.pokemon_row.view.*

class PokemonListAdapter(
    private val onPokemonClickListener: OnPokemonClickListener
) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {
    private val pokemonLists = ArrayList<Pokemon>()

    interface OnPokemonClickListener {
        fun onPokemonClick(pokemon: Pokemon)
    }

    /**
     * It was used Glide to obtain the image of each pokemon.
     */
    class PokemonViewHolder(itemView: View, private val onPokemonClickListener: OnPokemonClickListener) :
        RecyclerView.ViewHolder(itemView) {
        fun bind(pokemon: Pokemon) {
            itemView.pokemon_name.text = pokemon.name
            Glide.with(itemView.pokemon_image.context)
                .load(pokemon.sprites.front_default)
                .into(itemView.pokemon_image)
            itemView.setOnClickListener {
                onPokemonClickListener.onPokemonClick(pokemon)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder = PokemonViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.pokemon_row, parent, false),
        onPokemonClickListener
    )

    override fun getItemCount(): Int = pokemonLists.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) = holder.bind(pokemonLists[position])

    fun setItems(list: List<Pokemon>) {
        pokemonLists.clear()
        pokemonLists.addAll(list)
        notifyDataSetChanged()
    }
}