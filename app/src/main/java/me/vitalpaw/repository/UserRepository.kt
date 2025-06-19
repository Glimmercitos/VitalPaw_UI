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
    fun getAllUsers(): List<User> {
        return listOf(
            User(
                id = "u1",
                googleId = "google-123",
                name = "Carlos",
                email = "carlos@example.com"
            ),
            User(
                id = "u2",
                googleId = "google-124",
                name = "Ana",
                email = "ana@example.com"
            ),
            User(
                id = "u3",
                googleId = "google-125",
                name = "Luis",
                email = "luis@example.com"
            )
        )
    }
}
