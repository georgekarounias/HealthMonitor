package com.aueb.healthmonitor.fhirclient

import androidx.health.connect.client.records.HeartRateRecord
import com.aueb.healthmonitor.recordConverters.RecordConverter
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.Bundle

class FhirServices {
    companion object{
        fun getPatientByIdentifier(id: String): IBaseResource?{
            val client = RestClient.getClient()
            val results: IBaseResource? = client?.read()?.resource("Patient")?.withId("1")?.execute()
            return results
        }

        fun createHeartRateObservation(record: HeartRateRecord, patientId: String): Bundle?{
            val client = RestClient.getClient()
            val bundle = RecordConverter.createHRBundle(record, patientId)
            val response = client?.transaction()?.withBundle(bundle)?.execute()
            return response
        }
    }
}