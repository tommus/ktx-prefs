package co.windly.ktxprefs.sample.kotlin.presentation.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MenuViewModel : ViewModel() {

  //region Coroutine

  private val _coroutineClicked: MutableLiveData<Any> =
    MutableLiveData()

  val coroutineClicked: LiveData<Any> =
    _coroutineClicked

  fun onCoroutineClicked() =
    _coroutineClicked.postValue(null)

  //endregion

  //region Reactive

  private val _reactiveClicked: MutableLiveData<Any> =
    MutableLiveData()

  val reactiveClicked: LiveData<Any> =
    _reactiveClicked

  fun onReactiveClicked() =
    _reactiveClicked.postValue(null)

  //endregion

  //region Simple

  private val _simpleClicked: MutableLiveData<Any> =
    MutableLiveData()

  val simpleClicked: LiveData<Any> =
    _simpleClicked

  fun onSimpleClicked() =
    _simpleClicked.postValue(null)

  //endregion
}
