package com.example.actividad3evaluable_pmdm_yolandarabal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.actividad3evaluable_pmdm_yolandarabal.adapters.FavoritosAdapter
import com.example.actividad3evaluable_pmdm_yolandarabal.databinding.FragmentFavouritesBinding
import com.example.actividad3evaluable_pmdm_yolandarabal.preferences.PreferencesManager

class FavouritesFragment : Fragment() {

    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var favoritosAdapter: FavoritosAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        setupRecyclerView()
        cargarFavoritos()

        return binding.root
    }

    private fun setupRecyclerView() {
        favoritosAdapter = FavoritosAdapter(mutableListOf())
        binding.recyclerViewFavoritos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = favoritosAdapter
        }
    }

    private fun cargarFavoritos() {
        val favoritosList = PreferencesManager.getFavoritos(requireContext())
        favoritosAdapter.updateList(favoritosList)
    }
}
