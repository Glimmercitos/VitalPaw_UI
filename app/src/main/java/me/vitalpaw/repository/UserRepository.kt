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
            User("u1", "google-123", "Carlos", "carlos@example.com"),
            User("v1", "google-456", "Dra. Karla", "vet1@example.com", role = "veterinario"),
            User("v2", "google-457", "Dr. Juan", "vet2@example.com", role = "veterinario"),
            User("v3", "google-458", "Dra. Ana", "vet3@example.com", role = "veterinario"),
            User("v4", "google-459", "Dr. Luis", "vet4@example.com", role = "veterinario"),
            User("v5", "google-460", "Dra. Laura", "vet5@example.com", role = "veterinario"),
            User("u2", "google-124", "Ana", "ana@example.com"),
            User("u3", "google-125", "Luis", "luis@example.com")
        )
    }
}

