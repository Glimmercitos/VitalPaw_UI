package me.vitalpaw.repository

import me.vitalpaw.models.Appointment
import javax.inject.Inject

class AppointmentRepository @Inject constructor (
    private val userRepository: UserRepository,
    private val petRepository: PetRepository
) {

    fun getAppointments(): List<Appointment> {
        val owner = userRepository.getCurrentUser()
        val vet = userRepository.getVeterinarian()
        val pet = petRepository.getPets().first() // o selecciona el que necesites

        return listOf(
            Appointment(
                id = "1",
                owner = owner,
                pet = pet,
                petName = pet.name,
                service = "Consulta médica",
                description = "Chequeo general",
                date = "2025-06-28",
                time = "10:00 AM",
                veterinarian = vet
            )
        )
    }
}
