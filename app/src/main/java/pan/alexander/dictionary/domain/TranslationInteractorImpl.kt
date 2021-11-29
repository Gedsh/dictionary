package pan.alexander.dictionary.domain

import io.reactivex.rxjava3.core.Single
import pan.alexander.dictionary.domain.entities.Translation
import pan.alexander.dictionary.utils.rx.SchedulerProvider
import java.io.IOException
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicInteger
import javax.inject.Inject

private const val ERROR_RETRY_COUNT = 3

class TranslationInteractorImpl @Inject constructor(
    private val remoteRepository: RemoteRepository,
    private val schedulerProvider: SchedulerProvider
) : TranslationInteractor {

    override fun getTranslations(word: String): Single<List<Translation>> =
        remoteRepository.requestTranslations(word)
            .retryWhen { e ->
                val counter = AtomicInteger()
                e.flatMapSingle {
                    if (it is IOException && counter.getAndIncrement() < ERROR_RETRY_COUNT) {
                        Single.timer(counter.get() * 100L, TimeUnit.MILLISECONDS)
                    } else {
                        Single.error(it)
                    }
                }
            }
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())

}
