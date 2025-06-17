package me.vitalpaw.ui.components.icons

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun TimePickerDialog(
    initialTime: Calendar,
    onTimeSelected: (Calendar) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        val hour = initialTime.get(Calendar.HOUR_OF_DAY)
        val minute = initialTime.get(Calendar.MINUTE)

        val picker = TimePickerDialog(
            context,
            { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                val newTime = Calendar.getInstance().apply {
                    set(Calendar.HOUR_OF_DAY, selectedHour)
                    set(Calendar.MINUTE, selectedMinute)
                }
                onTimeSelected(newTime)
            },
            hour,
            minute,
            false // formato 12 horas
        )

        picker.setOnDismissListener { onDismiss() }
        picker.show()

        onDispose {
            picker.dismiss()
        }
    }
}
