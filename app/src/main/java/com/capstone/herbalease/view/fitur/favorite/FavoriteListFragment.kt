package com.capstone.herbalease.view.fitur.favorite

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
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.databinding.FavoriteItemRecyclerviewBinding
import com.capstone.herbalease.view.adapter.SearchIngredientsAdapter
import com.capstone.herbalease.view.ingredients_detail.IngredientsDetailFragment

class FavoriteListFragment : Fragment(R.layout.favorite_item_recyclerview){
    private var _binding : FavoriteItemRecyclerviewBinding? = null
    private val binding get() = _binding!!
//    private lateinit var viewModel:
    private lateinit var adapter: SearchIngredientsAdapter
    private lateinit var listFavorite : List<Ingredients>
    private lateinit var viewModel: FavoriteHistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FavoriteItemRecyclerviewBinding.inflate(inflater, container,false)
        adapter = SearchIngredientsAdapter()
        viewModel = ViewModelProvider(this).get(FavoriteHistoryViewModel::class.java)
        setListeners()
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.listFavorite.observe(viewLifecycleOwner, Observer {
            listFavorite = it
        })

        adapter.submitList(listFavorite)

        binding.list.setHasFixedSize(false)
        binding.list.layoutManager = LinearLayoutManager(activity)
        binding.list.adapter = adapter
    }

    private fun setListeners() {
        binding.apply {
            adapter.onIngredientsClick = { ingredients ->
                val bundle = Bundle()
                bundle.putParcelable(IngredientsDetailFragment.EXTRA_INGREDIENTS, ingredients)
                findNavController().navigate(R.id.navigation_detail, bundle)
            }
        }
    }
}