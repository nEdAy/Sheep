package cn.neday.sheep.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.ViewModel
import cn.neday.sheep.SingleLiveEvent
import com.tencent.mmkv.MMKV

/**
 * ViewModel基类
 *
 * @author nEdAy
 */
open class BaseViewModel : ViewModel(), LifecycleObserver {

    val kv: MMKV = MMKV.defaultMMKV()

    val errMsg = SingleLiveEvent<String>()
    val onComplete = SingleLiveEvent<Unit>()
}