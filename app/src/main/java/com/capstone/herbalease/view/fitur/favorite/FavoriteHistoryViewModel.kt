package com.capstone.herbalease.view.fitur.favorite

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.herbalease.data.model.response.AppResponseItem
import com.capstone.herbalease.data.pref.Ingredient
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.view.fitur.favorite.database.FavoriteDao
import com.capstone.herbalease.view.fitur.favorite.database.FavoriteDatabase
import com.capstone.herbalease.view.fitur.favorite.database.HistoryDao
import com.capstone.herbalease.view.fitur.favorite.database.HistoryDatabase
import kotlinx.coroutines.CoroutineScope
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
            var list : MutableList<AppResponseItem>? = mutableListOf()  // Initialize the list
            favorites?.forEach {
                listToAppResponse(it)?.let { it1 -> list?.add(it1) }
            }
            _listFavorite.postValue(list)
        }
    }

    fun addFavorite(ingredients : Ingredients){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.addFavorite(ingredients)
        }
    }

    fun removeFavorite(id : Int){
        CoroutineScope(Dispatchers.IO).launch {
            favDao?.removeFavorite(id)
        }
    }

    fun getHistory(){
        viewModelScope.launch {
            val history = withContext(Dispatchers.IO) {
                hisDao?.getHistory()
            }
            var list : MutableList<AppResponseItem>? = mutableListOf()  // Initialize the list
            history?.forEach {
                ingredientToAppResponse(it)?.let { it1 -> list?.add(it1) }
            }
            _listHistory.postValue(list)
        }
    }

    fun addHistory(ingredient: Ingredient) {
        Log.d("FavoriteHistoryViewModel", "Adding to history: $ingredient")
        CoroutineScope(Dispatchers.IO).launch {
            hisDao?.upsertHistory(ingredient)
            Log.d("FavoriteHistoryViewModel", "History added: $ingredient")
        }
    }

    private fun listToAppResponse(ingredients: Ingredients): AppResponseItem? {
        return AppResponseItem(
            id = ingredients.id,
            nama = ingredients.name,
            imageUrl = ingredients.imageUrl,
            deskripsi = ingredients.description,
            khasiat = ingredients.listKhasiat.joinToString(", "),
            keyword = ingredients.listKeywords.joinToString(", "),
            kandungan = ingredients.listKandungan.joinToString(", "),
            rekomendasiMenu = ingredients.listRekomendasi
        )
    }

    private fun ingredientToAppResponse(ingredient: Ingredient): AppResponseItem? {
        return AppResponseItem(
            id = ingredient.id,
            nama = ingredient.name,
            imageUrl = ingredient.imageUrl,
            deskripsi = ingredient.description,
            khasiat = ingredient.listKhasiat.joinToString(", "),
            keyword = ingredient.listKeywords.joinToString(", "),
            kandungan = ingredient.listKandungan.joinToString(", "),
            rekomendasiMenu = ingredient.listRekomendasi
        )
    }
}
