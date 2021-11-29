package pan.alexander.dictionary.utils.configuration

import pan.alexander.dictionary.BuildConfig
import javax.inject.Inject

class ConfigurationManagerImpl @Inject constructor() : ConfigurationManager {
    override fun getBaseUrl(): String = BuildConfig.API_BASE_URL
}
