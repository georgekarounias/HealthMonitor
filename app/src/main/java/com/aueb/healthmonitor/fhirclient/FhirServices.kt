package com.aueb.healthmonitor.fhirclient

import android.content.Context
import androidx.health.connect.client.records.HeartRateRecord
import ca.uhn.fhir.rest.api.MethodOutcome
import com.aueb.healthmonitor.R
import com.aueb.healthmonitor.patient.PatientManager
import com.aueb.healthmonitor.recordConverters.HRRecordConverter
import com.aueb.healthmonitor.staticVariables.StaticVariables.Companion.PatientIdSystemCode
import com.aueb.healthmonitor.utils.hashString
import com.aueb.healthmonitor.utils.toastMessage
import org.hl7.fhir.instance.model.api.IBaseBundle
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.*
import java.util.*


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

        fun createPatient(id: String, name: String, surname: String, sex: Enumerations.AdministrativeGender, birthDate: Date, ctx: Context, patientManager: PatientManager): Boolean{
            val existingPatient = getPatientByIdentifier(id, null)
            if(existingPatient != null){
                toastMessage(ctx, ctx.getString(R.string.fhir_patient_exist))
                return false
            }
            patientManager.SavePatientInfo(name, surname)
            val patient = Patient()
            patient.addIdentifier().setSystem(PatientIdSystemCode).value = id
            patient.addName().setFamily(hashString(name)).addGiven(hashString(surname))
            patient.gender = sex
            patient.birthDate = birthDate
            val client = RestClient.getClient()
            try {
                client!!.create().resource(patient).execute()
                toastMessage(ctx, ctx.getString(R.string.fhir_patient_created))
                return true
            }catch (e: Exception){
                toastMessage(ctx, ctx.getString(R.string.message_fhir_error_on_patient_create))
                return false
            }
        }

        fun getPatientByIdentifier(id: String, ctx: Context?): IBaseResource? {
            val client = RestClient.getClient()
            try {
                val results: Bundle = client!!.search<Bundle>()
                    .forResource(Patient::class.java)
                    .where(Patient.IDENTIFIER.exactly().systemAndCode(PatientIdSystemCode, id))
                    .returnBundle(Bundle::class.java)
                    .execute()
                 var fhirPatient = results.entry.asSequence().first().resource as Patient
                return fhirPatient
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

        fun saveDeviceInfo(manufacturer: String, model: String, patientId: String, ctx: Context?): MethodOutcome?{
            val existingPatient = getPatientByIdentifier(patientId, null)
            if(existingPatient == null){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_patient_not_found))
                return null
            }
            val device = Device()
            device.type.text = "Smart Watch"
            device.manufacturer = manufacturer
            device.modelNumber = model
            val patient = existingPatient as Patient
            val patientRef = Reference(patient.id)
            patientRef.display = patient.nameFirstRep.nameAsSingleString
            device.patient = patientRef

            val client = RestClient.getClient()
            try {
                val deviceOutcome: MethodOutcome = client!!.create()
                    .resource(device)
                    .execute()
                return deviceOutcome
            }catch (e: Exception){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_patient_not_found))
                return null
            }
        }

        fun getConnectedDevices(patientId: String, ctx: Context?): List<Device>{
            // search for all devices
            val existingPatient = getPatientByIdentifier(patientId, null)
            if(existingPatient == null){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_patient_device_not_found))
                return listOf()
            }
            val client = RestClient.getClient()
            try {
                // search for the patient's devices
                val deviceResults: Bundle = client!!
                    .search<IBaseBundle>()
                    .forResource(Device::class.java)
                    .where(Device.PATIENT.hasId(existingPatient.idElement.value))
                    .returnBundle(Bundle::class.java)
                    .execute()
                return deviceResults.entry.asSequence().map{x->x.resource as Device}.toList()
            }catch (e: Exception){
                toastMessage(ctx, ctx?.getString(R.string.message_fhir_patient_device_not_found))
                return listOf()
            }
        }
    }
}