package com.mfank.nlw.nearby.ui.screen.marketdetails

sealed class MarketDetailsUiEvent {
    data class OnFetchRules(val marketId: String) : MarketDetailsUiEvent()
    data class OnFetchCoupon(val qrCodeContent: String) : MarketDetailsUiEvent()
    data object OnResetCoupon : MarketDetailsUiEvent()
}