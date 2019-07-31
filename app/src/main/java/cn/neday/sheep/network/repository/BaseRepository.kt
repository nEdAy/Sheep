package cn.neday.sheep.network.repository

import cn.neday.sheep.model.Response

abstract class BaseRepository {

    suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Response<T> {
        return call.invoke()
    }
}