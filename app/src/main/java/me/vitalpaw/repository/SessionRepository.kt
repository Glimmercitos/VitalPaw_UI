package me.vitalpaw.repository

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionRepository @Inject constructor() {

    fun login(email: String, password: String): Boolean {
        return email.isNotBlank() && password.isNotBlank()
    }
}
