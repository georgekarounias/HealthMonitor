package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.ui.components.dropdown.DropDownMenu
import com.aueb.healthmonitor.ui.components.datepicker.DatePicker
import com.aueb.healthmonitor.ui.getGenderOptions
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.LaunchedEffect
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.ui.components.headertitle.Header
import com.aueb.healthmonitor.ui.components.loader.LoadingDialog
import com.aueb.healthmonitor.ui.components.textfield.CustomTextField

@Composable
fun PatienScreen(navController: NavController, context: Context, patientManager: PatientManager){
    val viewModel: PatientViewModel = viewModel(
        factory = PatientViewModelFactory(context, patientManager)
    )

    LaunchedEffect(viewModel) {
        //viewModel.fetchData()
    }

    val genderOptions = getGenderOptions()

    LoadingDialog(viewModel.isLoading, viewModel.loadingTitle, viewModel.loadingText)

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Header(
                text = stringResource(id = R.string.patient_screen_form_header),
                fontSize = 30.sp
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                label = stringResource(id = R.string.patient_screen_form_name),
                value = viewModel.name,
                onValueChange = { viewModel.UpdateName(it) },
                readOnly = viewModel.readOnly,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(15.dp))

            CustomTextField(
                label = stringResource(id = R.string.patient_screen_form_surname),
                value = viewModel.surname,
                onValueChange = { viewModel.UpdateSurname(it) },
                readOnly = viewModel.readOnly,
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            Spacer(modifier = Modifier.height(15.dp))

            Box(modifier = Modifier.fillMaxWidth(0.8f)) {
                DropDownMenu(
                    items = genderOptions,
                    label = "Gender",
                    selectedItem = genderOptions.find { it.code == viewModel.gender }
                        ?: genderOptions.first(),
                    onSelected = {
                        viewModel.UpdateGender(it.code)
                    }
                )
            }

            Spacer(modifier = Modifier.height(15.dp))

            if(viewModel.readOnly){
                CustomTextField(
                    label = stringResource(id = R.string.patient_screen_form_birthdate),
                    value = viewModel.birthdateStr,
                    onValueChange = {  },
                    readOnly = true,
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
            }else {
                DatePicker(
                    label = stringResource(id = R.string.date_picker_selected_date_prefix),
                    onDateSelected = {
                    viewModel.UpdateBirthDate(it)
                })
            }

            if(viewModel.readOnly){
                Spacer(modifier = Modifier.height(15.dp))
                //TODO : add component to show devices
                Text("TODO get list of devices")
                Spacer(modifier = Modifier.height(15.dp))
                //TODO : fix + button to add show form for devices
                Text("TODO add dynamic list of devices")
            }
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