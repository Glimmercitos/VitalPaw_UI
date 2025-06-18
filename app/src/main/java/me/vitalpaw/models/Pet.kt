package me.vitalpaw.models

data class Pet(
    val id: String? = null,
    val name: String,
    val species: String,
    val age: Int,
    val breed: String,
    val weight: Double,
    val gender: Boolean, // true = macho, false = hembra
    val unitAge: String? = null, // "months" o "years"
    val owner: User,
    val createdAt: String? = null,
    val imageRes: Int
//    val updatedAt: String? = null
)
