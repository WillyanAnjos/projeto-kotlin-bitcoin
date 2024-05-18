package com.willyananjos.projetocoin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.willyananjos.projetocoin.service.CryptoService
import com.willyananjos.projetocoin.state.ScreenState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class CryptoViewModel(private val service: CryptoService): ViewModel() {
    private val _tickerLiveData = MutableLiveData<ScreenState>()
    val tickerLiveData : LiveData<ScreenState> = _tickerLiveData

    init {
        viewModelScope.launch (Dispatchers.IO){
            fetch()
        }
    }

    private suspend fun fetch() {
        _tickerLiveData.postValue(ScreenState.Loading)

        try {
            val response = service.fetchCoinTicker()
            _tickerLiveData.postValue(ScreenState.Success(data = response.body()!!.ticker))
        } catch (e: Throwable) {
            _tickerLiveData.postValue(ScreenState.Error(exception = e))
        }
    }

    fun refresh(){
        viewModelScope.launch (Dispatchers.IO){
            fetch()
        }
    }
}