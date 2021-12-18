package pan.alexander.dictionary.ui.translation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.TranslationRecyclerItemBinding
import pan.alexander.dictionary.domain.dto.TranslationDto

class TranslationAdapter(
    private var onListItemClickListener: (listItemData: TranslationDto) -> Unit
) : RecyclerView.Adapter<TranslationAdapter.RecyclerItemViewHolder>() {

    private val translations = mutableListOf<TranslationDto>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<TranslationDto>) {
        translations.apply {
            clear()
            addAll(data)
        }
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        return RecyclerItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.translation_recycler_item, parent, false)
        ).apply {
            itemView.setOnClickListener(this)
        }
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(translations[position])
        }
    }

    override fun getItemCount(): Int {
        return translations.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {

        fun bind(translation: TranslationDto) {
            val binding = TranslationRecyclerItemBinding.bind(itemView)
            binding.headerTextviewTranslationItem.text = translation.word
            binding.descriptionTextviewTranslationItem.text =
                translation.meanings.joinToString("\n") { "\u00B7 ${it.translation}" }
        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onListItemClickListener(translations[position])
            }
        }
    }
}
