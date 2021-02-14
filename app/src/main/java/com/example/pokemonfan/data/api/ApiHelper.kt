package com.example.pokemonfan.data.api

import com.example.pokemonfan.data.model.PokemonList
import com.example.pokemonfan.data.model.PokemonPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Url

interface ApiHelper {
    suspend fun getPokemons(@Url pokemonURL:String): Response<PokemonList>

    suspend fun postFavoritePokemon(@Url url:String, @Body pokemonPost: PokemonPost): Response<Void>
}