package com.gavinthomas.herodatakick.network

import com.squareup.moshi.Json

data class Product(
    @field:Json(name = "gtin14") val id: String,
    @field:Json(name = "name") val name: String,
    @field:Json(name = "size") var size: String?,
    var sizeValue: Double?
)


fun sizeSelector(p: Product): Double? = p.sizeValue
fun nameSelector(p: Product): String = p.name


