package com.aueb.healthmonitor.utils

import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import com.aueb.healthmonitor.staticVariables.StaticVariables.Companion.ASP_PatientId
import com.aueb.healthmonitor.staticVariables.StaticVariables.Companion.AppSharedPreferences
import java.security.MessageDigest
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*

fun dateTimeWithOffsetOrDefault(time: Instant, offset: ZoneOffset?): ZonedDateTime =
    if (offset != null) {
        ZonedDateTime.ofInstant(time, offset)
    } else {
        ZonedDateTime.ofInstant(time, ZoneId.systemDefault())
    }

fun Duration.formatTime() = String.format(
    "%02d:%02d:%02d",
    this.toHours() % 24,
    this.toMinutes() % 60,
    this.seconds % 60
)

fun Duration.formatHoursMinutes() = String.format(
    "%01dh%02dm",
    this.toHours() % 24,
    this.toMinutes() % 60
)

fun formatDisplayTimeStartEnd(
    startTime: Instant,
    startZoneOffset: ZoneOffset?,
    endTime: Instant,
    endZoneOffset: ZoneOffset?
): String {
    val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT)
    val start = timeFormatter.format(dateTimeWithOffsetOrDefault(startTime, startZoneOffset))
    val end = timeFormatter.format(dateTimeWithOffsetOrDefault(endTime, endZoneOffset))
    return "$start - $end"
}

fun convertInstantToString(
    time: Instant,
    zoneOffset: ZoneOffset?
): String{
    //val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.FULL)
    val timeFormatter = DateTimeFormatter.ISO_DATE_TIME
    return timeFormatter.format(dateTimeWithOffsetOrDefault(time, zoneOffset))
}

fun GetYear(date: Date): Int{
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.time = date
    return calendar[Calendar.YEAR]
}

fun GetMonth(date: Date): Int{
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.time = date
    return calendar[Calendar.MONTH]
}

fun GetDayOfMonth(date: Date): Int{
    val calendar = Calendar.getInstance(TimeZone.getDefault())
    calendar.time = date
    return calendar[Calendar.DAY_OF_MONTH]
}

fun CreateDate(year:Int, month: Int, dayOfMonth: Int, hour: Int, minute: Int): Date {
    val cal = Calendar.getInstance()
    cal[Calendar.YEAR] = year
    cal[Calendar.MONTH] = month
    cal[Calendar.DAY_OF_MONTH] = dayOfMonth
    cal[Calendar.HOUR_OF_DAY] = hour
    cal[Calendar.MINUTE] = minute
    cal[Calendar.SECOND] = 0
    cal[Calendar.MILLISECOND] = 0
    return cal.time
}

fun hashString(input: String): String {
    val bytes = MessageDigest.getInstance("SHA-256").digest(input.toByteArray())
    return bytes.joinToString("") { "%02x".format(it) }
}

fun toastMessage(ctx: Context?, message: String?){
    if(ctx == null || message == null){
        return
    }
    ctx.mainExecutor.execute {
        Toast.makeText(ctx, message, Toast.LENGTH_LONG).show()
    }
}

fun dateToIsoString(date: Date): String {
    //val formatter = DateTimeFormatter.ISO_DATE_TIME//does not ignore time and time zone
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val instant = date.toInstant()
    val zonedDateTime = instant.atZone(ZoneId.systemDefault())
    return formatter.format(zonedDateTime)
}
//fun <T>convertToJsonString(data: T):String{
//    val gsonPretty = GsonBuilder().setPrettyPrinting().create()
//    val jsonString: String = gsonPretty.toJson(data)
//    return jsonString
//}
