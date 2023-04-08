package com.aueb.healthmonitor.recordConverters

import org.hl7.fhir.r4.model.Observation

class ObservationParams{
    companion object{
        fun createHeartRate(): ObservationTypeParams{
            val catParams = CategoryParams(
                display = "Vital Signs",
                code = "vital-signs",
                system = "http://terminology.hl7.org/CodeSystem/observation-category",
            )
            val valParams = ValueParams(
                unit = "bpm",
                code = "/min",
                system = "http://unitsofmeasure.org",
            )
            val codeParams = CodeParams(
                display = "Heart rate",
                code = "8867-4",
                system = "http://loinc.org",
            )
            val params = ObservationTypeParams(
                observationStatusType = Observation.ObservationStatus.FINAL,
                codeParams = codeParams,
                valueParams = valParams,
                categoryParams = catParams
            )
            return params
        }
    }
}

data class ObservationTypeParams(
    val observationStatusType: Observation.ObservationStatus,
    val codeParams: CodeParams,
    val valueParams: ValueParams,
    val categoryParams: CategoryParams
)

data class CodeParams(
    val display:String,
    val code:String,
    val system:String
)

data class ValueParams(
    val unit:String,
    val code:String,
    val system:String,
)

data class CategoryParams(
    val display:String,
    val code:String,
    val system:String
)