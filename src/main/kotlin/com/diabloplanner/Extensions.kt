package com.diabloplanner

import java.time.LocalDateTime
import java.time.format.DateTimeFormatterBuilder
import java.time.temporal.ChronoField
import java.util.*

fun LocalDateTime.format() = this.format(englishDateFormatter)

private val daysLookup = (1..31).associate { it.toLong() to getOrdinal(it) }

private val englishDateFormatter = DateTimeFormatterBuilder()
		.appendPattern("yyyy-MM-dd")
		.toFormatter(Locale.ENGLISH)

private fun getOrdinal(n: Int) = when {
	n in 11..13 -> "${n}th"
	n % 10 == 1 -> "${n}st"
	n % 10 == 2 -> "${n}nd"
	n % 10 == 3 -> "${n}rd"
	else -> "${n}th"
}

fun String.toSlug() = toLowerCase()
		.replace("\n", " ")
		.replace("[^a-z\\d\\s]".toRegex(), " ")
		.split(" ")
		.joinToString("-")
		.replace("-+".toRegex(), "-")

inline fun <reified T : Enum<T>> enumValueOfOrNull(name: String): T? {
	return enumValues<T>().find { it.name == name }
}

fun <T> Optional<T>.orNull(): T? = orElse(null)