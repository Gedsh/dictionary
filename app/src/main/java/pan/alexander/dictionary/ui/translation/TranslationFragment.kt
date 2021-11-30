package pan.alexander.dictionary.ui.translation

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import pan.alexander.dictionary.R
import pan.alexander.dictionary.databinding.TranslationFragmentBinding
import pan.alexander.dictionary.domain.entities.Translation
import pan.alexander.dictionary.ui.base.BaseFragment
import pan.alexander.dictionary.ui.translation.adapter.TranslationAdapter
import pan.alexander.dictionary.utils.app
import javax.inject.Inject

class TranslationFragment : BaseFragment<TranslationViewState>(
    R.layout.translation_fragment
) {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(TranslationViewModel::class.java)
    }

    private val binding by viewBinding(TranslationFragmentBinding::bind)

    private val onListItemClickListener: TranslationAdapter.OnListItemClickListener =
        object : TranslationAdapter.OnListItemClickListener {
            override fun onItemClick(data: Translation) {
                Toast.makeText(this@TranslationFragment.context, data.text, Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val adapter: TranslationAdapter by lazy { TranslationAdapter(onListItemClickListener) }

    override fun onCreate(savedInstanceState: Bundle?) {
        requireContext().app.daggerComponent.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecycler()

        initSearchFabClickListener()

        initReloadButtonClickListener()

        observeViewStateChanges()
    }

    private fun initRecycler() {
        binding.translationRecyclerview.layoutManager =
            LinearLayoutManager(requireContext())
        binding.translationRecyclerview.adapter = adapter
    }

    private fun initSearchFabClickListener() {
        binding.searchFab.setOnClickListener {
            SearchDialogFragment.newInstance().also {
                it.setOnSearchClickListener(object :
                    SearchDialogFragment.OnSearchClickListener {
                    override fun onClick(searchWord: String) {
                        viewModel.getTranslations(searchWord)
                    }
                })
                it.show(parentFragmentManager, BOTTOM_SHEET_FRAGMENT_DIALOG_TAG)
            }
        }
    }

    private fun initReloadButtonClickListener() {
        binding.reloadButton.setOnClickListener {
            viewModel.getTranslations()
        }
    }

    private fun observeViewStateChanges() {
        viewModel.getViewStateLiveData().observe(viewLifecycleOwner) {
            setState(it)
        }
    }

    override fun setState(viewState: TranslationViewState) {
        when (viewState) {
            is TranslationViewState.Success -> {
                val translations = viewState.translations
                if (translations.isEmpty()) {
                    showErrorScreen(getString(R.string.empty_server_response_on_success))
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
    }

    private fun showViewLoading() {
        binding.translationRecyclerview.visibility = View.GONE
        binding.searchFab.visibility = View.GONE
        binding.loadingFrameLayout.visibility = View.VISIBLE
        binding.errorLinearLayout.visibility = View.GONE
    }

    private fun showViewError() {
        binding.translationRecyclerview.visibility = View.GONE
        binding.searchFab.visibility = View.VISIBLE
        binding.loadingFrameLayout.visibility = View.GONE
        binding.errorLinearLayout.visibility = View.VISIBLE
    }

    companion object {
        private const val BOTTOM_SHEET_FRAGMENT_DIALOG_TAG =
            "74a54328-5d62-46bf-ab6b-cbf5fgt0-092395"

        fun newInstance() = TranslationFragment()
    }
}