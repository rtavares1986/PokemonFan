package com.example.pokemonfan.di

import com.example.pokemonfan.BuildConfig
import com.example.pokemonfan.data.api.ApiHelper
import com.example.pokemonfan.data.api.ApiHelperImpl
import com.example.pokemonfan.data.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

/**
 * It is used only one Module for the entire Application lifetime due to it's simplicity.
 * It was used the annotation classes 'PokemonListUrl' and 'WebHookUrl' to distinguish the two
 * Strings provided by the Module.
 */

@Qualifier
annotation class PokemonListUrl

@Qualifier
annotation class WebHookUrl

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @PokemonListUrl
    @Provides
    fun providePokemonListUrl(): String = BuildConfig.POKEMON_LIST_URL

    @WebHookUrl
    @Provides
    fun provideWebHookUrl(): String = BuildConfig.WEB_HOOK_URL

    @Provides
    @Singleton
    fun provideOkHttpClient() = if (BuildConfig.DEBUG) {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    } else OkHttpClient
        .Builder()
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, @PokemonListUrl baseUrl: String): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(MoshiConverterFactory.create())
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper
}