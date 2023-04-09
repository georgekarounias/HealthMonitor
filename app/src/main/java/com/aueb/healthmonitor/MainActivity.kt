package com.aueb.healthmonitor


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.health.connect.client.records.HeartRateRecord
import com.aueb.healthmonitor.fhirclient.FhirServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.Instant

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //////////////////////////////////////////////
        //HapiFhirServices.getObservationsByPatientId("1")
        ////////////TODO: ADD Health CONNECT
//        val now = Instant.now()
//        val heartRateRecord = HeartRateRecord(
//            Instant.now(),
//            null,
//            Instant.now().plusSeconds(300),
//            null,
//            listOf(
//                HeartRateRecord.Sample(now.minusSeconds(10), 70),
//                HeartRateRecord.Sample(now, 80)
//            )
//        )
//        val observation = toFhirObservation(heartRateRecord, "1")
//
//        HapiFhirServices.createObservation(observation)
        ///////////

        GlobalScope.launch(Dispatchers.IO) {
//            val patient = withContext(Dispatchers.Default) {
//                FhirServices.getPatientByIdentifier("1")
//            }
//            val xx = patient.toString()

            withContext(Dispatchers.Default) {
                    val now = Instant.now()
                    val heartRateRecord = HeartRateRecord(
                        Instant.now(),
                        null,
                        Instant.now().plusSeconds(300),
                        null,
                        listOf(
                            HeartRateRecord.Sample(now.minusSeconds(10), 66),
                            HeartRateRecord.Sample(now, 23)
                        )
                    )
                FhirServices.createHeartRateObservation(heartRateRecord, "1")
            }
        }
    }

}