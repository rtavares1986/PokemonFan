package com.example.pokemonfan.ui.pokemondetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pokemonfan.utils.Resource
import com.example.pokemonfan.data.api.NetworkHelper
import com.example.pokemonfan.data.model.PokemonPost
import com.example.pokemonfan.di.WebHookUrl
import com.example.pokemonfan.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * This ViewModel is responsible for making the favorite POST request to WebHook and display the
 * response to user via observe.
 */

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    @WebHookUrl private val webHookUrl: String,
    private val mainRepository: MainRepository,
    private val networkHelper: NetworkHelper
) : ViewModel() {
    private val _response = MutableLiveData<Resource<String>>()
    val response: LiveData<Resource<String>>
        get() = _response

    fun postFavoritePokemon(pokemonPost: PokemonPost) {
        viewModelScope.launch {
            _response.postValue(Resource.loading(null))
            if (networkHelper.isNetworkConnected()) {
                mainRepository.postFavoritePokemon(webHookUrl, pokemonPost).let {
                    if (it.isSuccessful) {
                        _response.postValue(Resource.success("Success"))
                    } else _response.postValue(Resource.error(it.errorBody().toString(), null))
                }
            } else _response.postValue(Resource.error("No internet connection", null))
        }
    }
}