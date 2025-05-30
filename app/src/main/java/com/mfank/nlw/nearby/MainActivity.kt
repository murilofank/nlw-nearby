package com.mfank.nlw.nearby

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mfank.nlw.nearby.data.model.Market
import com.mfank.nlw.nearby.ui.screen.home.HomeScreen
import com.mfank.nlw.nearby.ui.screen.marketdetails.MarketDetailsScreen
import com.mfank.nlw.nearby.ui.screen.splash.SplashScreen
import com.mfank.nlw.nearby.ui.screen.welcome.WelcomeScreen
import com.mfank.nlw.nearby.ui.screen.home.HomeViewModel
import com.mfank.nlw.nearby.ui.route.Home
import com.mfank.nlw.nearby.ui.route.QRCodeScanner
import com.mfank.nlw.nearby.ui.route.Splash
import com.mfank.nlw.nearby.ui.route.Welcome
import com.mfank.nlw.nearby.ui.screen.marketdetails.MarketDetailsUiEvent
import com.mfank.nlw.nearby.ui.screen.marketdetails.MarketDetailsViewModel
import com.mfank.nlw.nearby.ui.screen.qrcode_scanner.QRCodeScannerScreen
import com.mfank.nlw.nearby.ui.theme.NearbyTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NearbyTheme {
                val navController = rememberNavController()

                val homeViewModel by viewModels<HomeViewModel>()
                val homeUiState by homeViewModel.uiState.collectAsStateWithLifecycle()
                val marketDetailsViewModel by viewModels<MarketDetailsViewModel>()
                val marketDetailsUiState by marketDetailsViewModel.uiState.collectAsStateWithLifecycle()

                NavHost(
                    navController = navController,
                    startDestination = Splash
                ) {
                    composable<Splash> {
                        SplashScreen(
                            modifier = Modifier.fillMaxSize(),
                            onNavigateToWelcome = {
                                navController.navigate(Welcome)
                            }
                        )
                    }
                    composable<Welcome> {
                        WelcomeScreen(
                            onNavigateToHome = {
                                navController.navigate(Home)
                            }
                        )
                    }
                    composable<Home> {
                        HomeScreen(
                            uiState = homeUiState,
                            onEvent = homeViewModel::onEvent,
                            onNavigateToMarketDetails = {selectedMarket ->
                                navController.navigate(selectedMarket)
                            }
                        )
                    }
                    composable<Market> {
                        val selectedMarket = it.toRoute<Market>()
                        MarketDetailsScreen(
                            market = selectedMarket,
                            uiState = marketDetailsUiState,
                            onEvent = marketDetailsViewModel::onEvent,
                            onNavigateBack = {
                                navController.popBackStack()
                            },
                            onNavigateToQrCodeScanner = {
                                navController.navigate(QRCodeScanner)
                            }
                        )
                    }
                    composable<QRCodeScanner> {
                        QRCodeScannerScreen(onCompletedScan = { couponCode ->
                            if (couponCode.isNotEmpty()) {
                                marketDetailsViewModel.onEvent(
                                    MarketDetailsUiEvent.OnFetchCoupon(
                                        qrCodeContent = couponCode
                                    )
                                )
                            }
                            navController.popBackStack()
                        })
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    NearbyTheme {
    }
}