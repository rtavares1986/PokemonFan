package com.example.pokemonfan.data.api

import com.example.pokemonfan.data.model.Pokemon
import com.example.pokemonfan.data.model.PokemonList
import com.example.pokemonfan.data.model.PokemonPost
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Url
import javax.inject.Inject

/**
 * This implementation is tricky because the API structure doesn't send the list of pokemon with the
 * image attached (it only delivers the name and the URL detail of each pokemon)
 * So, the fastest solution was to take advantage of the pagination and obtain all the detailed information
 * about the pokemons and merge with the simple list that is given, that contains only the name and the URL
 */

class ApiHelperImpl @Inject constructor(private val apiService: ApiService) : ApiHelper {

    override suspend fun getPokemons(@Url pokemonURL:String): Response<PokemonList> {
        val pokemons = ArrayList<Pokemon>()
        val resp: Response<PokemonList> = apiService.getPokemons(pokemonURL)
        if (resp.isSuccessful) {
            val pokemonList = resp.body()
            val listPokemons = pokemonList?.results
            if (listPokemons != null) {
                for (pokemon in listPokemons) {
                    val pokemonResp = apiService.getPokemon(pokemon.url)
                    if (pokemonResp.isSuccessful) {
                        val pokemon1 = pokemonResp.body()
                        if (pokemon1 != null)
                            pokemons.add(pokemon1)
                    }
                }
                pokemonList.results = pokemons
                return Response.success(pokemonList)    // Response is simulated as successful
            }
        }
        return Response.error(500, resp.errorBody()!!) // if anything goes wrong
    }

    override suspend fun postFavoritePokemon(@Url url:String, @Body pokemonPost: PokemonPost): Response<Void> =
        apiService.postFavoritePokemon(url, pokemonPost)
}