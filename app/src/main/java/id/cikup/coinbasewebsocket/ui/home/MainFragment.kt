package id.cikup.coinbasewebsocket.ui.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import id.cikup.coinbasewebsocket.R
import id.cikup.coinbasewebsocket.ui.CoinBaseDataLoaded
import id.cikup.coinbasewebsocket.ui.ErrorState
import id.cikup.coinbasewebsocket.ui.MainState
import id.cikup.coinbasewebsocket.ui.MainViewModel
import kotlinx.android.synthetic.main.fragment_main.*
import org.koin.android.ext.android.inject


class MainFragment : Fragment() {

    val vm: MainViewModel by inject()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        vm.mainState.observe(viewLifecycleOwner, startObservable)
        vm.getSocketCoinBase()
    }

    private val startObservable = Observer<MainState>{ coinbase ->
        when(coinbase){
            is CoinBaseDataLoaded ->{
                priceTV.text = coinbase.coinbaseDomain.price
            }
            is ErrorState ->{

            }
        }

    }
}