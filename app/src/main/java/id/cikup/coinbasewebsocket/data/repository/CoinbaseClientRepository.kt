package id.cikup.coinbasewebsocket.data.repository

import id.cikup.coinbasewebsocket.data.request.SubscribeTradeRequest
import id.cikup.coinbasewebsocket.domain.CoinbaseDomain
import io.reactivex.Flowable

interface CoinbaseClientRepository {
    fun subscribeCoinbase(subscribeTradeRequest: SubscribeTradeRequest): Flowable<CoinbaseDomain>

}