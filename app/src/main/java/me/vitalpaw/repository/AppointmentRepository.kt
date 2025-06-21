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
        val pets = petRepository.getPets()

        return listOf(
            Appointment(
                id = "1",
                owner = owner,
                pet = pets[0].copy(),
                service = "Consulta médica",
                description = "Chequeo general",
                date = "2025-06-28",
                time = "10:00 AM",
                veterinarian = vet
            ),
            Appointment(
                id = "2",
                owner = owner,
                pet = pets[1].copy(),
                service = "Vacunación",
                description = "Primera dosis de vacunas",
                date = "2025-07-18",
                time = "1:00 PM",
                veterinarian = vet
            )
        )
    }

}
