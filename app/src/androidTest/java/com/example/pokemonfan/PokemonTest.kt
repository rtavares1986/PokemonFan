package com.example.pokemonfan

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBackUnconditionally
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isChecked
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.example.pokemonfan.data.api.ApiHelper
import com.example.pokemonfan.data.api.ApiHelperImpl
import com.example.pokemonfan.data.api.ApiService
import com.example.pokemonfan.data.model.*
import com.example.pokemonfan.di.ApplicationModule
import com.example.pokemonfan.di.PokemonListUrl
import com.example.pokemonfan.di.WebHookUrl
import com.example.pokemonfan.ui.MainActivity
import com.example.pokemonfan.ui.pokemonlist.PokemonListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.mockk.coEvery
import io.mockk.mockk
import org.junit.Rule
import org.junit.Test
import retrofit2.Response
import javax.inject.Singleton

/**
 * This instrumentation test was made to test the UI components of the application
 * by simulating the ApplicationModule. The images (Glide tests) were not included in this tests.
 */

@HiltAndroidTest
@UninstallModules(ApplicationModule::class)
class PokemonTest {
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun test_withMockedModule() {
        ActivityScenario.launch(MainActivity::class.java)

        // Checks if the first fragment is active
        onView(ViewMatchers.withId(R.id.pokemon_list)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.btn_prev)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.btn_next)).check(matches(isDisplayed()))

        // Click on the first row of the RecyclerView (first pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.PokemonViewHolder>(0, click())
        )
        // Checks if the information displayed is the expected one (first pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_name)).check(matches(ViewMatchers.withText("PokemonTest 1\n")))
        onView(ViewMatchers.withId(R.id.pokemon_height)).check(matches(ViewMatchers.withText("11\n")))
        onView(ViewMatchers.withId(R.id.pokemon_weight)).check(matches(ViewMatchers.withText("12\n")))
        onView(ViewMatchers.withId(R.id.pokemon_base_experience)).check(matches(ViewMatchers.withText("13\n")))
        onView(ViewMatchers.withId(R.id.pokemon_abilities)).check(matches(ViewMatchers.withText("\u2022 Ability 1\n")))
        onView(ViewMatchers.withId(R.id.pokemon_moves)).check(matches(ViewMatchers.withText("\u2022 Move 1\n")))
        // Checks if POST request is successfull
        onView(ViewMatchers.withId(R.id.pokemon_favorite)).perform(click()).check(matches(isChecked()))
        // Click in the back button (first fragment is visible)
        pressBackUnconditionally()
        // Click on the second row of the RecyclerView (second pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.PokemonViewHolder>(1, click())
        )
        // Checks if the information displayed is the expected one (second pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_name)).check(matches(ViewMatchers.withText("PokemonTest 2\n")))
        onView(ViewMatchers.withId(R.id.pokemon_height)).check(matches(ViewMatchers.withText("21\n")))
        onView(ViewMatchers.withId(R.id.pokemon_weight)).check(matches(ViewMatchers.withText("22\n")))
        onView(ViewMatchers.withId(R.id.pokemon_base_experience)).check(matches(ViewMatchers.withText("23\n")))
        onView(ViewMatchers.withId(R.id.pokemon_abilities)).check(matches(ViewMatchers.withText("\u2022 Ability 2\n")))
        onView(ViewMatchers.withId(R.id.pokemon_moves)).check(matches(ViewMatchers.withText("\u2022 Move 2\n")))
        // Checks if POST request is successfull
        onView(ViewMatchers.withId(R.id.pokemon_favorite)).perform(click()).check(matches(isChecked()))
        // Click in the back button (first fragment is visible)
        pressBackUnconditionally()

        Thread.sleep(1000)
        // Wait one second and proceed to the second page
        onView(ViewMatchers.withId(R.id.btn_next)).perform(click())

        // Click on the first row of the RecyclerView (third pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.PokemonViewHolder>(0, click())
        )
        // Checks if the information displayed is the expected one (third pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_name)).check(matches(ViewMatchers.withText("PokemonTest 3\n")))
        onView(ViewMatchers.withId(R.id.pokemon_height)).check(matches(ViewMatchers.withText("31\n")))
        onView(ViewMatchers.withId(R.id.pokemon_weight)).check(matches(ViewMatchers.withText("32\n")))
        onView(ViewMatchers.withId(R.id.pokemon_base_experience)).check(matches(ViewMatchers.withText("33\n")))
        onView(ViewMatchers.withId(R.id.pokemon_abilities)).check(matches(ViewMatchers.withText("\u2022 Ability 3\n")))
        onView(ViewMatchers.withId(R.id.pokemon_moves)).check(matches(ViewMatchers.withText("\u2022 Move 3\n")))
        // Checks if POST request is successfull
        onView(ViewMatchers.withId(R.id.pokemon_favorite)).perform(click()).check(matches(isChecked()))
        // Click in the back button (first fragment is visible)
        pressBackUnconditionally()
        // Click on the second row of the RecyclerView (fourth pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_list)).perform(
            RecyclerViewActions.actionOnItemAtPosition<PokemonListAdapter.PokemonViewHolder>(1, click())
        )
        // Checks if the information displayed is the expected one (fourth pokemon)
        onView(ViewMatchers.withId(R.id.pokemon_name)).check(matches(ViewMatchers.withText("PokemonTest 4\n")))
        onView(ViewMatchers.withId(R.id.pokemon_height)).check(matches(ViewMatchers.withText("41\n")))
        onView(ViewMatchers.withId(R.id.pokemon_weight)).check(matches(ViewMatchers.withText("42\n")))
        onView(ViewMatchers.withId(R.id.pokemon_base_experience)).check(matches(ViewMatchers.withText("43\n")))
        onView(ViewMatchers.withId(R.id.pokemon_abilities)).check(matches(ViewMatchers.withText("\u2022 Ability 4\n")))
        onView(ViewMatchers.withId(R.id.pokemon_moves)).check(matches(ViewMatchers.withText("\u2022 Move 4\n")))
        // Checks if POST request is successfull
        onView(ViewMatchers.withId(R.id.pokemon_favorite)).perform(click()).check(matches(isChecked()))
    }
}

/**
 * This ApplicationTestModule will simulate a list with two pages, each one with two pokemons and the
 * respective details.
 */

@Module
@InstallIn(SingletonComponent::class)
class ApplicationTestModule {

    @PokemonListUrl
    @Provides
    fun provideUrl1(): String = "pokemonList_url_1" // entry page of the application

    @WebHookUrl
    @Provides
    fun provideUrl2(): String = "mock2"

    @Provides
    @Singleton
    fun provideApiService(): ApiService {
        val service = mockk<ApiService>()
        val pokemonList1 = PokemonList("pokemonList_url_2", null, listOf(
            Pokemon(1, "PokemonTest 1", 11, 12, 13, "pokemon_url_1", Sprite("mock_image 1"), listOf(Abilities(Ability("Ability 1"))), listOf(Moves(Move("Move 1")))),
            Pokemon(2, "PokemonTest 2", 21, 22, 23, "pokemon_url_2", Sprite("mock_image 2"), listOf(Abilities(Ability("Ability 2"))), listOf(Moves(Move("Move 2"))))
        ))
        val pokemonList2 = PokemonList(null, "pokemonList_url_1", listOf(
            Pokemon(3, "PokemonTest 3", 31, 32, 33, "pokemon_url_3", Sprite("mock_image 3"), listOf(Abilities(Ability("Ability 3"))), listOf(Moves(Move("Move 3")))),
            Pokemon(4, "PokemonTest 4", 41, 42, 43, "pokemon_url_4", Sprite("mock_image 4"), listOf(Abilities(Ability("Ability 4"))), listOf(Moves(Move("Move 4"))))
        ))
        coEvery { service.getPokemons("pokemonList_url_1") } returns Response.success(pokemonList1)
        coEvery { service.getPokemons("pokemonList_url_2") } returns Response.success(pokemonList2)
        coEvery { service.getPokemon("pokemon_url_1") } returns Response.success(pokemonList1.results[0])
        coEvery { service.getPokemon("pokemon_url_2") } returns Response.success(pokemonList1.results[1])
        coEvery { service.getPokemon("pokemon_url_3") } returns Response.success(pokemonList2.results[0])
        coEvery { service.getPokemon("pokemon_url_4") } returns Response.success(pokemonList2.results[1])
        coEvery { service.postFavoritePokemon(any(), any()) } returns Response.success(null)
        return service
    }

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}