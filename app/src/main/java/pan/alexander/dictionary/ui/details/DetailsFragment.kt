package pan.alexander.dictionary.ui.details

import android.media.AudioAttributes
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.DetailsFragmentBinding
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.ui.details.adapter.DetailsAdapter
import pan.alexander.dictionary.utils.coroutines.DispatcherProvider
import pan.alexander.dictionary.utils.logger.AppLogger
import java.lang.Exception

private const val TRANSLATION_EXTRA = "pan.alexander.dictionary.TRANSLATION_EXTRA"

class DetailsFragment : Fragment(R.layout.details_fragment) {

    private val binding by viewBinding(DetailsFragmentBinding::bind)
    private var translation: TranslationDto? = null

    private val adapter by lazy { DetailsAdapter(lifecycleScope, get()) }
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

        initRecycler()

        initOnTranscriptionButtonClickListener()

        setData()
    }

    private fun initRecycler() {
        binding.descriptionScreenRecycler.adapter = adapter.apply {
            setHasStableIds(true)
        }
    }

    private fun setData() {
        translation?.let { translation ->
            binding.descriptionScreenWordTextview.text = translation.word
            binding.descriptionScreenTranscriptionButton.text =
                translation.meanings.firstOrNull()?.transcription ?: ""
            adapter.setData(translation)
        }
    }

    private fun initOnTranscriptionButtonClickListener() {
        binding.descriptionScreenTranscriptionButton.setOnClickListener {
            lifecycleScope.launch(dispatcherProvider.io()) {
                try {
                    trySetupPlayer()
                } catch (e: Exception) {
                    AppLogger.logE("Play word failure", e)
                }
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
