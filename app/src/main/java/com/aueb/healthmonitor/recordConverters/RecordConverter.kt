package com.aueb.healthmonitor.recordConverters

import com.aueb.healthmonitor.enums.HealthRecordType
import com.aueb.healthmonitor.utils.hashString

class RecordConverter {
    companion object{
        fun systemObservationIdentifier():String{
            return "hash(isoDateSt,value,type,patientId)"
        }

        fun createObservationIdentifier(isoDateStr: String, value: String, observationType: HealthRecordType, patientId: String ): String{
            val key = isoDateStr + value + observationType.name + patientId
            return hashString(key)
        }
    }
}