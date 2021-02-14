package com.example.pokemonfan.ui.pokemonlist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonfan.utils.Resource
import com.example.pokemonfan.data.api.NetworkHelper
import com.example.pokemonfan.data.model.PokemonList
import com.example.pokemonfan.di.PokemonListUrl
import com.example.pokemonfan.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    @PokemonListUrl private val pokemonListUrl: String,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _pokemonList = MutableLiveData<Resource<PokemonList>>()
    val pokemonList: LiveData<Resource<PokemonList>>
        get() = _pokemonList

    init {
        fetchPokemons(pokemonListUrl)
    }

    private fun fetchPokemons(url: String) {
        viewModelScope.launch {
            _pokemonList.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.getPokemons(url).let {
                    if (it.isSuccessful) {
                        _pokemonList.postValue(Resource.success(it.body()))
                    } else _pokemonList.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _pokemonList.postValue(Resource.error("No internet connection", null))
        }
    }

    fun fecthNextPage() {
        _pokemonList.value?.let { pokemonListData ->
            pokemonListData.data?.let { pokemonList ->
                pokemonList.next?.let { fetchPokemons(pokemonList.next) }
            }
        }
    }

    fun fecthPreviousPage() {
        _pokemonList.value?.let { pokemonListData ->
            pokemonListData.data?.let { pokemonList ->
                pokemonList.previous?.let { fetchPokemons(pokemonList.previous) }
            }
        }
    }
}