package com.example.fishingapp.ui.main.fishsearch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fishingapp.R
import com.example.fishingapp.data.model.Fish
import com.example.fishingapp.ui.main.fishsearch.adapter.FishAdapter
import com.google.android.material.tabs.TabLayout
import com.google.firebase.firestore.FirebaseFirestore
import androidx.recyclerview.widget.RecyclerView
import android.widget.ImageView
import androidx.appcompat.widget.SearchView

class FishSearchFragment : Fragment() {
    private lateinit var searchBar: SearchView
    private lateinit var tabLayout: TabLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var fishAdapter: FishAdapter
    private val db = FirebaseFirestore.getInstance()
    private var currentWaterType: String? = null
    private var currentQuery: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fish_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        searchBar = view.findViewById(R.id.searchBar)
        tabLayout = view.findViewById(R.id.tabLayout)
        recyclerView = view.findViewById(R.id.recyclerViewFish)
        view.findViewById<ImageView>(R.id.imageView).setOnClickListener { 
            findNavController().navigate(R.id.action_fishSearchFragment_to_homeFragment) 
        }

        setupRecyclerView()
        setupSearchBar()
        setupTabLayout()
        loadFish()
    }

    private fun setupRecyclerView() {
        fishAdapter = FishAdapter { fish ->
            navigateToFishDetails(fish)
        }
        recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = fishAdapter
        }
    }

    private fun navigateToFishDetails(fish: Fish) {
        findNavController().navigate(
            FishSearchFragmentDirections.actionFishSearchToFishDetails(fish.id)
        )
    }

    private fun setupSearchBar() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                currentQuery = query?.trim()?.lowercase() ?: ""
                loadFish()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                currentQuery = newText?.trim()?.lowercase() ?: ""
                loadFish()
                return true
            }
        })
    }

    private fun setupTabLayout() {
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                when(tab?.position) {
                    0 -> currentWaterType = null
                    1 -> currentWaterType = Fish.WATER_TYPE_LAKE
                    2 -> currentWaterType = Fish.WATER_TYPE_RIVER
                    3 -> currentWaterType = Fish.WATER_TYPE_SEA
                }
                loadFish()
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    private fun loadFish() {
        val baseQuery = db.collection("fish")
        
        val query = when {
            currentWaterType != null && currentQuery.isNotEmpty() -> {
                baseQuery
                    .whereEqualTo("waterType", currentWaterType)
                    .whereGreaterThanOrEqualTo("nameLower", currentQuery)
                    .whereLessThanOrEqualTo("nameLower", currentQuery + '\uf8ff')
            }
            currentWaterType != null -> {
                baseQuery.whereEqualTo("waterType", currentWaterType)
            }
            currentQuery.isNotEmpty() -> {
                baseQuery
                    .whereGreaterThanOrEqualTo("nameLower", currentQuery)
                    .whereLessThanOrEqualTo("nameLower", currentQuery + '\uf8ff')
            }
            else -> baseQuery
        }

        query.addSnapshotListener { snapshot, e ->
            if (e != null) {
                return@addSnapshotListener
            }

            val fishList = snapshot?.documents?.mapNotNull { doc ->
                doc.toObject(Fish::class.java)?.copy(id = doc.id)
            } ?: listOf()

            fishAdapter.submitList(fishList)
        }
    }
} 