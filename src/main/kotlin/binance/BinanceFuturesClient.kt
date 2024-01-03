package binance

import com.binance.connector.futures.client.impl.UMFuturesClientImpl
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.slf4j.LoggerFactory

class BinanceFuturesClient(
    private val futuresClient: UMFuturesClientImpl,
) {
    private val logger = LoggerFactory.getLogger(BinanceFuturesClient::class.java)

    private val json = Json { ignoreUnknownKeys = true }

    fun createOrder(
        symbol: String,
        side: String,
        type: String,
        quantity: String? = null,
        reduceOnly: Boolean? = null,
    ): BinanceFuturesOrderResult {
        val parameters = LinkedHashMap<String, Any>()
        parameters["newOrderRespType"] = "RESULT"
        parameters["symbol"] = symbol.uppercase()
        parameters["side"] = side
        parameters["type"] = type
        quantity?.let { parameters["quantity"] = it }
        reduceOnly?.let { parameters["reduceOnly"] = it }

        val res = futuresClient.account().newOrder(parameters)
        logger.debug(res)
        return json.decodeFromString(res)
    }

    fun accountInfo(): AccountInfo {
        val res = futuresClient.account().accountInformation(LinkedHashMap())
        logger.debug(res)
        return json.decodeFromString(res)
    }

    fun closeOrder(symbol: String, clientOrderId: String) {
        val parameters = LinkedHashMap<String, Any>()
        parameters["symbol"] = symbol
        parameters["origClientOrderId"] = clientOrderId
        val res = futuresClient.account().cancelOrder(parameters)
        logger.debug(res)
    }

    fun allOpenOrders(): List<BinanceFuturesOrderResult> {
        val res = futuresClient.account().currentAllOpenOrders(LinkedHashMap())
        logger.debug(res)
        return json.decodeFromString(res)
    }
}
