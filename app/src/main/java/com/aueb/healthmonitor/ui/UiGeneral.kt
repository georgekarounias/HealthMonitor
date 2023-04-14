package com.aueb.healthmonitor.ui

import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.ui.components.dropdown.MenuItem
import org.hl7.fhir.r4.model.Enumerations

enum class Screen(val route: String, val titleId: Int, val hasMenuItem: Boolean = true) {
    HomeScreen("home_screen", R.string.home_screen),
    PatientScreen("patient_screen", R.string.patient_screen),
    VitalsScreen("vitals_screen", R.string.vitals_screen),
    InfoScreen("info_screen", R.string.info_screen),
    SettingScreen("settings_screen", R.string.info_screen)
}

fun getGernderListFirstElement(): MenuItem{
    val firstElement = getGenderOptions().firstOrNull()
    if(firstElement == null){
        val value = Enumerations.AdministrativeGender.MALE
        return MenuItem(name = value.name, code = value.toCode())
    }
    return firstElement
}

fun getGernderListFirstCode(): String{
    val firstElement = getGenderOptions().firstOrNull()
    if(firstElement == null){
        return Enumerations.AdministrativeGender.MALE.toCode()
    }
    return firstElement.code
}

fun getGenderOptions(): List<MenuItem>{
    val sequence = Enumerations.AdministrativeGender.values().asSequence().filter{ x->x.name != "NULL"}
    val list = sequence.map{x-> MenuItem(name = x.name, code = x.toCode())}.toList()
    return list
}

fun getGenderByCode(code: String): Enumerations.AdministrativeGender{
    return Enumerations.AdministrativeGender.values().asSequence().filter{ x->x.toCode() == code}.first()
}