package me.vitalpaw.ui.components.icons

import android.app.TimePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalTime

@Composable
fun TimePickerDialog(
    initialTime: LocalTime,
    onTimeSelected: (LocalTime) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val hour = initialTime.hour
    val minute = initialTime.minute

    TimePickerDialog(
        context,
        { _, selectedHour, selectedMinute ->
            val selected = LocalTime.of(selectedHour, selectedMinute)
            onTimeSelected(selected)
        },
        hour,
        minute,
        false // false para usar formato 12 horas (AM/PM)
    ).apply {
        setOnDismissListener { onDismiss() }
        show()
    }
}
