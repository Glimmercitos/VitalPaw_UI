package me.vitalpaw.utils

import android.content.Context
import android.net.Uri
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

// Extension para convertir String a RequestBody
fun String.toRequestBody(): RequestBody {
    return RequestBody.create("text/plain".toMediaTypeOrNull(), this)
}

// Extension para convertir Uri en MultipartBody.Part
fun Context.toMultipartBody(uri: Uri, partName: String): MultipartBody.Part {
    val contentResolver = contentResolver
    val inputStream = contentResolver.openInputStream(uri)
        ?: throw IllegalArgumentException("No se pudo abrir el inputStream del URI: $uri")

    // Crear archivo temporal
    val tempFile = File.createTempFile("upload", ".tmp", cacheDir)
    val outputStream = FileOutputStream(tempFile)
    inputStream.use { input -> outputStream.use { output -> input.copyTo(output) } }

    val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), tempFile)
    return MultipartBody.Part.createFormData(partName, tempFile.name, requestFile)
}
