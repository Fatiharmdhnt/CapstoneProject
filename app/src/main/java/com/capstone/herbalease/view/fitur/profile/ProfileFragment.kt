package com.capstone.herbalease.view.fitur.profile

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capstone.herbalease.databinding.FragmentProfileBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.customview.LoadingDialog
import com.capstone.herbalease.di.Result

class ProfileFragment : Fragment() {

    private var editMode: Boolean = false
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    var imageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel by viewModels<ProfileViewModel> {
            ViewModelFactory(requireContext())
        }

        val loadingDialog = LoadingDialog(requireContext())
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nameTextView: TextView = binding.profileName
        val emailTextView: TextView = binding.profileEmail

        profileViewModel.getUser().observe(viewLifecycleOwner) { result ->
            when (result) {
                is Result.Loading -> {
                    loadingDialog.show()
                }
                is Result.Success -> {
                    loadingDialog.dismiss()
                    nameTextView.text = result.data.name
                    emailTextView.text = result.data.email
                    Glide.with(requireContext()).load(result.data.profilePictureUrl ?: "https://storage.googleapis.com/herbalease-image/Foto-Profil/blank-profile-picture-973460_1280.png")
                        .override(200, 200).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(binding.profileImage)

                    binding.buttonEdit.setOnClickListener {
                        val toEditFragment = ProfileFragmentDirections.actionNavigationProfileToEditProfileFragment(result.data)
                        findNavController().navigate(toEditFragment)
                    }
                }
                is Result.Error -> {
                    loadingDialog.dismiss()
                    showToast(result.error)
                }
            }
        }

        binding.logoutPromptTextView.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Logout")
                setMessage("Are you sure you want to logout?")
                setPositiveButton("Yes") { _, _ ->
                    profileViewModel.logout()
                    Toast.makeText(requireContext(), "Logout Success", Toast.LENGTH_SHORT).show()
                }
                setNegativeButton("No") { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showToast(error: String) {
        Toast.makeText(requireContext(), error, Toast.LENGTH_SHORT).show()
    }
}
