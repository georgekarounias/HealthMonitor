package com.aueb.healthmonitor.recordConverters

import androidx.health.connect.client.records.HeartRateRecord
import com.aueb.healthmonitor.observationparams.HRObservationParams
import org.hl7.fhir.r4.model.*
import java.util.*

class HRRecordConverter {
    companion object{
        private fun createSingleHRObservation(sample: HeartRateRecord.Sample, patientId: String): Observation
        {
            val patient = "Patient/$patientId"
            val params = HRObservationParams.createHeartRate()
            return Observation()
                .setSubject(Reference(patient))
                .setEffective(DateTimeType(Date.from(sample.time)))
                .setStatus(params.observationStatusType)
                .setCode(CodeableConcept().addCoding(Coding().setCode(params.codeParams.code).setDisplay(params.codeParams.display).setSystem(params.codeParams.system)))
                .setValue(Quantity().setValue(sample.beatsPerMinute).setUnit(params.valueParams.unit).setCode(params.valueParams.code).setSystem(params.valueParams.system))
                .setCategory(listOf(CodeableConcept().addCoding(Coding().setCode(params.categoryParams.code).setDisplay(params.categoryParams.display).setSystem(params.categoryParams.system))))
        }

        private fun createMultipleHRObservations(records: List<HeartRateRecord>, patientId: String): List<Observation>{
            val observationList = mutableListOf<Observation>()
            for(record in records) {
                for (heartRate in record.samples) {
                    val observation = createSingleHRObservation(heartRate, patientId)
                    observationList.add(observation)
                }
            }
            return observationList
        }

        fun createHRBundle(records: List<HeartRateRecord>, patientId: String): Bundle {
            val observationList = createMultipleHRObservations(records, patientId)
            val bundle = Bundle()
            bundle.type = Bundle.BundleType.TRANSACTION
            for(obs in observationList){
                val observationEntry = bundle.addEntry()
                observationEntry.resource = obs
                observationEntry.request = Bundle.BundleEntryRequestComponent()
                observationEntry.request.method = Bundle.HTTPVerb.POST
                observationEntry.request.url = "Observation"
            }
            return bundle
        }
    }
}