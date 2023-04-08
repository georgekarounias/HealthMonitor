package com.aueb.healthmonitor.httpServices

import com.google.gson.JsonObject
import org.hl7.fhir.r4.model.Bundle
import org.hl7.fhir.r4.model.Observation
import retrofit2.Call
import retrofit2.http.*

interface IRetrofitService
{
    @GET("Observation/_search")
    fun getObservationsByPatientId(@Query("patient")patient: String): Call<JsonObject>

    @POST("Observation")
    fun createObservation(@Body observation: JsonObject): Call<JsonObject>
}