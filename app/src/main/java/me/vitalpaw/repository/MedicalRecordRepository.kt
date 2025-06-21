package me.vitalpaw.repository

import me.vitalpaw.models.MedicalRecord
import javax.inject.Inject

class MedicalRecordRepository @Inject constructor(
    private val petRepository: PetRepository,
    private val appointmentRepository: AppointmentRepository
) {
    fun getMedicalRecordById(appointmentId: String): MedicalRecord {
        val appointment = appointmentRepository.getAppointments().find { it.id == appointmentId }
            ?: throw IllegalArgumentException("No se encontró la cita con id: $appointmentId")

        return MedicalRecord(
            id = "1",
            notes = "",
            treatment = "",
            pet = appointment.pet,
            appointment = appointment,
            service = appointment.service,
            description = appointment.description,
            date = appointment.date,
            time = appointment.time
        )
    }

}
