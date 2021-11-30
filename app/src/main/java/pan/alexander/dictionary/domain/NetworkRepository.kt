package pan.alexander.dictionary.domain

interface NetworkRepository {
    fun isConnectionAvailable(): Boolean
}
