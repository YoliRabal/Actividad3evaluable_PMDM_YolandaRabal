package com.example.actividad3evaluable_pmdm_yolandarabal.preferences

import android.content.Context
import com.example.actividad3evaluable_pmdm_yolandarabal.model.EquipoJSON
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object PreferencesManager {

    private const val PREFS_NAME = "favoritos_prefs"
    private const val FAVORITOS_KEY = "favoritos"

    fun isFavorito(context: Context, idEquipo: String): Boolean {
        val favoritos = getFavoritos(context)
        return favoritos.any { it.idTeam == idEquipo }
    }

    fun setFavorito(context: Context, equipo: EquipoJSON, favorito: Boolean) {
        val favoritos = getFavoritos(context).toMutableList()

        if (favorito) {
            if (favoritos.none { it.idTeam == equipo.idTeam }) {
                favoritos.add(equipo)
            }
        } else {
            favoritos.removeAll { it.idTeam == equipo.idTeam }
        }

        saveFavoritos(context, favoritos)
    }

    fun getFavoritos(context: Context): List<EquipoJSON> {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val json = sharedPreferences.getString(FAVORITOS_KEY, "[]")
        val type = object : TypeToken<List<EquipoJSON>>() {}.type
        return Gson().fromJson(json, type)
    }

    private fun saveFavoritos(context: Context, favoritos: List<EquipoJSON>) {
        val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val json = Gson().toJson(favoritos)
        editor.putString(FAVORITOS_KEY, json)
        editor.apply()
    }
}
