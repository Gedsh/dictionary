package pan.alexander.dictionary.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T>(
    @LayoutRes layout: Int
) : Fragment(layout) {

    abstract val viewModel: BaseViewModel<T>

    abstract fun setState(viewState: T)

}
