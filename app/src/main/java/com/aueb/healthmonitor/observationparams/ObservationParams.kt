package com.aueb.healthmonitor.recordConverters

import org.hl7.fhir.r4.model.Observation

data class ObservationParams(
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