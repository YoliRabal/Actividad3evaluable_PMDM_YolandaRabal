package com.example.actividad3evaluable_pmdm_yolandarabal.model

import java.io.Serializable

data class LigaJSON(
    val idLeague: String?,
    val strLeague: String?,
    val strSport: String?,
    val strLeagueAlternate: String?
) : Serializable
