package com.example.pokemonfan.data.repository

import com.example.pokemonfan.data.api.ApiHelper
import com.example.pokemonfan.data.model.PokemonList
import com.example.pokemonfan.data.model.PokemonPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Url
import javax.inject.Inject

/**
 *  It was only used the repository for fetching the API data, there is no local data stored.
 */

class MainRepository @Inject constructor(private val apiHelper: ApiHelper) {
    suspend fun getPokemons(@Url pokemonURL:String): Response<PokemonList> = apiHelper.getPokemons(pokemonURL)

    suspend fun postFavoritePokemon(@Url url:String, @Body pokemonPost: PokemonPost): Response<Void> =
        apiHelper.postFavoritePokemon(url, pokemonPost)
}