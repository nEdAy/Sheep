package cn.neday.sheep.network

import androidx.lifecycle.viewModelScope
import cn.neday.sheep.R
import cn.neday.sheep.viewmodel.BaseViewModel
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils
import kotlinx.coroutines.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException

/**
 * execute in main thread
 * @param start doSomeThing first
 */
infix fun BaseViewModel.start(start: (() -> Unit)): BaseViewModel {
    viewModelScope.launch(Dispatchers.Main) {
        if (NetworkUtils.isConnected()) {
            start()
        } else {
            errMsg.value = StringUtils.getString(R.string.network_tips)
        }
    }
    return this
}

/**
 * execute in io thread pool
 * @param loader http request
 */
infix fun <T> BaseViewModel.requestAsync(loader: suspend () -> T): Deferred<T> {
    return viewModelScope.async(Dispatchers.IO, start = CoroutineStart.LAZY) {
        loader()
    }
}

/**
 * execute in main thread
 * @param onSuccess callback for onSuccess
 * @param onError callback for onError
 * @param onComplete callback for onComplete
 */
fun <T> Deferred<T>.then(
    onSuccess: suspend (T) -> Unit,
    onError: suspend (String) -> Unit,
    onComplete: (() -> Unit)? = null
): Job {
    return GlobalScope.launch(context = Dispatchers.Main) {
        try {
            val result = this@then.await()
            onSuccess(result)
//            if (response.code != 0) {
//                errorBlock()
//            } else {
//                successBlock()
//            }
        } catch (e: Exception) {
//            if (e !is CancellationException) {
//
//            }
            e.printStackTrace()
            when (e) {
                is UnknownHostException -> onError("network is error!")
                is TimeoutException -> onError("network is error!")
                is SocketTimeoutException -> onError("network is error!")
                else -> onError("network is error!")
            }
        } finally {
            onComplete?.invoke()
        }
    }
}