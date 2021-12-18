package pan.alexander.dictionary.ui.details

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.SimpleItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import pan.alexander.core_utils.coroutines.DispatcherProvider
import pan.alexander.core_utils.logger.AppLogger
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.DetailsFragmentBinding
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.ui.details.adapter.*
import java.lang.Exception

private const val TRANSLATION_EXTRA = "pan.alexander.dictionary.TRANSLATION_EXTRA"

class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val binding by viewBinding(DetailsFragmentBinding::bind)
    private var translation: TranslationDto? = null

    private var adapter: AsyncListDifferDelegationAdapter<DetailsAdapterRecyclerItem>? = null
    private val mediaPlayer by lazy { MediaPlayer() }
    private val dispatcherProvider: DispatcherProvider by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            translation = it.getParcelable(TRANSLATION_EXTRA)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        initRecycler()

        setData(false)
    }

    private fun setTitle() {
        activity?.setTitle(R.string.details)
    }

    private fun initRecycler() {
        (binding.descriptionScreenRecycler.itemAnimator as SimpleItemAnimator)
            .supportsChangeAnimations = false
        binding.descriptionScreenRecycler.adapter = AsyncListDifferDelegationAdapter(
            DetailsAdapterRecyclerItemDiffCallback(),
            detailsAdapterHeaderDelegate(),
            detailsAdapterTranscriptionDelegate(::transcriptionButtonClicked),
            detailsAdapterDetailsDelegate(get(), lifecycleScope)
        ).also {
            adapter = it
        }
    }

    private fun setData(showProgress: Boolean) {
        translation?.let { translation ->

            val detailItems = mutableListOf<DetailsAdapterRecyclerItem>().apply {
                add(DetailsAdapterHeaderItem(translation.word))
                add(
                    DetailsAdapterTranscriptionItem(
                        translation.meanings.firstOrNull()?.transcription ?: "", showProgress
                    )
                )
                addAll(translation.meanings.map {
                    DetailsAdapterDetailsItem(
                        it.translation,
                        it.imgUrl
                    )
                })
            }

            adapter?.setItems(detailItems)
        }
    }

    private fun transcriptionButtonClicked() {
        lifecycleScope.launch(dispatcherProvider.io()) {
            try {
                setData(true)
                trySetupPlayer()
            } catch (e: Exception) {
                AppLogger.logE("Play word failure", e)
            } finally {
                setData(false)
            }
        }
    }

    private fun trySetupPlayer() {
        mediaPlayer.apply {
            reset()
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(
                "https:${translation?.meanings?.firstOrNull()?.soundUrl}"
            )
            prepare()
            start()
        }
    }

    override fun onPause() {
        super.onPause()

        mediaPlayer.release()
    }

    companion object {
        fun newInstance(translation: TranslationDto) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TRANSLATION_EXTRA, translation)
                }
            }
    }
}
