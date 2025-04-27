package com.example.actividad3evaluable_pmdm_yolandarabal.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.actividad3evaluable_pmdm_yolandarabal.adapter.LigasAdapter
import com.example.actividad3evaluable_pmdm_yolandarabal.databinding.FragmentHomeBinding
import com.example.actividad3evaluable_pmdm_yolandarabal.model.LigaJSON
import com.google.gson.Gson
import org.json.JSONArray

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var ligasAdapter: LigasAdapter
    private val ligasList = mutableListOf<LigaJSON>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        setupRecyclerView()
        cargarLigas()

        return binding.root
    }

    private fun setupRecyclerView() {
        ligasAdapter = LigasAdapter(ligasList)
        binding.recyclerViewLigas.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = ligasAdapter
        }
    }

    private fun cargarLigas() {
        val url = "https://www.thesportsdb.com/api/v1/json/3/all_leagues.php"

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val leaguesArray: JSONArray = response.getJSONArray("leagues")
                val gson = Gson()

                for (i in 0 until leaguesArray.length()) {
                    val leagueJsonObject = leaguesArray.getJSONObject(i)
                    val liga = gson.fromJson(leagueJsonObject.toString(), LigaJSON::class.java)

                    // Filtrar solo ligas de fútbol
                    if (liga.strSport == "Soccer") {
                        ligasList.add(liga)
                    }
                }

                ligasAdapter.notifyDataSetChanged()
            },
            { error ->
                Toast.makeText(requireContext(), "Error cargando ligas: ${error.message}", Toast.LENGTH_SHORT).show()
            }
        )

        Volley.newRequestQueue(requireContext()).add(jsonObjectRequest)
    }
}
