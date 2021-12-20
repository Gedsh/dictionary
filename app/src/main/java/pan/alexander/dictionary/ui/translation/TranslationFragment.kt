package pan.alexander.dictionary.ui.translation

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import pan.alexander.core_ui.base.BaseFragment
import pan.alexander.core_ui.delegates.replace
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.TranslationFragmentBinding
import pan.alexander.dictionary.domain.dto.TranslationDto
import pan.alexander.dictionary.ui.details.DetailsFragment
import pan.alexander.dictionary.ui.translation.adapter.TranslationAdapter

@ExperimentalCoroutinesApi
class TranslationFragment : BaseFragment<TranslationViewState>(
    R.layout.translation_fragment
) {

    override val uiViewModel by viewModel<TranslationViewModel>()

    private val binding by viewBinding(TranslationFragmentBinding::bind)

    private val adapter: TranslationAdapter by lazy { TranslationAdapter(::onItemClick) }

    private val searchClickListener by lazy {
        object : SearchDialogFragment.OnSearchClickListener {
            override fun onClick(searchWord: String) {
                uiViewModel.getTranslations(searchWord)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()

        checkSearchFragment()

        savedInstanceState ?: checkArguments()

        initRecycler()

        initSearchFabClickListener()

        initReloadButtonClickListener()

        observeViewStateChanges()
    }

    private fun setTitle() {
        activity?.setTitle(R.string.translations)
    }

    private fun checkSearchFragment() {
        val searchDialogFragment = parentFragmentManager
            .findFragmentByTag(BOTTOM_SHEET_FRAGMENT_DIALOG_TAG) as? SearchDialogFragment
        searchDialogFragment?.setOnSearchClickListener(searchClickListener)
    }

    private fun checkArguments() {
        if (uiViewModel.getViewStateLiveData().value == null) {
            arguments?.getString(WORD_EXTRA)?.let {
                uiViewModel.getTranslations(it)
            } ?: arguments?.getBoolean(INIT_SEARCH_EXTRA)?.let {
                initSearchFragment()
            }
        }
    }

    private fun initRecycler() {
        binding.translationRecyclerview.layoutManager =
            LinearLayoutManager(requireContext())
        binding.translationRecyclerview.adapter = adapter
    }

    private fun initSearchFabClickListener() {
        binding.searchFab.setOnClickListener {
            initSearchFragment()
        }
    }

    private fun initSearchFragment(word: String? = null) {

        val searchDialogFragment = parentFragmentManager
            .findFragmentByTag(BOTTOM_SHEET_FRAGMENT_DIALOG_TAG) as? SearchDialogFragment
        if (searchDialogFragment != null) {
            searchDialogFragment.setOnSearchClickListener(searchClickListener)
        } else {
            SearchDialogFragment.newInstance(word).also {
                it.setOnSearchClickListener(searchClickListener)
                it.show(parentFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
            }
        }

    }

    private fun initReloadButtonClickListener() {
        binding.reloadButton.setOnClickListener {
            uiViewModel.getTranslations()
        }
    }

    private fun onItemClick(data: TranslationDto) {
        val transaction by replace(DetailsFragment.newInstance(data))
        transaction.commit()
    }

    private fun observeViewStateChanges() {
        uiViewModel.getViewStateLiveData().observe(viewLifecycleOwner) {
            setState(it)
        }
    }

    override fun setState(viewState: TranslationViewState) {
        when (viewState) {
            is TranslationViewState.Success -> {
                val translations = viewState.translations
                if (translations.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
                    initSearchFragment(uiViewModel.getLastWord())
                } else {
                    showViewSuccess()
                    adapter.setData(translations)
                }
            }
            is TranslationViewState.Loading -> {
                showViewLoading()
                if (viewState.progress != null) {
                    binding.progressBarHorizontal.visibility = View.VISIBLE
                    binding.progressBarRound.visibility = View.GONE
                    binding.progressBarHorizontal.progress = viewState.progress
                } else {
                    binding.progressBarHorizontal.visibility = View.GONE
                    binding.progressBarRound.visibility = View.VISIBLE
                }
            }
            is TranslationViewState.Error -> {
                showErrorScreen(viewState.error.message)
            }
            is TranslationViewState.NoConnection -> {
                showErrorScreen(getString(R.string.device_is_offline))
            }
        }
    }

    private fun showErrorScreen(error: String?) {
        showViewError()
        binding.errorTextview.text = error ?: getString(R.string.undefined_error)
    }

    private fun showViewSuccess() {
        binding.translationRecyclerview.visibility = View.VISIBLE
        binding.searchFab.visibility = View.VISIBLE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.GONE
        binding.noTranslationsLayout.visibility = View.GONE
    }

    private fun showViewLoading() {
        binding.translationRecyclerview.visibility = View.GONE
        binding.searchFab.visibility = View.GONE
        binding.loadingFrameLayout.visibility = View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
        binding.noTranslationsLayout.visibility = View.GONE
    }

    private fun showViewError() {
        binding.translationRecyclerview.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.VISIBLE
        binding.noTranslationsLayout.visibility = View.GONE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "pan.alexander.dictionary.BOTTOM_SHEET_FRAGMENT_DIALOG_TAG"
        private const val WORD_EXTRA = "pan.alexander.dictionary.WORD_EXTRA"
        private const val INIT_SEARCH_EXTRA = "pan.alexander.dictionary.INIT_SEARCH_EXTRA"

        fun newInstance(word: String? = null, initSearch: Boolean? = null) =
            TranslationFragment().also {
                it.arguments = Bundle().apply {
                    word?.let { word -> putString(WORD_EXTRA, word) }
                    initSearch?.let { initSearch -> putBoolean(INIT_SEARCH_EXTRA, initSearch) }

                }
            }
    }
}
