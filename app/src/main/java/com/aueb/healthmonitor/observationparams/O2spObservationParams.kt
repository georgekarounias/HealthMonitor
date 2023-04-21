package com.aueb.healthmonitor.observationparams

import com.aueb.healthmonitor.recordConverters.CategoryParams
import com.aueb.healthmonitor.recordConverters.CodeParams
import com.aueb.healthmonitor.recordConverters.ObservationParams
import com.aueb.healthmonitor.recordConverters.ValueParams
import org.hl7.fhir.r4.model.Observation

class O2spObservationParams {
    companion object{
        fun createO2sp(): ObservationParams {
            val catParams = CategoryParams(
                display = "Vital Signs",
                code = "vital-signs",
                system = "http://terminology.hl7.org/CodeSystem/observation-category",
            )
            val valParams = ValueParams(
                unit = "%",
                code = "%",
                system = "http://unitsofmeasure.org",
            )
            val codeParams = CodeParams(
                display = "Oxygen saturation",
                code = "59408-5",
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