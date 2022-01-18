package pan.alexander.dictionary.ui.details.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.DetailsRecyclerItemBinding
import pan.alexander.dictionary.domain.dto.TranslationDto

class DetailsAdapter(
    private val coroutineScope: CoroutineScope,
    private val imageLoader: ImageLoader
) : RecyclerView.Adapter<DetailsAdapter.RecyclerItemViewHolder>() {

    private val translationDetails = mutableListOf<TranslationDetailItem>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(translationDto: TranslationDto) {
        translationDetails.apply {
            clear()
            addAll(
                translationDto.meanings.map {
                    TranslationDetailItem(it.id, it.translation, it.imgUrl)
                }
            )
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder =
        RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.details_recycler_item, parent, false)
        )

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        coroutineScope.launch { holder.bind(translationDetails[position]) }
    }

    override fun getItemCount(): Int = translationDetails.size

    override fun getItemId(position: Int): Long = translationDetails[position].id

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        suspend fun bind(translation: TranslationDetailItem) {
            val binding = DetailsRecyclerItemBinding.bind(itemView)
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.detailsRecyclerTranslationTextview.text = translation.translation

                loadImage(binding, translation.imageUrl)
            }
        }

        private suspend fun loadImage(binding: DetailsRecyclerItemBinding, imageUrl: String) {
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
    }

}
