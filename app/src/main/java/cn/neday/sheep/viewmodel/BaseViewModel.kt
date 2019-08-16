package cn.neday.sheep.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import cn.neday.sheep.SingleLiveEvent

/**
 * ViewModel基类
 *
 * @author nEdAy
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val errMsg = SingleLiveEvent<String>()

    val onComplete = SingleLiveEvent<Unit>()
}