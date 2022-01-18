package pan.alexander.core_utils.configuration

import pan.alexander.core_utils.BuildConfig

class ConfigurationManagerImpl : ConfigurationManager {
    override fun getBaseUrl(): String = BuildConfig.API_BASE_URL
    override fun getAppDbName(): String = "main_database.db"
}
