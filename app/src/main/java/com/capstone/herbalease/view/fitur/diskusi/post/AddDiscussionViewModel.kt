package com.capstone.herbalease.view.fitur.diskusi.post

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.icu.text.SimpleDateFormat
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.response.discussion.PostDiscussionResponse
import com.capstone.herbalease.data.pref.Keyword
import com.capstone.herbalease.data.pref.AppRepository
import com.capstone.herbalease.data.pref.UserModel
import com.capstone.herbalease.data.pref.UserRepository
import com.capstone.herbalease.di.Result
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Date
import java.util.Locale

class AddDiscussionViewModel(private val repository: UserRepository, private val appRepository: AppRepository) : ViewModel() {

    private val _responseAddDiscussion = MutableLiveData<PostDiscussionResponse?>()
    val responseAddDiscussion : LiveData<PostDiscussionResponse?> get() = _responseAddDiscussion
    private val _userSession = MutableLiveData<UserModel>()
    val userSession: LiveData<UserModel> get() = _userSession

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
    private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

   fun getSession(){
       viewModelScope.launch {
           repository.getSession().collect { session ->
               _userSession.postValue(session)
           }
       }
   }
    fun postDiscussion(title : String, image : Uri, desc : String, keyword : List<Keyword>, context: Context){

        val imageFile = uriToFile(image, context)
        val requestTitle = title.toRequestBody("text/plain".toMediaType())
        val requestDesc = desc.toRequestBody("text/plain".toMediaType())
        val requestKeyword = keywordToString(keyword).toRequestBody("text/plain".toMediaType())
        val requestImage = imageFile.asRequestBody("image/jpeg".toMediaType())

        val multipartBody = MultipartBody.Part.createFormData(
            "photoDiscussionUrl",
            imageFile.name,
            requestImage
        )
        viewModelScope.launch {
            val response = appRepository.postDiscussion(
                requestTitle,
                multipartBody,
                requestDesc,
                requestKeyword
            )

            response.asFlow().collect{ result ->
                when (result) {
                    is Result.Loading -> {
                        _isLoading.postValue(true)
                    }
                    is Result.Success -> {
                        _isLoading.postValue(false)
                        _responseAddDiscussion.postValue(result.data)
                    }
                    is Result.Error -> {
                        _isLoading.value = false
                        _errorMessage.value = result.error
                    }
                }

            }
        }
    }

    private fun createCustomTempFile(context: Context): File {
        val filesDir = context.externalCacheDir
        return File.createTempFile(timeStamp, ".jpg", filesDir)
    }
    private fun uriToFile(imageUri: Uri, context: Context): File {
        val myFile = createCustomTempFile(context)
        val inputStream = context.contentResolver.openInputStream(imageUri) as InputStream
        val outputStream = FileOutputStream(myFile)
        val buffer = ByteArray(1024)
        var length: Int
        while (inputStream.read(buffer).also { length = it } > 0) outputStream.write(buffer, 0, length)
        outputStream.close()
        inputStream.close()
        return myFile
    }

    private fun keywordToString(keyword : List<Keyword>) : String{
        return keyword.joinToString(", ") { it.keyword }
    }

    fun resizeAndCompressImage(uri: Uri, contentResolver: ContentResolver, maxWidth: Int, maxHeight: Int, maxFileSize: Long): Bitmap? {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val originalBitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()

        var resizedBitmap = originalBitmap
        var streamLength = 0
        var compressQuality = 100
        val bmpStream = ByteArrayOutputStream()

        do {
            bmpStream.reset()
            resizedBitmap?.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            streamLength = bmpStream.size()
            compressQuality -= 5
        } while (streamLength >= maxFileSize && compressQuality > 0)

        return resizedBitmap
    }
}