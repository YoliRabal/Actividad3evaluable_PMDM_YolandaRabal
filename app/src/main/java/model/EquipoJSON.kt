package com.example.actividad3evaluable_pmdm_yolandarabal.model

import java.io.Serializable

data class EquipoJSON(
    val idTeam: String?,
    val strTeam: String?,
    val strBadge: String?,
    val strStadium: String?,
    val strCountry: String?
) : Serializable
