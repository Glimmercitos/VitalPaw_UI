package me.vitalpaw.network.response

import me.vitalpaw.models.MedicalRecord


data class MedicalRecordResponse(
    val message: String,
    val medicalRecord: MedicalRecord
)