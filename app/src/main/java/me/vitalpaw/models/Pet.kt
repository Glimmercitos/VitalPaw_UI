package me.vitalpaw.models

data class Pet(
    val id: String,
    val name: String,
    val species: String,
    val age: Int,
    val breed: String,
    val weight: Double,
    val gender: Boolean, // true = macho, false = hembra
    val unitAge: String?, // "months" o "years"
    val owner: User,
    val imageRes: Int
)
