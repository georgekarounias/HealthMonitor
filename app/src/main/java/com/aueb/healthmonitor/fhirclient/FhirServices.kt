package com.aueb.healthmonitor.fhirclient

import android.content.Context
import androidx.health.connect.client.records.HeartRateRecord
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.recordConverters.HRRecordConverter
import com.aueb.healthmonitor.staticVariables.StaticVariables
import com.aueb.healthmonitor.utils.hashString
import com.aueb.healthmonitor.utils.setValueToSharedPreferences
import com.aueb.healthmonitor.utils.toastMessage
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.*
import java.util.Date

class FhirServices {
    companion object{

        fun checkServerAvailability(ctx: Context?){
            val client = RestClient.getClient()
            try {
                client!!.capabilities().ofType(CapabilityStatement::class.java).execute()
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_server_running))
            }catch (e: Exception){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_server_down))
            }
        }

        fun createPatient(id: String, name: String, surname: String, sex: Enumerations.AdministrativeGender, birthDate: Date, ctx: Context): Boolean{
            val existingPatient = getPatientByIdentifier(id, null)
            if(existingPatient != null){
                toastMessage(ctx, ctx.getString(R.string.fhir_patient_exist))
                return false
            }
            setValueToSharedPreferences(ctx, StaticVariables.ASP_PatientName, name)
            setValueToSharedPreferences(ctx, StaticVariables.ASP_PatientSurname, surname)
            val patient = Patient()
            patient.addIdentifier().setSystem("http://example.com/patient-identifier").value = id
            patient.addName().setFamily(hashString(name)).addGiven(hashString(surname))
            patient.gender = sex
            patient.birthDate = birthDate
            val client = RestClient.getClient()
            try {
                client!!.create().resource(patient).execute()
                toastMessage(ctx, ctx.getString(R.string.fhir_patient_created))
                return true
            }catch (e: Exception){
                toastMessage(ctx, ctx.getString(R.string.message_fhir_resource_not_found))
                return false
            }
        }

        fun getPatientByIdentifier(id: String, ctx: Context?): IBaseResource? {
            val client = RestClient.getClient()
            try {
                return client!!.read().resource("Patient").withId(id).execute()
            }catch (e: Exception){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_resource_not_found))
                return null
            }
        }

        fun createHeartRateObservation(records: List<HeartRateRecord>, patientId: String, ctx: Context?): Bundle? {
            val client = RestClient.getClient()
            val bundle = HRRecordConverter.createHRBundle(records, patientId)
            try {
                return client?.transaction()?.withBundle(bundle)?.execute()
            }catch (e: Exception){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_transaction_failed))
                return null
            }
        }
    }
}