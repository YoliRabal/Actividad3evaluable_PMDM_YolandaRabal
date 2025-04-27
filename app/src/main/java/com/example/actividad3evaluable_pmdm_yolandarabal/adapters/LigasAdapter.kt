package com.example.actividad3evaluable_pmdm_yolandarabal.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.actividad3evaluable_pmdm_yolandarabal.R
import com.example.actividad3evaluable_pmdm_yolandarabal.model.LigaJSON

class LigasAdapter(private val ligasList: List<LigaJSON>) : RecyclerView.Adapter<LigasAdapter.LigaViewHolder>() {

    inner class LigaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreLiga: TextView = itemView.findViewById(R.id.textNombreLiga)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LigaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_liga, parent, false)
        return LigaViewHolder(view)
    }

    override fun onBindViewHolder(holder: LigaViewHolder, position: Int) {
        val liga = ligasList[position]
        holder.nombreLiga.text = liga.strLeague ?: "Nombre no disponible"

        holder.itemView.setOnClickListener {
            val bundle = Bundle().apply {
                putString("nombreLeague", liga.strLeague) // 🔥 pasamos el nombre de la liga ahora
            }
            it.findNavController().navigate(R.id.action_homeFragment_to_leagueFragment, bundle)
        }
    }

    override fun getItemCount(): Int = ligasList.size
}
