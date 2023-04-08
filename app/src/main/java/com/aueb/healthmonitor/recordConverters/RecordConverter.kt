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

        fun toFhirObservation(record: HeartRateRecord, patientId: String): JsonObject {
            val observation = createObservation(record, patientId)
            val jsonObj = convertToJsonObject(observation)
            return jsonObj
        }

    }
}