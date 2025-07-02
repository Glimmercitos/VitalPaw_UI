package me.vitalpaw.ui.components.icons

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.Calendar

@Composable
fun DatePickerDialog(
    initialDate: Calendar = Calendar.getInstance(),
    onDateSelected: (Calendar) -> Unit,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current

    // Extraemos los valores iniciales
    val year = initialDate.get(Calendar.YEAR)
    val month = initialDate.get(Calendar.MONTH)
    val day = initialDate.get(Calendar.DAY_OF_MONTH)

    // Mostramos el DatePickerDialog
    android.app.DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            val selectedDate = Calendar.getInstance().apply {
                set(Calendar.YEAR, selectedYear)
                set(Calendar.MONTH, selectedMonth)
                set(Calendar.DAY_OF_MONTH, selectedDay)
            }
            onDateSelected(selectedDate)
        },
        year,
        month,
        day
    ).apply {
        setOnDismissListener { onDismiss() }
        show()
    }
}
