package com.mfank.nlw.nearby.ui.screen.qrcode_scanner

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.mfank.nlw.nearby.MainActivity

@Composable
fun QRCodeScannerScreen(modifier: Modifier = Modifier, onCompletedScan: (String) -> Unit) {
    val context = LocalContext.current

    val scanOptions = ScanOptions()
        .setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        .setPrompt("Scan coupon's QR code")
        .setCameraId(0)
        .setBeepEnabled(false)
        .setOrientationLocked(false)
        .setBarcodeImageEnabled(true)

    val barcodeLauncher = rememberLauncherForActivityResult(
        ScanContract()
    ) { result ->
        onCompletedScan(result.contents.orEmpty())
    }

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) {
            ActivityResultContracts.RequestPermission()
        } else {
            barcodeLauncher.launch(scanOptions)
        }
    }

    fun checkCameraPermission() {
        if (ContextCompat.checkSelfPermission(
                context,
                android.Manifest.permission.CAMERA
            ) == android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            barcodeLauncher.launch(scanOptions)
        } else if (shouldShowRequestPermissionRationale(
                context as MainActivity,
                android.Manifest.permission.CAMERA
            )
        ) {
            Toast.makeText(
                context,
                "Camera permission is required to scan QR codes",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
    }

    LaunchedEffect(true) {
        checkCameraPermission()
    }

    Column(modifier = modifier.fillMaxSize()) {  }

}