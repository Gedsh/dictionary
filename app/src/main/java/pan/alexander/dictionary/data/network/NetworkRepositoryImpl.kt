package pan.alexander.dictionary.data.network

import pan.alexander.dictionary.domain.NetworkRepository
import pan.alexander.dictionary.utils.network.NetworkUtils

class NetworkRepositoryImpl(
    private val networkUtils: NetworkUtils
) : NetworkRepository {
    override fun isConnectionAvailable(): Boolean = networkUtils.isConnectionAvailable()
}
