package com.capstone.herbalease.view.fitur.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.capstone.herbalease.R
import com.capstone.herbalease.databinding.FragmentHomeBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.adapter.HeadlineIngredientsAdapter
import com.capstone.herbalease.view.customview.LoadingDialog
import com.capstone.herbalease.view.ingredients_detail.IngredientsDetailFragment
import com.capstone.herbalease.view.search.SearchFragment
import com.capstone.herbalease.di.Result


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var loadingDialog: LoadingDialog

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory(requireContext())
    }

    private val headlineIngredientsAdapter = HeadlineIngredientsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        loadingDialog = LoadingDialog(requireContext())

        observeViewModel()

        setRecyclerViews()
        setListeners()

        return binding.root
    }

    private fun observeViewModel() {
        viewModel.apply {

            getUser().observe(requireActivity()) { result ->
                when (result) {
                    is Result.Loading -> {
                        loadingDialog.show()
                    }

                    is Result.Success -> {
                        loadingDialog.dismiss()
                        binding.tvUsername.text = "Hi, ${result.data.name} !"
                        Glide.with(requireContext()).load(
                            result.data.profilePictureUrl
                                ?: "https://storage.googleapis.com/herbalease-image/Foto-Profil/blank-profile-picture-973460_1280.png"
                        )
                            .override(200, 200).into(binding.ivProfile)
                    }

                    is Result.Error -> {
                        loadingDialog.dismiss()
                        showToast(result.error)
                    }

                    else -> {}
                }
            }

            isLoadingIngredients.observe(viewLifecycleOwner) {
                if (it) {
                    // Handle ketika Loading disini
                } else {
                    // Handle ketika selesai Loading disini
                }
            }

            ingredientList.observe(viewLifecycleOwner) {
                // Handling list headline bahan
                if (it != null) {
                    headlineIngredientsAdapter.setData(it.toMutableList())
                }
            }

            errorMessage.observe(viewLifecycleOwner) {
                // Handling error disini
            }
        }
    }

    private fun showToast(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }

    private fun setListeners() {
        binding.apply {
            searchBar.setOnClickListener {
                val bundle = Bundle()
                bundle.putBoolean(SearchFragment.IS_FROM_SEARCH, true)
                findNavController().navigate(R.id.navigation_search, bundle)
            }

            ivProfile.setOnClickListener {
                findNavController().navigate(R.id.action_homeFragment_to_profileFragment)
            }
        }
    }

    private fun setRecyclerViews() {
        binding.rvHeadlineIngredients.apply {
            headlineIngredientsAdapter.onItemClick = { ingredients ->
                val bundle = Bundle()
                bundle.putParcelable(IngredientsDetailFragment.EXTRA_INGREDIENTS, ingredients)
                findNavController().navigate(R.id.navigation_detail, bundle)
            }

            headlineIngredientsAdapter.onLoadMoreClick = { listIngredients ->
                // Handling klik load more disini, variable listIngredients isinya
                // semua ingredients, pass saja ke activity/fragment yang dituju
                findNavController().navigate(R.id.navigation_search)
            }

            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = headlineIngredientsAdapter
        }
    }
}