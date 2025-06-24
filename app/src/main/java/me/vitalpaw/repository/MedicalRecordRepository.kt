package me.vitalpaw.repository

import me.vitalpaw.models.MedicalRecord
import me.vitalpaw.models.Appointment
import me.vitalpaw.models.Pet
import javax.inject.Inject

class MedicalRecordRepository @Inject constructor(
    private val appointmentRepository: AppointmentRepository,

    private val petRepository: PetRepository
) {

    private val pets: List<Pet> = petRepository.getPets()

    private val medicalRecords: List<MedicalRecord> = listOfNotNull(
        pets.find { it.id == "p1" }?.let { pet ->
            listOf(
                MedicalRecord(
                    id = "mr1",
                    notes = "Se observó leve inflamación en la oreja derecha.",
                    treatment = "Antibiótico tópico cada 12 horas por 5 días.",
                    pet = pet,
                    appointment = Appointment(
                        id = "1",
                        owner = pet.owner,
                        pet = pet,
                        service = "Consulta general",
                        description = "Revisión general y control de peso",
                        date = "2020-06-20",
                        time = "10:00",
                        veterinarian = pet.owner,
                        createdAt = "2020-06-10"
                    ),
                    service = "Consulta general",
                    description = "Revisión general y control de peso",
                    date = "2020-06-20",
                    time = "10:00"
                ),
                MedicalRecord(
                    id = "mr2",
                    notes = "Aplicación de vacunas al día.",
                    treatment = "No requiere tratamiento adicional.",
                    pet = pet,
                    appointment = Appointment(
                        id = "2",
                        owner = pet.owner,
                        pet = pet,
                        service = "Vacunación",
                        description = "Vacuna contra la rabia",
                        date = "2020-06-15",
                        time = "14:00",
                        veterinarian = pet.owner,
                        createdAt = "2020-06-05"
                    ),
                    service = "Vacunación",
                    description = "Vacuna contra la rabia",
                    date = "2020-06-15",
                    time = "14:00"
                )
            )
        },
        pets.find { it.id == "p2" }?.let { pet ->
            listOf(
                MedicalRecord(
                    id = "mr3",
                    notes = "Revisión por pérdida de apetito.",
                    treatment = "Suplemento vitamínico por 7 días.",
                    pet = pet,
                    appointment = Appointment(
                        id = "3",
                        owner = pet.owner,
                        pet = pet,
                        service = "Consulta general",
                        description = "Evaluación nutricional",
                        date = "2020-07-10",
                        time = "09:30",
                        veterinarian = pet.owner,
                        createdAt = "2020-07-01"
                    ),
                    service = "Consulta general",
                    description = "Evaluación nutricional",
                    date = "2020-07-10",
                    time = "09:30"
                ),
                MedicalRecord(
                    id = "mr4",
                    notes = "Chequeo postoperatorio sin complicaciones.",
                    treatment = "Continuar con antibióticos por 3 días.",
                    pet = pet,
                    appointment = Appointment(
                        id = "4",
                        owner = pet.owner,
                        pet = pet,
                        service = "Cirugía",
                        description = "Esterilización",
                        date = "2020-07-01",
                        time = "11:00",
                        veterinarian = pet.owner,
                        createdAt = "2020-06-25"
                    ),
                    service = "Cirugía",
                    description = "Esterilización",
                    date = "2020-07-01",
                    time = "11:00"
                )
            )
        }
    ).flatten()

    fun getMedicalRecordById(appointmentId: String): MedicalRecord? {
        return medicalRecords.find { it.appointment?.id == appointmentId }
    }

    fun getMedicalRecordsByPetId(petId: String): List<MedicalRecord> {
        return medicalRecords.filter { it.pet.id == petId }
    }

//    fun getPetById(petId: String): Pet? {
//        return PetRepository.getPets().find { it.id == petId }
//    }

}


