package com.aueb.healthmonitor.httpServices

import ca.uhn.fhir.context.FhirContext
import com.google.gson.Gson
import com.google.gson.JsonObject
import org.hl7.fhir.r4.model.Bundle
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HapiFhirServices {
    companion object{

        private val retroInstance: IRetrofitService =
            RetrofitInstance.getRetroInstance().create(IRetrofitService::class.java)

        fun getObservationsByPatientId(patientId: String)
        {
            val call = retroInstance.getObservationsByPatientId(patientId)
            call.enqueue(object : Callback<JsonObject>{
                override fun onResponse(call: Call<JsonObject>, response: Response<JsonObject>) {
                    val json = response.body().toString()
                    val fhirCtx = FhirContext.forR4()
                    val bundle = fhirCtx.newJsonParser().parseResource(Bundle::class.java, json) as Bundle
                }

                override fun onFailure(call: Call<JsonObject>, t: Throwable) {

                }
            })
        }
    }
}