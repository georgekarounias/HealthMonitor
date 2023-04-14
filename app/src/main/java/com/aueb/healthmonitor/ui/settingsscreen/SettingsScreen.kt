package com.aueb.healthmonitor.ui.settingsscreen

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.ui.components.headertitle.Header
import com.aueb.healthmonitor.ui.components.textfield.CustomTextField

@Composable
fun SettingsScreen(){
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally) {

            Header(text =stringResource(id = R.string.settings_screen_header) )
            Spacer(modifier = Modifier.height(15.dp))
            CustomTextField(
                label = stringResource(id = R.string.settings_screen_hapi_fhir_url),
                value = "",
                onValueChange = {  },
                modifier = Modifier.fillMaxWidth(0.8f)
            )
        Spacer(modifier = Modifier.height(15.dp))
        Text(text = "For now this is hardcoded in code a static variable")
    }

}