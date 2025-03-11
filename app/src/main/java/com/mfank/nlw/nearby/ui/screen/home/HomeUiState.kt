package com.mfank.nlw.nearby.ui.screen.home

import com.google.android.gms.maps.model.LatLng
import com.mfank.nlw.nearby.data.model.Category
import com.mfank.nlw.nearby.data.model.Market

data class HomeUiState(
    val categories: List<Category>? = null,
    val markets: List<Market>? = null,
    val marketLocations: List<LatLng>? = null
)