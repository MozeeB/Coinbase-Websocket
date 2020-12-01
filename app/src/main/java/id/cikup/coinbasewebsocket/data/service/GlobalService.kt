package id.cikup.coinbasewebsocket.data.service

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import id.cikup.coinbasewebsocket.data.model.CoinbaseModel
import id.cikup.coinbasewebsocket.data.request.SubscribeTradeRequest
import io.reactivex.Flowable

interface GlobalService {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendTradeRequest(subscribeTradeRequest: SubscribeTradeRequest)

    @Receive
    fun observeTrade(): Flowable<CoinbaseModel>

    companion object {
        const val BASE_URI = "wss://ws-feed.pro.coinbase.com"
    }
}