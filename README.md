# PokemonFan
Pokemon API Challenge

• This application was made with a Hilt + Retrofit + ViewModel approach.
• It consists of an Activity with two fragments, one for displaying the pokemon lists and another for displaying the pokemon details.
• For each fragment there is a Viewmodel, the first ViewModel ‘PokemonListViewModel’ fetch the information, via API, of the pokemon list, this ViewModel delivers the information via observe to the fragment ‘PokemonListFragment’ to display the list to the user.
• The second ViewModel ‘PokemonDetailViewModel’ is responsible for marking the selected pokemon as Favorite, by sending a POST request to a WebHook site with the pokemon ID. This ViewModel delivers the POST response via observe to the fragment ‘PokemonDetailFragment’, to inform the user that the POST request was successful (or not) and to check the Favorite star checkbox as true (if successful).
• The data class ‘PokemonList’ has the information about the list of pokemons, the URL for the next page and the URL for the previous page, and the ‘Pokemon’ data class as the detailed information about the Pokemon.
• There is one instrumentation test in the androidTest folder named PokemonTest.kt.
