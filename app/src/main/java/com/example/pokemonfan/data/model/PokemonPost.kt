package com.example.pokemonfan.data.model

import com.squareup.moshi.Json

/**
 * Data class used to mark a pokemon as favourite in the POST request to WebHook.
 */

data class PokemonPost(
    @Json(name = "pokemonId")
    val pokemonId: Int = 0,
    @Json(name = "favorite")
    val favorite: Boolean = false,
)