package me.vitalpaw.repository

import me.vitalpaw.models.User
import javax.inject.Inject

class UserRepository @Inject constructor(){

    fun getCurrentUser(): User {
        return User(
            id = "u1",
            googleId = "google-123",
            name = "Carlos",
            email = "carlos@example.com"
        )
    }

    fun getVeterinarian(): User {
        return User(
            id = "v1",
            googleId = "google-456",
            name = "Dra. Karla",
            email = "vet@example.com",
            role = "veterinario"
        )
    }
}
