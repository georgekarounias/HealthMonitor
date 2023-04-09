package com.aueb.healthmonitor.fhirclient


import ca.uhn.fhir.context.FhirContext
import ca.uhn.fhir.rest.client.api.IGenericClient
import com.aueb.healthmonitor.staticVariables.StaticVariables

class RestClient {
    companion object {
        private var ctx: FhirContext? = null
        private var iGenericClient: IGenericClient? = null

        fun getContext(): FhirContext? {
            if (ctx == null) {
                ctx = FhirContext.forR4()
            }
            return ctx
        }

        fun getClient(): IGenericClient? {
            if (ctx == null) {
                ctx = getContext()
            }
            if (iGenericClient == null) {
                iGenericClient = ctx!!.newRestfulGenericClient(StaticVariables.BASE_URL)
            }
            return iGenericClient
        }
    }
}