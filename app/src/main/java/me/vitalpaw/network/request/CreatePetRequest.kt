package me.vitalpaw.network.request

data class CreatePetRequest(
    val name: String,
    val species: String,
    val breed: String,
    val gender: Boolean,
    val age: Number,
    val weight: String,
    val unitAge: String,
)
