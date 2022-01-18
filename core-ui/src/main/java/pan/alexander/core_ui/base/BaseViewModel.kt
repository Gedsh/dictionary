package pan.alexander.core_ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<T> : ViewModel() {

    protected val viewStateMutableLiveData = MutableLiveData<T>()

    fun getViewStateLiveData(): LiveData<T> =
        viewStateMutableLiveData
}
