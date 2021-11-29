package pan.alexander.dictionary.domain

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.domain.entities.Translation

interface RemoteRepository {
    fun requestTranslations(word: String): Single<List<Translation>>
}
