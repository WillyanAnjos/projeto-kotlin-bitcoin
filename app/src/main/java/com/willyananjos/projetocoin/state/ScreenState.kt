package com.willyananjos.projetocoin.state

import com.willyananjos.projetocoin.service.Ticker

sealed class ScreenState {
    object Loading : ScreenState()
    data class Success(val data: Ticker) : ScreenState()
    data class Error(val exception: Throwable) : ScreenState()
}