package com.aueb.healthmonitor.recordConverters

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Air
import androidx.compose.material.icons.filled.Bloodtype
import androidx.compose.material.icons.filled.HeartBroken
import androidx.health.connect.client.records.BloodGlucoseRecord
import androidx.health.connect.client.records.BloodPressureRecord
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.OxygenSaturationRecord
import com.aueb.healthmonitor.models.DataTableOptions
import com.aueb.healthmonitor.ui.components.datatable.DataTableItem
import com.aueb.healthmonitor.utils.displayIsoDateString

fun<T> SetDataTableOptions(records: List<T>?): DataTableOptions{
    var options = DataTableOptions()
    if(records.isNullOrEmpty()){
        return options
    }
    val firstElement = records.first()

    when(firstElement){
        is HeartRateRecord ->{
            options = DataTableOptions(
                icon = Icons.Default.HeartBroken,
                title = "Heart Rate",
                records = heartRateDTConverter(records as List<HeartRateRecord>)
            )
        }
        is OxygenSaturationRecord ->{
            options = DataTableOptions(
                icon = Icons.Default.Air,
                title = "O2sp",
                records = oxygenSaturationDTConverter(records as List<OxygenSaturationRecord>)
            )
        }
        is BloodGlucoseRecord ->{
            options = DataTableOptions(
                icon = Icons.Default.Bloodtype,
                title = "Blood Glucose",
                records = bloodGlucoseDTConverter(records as List<BloodGlucoseRecord>)
            )
        }
        is BloodPressureRecord ->{
            options = DataTableOptions(
                icon = Icons.Default.Bloodtype,
                title = "Blood Pressure",
                records = bloodPressureDTConverter(records as List<BloodPressureRecord>)
            )
        }
    }
    return options
}

private fun heartRateDTConverter(records: List<HeartRateRecord>): List<DataTableItem>{
    val dtRecords = mutableListOf<DataTableItem>()
    for(record in records){
        for(sample in record.samples){
            val dtRecord = DataTableItem(
                type = "Heart Rate",
                value = sample.beatsPerMinute.toString(),
                date = displayIsoDateString(sample.time.toString())
            )
            dtRecords.add(dtRecord)
        }
    }
    return dtRecords
}

private fun oxygenSaturationDTConverter(records: List<OxygenSaturationRecord>): List<DataTableItem>{
    val dtRecords = mutableListOf<DataTableItem>()
    for(record in records){
        val dtRecord = DataTableItem(
            type = "O2sp",
            value = record.percentage.toString(),
            date = displayIsoDateString(record.time.toString())
        )
        dtRecords.add(dtRecord)
    }
    return dtRecords
}

private fun bloodGlucoseDTConverter(records: List<BloodGlucoseRecord>): List<DataTableItem>{
    val dtRecords = mutableListOf<DataTableItem>()
    for(record in records){
        val dtRecord = DataTableItem(
            type = "Blood Glucose",
            value = record.level.toString(),
            date = displayIsoDateString(record.time.toString())
        )
        dtRecords.add(dtRecord)
    }
    return dtRecords
}

private fun bloodPressureDTConverter(records: List<BloodPressureRecord>): List<DataTableItem>{
    val dtRecords = mutableListOf<DataTableItem>()
    for(record in records){
        val dtRecord = DataTableItem(
            type = "Blood Glucose",
            value = "DIA: "+ record.diastolic.toString()+" | "+"SYS: "+ record.systolic.toString(),
            date = displayIsoDateString(record.time.toString())
        )
        dtRecords.add(dtRecord)
    }
    return dtRecords
}