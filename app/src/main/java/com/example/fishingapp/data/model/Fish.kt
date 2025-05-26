package com.example.fishingapp.data.model

data class Fish(
    val id: String = "",
    val name: String = "",
    val nameLower: String = name.lowercase(),  // Добавляем поле для поиска в нижнем регистре
    val averageWeight: Double = 0.0,  // в кг
    val averageLength: Double = 0.0,  // в см
    val waterType: String = "",       // LAKE, RIVER, SEA
    val description: String = "",
    val baitTypes: List<String> = listOf(),  // на что клюет
    val photoUrl: String = "",
    val seasonality: List<String> = listOf(), // в какие сезоны активна
    val bestTimeOfDay: String = ""    // лучшее время для ловли
) {
    companion object {
        const val WATER_TYPE_LAKE = "LAKE"
        const val WATER_TYPE_RIVER = "RIVER"
        const val WATER_TYPE_SEA = "SEA"
    }
} 