import java.math.RoundingMode

inline fun <T> attempt(times: Int = 3, op: () -> T): Result<T> {
    var attemps = 0
    var lastException: Exception? = null
    while (attemps < times) {
        try {
            attemps++
            return Result.success(op())
        } catch (e: Exception) {
            lastException = e
        }
    }

    return Result.failure(requireNotNull(lastException))
}

fun Boolean.toBuyOrSell() = if (this) "SELL" else "BUY"

fun Double.toStr(precision: Int = 4) = toBigDecimal().setScale(precision, RoundingMode.HALF_EVEN).toPlainString()
