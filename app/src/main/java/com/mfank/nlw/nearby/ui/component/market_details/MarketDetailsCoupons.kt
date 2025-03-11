package com.mfank.nlw.nearby.ui.component.market_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mfank.nlw.nearby.R
import com.mfank.nlw.nearby.ui.theme.Gray400
import com.mfank.nlw.nearby.ui.theme.GreenBase
import com.mfank.nlw.nearby.ui.theme.GreenExtraLight
import com.mfank.nlw.nearby.ui.theme.Typography

@Composable
fun MarketDetailsCoupons(modifier: Modifier = Modifier, coupons: List<String>) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "Utilize este cupon", style = Typography.headlineSmall, color = Gray400)

        coupons.forEach { coupon ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(GreenExtraLight)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.ic_ticket),
                    tint = GreenBase,
                    contentDescription = "ícone cupons"
                )
                Text(text = coupon, style = Typography.headlineSmall)
            }
        }
    }
}

@Preview
@Composable
private fun MarketDetailsCouponsPreview() {
    MarketDetailsCoupons(
        modifier = Modifier.fillMaxWidth(),
        coupons = listOf("FA341PF2", "FA341PF7")
    )
}