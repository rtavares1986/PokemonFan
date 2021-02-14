package com.example.pokemonfan.data.model

import com.squareup.moshi.Json

/**
 * Data class used to obtain the list of pokemons and the current pagination.
 */

data class PokemonList(
    @Json(name = "next")
    val next: String?,
    @Json(name = "previous")
    val previous: String?,
    @Json(name = "results")
    var results: List<Pokemon>
)