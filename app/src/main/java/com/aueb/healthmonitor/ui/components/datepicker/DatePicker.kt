package com.aueb.healthmonitor.ui.components.datepicker

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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

    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Button(onClick = {
            mDatePickerDialog.show()
        }, colors = ButtonDefaults.buttonColors(backgroundColor = Color(0XFF0F9D58)) ) {
            Text(text = stringResource(id = R.string.date_picker_open_modal_btn_title), color = Color.White)
        }

        Spacer(modifier = Modifier.size(10.dp))

        Text(text = stringResource(id = R.string.date_picker_selected_date_prefix)+": ${mDate.value}", fontSize = 10.sp, textAlign = TextAlign.Center)
    }
}