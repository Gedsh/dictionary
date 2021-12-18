package pan.alexander.dictionary.ui.history

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.SimpleItemAnimator
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import pan.alexander.core_ui.base.BaseFragment
import pan.alexander.core_ui.delegates.replace
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.HistoryFragmentBinding
import pan.alexander.dictionary.ui.history.adapter.HistoryAdapter
import pan.alexander.dictionary.ui.history.adapter.ItemTouchHelperCallback
import pan.alexander.dictionary.ui.translation.TranslationFragment

@ExperimentalCoroutinesApi
class HistoryFragment : BaseFragment<List<String>>(
    R.layout.history_fragment
) {

    override val uiViewModel by viewModel<HistoryViewModel>()

    private val binding by viewBinding(HistoryFragmentBinding::bind)

    private val adapter by lazy {
        HistoryAdapter(requireContext(), ::onHistoryItemClick, ::onHistoryItemSwiped)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        uiViewModel.getHistory()

        initRecycler()

        initSearchButtonOnClickListener()

        observeHistoryChanges()
    }

    private fun setTitle() {
        activity?.setTitle(R.string.history)
    }

    private fun initRecycler() {
        (binding.historyRecyclerview.itemAnimator as SimpleItemAnimator)
            .supportsChangeAnimations = false
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).apply {
            attachToRecyclerView(binding.historyRecyclerview)
        }
        binding.historyRecyclerview.adapter = adapter
    }

    private fun onHistoryItemClick(word: String) {
        showTranslationsScreen(TranslationFragment.newInstance(word))
    }

    private fun onHistoryItemSwiped(word: String) {
        uiViewModel.deleteWordFromHistory(word)
    }

    private fun initSearchButtonOnClickListener() {
        binding.searchFab.setOnClickListener {
            showTranslationsScreen(TranslationFragment.newInstance(initSearch = true))
        }
    }

    private fun observeHistoryChanges() {
        uiViewModel.getViewStateLiveData().observe(viewLifecycleOwner) {
            setState(it)
        }
    }

    override fun setState(viewState: List<String>) =
        adapter.setWords(viewState)

    private fun showTranslationsScreen(fragment: TranslationFragment) {
        val transaction by replace(fragment)
        transaction.commit()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.history_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_clear_history) {
            uiViewModel.clearHistory()
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance() = HistoryFragment()
    }

}
