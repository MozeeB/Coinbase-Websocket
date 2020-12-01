package id.cikup.coinbasewebsocket.data.request

import com.squareup.moshi.Json

abstract class BaseSubscribeRequest(
    @Json(name = "type")
    open val type: String,
    @Json(name = "channels")
    open val channels: List<Channel>

)

data class SubscribeTradeRequest(
    override val type: String,
    override val channels: List<Channel>
) : BaseSubscribeRequest(
    type = type,
    channels = channels
)

data class Channel(
    val name: String,
    val product_ids: List<String>
)

val BITCOIN_TICKER_SUBSCRIBE_MESSAGE = SubscribeTradeRequest(
    type = "subscribe",
    channels = listOf(
        Channel(
        name = "ticker", product_ids = listOf("BTC-EUR")

    ))
)

