package pan.alexander.dictionary.data.network

import pan.alexander.dictionary.domain.NetworkRepository
import pan.alexander.dictionary.utils.network.NetworkUtils
import javax.inject.Inject

class NetworkRepositoryImpl @Inject constructor(
    private val networkUtils: NetworkUtils
) : NetworkRepository {
    override fun isConnectionAvailable(): Boolean = networkUtils.isConnectionAvailable()
}
