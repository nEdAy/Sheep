package cn.neday.sheep.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cn.neday.sheep.R
import cn.neday.sheep.model.Response
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.StringUtils.getString
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel(), LifecycleObserver {

    val errMsg: MutableLiveData<String> = MutableLiveData()

    fun launch(tryBlock: suspend CoroutineScope.() -> Unit) {
        launchOnUI {
            tryCatch(tryBlock, {}, {}, true)
        }
    }

    private fun launchOnUI(block: suspend CoroutineScope.() -> Unit) {
        viewModelScope.launch {
            if (NetworkUtils.isConnected()) {
                //TODO : 待测试 断网卡顿
                block()
            } else {
                errMsg.value = getString(R.string.network_tips)
            }
        }
    }

    private suspend fun tryCatch(
        tryBlock: suspend CoroutineScope.() -> Unit,
        catchBlock: suspend CoroutineScope.(Throwable) -> Unit,
        finallyBlock: suspend CoroutineScope.() -> Unit,
        handleCancellationExceptionManually: Boolean = false
    ) {
        coroutineScope {
            try {
                tryBlock()
            } catch (exception: Exception) {
                if (exception !is CancellationException || handleCancellationExceptionManually) {
                    catchBlock(exception)
                } else {
                    throw exception
                }
            } finally {
                finallyBlock()
            }
        }
    }

    suspend fun executeResponse(
        response: Response<Any>, successBlock: suspend CoroutineScope.() -> Unit,
        errorBlock: suspend CoroutineScope.() -> Unit
    ) {
        coroutineScope {
            if (response.code != 0) {
                errorBlock()
            } else {
                successBlock()
            }
        }
    }
}