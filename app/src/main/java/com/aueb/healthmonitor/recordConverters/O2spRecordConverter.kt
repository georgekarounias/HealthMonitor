package com.aueb.healthmonitor.recordConverters

import androidx.health.connect.client.records.OxygenSaturationRecord
import com.aueb.healthmonitor.enums.HealthRecordType
import com.aueb.healthmonitor.observationparams.O2spObservationParams
import org.hl7.fhir.r4.model.Bundle
import org.hl7.fhir.r4.model.CodeableConcept
import org.hl7.fhir.r4.model.Coding
import org.hl7.fhir.r4.model.DateTimeType
import org.hl7.fhir.r4.model.Identifier
import org.hl7.fhir.r4.model.Observation
import org.hl7.fhir.r4.model.Quantity
import org.hl7.fhir.r4.model.Reference
import java.util.Date

class O2spRecordConverter {
    companion object{
        private fun createSingleO2spObservation(value: OxygenSaturationRecord, patientId: String): Observation
        {
            val patient = "Patient/$patientId"
            val params = O2spObservationParams.createO2sp()
            val system = RecordConverter.systemObservationIdentifier()
            val key = RecordConverter.createObservationIdentifier(value.time.toString(),value.percentage.value.toString(),
                HealthRecordType.OxygenSaturation, patientId)
            return Observation()
                .setIdentifier(
                    listOf(
                        Identifier()
                            .setSystem(system)
                            .setValue(key)
                    )
                )
                .setSubject(Reference(patient))
                .setEffective(DateTimeType(Date.from(value.time)))
                .setStatus(params.observationStatusType)
                .setCode(CodeableConcept().addCoding(Coding().setCode(params.codeParams.code).setDisplay(params.codeParams.display).setSystem(params.codeParams.system)))
                .setValue(Quantity().setValue(value.percentage.value).setUnit(params.valueParams.unit).setCode(params.valueParams.code).setSystem(params.valueParams.system))
                .setCategory(listOf(CodeableConcept().addCoding(Coding().setCode(params.categoryParams.code).setDisplay(params.categoryParams.display).setSystem(params.categoryParams.system))))
        }

        private fun createMultipleO2spObservations(records: List<OxygenSaturationRecord>, patientId: String): List<Observation>{
            val observationList = mutableListOf<Observation>()
            for(record in records) {
                val observation = createSingleO2spObservation(record, patientId)
                observationList.add(observation)
            }
            return observationList
        }

        fun createO2spBundle(records: List<OxygenSaturationRecord>, patientId: String): Bundle {
            val observationList = createMultipleO2spObservations(records, patientId)
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