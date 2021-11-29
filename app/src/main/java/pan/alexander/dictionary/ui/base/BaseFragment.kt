package pan.alexander.dictionary.ui.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment

abstract class BaseFragment<T>(
    @LayoutRes layout: Int
) : Fragment(layout), BaseView<T> {

    private lateinit var presenter: BasePresenter<T>

    protected abstract fun getPresenter(): BasePresenter<T>

    abstract override fun setState(viewState: T)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = getPresenter()
    }

    override fun onStart() {
        super.onStart()
        presenter.onAttachView(this)
    }

    override fun onStop() {
        super.onStop()
        presenter.onDetachView()
    }
}
