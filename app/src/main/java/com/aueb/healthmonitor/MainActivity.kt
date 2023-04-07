package com.aueb.healthmonitor

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.aueb.healthmonitor.httpServices.HapiFhirServices

//import ca.uhn.fhir.context.FhirContext
//import org.hl7.fhir.instance.model.api.IBaseBundle
//import org.hl7.fhir.r4.model.Patient


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //////////////////////////////////////////////
        val serverBase = "http://192.168.2.3:8080/fhir/"

        HapiFhirServices.getObservationsByPatientId("1")

//        val bundle = client.search<IBaseBundle>()
//            .forResource(Patient::class.java)
//            .where(Patient.NAME.matches().value("John"))
//            .returnBundle(Bundle::class.java)
//            .execute()
        val s0 = "ddd"
        //////////////////////////////////////////////
    }
}