import binance.BinanceFuturesClient
import com.binance.connector.futures.client.impl.UMFuturesClientImpl
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.runBlocking
import org.slf4j.LoggerFactory
import kotlin.system.exitProcess
import kotlin.time.Duration.Companion.seconds

fun main() {
    val telegram = getTelegram()
    runBlocking {
        val logger = LoggerFactory.getLogger("Main")
        telegram.sendMessage("Safety Switch starts")
        try {
            val binanceClient = getBinanceClient()
            val accountMonitor = AccountMonitor(
                binanceClient = binanceClient,
                telegram = telegram,
                warningThreshold = -0.15,
                emergencyExitThreshold = -0.2,
            )
            while (isActive) {
                accountMonitor.checkAccount()
                delay(15.seconds)
            }
        } catch (e: Throwable) {
            try {
                println(e.toString())
                e.printStackTrace()
                runCatching { logger.error("Error: ${e.message}", e) }
                runCatching { telegram.sendMessage("Safety Switch crash: $e") }
            } finally {
                exitProcess(1)
            }
        }
    }
    telegram.sendMessage("Safety Switch stops")
}

private fun getBinanceClient(): BinanceFuturesClient {
    val apiKey = System.getenv("BINANCE_API_KEY")
    val apiSecret = System.getenv("BINANCE_API_SECRET")
    val binanceClient = UMFuturesClientImpl(apiKey, apiSecret)
    return BinanceFuturesClient(binanceClient)
}

private fun getTelegram(): Telegram {
    val telegramApiToken = System.getenv("TELEGRAM_API_TOKEN")
    val telegramChatId = System.getenv("TELEGRAM_CHAT_ID")
    return Telegram(telegramApiToken, telegramChatId)
}
