package me.vitalpaw.network.response

import me.vitalpaw.models.MedicalRecord


data class MedicalRecordResponse(
    val message: String,
    val medicalRecord: MedicalRecord
)

data class MedicalRecordsResponse(
    val message: String,
    val medicalRecords: List<MedicalRecord>
)
