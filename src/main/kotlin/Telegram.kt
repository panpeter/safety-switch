import java.net.URI
import java.net.URLEncoder

class Telegram(
    val apiToken: String,
    val chatId: String,
) {
    private val urlTemplate = "https://api.telegram.org/bot%s/sendMessage?chat_id=%s&text=%s"

    fun sendMessage(
        message: String,
    ) {
        val text = URLEncoder.encode(message, "UTF-8")
        val urlString = String.format(urlTemplate, apiToken, chatId, text)
        URI(urlString).toURL().openConnection().getInputStream()
    }
}
