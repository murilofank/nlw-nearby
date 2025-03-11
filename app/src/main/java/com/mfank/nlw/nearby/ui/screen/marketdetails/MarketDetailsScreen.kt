package com.mfank.nlw.nearby.ui.screen.marketdetails

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mfank.nlw.nearby.R
import com.mfank.nlw.nearby.data.model.Market
import com.mfank.nlw.nearby.data.model.mock.mockMarkets
import com.mfank.nlw.nearby.ui.component.button.NearbyButton
import com.mfank.nlw.nearby.ui.component.market_details.MarketDetailsCoupons
import com.mfank.nlw.nearby.ui.component.market_details.MarketDetailsInfos
import com.mfank.nlw.nearby.ui.component.market_details.MarketDetailsRules
import com.mfank.nlw.nearby.ui.theme.Typography

@Composable
fun MarketDetailsScreen(
    modifier: Modifier = Modifier,
    uiState: MarketDetailsUiState,
    onEvent: (MarketDetailsUiEvent) -> Unit,
    market: Market,
    onNavigateToQrCodeScanner: () -> Unit,
    onNavigateBack: () -> Unit) {

    LaunchedEffect(true) {
        onEvent(MarketDetailsUiEvent.OnFetchRules(marketId = market.id))
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f),
            contentDescription = "Location image",
            contentScale = ContentScale.Crop,
            model = market.cover
        )
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(topEnd = 16.dp, topStart = 16.dp))
                .fillMaxWidth()
                .fillMaxHeight(0.75f)
                .align(Alignment.BottomCenter)
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(36.dp)
            ) {
                Column {
                    Text(text = market.name, style = Typography.headlineLarge)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = market.description, style = Typography.bodyLarge)
                }
                Spacer(modifier = Modifier.height(48.dp))
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                ) {
                    MarketDetailsInfos(market = market)
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 24.dp)
                    )
                    if (!uiState.rules.isNullOrEmpty()) {
                        MarketDetailsRules(rules = uiState.rules)
                        HorizontalDivider(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 24.dp)
                        )
                    }
                    if (!uiState.coupon.isNullOrEmpty()) {
                        MarketDetailsCoupons(coupons = listOf(uiState.coupon))
                    }
                }
                NearbyButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp),
                    text = "Ler QR code",
                    onClick = onNavigateToQrCodeScanner
                )
            }
        }
        NearbyButton(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(24.dp),
            iconRes = R.drawable.ic_arrow_left,
            onClick = onNavigateBack
        )
    }
}

@Preview
@Composable
private fun MarketDetailsScreenPreview(modifier: Modifier = Modifier) {
    MarketDetailsScreen(
        market = mockMarkets.first(),
        onNavigateToQrCodeScanner = {},
        onNavigateBack = {},
        uiState = MarketDetailsUiState(),
        onEvent = {}
    )
}