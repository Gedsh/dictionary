package pan.alexander.dictionary.ui.translation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.TranslationRecyclerItemBinding
import pan.alexander.dictionary.domain.entities.Translation

class TranslationAdapter(
    private var onListItemClickListener: OnListItemClickListener
) : RecyclerView.Adapter<TranslationAdapter.RecyclerItemViewHolder>() {

    private val translations = mutableListOf<Translation>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Translation>) {
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
        )
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(translations[position])
    }

    override fun getItemCount(): Int {
        return translations.size
    }

    inner class RecyclerItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        fun bind(translation: Translation) {
            val binding = TranslationRecyclerItemBinding.bind(itemView)
            if (layoutPosition != RecyclerView.NO_POSITION) {
                binding.headerTextviewTranslationItem.text = translation.text
                binding.descriptionTextviewTranslationItem.text =
                    translation.meanings.joinToString("\n") { it.translation }
                itemView.setOnClickListener { openInNewWindow(translation) }
            }
        }
    }

    private fun openInNewWindow(listItemData: Translation) {
        onListItemClickListener.onItemClick(listItemData)
    }

    interface OnListItemClickListener {
        fun onItemClick(data: Translation)
    }
}
