package com.example.actividad3evaluable_pmdm_yolandarabal.adapters

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

class FavoritosAdapter(private var favoritosList: MutableList<EquipoJSON>) :
    RecyclerView.Adapter<FavoritosAdapter.FavoritoViewHolder>() {

    class FavoritoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nombreEquipo: TextView = itemView.findViewById(R.id.textNombreEquipo)
        val imagenEquipo: ImageView = itemView.findViewById(R.id.imageEquipo)
        val btnFavorito: ImageView = itemView.findViewById(R.id.btnFavorito)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_equipo, parent, false)
        return FavoritoViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoritoViewHolder, position: Int) {
        val equipo = favoritosList[position]
        val context = holder.itemView.context

        holder.nombreEquipo.text = equipo.strTeam ?: "Nombre no disponible"

        // 🔥 Cargar el escudo (asegúrate que en EquipoJSON tienes strBadge bien configurado)
        Glide.with(context)
            .load(equipo.strBadge)  // USAMOS strBadge 🔥
            .into(holder.imagenEquipo)

        // 🔥 Siempre ponemos la estrella en amarillo (favorito activado)
        holder.btnFavorito.setImageResource(android.R.drawable.btn_star_big_on)

        // ⭐ Pulsar en la estrella para quitar favorito
        holder.btnFavorito.setOnClickListener {
            // 1. Quitar de SharedPreferences
            PreferencesManager.setFavorito(context, equipo, false)

            // 2. Quitar de la lista local
            favoritosList.removeAt(position)

            // 3. Notificar que el item se eliminó
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, favoritosList.size)
        }
    }

    override fun getItemCount(): Int = favoritosList.size

    fun updateList(nuevaLista: List<EquipoJSON>) {
        favoritosList.clear()
        favoritosList.addAll(nuevaLista)
        notifyDataSetChanged()
    }
}
