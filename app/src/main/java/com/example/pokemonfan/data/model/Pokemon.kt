package com.example.pokemonfan.data.model

import com.squareup.moshi.Json
import java.io.Serializable

/**
 * Data class to obtain the detailed information of each pokemon.
 */

data class Pokemon(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "name")
    val name: String = "",
    @Json(name = "height")
    val height: Int = 0,
    @Json(name = "weight")
    val weight: Int = 0,
    @Json(name = "base_experience")
    val base_experience: Int = 0,
    @Json(name = "url")
    val url: String = "",
    @Json(name = "sprites")
    val sprites: Sprite,
    @Json(name = "abilities")
    val abilities: List<Abilities>,
    @Json(name = "moves")
    val moves: List<Moves>
    ): Serializable

data class Sprite(
    @Json(name = "front_default")
    val front_default: String = ""
): Serializable

data class Abilities(
    @Json(name = "ability")
    val ability: Ability
): Serializable

data class Ability(
    @Json(name = "name")
    val name: String = ""
): Serializable

data class Moves(
    @Json(name = "move")
    val move: Move
): Serializable

data class Move(
    @Json(name = "name")
    val name: String
): Serializable