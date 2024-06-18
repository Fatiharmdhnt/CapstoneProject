package com.capstone.herbalease.view.fitur.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.retrofit.AppResponseItem
import com.capstone.herbalease.data.pref.Ingredient
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.view.fitur.favorite.database.FavoriteDao
import com.capstone.herbalease.view.fitur.favorite.database.FavoriteDatabase
import com.capstone.herbalease.view.fitur.favorite.database.HistoryDao
import com.capstone.herbalease.view.fitur.favorite.database.HistoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteHistoryViewModel(app : Application) : AndroidViewModel(app) {

    private var favDao : FavoriteDao?
    private var favDb : FavoriteDatabase
    private var hisDao : HistoryDao?
    private var hisDb : HistoryDatabase
    private val _listFavorite = MutableLiveData<List<AppResponseItem>?>()
    val listFavorite : LiveData<List<AppResponseItem>?> get() = _listFavorite
    private val _listHistory = MutableLiveData<List<AppResponseItem>?>()
    val listHistory : LiveData<List<AppResponseItem>?> get() = _listHistory

    init {
        favDb = FavoriteDatabase.getDatabase(app)
        favDao = favDb.FavoriteDao()

        hisDb = HistoryDatabase.getDatabase(app)
        hisDao = hisDb.HistoryDao()
        getFavorite()
        getHistory()
    }

    fun getFavorite(){
        viewModelScope.launch {
            val favorites = withContext(Dispatchers.IO) {
                favDao?.getFavorite()
            }
            var list : MutableList<AppResponseItem>? = null
            favorites?.forEach {
                listToAppResponse(it)?.let { it1 -> list?.add(it1) }
            }
            _listFavorite.postValue(list)
        }
    }

    fun getHistory(){
        viewModelScope.launch {
            val history = withContext(Dispatchers.IO) {
                hisDao?.getHistory()
            }
            var list : MutableList<AppResponseItem>? = null
            history?.forEach {
                ingredientToAppResponse(it)?.let { it1 -> list?.add(it1) }
            }
            _listHistory.postValue(list)
        }
    }

    private fun listToAppResponse(ingredients: Ingredients) : AppResponseItem? {
        var result : AppResponseItem? = null
        result?.id = ingredients.id
        result?.nama = ingredients.name
        result?.imageUrl = ingredients.imageUrl
        result?.deskripsi = ingredients.description
        result?.khasiat = ingredients.listKhasiat.joinToString(", ")
        result?.keyword = ingredients.listKeywords.joinToString(", ")
        result?.kandungan = ingredients.listKandungan.joinToString(", ")
        result?.rekomendasiMenu = ingredients.listRekomendasi
        return result
    }

    private fun ingredientToAppResponse(ingredient: Ingredient) : AppResponseItem? {
        var result : AppResponseItem? = null
        result?.id = ingredient.id
        result?.nama = ingredient.name
        result?.imageUrl = ingredient.imageUrl
        result?.deskripsi = ingredient.description
        result?.khasiat = ingredient.listKhasiat.joinToString(", ")
        result?.keyword = ingredient.listKeywords.joinToString(", ")
        result?.kandungan = ingredient.listKandungan.joinToString(", ")
        result?.rekomendasiMenu = ingredient.listRekomendasi
        return result
    }
}