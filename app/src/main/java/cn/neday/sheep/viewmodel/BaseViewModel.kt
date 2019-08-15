package cn.neday.sheep.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * ViewModel基类
 *
 * @author nEdAy
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val errMsg: MutableLiveData<String> = MutableLiveData()
}