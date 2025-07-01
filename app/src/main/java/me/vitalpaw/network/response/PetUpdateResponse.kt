package me.vitalpaw.network.response

import me.vitalpaw.models.Pet

data class PetUpdateResponse(
    val message: String,
    val pet: Pet
)
