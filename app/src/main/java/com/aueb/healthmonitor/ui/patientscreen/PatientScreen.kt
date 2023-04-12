package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.ui.components.DropDownMenu
import com.aueb.healthmonitor.ui.components.datepicker.DatePicker
import com.aueb.healthmonitor.ui.getGenderOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect

@Composable
fun PatienScreen(navController: NavController, context: Context){
    val viewModel: PatientViewModel = viewModel(
        factory = PatientViewModelFactory(context)
    )

    LaunchedEffect(viewModel) {
        //viewModel.fetchData()
    }

    val genderOptions = getGenderOptions()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                text = stringResource(id = R.string.patient_screen_form_header),
                style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Default)
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                label = { Text(text = stringResource(id = R.string.patient_screen_form_name)) },
                value = viewModel.name,
                onValueChange = { viewModel.UpdateName(it) }
            )

            Spacer(modifier = Modifier.height(15.dp))

            TextField(
                label = { Text(text = stringResource(id = R.string.patient_screen_form_surname)) },
                value = viewModel.surname,
                onValueChange = { viewModel.UpdateSurname(it) }
            )

            Spacer(modifier = Modifier.height(15.dp))

            DropDownMenu(items = genderOptions, onItemSelected = {
                viewModel.UpdateGender(it.toCode())
            })

            Spacer(modifier = Modifier.height(15.dp))

            DatePicker(onDateSelected = {
                viewModel.UpdateBirthDate(it)
            })
        }

        if(viewModel.isFormValidated){
            item {
                Spacer(modifier = Modifier.height(15.dp))

                Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
                    Button(
                        onClick = { viewModel.savePatient() },
                        shape = RoundedCornerShape(50.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Text(text = stringResource(id = R.string.patient_screen_create_btn))
                    }
                }
            }
        }
    }
}