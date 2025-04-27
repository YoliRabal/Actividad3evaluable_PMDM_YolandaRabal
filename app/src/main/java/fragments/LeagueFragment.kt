package com.example.actividad3evaluable_pmdm_yolandarabal.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.actividad3evaluable_pmdm_yolandarabal.adapter.EquiposAdapter
import com.example.actividad3evaluable_pmdm_yolandarabal.databinding.FragmentLeagueBinding
import com.example.actividad3evaluable_pmdm_yolandarabal.model.EquipoJSON
import com.google.gson.Gson
import org.json.JSONArray

class LeagueFragment : Fragment() {

    private lateinit var binding: FragmentLeagueBinding
    private lateinit var equiposAdapter: EquiposAdapter
    private val equiposList = mutableListOf<EquipoJSON>()

    private var nombreLeague: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLeagueBinding.inflate(inflater, container, false)

        // Recuperar el nombre de la liga enviado desde HomeFragment
        nombreLeague = arguments?.getString("nombreLeague")

        setupRecyclerView()

        if (!nombreLeague.isNullOrEmpty()) {
            cargarEquipos(nombreLeague!!)
        } else {
            Toast.makeText(requireContext(), "No se recibió el nombre de la liga", Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }

    private fun setupRecyclerView() {
        equiposAdapter = EquiposAdapter(equiposList)
        binding.recyclerViewEquipos.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = equiposAdapter
        }
    }

    private fun cargarEquipos(nombreLiga: String) {
        val nombreFormateado = nombreLiga.replace(" ", "%20")
        val url = "https://www.thesportsdb.com/api/v1/json/3/search_all_teams.php?l=$nombreFormateado"

        Log.d("LeagueFragment", "Cargando URL: $url")

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val teamsArray: JSONArray? = response.optJSONArray("teams")

                equiposList.clear()

                if (teamsArray != null) {
                    val gson = Gson()

                    for (i in 0 until teamsArray.length()) {
                        val teamJsonObject = teamsArray.getJSONObject(i)
                        val equipo = gson.fromJson(teamJsonObject.toString(), EquipoJSON::class.java)
                        equiposList.add(equipo)
                    }

                    equiposAdapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(requireContext(), "No hay equipos disponibles para esta liga", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(requireContext(), "Error cargando equipos: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)
    }
}
