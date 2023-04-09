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


        GlobalScope.launch(Dispatchers.IO) {
//            val patient = withContext(Dispatchers.Default) {
//                FhirServices.getPatientByIdentifier("1")
//            }
//            val xx = patient.toString()

            withContext(Dispatchers.Default) {
                val heartRecords = mutableListOf<HeartRateRecord>()
                val now = Instant.now()
                val heartRateRecord = HeartRateRecord(
                    Instant.now(),
                    null,
                    Instant.now().plusSeconds(300),
                    null,
                    listOf(
                        HeartRateRecord.Sample(now.minusSeconds(10), 55),
                        HeartRateRecord.Sample(now, 44)
                    )
                )
                heartRecords.add(heartRateRecord)
                val heartRateRecord2 = HeartRateRecord(
                    Instant.now(),
                    null,
                    Instant.now().plusSeconds(400),
                    null,
                    listOf(
                        HeartRateRecord.Sample(now.minusSeconds(100), 22),
                        HeartRateRecord.Sample(now, 33)
                    )
                )
                heartRecords.add(heartRateRecord2)
                FhirServices.createHeartRateObservation(heartRecords, "1")
            }

        }

    }

}