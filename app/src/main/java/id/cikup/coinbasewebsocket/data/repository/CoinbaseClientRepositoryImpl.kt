package id.cikup.coinbasewebsocket.data.repository

import android.annotation.SuppressLint
import com.tinder.scarlet.WebSocket
import id.cikup.coinbasewebsocket.data.mapper.CoinbaseMapper
import id.cikup.coinbasewebsocket.data.request.SubscribeTradeRequest
import id.cikup.coinbasewebsocket.data.service.GlobalService
import id.cikup.coinbasewebsocket.domain.CoinbaseDomain
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class CoinbaseClientRepositoryImpl(
    private val globalService: GlobalService,
    private val coinbaseMapper: CoinbaseMapper
) : CoinbaseClientRepository{

    @SuppressLint("CheckResult")
    override fun subscribeCoinbase(subscribeTradeRequest: SubscribeTradeRequest): Flowable<CoinbaseDomain> {
        globalService.openWebSocketEvent()
            .filter {
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                globalService.sendTradeRequest(subscribeTradeRequest)
            }, { e ->
                Timber.e(e)
            })

        return  globalService.observeTrade()
            .subscribeOn(Schedulers.io())
            .map {
                coinbaseMapper.mapToDomain(it)
            }
    }


}