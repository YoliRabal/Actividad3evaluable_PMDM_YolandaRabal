package com.example.actividad3evaluable_pmdm_yolandarabal.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.actividad3evaluable_pmdm_yolandarabal.R
import com.example.actividad3evaluable_pmdm_yolandarabal.model.EquipoJSON
import com.example.actividad3evaluable_pmdm_yolandarabal.preferences.PreferencesManager

class EquiposAdapter(private val equiposList: List<EquipoJSON>) :
    RecyclerView.Adapter<EquiposAdapter.EquipoViewHolder>() {

    class EquipoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreEquipo: TextView = itemView.findViewById(R.id.textNombreEquipo)
        val imagenEquipo: ImageView = itemView.findViewById(R.id.imageEquipo)
        val btnFavorito: ImageView = itemView.findViewById(R.id.btnFavorito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipo, parent, false)
        return EquipoViewHolder(view)
    }

    override fun onBindViewHolder(holder: EquipoViewHolder, position: Int) {
        val equipo = equiposList[position]
        val context = holder.itemView.context

        holder.nombreEquipo.text = equipo.strTeam ?: "Nombre no disponible"

        // 🔥 Cargar el logo del equipo
        Glide.with(context)
            .load(equipo.strBadge)
            //.placeholder(R.drawable.placeholder) // opcional, por si no carga
            .into(holder.imagenEquipo)

        val isFavorito = PreferencesManager.isFavorito(context, equipo.idTeam ?: "")

        holder.btnFavorito.setImageResource(
            if (isFavorito) android.R.drawable.btn_star_big_on
            else android.R.drawable.btn_star_big_off
        )

        holder.btnFavorito.setOnClickListener {
            val nuevoEstado = !PreferencesManager.isFavorito(context, equipo.idTeam ?: "")
            PreferencesManager.setFavorito(context, equipo, nuevoEstado)

            notifyItemChanged(position)
        }
    }

    override fun getItemCount(): Int = equiposList.size
}
