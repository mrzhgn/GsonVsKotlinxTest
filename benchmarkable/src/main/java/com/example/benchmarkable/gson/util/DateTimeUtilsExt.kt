package com.example.benchmarkable.gson.util

import java.time.OffsetDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.TimeZone
import java.util.concurrent.TimeUnit

fun String.fromIsoOffsetDateTimeString(): OffsetDateTime {
    return DateTimeFormatter.ISO_DATE_TIME.parse(this, OffsetDateTime::from)
        .withOffsetSameInstant(defaultZoneOffset())
}

fun defaultZoneOffset(): ZoneOffset {
    return ZoneOffset.ofTotalSeconds(
        TimeUnit.MILLISECONDS.toSeconds(TimeZone.getDefault().rawOffset.toLong()).toInt()
    )
}