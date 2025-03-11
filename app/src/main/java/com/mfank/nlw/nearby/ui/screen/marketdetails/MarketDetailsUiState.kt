package com.mfank.nlw.nearby.ui.screen.marketdetails

import com.mfank.nlw.nearby.data.model.Rule

data class MarketDetailsUiState(
    val rules: List<Rule>? = null,
    val coupon: String? = null
)