package com.aueb.healthmonitor.ui.components.datepicker

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aueb.healthmonitor.R
import java.util.*


@Composable
fun DatePicker(onDateSelected: (Date) -> Unit){

    val mContext = LocalContext.current

    val mYear: Int
    val mMonth: Int
    val mDay: Int

    val mCalendar = Calendar.getInstance()

    mYear = mCalendar.get(Calendar.YEAR)
    mMonth = mCalendar.get(Calendar.MONTH)
    mDay = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    val mDate = remember { mutableStateOf("") }
    val mDateObj = remember { mutableStateOf(Date()) }

    val mDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            mDate.value = "$mDayOfMonth/${mMonth+1}/$mYear"
            val selectedDate = Calendar.getInstance().apply {
                set(mYear, mMonth, mDayOfMonth)
            }.time
            mDateObj.value = selectedDate
            onDateSelected(selectedDate)
        }, mYear, mMonth, mDay
    )

    Box(
        modifier = Modifier.clickable{
            mDatePickerDialog.show()
        }
    ){
        Row(
            modifier = Modifier
                .padding(16.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(8.dp)
                )
                .fillMaxWidth(0.9f)
                .height(60.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                imageVector = Icons.Default.DateRange,
                contentDescription = null,
                modifier = Modifier.size(24.dp)
                    .padding(2.dp)
            )
            Text(
                text = stringResource(id = R.string.date_picker_selected_date_prefix) + ": ${mDate.value}",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(8.dp)
            )
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = null,
                modifier = Modifier.align(Alignment.CenterVertically).padding(end = 15.dp)
            )
        }
    }
}