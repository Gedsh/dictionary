package pan.alexander.dictionary.ui.base

import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BasePresenter<T> {
    protected var currentView: BaseView<T>? = null
    private val disposables = CompositeDisposable()

    fun onAttachView(view: BaseView<T>) {
        currentView = view
    }

    fun onDetachView() {
        disposables.clear()
        currentView = null
    }

    fun Disposable.autoDispose() {
        disposables.add(this)
    }
}
