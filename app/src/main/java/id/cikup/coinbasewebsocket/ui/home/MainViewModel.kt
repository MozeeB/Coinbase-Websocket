package id.cikup.coinbasewebsocket.ui

import androidx.lifecycle.MutableLiveData
import id.cikup.coinbasewebsocket.data.repository.CoinbaseClientRepository
import id.cikup.coinbasewebsocket.data.request.BITCOIN_TICKER_SUBSCRIBE_MESSAGE
import id.cikup.coinbasewebsocket.domain.CoinbaseDomain
import id.cikup.coinbasewebsocket.ui.base.BaseViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import timber.log.Timber

sealed class MainState
data class ErrorState(val message:String?) : MainState()
data class CoinBaseDataLoaded(val coinbaseDomain: CoinbaseDomain) : MainState()
class MainViewModel(val coinbaseClientRepository: CoinbaseClientRepository) : BaseViewModel() {

    val mainState = MutableLiveData<MainState>()

    fun getSocketCoinBase(){
        compositeDisposable.add(
            coinbaseClientRepository.subscribeCoinbase(BITCOIN_TICKER_SUBSCRIBE_MESSAGE)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ coinbase ->
                    mainState.value = CoinBaseDataLoaded(coinbase)
                }, this::onError)
        )
    }
    override fun onError(error: Throwable) {
        Timber.e("observeCoinUseCase error: %s", error.localizedMessage)
    }
}