package com.gargalloeric.rickandmortyapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class CharacterResult (
    val info: Info,
    val results: List<Character>
)

@Serializable
data class Info (
    val count: Long,
    val pages: Long,
    val next: String,
    val prev: String? = null
)

@Serializable
data class Character (
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    val origin: Location,
    val location: Location,
    val image: String,
    val episode: List<String>,
    val url: String,
    val created: String
)
@Serializable
data class Location (
    val name: String,
    val url: String
)