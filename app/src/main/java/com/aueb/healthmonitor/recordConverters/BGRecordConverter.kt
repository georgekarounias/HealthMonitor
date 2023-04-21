package com.aueb.healthmonitor.recordConverters

import androidx.health.connect.client.records.BloodGlucoseRecord
import com.aueb.healthmonitor.enums.HealthRecordType
import com.aueb.healthmonitor.observationparams.BGObservationParams
import org.hl7.fhir.r4.model.Bundle
import org.hl7.fhir.r4.model.CodeableConcept
import org.hl7.fhir.r4.model.Coding
import org.hl7.fhir.r4.model.DateTimeType
import org.hl7.fhir.r4.model.Identifier
import org.hl7.fhir.r4.model.Observation
import org.hl7.fhir.r4.model.Quantity
import org.hl7.fhir.r4.model.Reference
import java.util.Date

class BGRecordConverter {
    companion object{
        private fun createSingleBGObservation(value: BloodGlucoseRecord, patientId: String): Observation
        {
            val patient = "Patient/$patientId"
            val params = BGObservationParams.createBloodGlucose()
            val system = RecordConverter.systemObservationIdentifier()
            val key = RecordConverter.createObservationIdentifier(value.time.toString(),value.level.inMilligramsPerDeciliter.toString(),
                HealthRecordType.BloodGlucose, patientId)
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
                .setValue(Quantity().setValue(value.level.inMilligramsPerDeciliter).setUnit(params.valueParams.unit).setCode(params.valueParams.code).setSystem(params.valueParams.system))
                .setCategory(listOf(CodeableConcept().addCoding(Coding().setCode(params.categoryParams.code).setDisplay(params.categoryParams.display).setSystem(params.categoryParams.system))))
        }

        private fun createMultipleBGObservations(records: List<BloodGlucoseRecord>, patientId: String): List<Observation>{
            val observationList = mutableListOf<Observation>()
            for(record in records) {
                val observation = createSingleBGObservation(record, patientId)
                observationList.add(observation)
            }
            return observationList
        }

        fun createBGBundle(records: List<BloodGlucoseRecord>, patientId: String): Bundle {
            val observationList = createMultipleBGObservations(records, patientId)
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