package binance

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AccountInfo(
    @SerialName("availableBalance") val availableBalance: String,
    @SerialName("totalWalletBalance") val totalBalance: String,
    @SerialName("totalUnrealizedProfit") val totalUnrealizedProfit: String,
    @SerialName("positions") val positions: List<AccountInfoPosition>,
)

@Serializable
data class AccountInfoPosition(
    @SerialName("symbol") val symbol: String,
    @SerialName("positionAmt") val amount: String,
    @SerialName("entryPrice") val entryPrice: String,
)

@Serializable
data class BinanceFuturesOrderResult(
    @SerialName("clientOrderId") val orderId: String,
    @SerialName("symbol") val symbol: String,
    @SerialName("side") val side: String,
    @SerialName("executedQty") val quantity: String,
    @SerialName("avgPrice") val price: String,
    @SerialName("status") val status: String,
)
