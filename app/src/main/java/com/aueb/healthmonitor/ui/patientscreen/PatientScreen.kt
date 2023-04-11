package com.aueb.healthmonitor.ui.patientscreen

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.ui.components.DropDownMenu
import com.aueb.healthmonitor.ui.getGenderOptions

@Composable
fun PatienScreen(navController: NavController, context: Context, onSave: ()->Unit){
    Column(
        modifier = Modifier.padding(20.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val name = remember {
            mutableStateOf(TextFieldValue())
        }
        val surname = remember {
            mutableStateOf(TextFieldValue())
        }

        val gender = remember {
            mutableStateOf("")
        }

        val genderOptions = getGenderOptions()
        Text(
            text = stringResource(id = R.string.patient_screen_form_header),
            style = TextStyle(fontSize = 40.sp, fontFamily = FontFamily.Default)
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            label = { Text(text = stringResource(id = R.string.patient_screen_form_name)) },
            value = name.value,
            onValueChange = { name.value = it }
        )

        Spacer(modifier = Modifier.height(15.dp))

        TextField(
            label = { Text(text = stringResource(id = R.string.patient_screen_form_surname)) },
            value = surname.value,
            onValueChange = { surname.value = it }
        )

        Spacer(modifier = Modifier.height(15.dp))

        DropDownMenu(items = genderOptions, onItemSelected = {
            gender.value = it.toCode()
        })

        Spacer(modifier = Modifier.height(15.dp))

        Box(modifier = Modifier.padding(40.dp, 0.dp, 40.dp, 0.dp)) {
            Button(
                onClick = {onSave()},
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