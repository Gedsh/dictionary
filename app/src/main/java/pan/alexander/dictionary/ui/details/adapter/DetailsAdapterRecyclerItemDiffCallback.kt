package pan.alexander.dictionary.ui.details.adapter

import androidx.recyclerview.widget.DiffUtil

internal class DetailsAdapterRecyclerItemDiffCallback :
    DiffUtil.ItemCallback<DetailsAdapterRecyclerItem>() {

    override fun areItemsTheSame(
        oldItem: DetailsAdapterRecyclerItem,
        newItem: DetailsAdapterRecyclerItem
    ): Boolean = oldItem.javaClass == newItem.javaClass

    override fun areContentsTheSame(
        oldItem: DetailsAdapterRecyclerItem,
        newItem: DetailsAdapterRecyclerItem
    ): Boolean =
        when (oldItem) {
            is DetailsAdapterHeaderItem -> {
                when (newItem) {
                    is DetailsAdapterHeaderItem -> oldItem == newItem
                    is DetailsAdapterTranscriptionItem -> false
                    is DetailsAdapterDetailsItem -> false
                }
            }
            is DetailsAdapterTranscriptionItem -> {
                when (newItem) {
                    is DetailsAdapterHeaderItem -> false
                    is DetailsAdapterTranscriptionItem -> oldItem == newItem
                    is DetailsAdapterDetailsItem -> false
                }
            }
            is DetailsAdapterDetailsItem -> {
                when (newItem) {
                    is DetailsAdapterHeaderItem -> false
                    is DetailsAdapterTranscriptionItem -> false
                    is DetailsAdapterDetailsItem -> oldItem == newItem
                }
            }
        }

}
