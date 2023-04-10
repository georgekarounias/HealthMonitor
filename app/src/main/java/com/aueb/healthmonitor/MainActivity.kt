package com.aueb.healthmonitor


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aueb.healthmonitor.ui.mainpage.MenuOptions

//import com.aueb.healthmonitor.healthconnect.HealthConnectApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val healthConnectManager = (application as BaseApplication).healthConnectManager
        val patientManager = (application as BaseApplication).patientManager


        setContent {
            MenuOptions(healthConnectManager = healthConnectManager)
        }

        //setContentView(R.layout.activity_main)
//
//        setContent {
//            //HealthConnectApp(healthConnectManager = healthConnectManager)
//        }

        GlobalScope.launch(Dispatchers.IO) {
//            val ss = withContext(Dispatchers.Default){
//                val ee = FhirServices.checkServerAvailability(applicationContext)
//            }
//
//            val sss = ss

            //Get Patient
//            val patient = withContext(Dispatchers.Default) {
//                FhirServices.getPatientByIdentifier("1", applicationContext)
//            }
//            val xx = patient.toString()

            //Create heart rate observations
//            withContext(Dispatchers.Default) {
//                val heartRecords = mutableListOf<HeartRateRecord>()
//                val now = Instant.now()
//                val heartRateRecord = HeartRateRecord(
//                    Instant.now(),
//                    null,
//                    Instant.now().plusSeconds(300),
//                    null,
//                    listOf(
//                        HeartRateRecord.Sample(now.minusSeconds(10), 55),
//                        HeartRateRecord.Sample(now, 44)
//                    )
//                )
//                heartRecords.add(heartRateRecord)
//                val heartRateRecord2 = HeartRateRecord(
//                    Instant.now(),
//                    null,
//                    Instant.now().plusSeconds(400),
//                    null,
//                    listOf(
//                        HeartRateRecord.Sample(now.minusSeconds(100), 22),
//                        HeartRateRecord.Sample(now, 33)
//                    )
//                )
//                heartRecords.add(heartRateRecord2)
//                FhirServices.createHeartRateObservation(heartRecords, "1")
//           }

        }

    }

}