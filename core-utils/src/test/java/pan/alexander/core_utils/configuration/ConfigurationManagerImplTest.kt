package pan.alexander.core_utils.configuration

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import pan.alexander.core_utils.Constants.URL_PATTERN

class ConfigurationManagerImplTest {

    private lateinit var configurationManager: ConfigurationManagerImpl

    @Before
    fun setUp() {
        configurationManager = ConfigurationManagerImpl()
    }

    @Test
    fun configurationManagerImpl_GetBaseUrl_ValidUrl() {
        assertTrue(URL_PATTERN.matcher(configurationManager.getBaseUrl()).matches())
    }

    @Test
    fun configurationManagerImpl_GetAppDbName_NotEmpty() {
        assertFalse(configurationManager.getAppDbName().isEmpty())
    }
}
