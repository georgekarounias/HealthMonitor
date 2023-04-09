package com.aueb.healthmonitor.recordConverters

import androidx.health.connect.client.records.HeartRateRecord
import ca.uhn.fhir.context.FhirContext
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import org.hl7.fhir.instance.model.api.IBaseResource
import org.hl7.fhir.r4.model.*
import java.util.*

class RecordConverter {
    companion object{

        private fun convertToJsonObject(fhirObj: IBaseResource): JsonObject{
            val ctx: FhirContext = FhirContext.forR4()
            val parser = ctx.newJsonParser()
            val json: String = parser.encodeResourceToString(fhirObj)
            val jsonObj = Gson().fromJson(json, JsonObject::class.java)
            return jsonObj
        }

        private fun createObservation(record: HeartRateRecord, patientId: String): Observation
        {
            //TODO: need fix here for patient id :: it comes from params
            //TODO: add more set for instance
            //TODO: need to fix hardcoded strings
            val recordValue = record.samples[0].beatsPerMinute
            val recordTime = record.samples[0].time
            val patient = "Patient/$patientId"
            //val params => ena static function me params gia kathe resource pou ipostirizw
            val params = ObservationParams.createHeartRate()//TODO: polimorfismos edw
            //TODELETE
            val observationStatusType = Observation.ObservationStatus.FINAL
            val codeCode = "8867-4"
            val codeDisplayName = "Heart rate"
            val codeSystem = "http://loinc.org"
            val valueUnit = "bpm"
            val valueCode = "/min"
            val valueSystem = "http://unitsofmeasure.org"
            val categoryCode = "vital-signs"
            val categoryDisplay = "Vital Signs"
            val categorySystem = "http://terminology.hl7.org/CodeSystem/observation-category"
            //TODELETE UNTIL HERE
            val observation = Observation()
                .setSubject(Reference(patient))
                .setEffective(DateTimeType(Date.from(recordTime)))
                .setStatus(observationStatusType)
                .setCode(CodeableConcept().addCoding(Coding().setCode(codeCode).setDisplay(codeDisplayName).setSystem(codeSystem)))
                .setValue(Quantity().setValue(recordValue).setUnit(valueUnit).setCode(valueCode).setSystem(valueSystem))
                .setCategory(listOf(CodeableConcept().addCoding(Coding().setCode(categoryCode).setDisplay(categoryDisplay).setSystem(categorySystem))))
            return observation
        }

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

        fun toFhirObservation(record: HeartRateRecord, patientId: String): JsonObject {
            val observation = createObservation(record, patientId)
            val jsonObj = convertToJsonObject(observation)
            return jsonObj
        }

    }
}