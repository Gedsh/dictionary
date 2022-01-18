package pan.alexander.core_ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T>(
    @LayoutRes layout: Int
) : Fragment(layout) {

    abstract val translationViewModel: BaseViewModel<T>

    abstract fun setState(viewState: T)

}
