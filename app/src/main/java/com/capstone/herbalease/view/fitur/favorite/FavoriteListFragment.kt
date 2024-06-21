package com.capstone.herbalease.view.fitur.favorite

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.herbalease.R
import com.capstone.herbalease.data.model.response.AppResponseItem
import com.capstone.herbalease.databinding.FavoriteItemRecyclerviewBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.adapter.FavHisAdapter
import com.capstone.herbalease.view.adapter.SearchIngredientsAdapter
import com.capstone.herbalease.view.ingredients_detail.IngredientsDetailActivity
import com.capstone.herbalease.view.ingredients_detail.IngredientsDetailFragment

class FavoriteListFragment : Fragment(R.layout.favorite_item_recyclerview) {
    private var _binding: FavoriteItemRecyclerviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: FavHisAdapter
    private lateinit var viewModel: FavoriteHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteItemRecyclerviewBinding.inflate(inflater, container, false)
        adapter = FavHisAdapter()
        viewModel = ViewModelProvider(this, ViewModelFactory(requireContext())).get(FavoriteHistoryViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observeFavoriteList()
        adapter.setOnItemClickCallback(object : FavHisAdapter.OnItemClickListener{
            override fun onItemClick(data: AppResponseItem) {
                val intent = Intent(requireContext(), IngredientsDetailActivity::class.java)
                intent.putExtra(IngredientsDetailActivity.EXTRA_INGREDIENT,data )
                startActivity(intent)
            }

        })
    }

    private fun setupRecyclerView() {
        binding.list.apply {
            setHasFixedSize(false)
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@FavoriteListFragment.adapter
        }
    }

    private fun observeFavoriteList() {
        viewModel.listFavorite.observe(viewLifecycleOwner, Observer { list ->
            list?.let {
                adapter.submitList(it)
            }
        })
    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavorite() // Ensure data is refreshed when fragment is resumed
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

