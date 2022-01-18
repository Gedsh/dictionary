package pan.alexander.dictionary.data.network

import pan.alexander.core_utils.network.NetworkUtils
import pan.alexander.dictionary.domain.NetworkRepository

class NetworkRepositoryImpl(
    private val networkUtils: NetworkUtils
) : NetworkRepository {
    override fun isConnectionAvailable(): Boolean = networkUtils.isConnectionAvailable()
}
