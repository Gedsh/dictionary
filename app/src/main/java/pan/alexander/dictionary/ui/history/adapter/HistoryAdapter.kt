package pan.alexander.dictionary.ui.history.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.HistoryRecyclerItemBinding

class HistoryAdapter(
    context: Context,
    private var onListItemClickListener: (listItemData: String) -> Unit,
    private var onItemSwipedOutListener: (listItemData: String) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.HistoryItemViewHolder>(), ItemTouchHelperAdapter {

    private val differ by lazy { AsyncListDiffer(this, diffCallback) }

    fun setWords(data: List<String>) {
        differ.submitList(data)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryItemViewHolder {
        return HistoryItemViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.history_recycler_item, parent, false)
        ).apply {
            itemView.setOnClickListener(this)
        }
    }

    override fun onBindViewHolder(holder: HistoryItemViewHolder, position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            holder.bind(differ.currentList[position])
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val itemBackgroundPurple = ContextCompat.getColor(context, R.color.color_purple_10)

    inner class HistoryItemViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view), View.OnClickListener {
        fun bind(word: String) {
            val binding = HistoryRecyclerItemBinding.bind(itemView)
            val position = layoutPosition
            if (position != RecyclerView.NO_POSITION) {
                binding.historyWordItemTextview.text = word
                setBackgroundColor(binding, position)
            }
        }

        private fun setBackgroundColor(binding: HistoryRecyclerItemBinding, position: Int) {
            if (position % 2 == 0) {
                binding.historyWordItemCard.setCardBackgroundColor(Color.WHITE)
            } else {
                binding.historyWordItemCard.setCardBackgroundColor(itemBackgroundPurple)
            }

        }

        override fun onClick(v: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                onListItemClickListener(differ.currentList[position])
            }
        }
    }

    private val diffCallback = object : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            false

    }

    override fun onItemDismiss(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            onItemSwipedOutListener(differ.currentList[position])
        }
    }
}
