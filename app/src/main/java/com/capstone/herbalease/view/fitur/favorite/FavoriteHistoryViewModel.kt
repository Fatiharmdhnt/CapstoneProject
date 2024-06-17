package com.capstone.herbalease.view.fitur.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.capstone.herbalease.data.pref.Ingredient
import com.capstone.herbalease.data.pref.Ingredients
import com.capstone.herbalease.view.fitur.favorite.database.FavoriteDao
import com.capstone.herbalease.view.fitur.favorite.database.FavoriteDatabase
import com.capstone.herbalease.view.fitur.favorite.database.HistoryDao
import com.capstone.herbalease.view.fitur.favorite.database.HistoryDatabase

class FavoriteHistoryViewModel(app : Application) : AndroidViewModel(app) {

    private var favDao : FavoriteDao?
    private var favDb : FavoriteDatabase
    private var hisDao : HistoryDao?
    private var hisDb : HistoryDatabase
    private val _listFavorite = MutableLiveData<List<Ingredients>>()
    val listFavorite : LiveData<List<Ingredients>> get() = _listFavorite
    private val _listHistory = MutableLiveData<List<Ingredient>>()
    val listHistory : LiveData<List<Ingredient>> get() = _listHistory

    init {
        favDb = FavoriteDatabase.getDatabase(app)
        favDao = favDb.FavoriteDao()

        hisDb = HistoryDatabase.getDatabase(app)
        hisDao = hisDb.HistoryDao()
        getFavorite()
        getHistory()
    }

    fun getFavorite(){
        _listFavorite.postValue(favDao?.getFavorite())
    }

    fun getHistory(){
        _listHistory.postValue(hisDao?.getHistory())
    }
}