import binance.AccountInfoPosition
import binance.BinanceFuturesClient
import org.slf4j.LoggerFactory

class AccountMonitor(
    private val binanceClient: BinanceFuturesClient,
    private val telegram: Telegram?,
    private val warningThreshold: Double,
    private val emergencyExitThreshold: Double,
) {
    private val logger = LoggerFactory.getLogger(AccountMonitor::class.java)

    fun checkAccount() {
        val accountInfo = binanceClient.accountInfo()
        val profit = accountInfo.totalUnrealizedProfit.toDouble() / accountInfo.totalBalance.toDouble()
        logger.info("Profit: $profit")
        if (profit <= warningThreshold) {
            logger.info("Sending telegram warning")
            telegram?.sendMessage("Warning: profit is ${profit.toStr(6)}")
        }
        if (profit <= emergencyExitThreshold) {
            logger.info("Triggering emergency exit")
            telegram?.sendMessage("Emergency exit: profit is ${profit.toStr(6)}")
            emergencyExit()
        }
    }

    private fun emergencyExit() {
        binanceClient.allOpenOrders().forEach { order ->
            logger.info("Closing order ${order.symbol} ${order.orderId}")
            attempt {
                binanceClient.closeOrder(
                    symbol = order.symbol,
                    clientOrderId = order.orderId,
                )
            }
        }
        binanceClient.accountInfo().positions.forEach { position ->
            closePosition(position)
        }
    }

    private fun closePosition(position: AccountInfoPosition) {
        val amount = position.amount.toDouble()
        if (amount != 0.0) {
            logger.info("Closing position ${position.symbol} ${position.amount}")
            val short = amount > 0
            attempt {
                binanceClient.createOrder(
                    symbol = position.symbol,
                    side = short.toBuyOrSell(),
                    type = "MARKET",
                    quantity = position.amount.removePrefix("-"),
                    reduceOnly = true,
                )
            }
        }
    }
}