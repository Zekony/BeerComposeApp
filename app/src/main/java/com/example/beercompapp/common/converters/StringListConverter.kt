package com.example.beercompapp.common.converters

import androidx.room.TypeConverter

class StringListConverter {

    @TypeConverter
    fun fromString(string: String): List<String> {
        return string.split(",")
    }

    @TypeConverter
    fun toString(stringList: List<String>): String {
        return stringList.joinToString { ", " }
    }
}