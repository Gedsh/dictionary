package pan.alexander.dictionary.domain

import pan.alexander.dictionary.domain.dto.TranslationDto
import java.io.IOException
import kotlin.jvm.Throws

interface RemoteRepository {
    @Throws(IOException::class)
    suspend fun requestTranslations(word: String): List<TranslationDto>
}
