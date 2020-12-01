package id.cikup.coinbasewebsocket.di

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import id.cikup.coinbasewebsocket.data.mapper.CoinbaseMapper
import id.cikup.coinbasewebsocket.data.repository.CoinbaseClientRepository
import id.cikup.coinbasewebsocket.data.repository.CoinbaseClientRepositoryImpl
import id.cikup.coinbasewebsocket.data.service.GlobalInterceptor
import id.cikup.coinbasewebsocket.data.service.GlobalService
import id.cikup.coinbasewebsocket.ui.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import java.util.concurrent.TimeUnit


val appModule = module {

    single { GlobalInterceptor() }
    single { createAndroidLifecycle(application = get()) }
    single { createOkHttpClient(get()) }
    single { createScarlet(okHttpClient = get(), lifecycle = get()) }

}

val dataModule = module {

    single<CoinbaseClientRepository> { CoinbaseClientRepositoryImpl(get(), get()) }

    single { CoinbaseMapper() }

    viewModel {
        MainViewModel(coinbaseClientRepository = get())
    }
}


private fun createAndroidLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}


fun createOkHttpClient(interceptor: GlobalInterceptor): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

    val timeout = 60L
    return OkHttpClient.Builder()
            .connectTimeout(timeout, TimeUnit.SECONDS)
            .readTimeout(timeout, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(interceptor)
            .build()
}

private val jsonMoshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

// A Retrofit inspired WebSocket client for Kotlin, Java, and Android, that supports websockets.
private fun createScarlet(okHttpClient: OkHttpClient, lifecycle: Lifecycle): GlobalService {
    return Scarlet.Builder()
            .webSocketFactory(okHttpClient.newWebSocketFactory(GlobalService.BASE_URI))
            .lifecycle(lifecycle)
            .addMessageAdapterFactory(MoshiMessageAdapter.Factory(jsonMoshi))
            .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
            .build()
            .create()
}

val myAppModule = listOf(appModule, dataModule)