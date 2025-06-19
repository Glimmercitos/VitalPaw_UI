package me.vitalpaw.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor() {

    fun login(email: String, password: String): Boolean {
        // Simulación de autenticación local
        return email.isNotBlank() && password.isNotBlank()
    }
}
