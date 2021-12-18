package pan.alexander.dictionary.ui.translation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import pan.alexander.core_utils.Constants.WORD_REGEX
import pan.alexander.dictionary.databinding.SearchDialogFragmentBinding

class SearchDialogFragment : BottomSheetDialogFragment() {

    private var _binding: SearchDialogFragmentBinding? = null
    private val binding get() = _binding!!
    private var onSearchClickListener: OnSearchClickListener? = null

    fun setOnSearchClickListener(listener: OnSearchClickListener) {
        onSearchClickListener = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = SearchDialogFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setWordIfAvailable()
        initOnSearchClickListener()
        initOnSearchActionListener()
        initOnTextChangedListener()
        initOnClearClickListener()
    }

    private fun setWordIfAvailable() {
        arguments?.getString(WORD_EXTRA)?.let {
            binding.searchEditText.setText(it, TextView.BufferType.EDITABLE)
        }
    }

    private fun initOnSearchClickListener() {
        binding.searchButtonTextview.setOnClickListener {
            searchWord()
        }
    }

    private fun initOnSearchActionListener() {
        binding.searchEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchWord()
                true
            } else {
                false
            }
        }
    }

    private fun searchWord() {
        onSearchClickListener?.onClick(binding.searchEditText.text.toString())
        dismiss()
    }

    private fun initOnTextChangedListener() {
        binding.searchEditText.addTextChangedListener {
            binding.searchButtonTextview.isEnabled = it != null && it.toString().matches(WORD_REGEX)
        }
    }

    private fun initOnClearClickListener() {
        binding.clearButtonTextview.setOnClickListener {
            binding.searchEditText.setText("")
            binding.searchButtonTextview.isEnabled = false
        }
    }

    override fun onDestroyView() {
        onSearchClickListener = null
        super.onDestroyView()
    }

    interface OnSearchClickListener {

        fun onClick(searchWord: String)
    }

    companion object {
        private const val WORD_EXTRA = "pan.alexander.dictionary.WORD_EXTRA"

        fun newInstance(word: String?): SearchDialogFragment {
            return SearchDialogFragment().also {
                it.arguments = Bundle().apply {
                    putString(WORD_EXTRA, word)
                }
            }
        }
    }
}
