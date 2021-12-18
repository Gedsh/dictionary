package pan.alexander.dictionary.ui.details.adapter

import android.view.View
import android.widget.ImageView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.DetailsHeaderRecyclerItemBinding
import pan.alexander.dictionary.databinding.DetailsRecyclerItemBinding
import pan.alexander.dictionary.databinding.DetailsTranscriptionRecyclerItemBinding

internal sealed class DetailsAdapterRecyclerItem

internal data class DetailsAdapterHeaderItem(
    val word: String
) : DetailsAdapterRecyclerItem()

internal data class DetailsAdapterTranscriptionItem(
    val transcription: String,
    val loading: Boolean
) : DetailsAdapterRecyclerItem()

internal data class DetailsAdapterDetailsItem(
    val translation: String,
    val imgUrl: String
) : DetailsAdapterRecyclerItem()

internal fun detailsAdapterHeaderDelegate() =
    adapterDelegateViewBinding<DetailsAdapterHeaderItem, DetailsAdapterRecyclerItem, DetailsHeaderRecyclerItemBinding>(
        { inflater, root ->
            DetailsHeaderRecyclerItemBinding.inflate(inflater, root, false)
        }
    ) {
        bind {
            binding.detailsScreenWordTextview.text = item.word
        }
    }

internal fun detailsAdapterTranscriptionDelegate(onClickListener: () -> Unit) =
    adapterDelegateViewBinding<DetailsAdapterTranscriptionItem, DetailsAdapterRecyclerItem, DetailsTranscriptionRecyclerItemBinding>(
        { inflater, root ->
            DetailsTranscriptionRecyclerItemBinding.inflate(inflater, root, false)
        }
    ) {
        binding.descriptionScreenTranscriptionButton.setOnClickListener {
            onClickListener()
        }
        bind {
            binding.descriptionScreenTranscriptionButton.text = item.transcription
            if (item.loading) {
                binding.descriptionScreenLoadingProgress.visibility = View.VISIBLE
            } else {
                binding.descriptionScreenLoadingProgress.visibility = View.GONE
            }
        }
    }

internal fun detailsAdapterDetailsDelegate(
    imageLoader: ImageLoader,
    coroutineScope: CoroutineScope
) =
    adapterDelegateViewBinding<DetailsAdapterDetailsItem, DetailsAdapterRecyclerItem, DetailsRecyclerItemBinding>(
        { inflater, root ->
            DetailsRecyclerItemBinding.inflate(inflater, root, false)
        }
    ) {
        bind {
            binding.detailsRecyclerTranslationTextview.text = item.translation
            coroutineScope.launch { loadImage(imageLoader, binding, item.imgUrl) }
        }
    }

private suspend fun loadImage(
    imageLoader: ImageLoader,
    binding: DetailsRecyclerItemBinding,
    imageUrl: String
) {
    val request = ImageRequest.Builder(binding.root.context)
        .data("https:$imageUrl")
        .target(
            onStart = {
                binding.detailsRecyclerWordImageview.scaleType = ImageView.ScaleType.CENTER
                binding.detailsRecyclerWordImageview.setImageResource(R.drawable.ic_loading_vector)
            },
            onSuccess = {
                binding.detailsRecyclerWordImageview.scaleType =
                    ImageView.ScaleType.FIT_CENTER
                binding.detailsRecyclerWordImageview.setImageDrawable(it)
            },
            onError = {
                binding.detailsRecyclerWordImageview.scaleType = ImageView.ScaleType.CENTER
                binding.detailsRecyclerWordImageview.setImageResource(R.drawable.ic_load_error_vector)
            }
        )
        .transformations(
            CircleCropTransformation(),
        )
        .build()

    imageLoader.execute(request)
}
