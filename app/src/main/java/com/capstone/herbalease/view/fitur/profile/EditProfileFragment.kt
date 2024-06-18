package com.capstone.herbalease.view.fitur.profile

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.capstone.herbalease.R
import com.capstone.herbalease.databinding.FragmentEditProfileBinding
import com.capstone.herbalease.view.ViewModelFactory
import com.capstone.herbalease.view.customview.LoadingDialog
import com.capstone.herbalease.di.Result
import com.capstone.herbalease.data.model.response.ProfileResponse
import com.github.dhaval2404.imagepicker.ImagePicker

import java.io.File

class EditProfileFragment : Fragment() {

    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!
    private var newPhoto: File? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel by viewModels<ProfileViewModel> {
            ViewModelFactory(requireContext())
        }

        val loadingDialog = LoadingDialog(requireContext())

        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val nameEditText: EditText = binding.profileName
        val emailEditText: EditText = binding.profileEmail
        val saveButton: Button = binding.buttonSave

        val userData: ProfileResponse = EditProfileFragmentArgs.fromBundle(arguments as Bundle).profileData
        nameEditText.setText(userData.name)
        emailEditText.setText(userData.email)
        Glide.with(requireContext())
            .load(userData.profilePictureUrl ?: "https://storage.googleapis.com/herbalease-image/Foto-Profil/blank-profile-picture-973460_1280.png")
            .override(200, 200)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(binding.profileImage)

        binding.profileName.requestFocus()
        binding.changeProfileImageText.setOnClickListener {
            ImagePicker.with(this@EditProfileFragment)
                .crop(1f, 1f)
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.buttonCancel.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.buttonSave.setOnClickListener {
            when {
                emailEditText.error.isNullOrEmpty().not() || emailEditText.text.isNullOrEmpty() ->
                    showToast("Fill email correctly!")

                nameEditText.error.isNullOrEmpty().not() || nameEditText.text.isNullOrEmpty() ->
                    showToast("Fill name correctly!")

                else -> {
                    profileViewModel.updateData(
                        nameEditText.text.toString(),
                        emailEditText.text.toString(), newPhoto
                    ).observe(requireActivity()) { result ->
                        when (result) {
                            is Result.Loading -> {
                                loadingDialog.show()
                            }
                            is Result.Success -> {
                                loadingDialog.dismiss()
                                AlertDialog.Builder(requireContext()).apply {
                                    setTitle("Yeah!")
                                    setMessage("Update Profile Success!")
                                    setPositiveButton("Next") { _, _ ->
                                        findNavController().navigate(R.id.action_editProfileFragment_to_navigation_profile)
                                    }
                                    create()
                                    show()
                                }
                            }
                            is Result.Error -> {
                                loadingDialog.dismiss()
                                showToast(result.error)
                            }
                        }
                    }
                }
            }
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            val uri: Uri = data?.data!!
            newPhoto = File(uri.path)
            Log.d("profile pic", "profile $uri")

            Glide.with(requireContext())
                .load(uri)
                .centerCrop()
                .override(500, 500)
                .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(binding.profileImage)
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            showToast(ImagePicker.getError(data))
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
