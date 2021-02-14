package com.example.pokemonfan.data.api

import com.example.pokemonfan.data.model.Pokemon
import com.example.pokemonfan.data.model.PokemonList
import com.example.pokemonfan.data.model.PokemonPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Url

/**
 *  The ApiService is responsible for fetching the pokemon list and the details of each pokemon.
 *  It also sends a POST for marking a pokemon as favourite.
 *
 *  It is easier to include the full URL in each request, because the API structure is oriented to
 *  use the full URL to fetch the resources.
 */

interface ApiService {
    @GET
    suspend fun getPokemons(@Url pokemonURL:String): Response<PokemonList>

    @GET
    suspend fun getPokemon(@Url pokemonURL:String): Response<Pokemon>

    /**
     *  The response is void because the WebHook only informs that the request was successful or not, it
     *  doesn't return the actual state of this information on the server. If this was a real solution,
     *  the POST response would inform the application that the information was correctly saved in the
     *  server.
     */
    @POST
    suspend fun postFavoritePokemon(@Url url:String, @Body pokemonPost: PokemonPost): Response<Void>
}