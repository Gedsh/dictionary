package pan.alexander.dictionary.domain

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.domain.entities.Translation

interface TranslationInteractor {
    fun getTranslations(word: String): Single<TranslationResponseState>
}
