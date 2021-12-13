package pan.alexander.dictionary.utils.configuration

import pan.alexander.dictionary.BuildConfig

class ConfigurationManagerImpl : ConfigurationManager {
    override fun getBaseUrl(): String = BuildConfig.API_BASE_URL
    override fun getAppDbName(): String = "main_database.db"
}
