package com.aueb.healthmonitor.recordConverters

import androidx.health.connect.client.records.HeartRateRecord
import org.hl7.fhir.r4.model.*
import java.util.*

class RecordConverter {
    companion object{
        fun createSingleHRObservation(sample: HeartRateRecord.Sample, patientId: String): Observation
        {
            val patient = "Patient/$patientId"
            val params = ObservationParams.createHeartRate()
            return Observation()
                .setSubject(Reference(patient))
                .setEffective(DateTimeType(Date.from(sample.time)))
                .setStatus(params.observationStatusType)
                .setCode(CodeableConcept().addCoding(Coding().setCode(params.codeParams.code).setDisplay(params.codeParams.display).setSystem(params.codeParams.system)))
                .setValue(Quantity().setValue(sample.beatsPerMinute).setUnit(params.valueParams.unit).setCode(params.valueParams.code).setSystem(params.valueParams.system))
                .setCategory(listOf(CodeableConcept().addCoding(Coding().setCode(params.categoryParams.code).setDisplay(params.categoryParams.display).setSystem(params.categoryParams.system))))
        }

        fun createMultipleHRObservations(record: HeartRateRecord, patientId: String): List<Observation>{
            val observationList = mutableListOf<Observation>()

            for (heartRate in record.samples) {
                val observation = createSingleHRObservation(heartRate, patientId)
                observationList.add(observation)
            }
            return observationList
        }

        fun createHRBundle(record: HeartRateRecord, patientId: String): Bundle {
            val observationList = createMultipleHRObservations(record, patientId)
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