package cn.neday.base.network.repository

import cn.neday.base.model.Response

abstract class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Response<T> {
        return call.invoke()
    }
}