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

        fun toFhirObservation(record: HeartRateRecord): JsonObject {
            //TODO: need fix here for patient id :: it comes from params
            //TODO: need to fix hardcoded strings
            val patientId = "1"
            val observation = Observation()
                .setSubject(Reference("Patient/$patientId"))
                .setEffective(DateTimeType(Date.from(record.samples[0].time))) //TODO: need fix here for list logic
                .setStatus(Observation.ObservationStatus.FINAL)
                .setCode(CodeableConcept().addCoding(Coding().setCode("8867-4").setDisplay("Heart rate").setSystem("http://loinc.org")))
                .setValue(Quantity().setValue(record.samples[0].beatsPerMinute).setUnit("bpm")) //TODO: need fix here for list logic
                .setCategory(listOf(CodeableConcept().addCoding(Coding().setCode("vital-signs").setDisplay("Vital Signs").setSystem("http://terminology.hl7.org/CodeSystem/observation-category"))))

//            val ctx: FhirContext = FhirContext.forR4()
//            val parser = ctx.newJsonParser()
//            val json: String = parser.encodeResourceToString(observation)
//
//            val jsonObj = Gson().fromJson(json, JsonObject::class.java)
            val jsonObj = convertToJsonObject(observation)
            return jsonObj
        }

    }
}