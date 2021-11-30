package pan.alexander.dictionary.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

abstract class BaseViewModel<T> : ViewModel() {
    private val disposables = CompositeDisposable()

    protected val viewStateMutableLiveData = MutableLiveData<T>()

    fun getViewStateLiveData(): LiveData<T> =
        viewStateMutableLiveData

    fun Disposable.autoDispose() {
        disposables.add(this)
    }

    override fun onCleared() {
        disposables.clear()
        super.onCleared()
    }
}
