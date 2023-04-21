package com.aueb.healthmonitor.observationparams

import com.aueb.healthmonitor.recordConverters.CategoryParams
import com.aueb.healthmonitor.recordConverters.CodeParams
import com.aueb.healthmonitor.recordConverters.ObservationParams
import com.aueb.healthmonitor.recordConverters.ValueParams
import org.hl7.fhir.r4.model.Observation

class BGObservationParams {
    companion object{
        fun createBloodGlucose(): ObservationParams {
            val catParams = CategoryParams(
                display = "Laboratory",
                code = "laboratory",
                system = "http://terminology.hl7.org/CodeSystem/observation-category",
            )
            val valParams = ValueParams(
                unit = "mg/dL",
                code = "mg/dL",
                system = "http://unitsofmeasure.org",
            )
            val codeParams = CodeParams(
                display = "Glucose [Moles/volume] in Blood",
                code = "2339-0",
                system = "http://loinc.org",
            )
            val params = ObservationParams(
                observationStatusType = Observation.ObservationStatus.FINAL,
                codeParams = codeParams,
                valueParams = valParams,
                categoryParams = catParams
            )
            return params
        }
    }
}