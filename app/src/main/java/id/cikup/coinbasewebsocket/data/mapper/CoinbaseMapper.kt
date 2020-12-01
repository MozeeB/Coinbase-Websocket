package id.cikup.coinbasewebsocket.data.mapper

import id.cikup.coinbasewebsocket.data.model.CoinbaseModel
import id.cikup.coinbasewebsocket.domain.CoinbaseDomain

class CoinbaseMapper : BaseMapper<CoinbaseModel, CoinbaseDomain>{
    override fun mapToDomain(model: CoinbaseModel): CoinbaseDomain {
        return CoinbaseDomain(
            model.time,
            model.price
        )
    }

    override fun mapToModel(domain: CoinbaseDomain): CoinbaseModel {
        return CoinbaseModel(
            domain.time,
            domain.price
        )
    }
}